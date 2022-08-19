#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <errno.h>
#include <signal.h>

#include "Socket_lib.h"
#include "AccessBillBag.h"

#define NB_CLIENTS 5
#define PORT_CHCK 8080

void * ThreadServeur_CIMP(void*);
void * ThreadClient_CIMP(void*);

void HandlerSigInt(int);

int hSocketEcoute, hSocketDupliquee, hSocketService[NB_CLIENTS];
int indiceLecture, indiceEcriture;

pthread_t handleServeur_CIMP;
pthread_t handleClient_CIMP[NB_CLIENTS];

pthread_mutex_t MutexConnexion;
pthread_cond_t CondConnexion;

int main()
{
    pthread_mutex_init(&MutexConnexion, NULL);
    pthread_cond_init(&CondConnexion, NULL);

    if(pthread_create(&handleServeur_CIMP, NULL,(void*(*)(void*)) ThreadServeur_CIMP, NULL) != 0)
    {
        perror("Erreur de creation du thread serveur ");
        return -1;
    }
    printf("Thread serveur cree !\n");

    struct sigaction SignalInt;
    SignalInt.sa_handler = HandlerSigInt;
    sigemptyset(&SignalInt.sa_mask);
    SignalInt.sa_flags = 0;
    sigaction(SIGINT, &SignalInt, NULL);

    if(pthread_join(handleServeur_CIMP, NULL) != 0)
    {
        perror("Erreur de join du thread serveur ");
        return -1;
    }

    exit(0);
}

void * ThreadServeur_CIMP(void*)
{
    int i;
    char ipClient[16];

    pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

    for (i = 0; i < NB_CLIENTS; i++)
    {
        hSocketService[i] = -1;
        if(pthread_create(&handleClient_CIMP[i], NULL,(void*(*)(void*)) ThreadClient_CIMP, NULL) != 0)
        {
            printf("Erreur de creation du thread serveur %d : %d", i, errno);
            pthread_exit(0);
        }
        printf("Thread client %d cree !\n", i);
    }

    if((hSocketEcoute = SocketServeur(PORT_CHCK)) == -1)
    {
        perror("Socket serveur non creee ");
        pthread_exit(NULL);
    }

    while(1)
    {
        if((hSocketDupliquee = Accept(hSocketEcoute, ipClient)) == -1)
        {
            perror("Erreur fonction Accept ");
            pthread_exit(NULL);
        }

        pthread_mutex_lock(&MutexConnexion);

        for(i = 0; i < NB_CLIENTS; i++)
        {
            if(hSocketService[i] == -1)
            {
                hSocketService[i] = hSocketDupliquee;
                indiceEcriture++;
                break;
            }
        }

        pthread_mutex_unlock(&MutexConnexion);
        pthread_cond_signal(&CondConnexion);
    }

    printf("pthread exit serveur\n");
    pthread_exit(0);
}

void * ThreadClient_CIMP(void*)
{
    int num, end, endLuggage, poids, prix;
    char Message[100], protocol[20], token1[30], token2[30], token3[30], str[10];

    pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

    while(1)
    {
        pthread_mutex_lock(&MutexConnexion);

        while(indiceEcriture == indiceLecture)
            pthread_cond_wait(&CondConnexion, &MutexConnexion);

        num = indiceLecture;

        indiceLecture++;

        pthread_mutex_unlock(&MutexConnexion);

        printf("Attente de reception d un message\n");

        end = 0;
        while(end == 0)
        {
            if(Receive(hSocketService[num], Message) == -1)
            {
                printf("Erreur de receive\n");
                pthread_exit(NULL);
            }
            printf("%s\n", Message);

            strcpy(protocol, strtok(Message, "#"));

            if(strcmp(protocol, "OFFICER_LOGIN") == 0)
            {
                strcpy(token1, strtok(NULL, "#"));
                strcpy(token2, strtok(NULL, "@"));
                printf("Demande de connexion recue !\nlogin : %s password : %s\n", token1, token2);
                if(Connexion(token1, token2) == 0)
                {
                    printf("Connexion bonne\n");
                    strcpy(Message, "LOGIN_OK");
                }
                else
                {
                    printf("Connexion pas bonne\n");
                    strcpy(Message, "LOGIN_KO");
                }

                if(Send(hSocketService[num], Message, strlen(Message)+1) == -1)
                {
                    printf("Erreur d'envoi de la reponse\n");
                    pthread_exit(NULL);
                }
                printf("Reponse envoyee\n");
            }

            if(strcmp(protocol, "OFFICER_LOGOUT") == 0)
            {
                printf("Demande de deconnexion recue !\n");
                close(hSocketService[num]);
                hSocketService[num] = -1;
                indiceLecture--;
                indiceEcriture--;
                end = 1;
            }

            if(strcmp(protocol, "CHECK_TICKET") == 0)
            {
                strcpy(token1, strtok(NULL, "#"));
                strcpy(token2, strtok(NULL, "@"));
                printf("Demande de verification de ticket recue !\nNumero : %s Nombre d accompagnants : %s\n", token1, token2);
                if(CheckTicket(token1, token2) == 0)
                {
                    printf("Ticket trouve\n");
                    strcpy(Message, "TICKET_OK");
                }
                else
                {
                    printf("Ticket non trouve\n");
                    strcpy(Message, "TICKET_KO");
                }

                if(Send(hSocketService[num], Message, strlen(Message)+1) == -1)
                {
                    printf("Erreur d'envoi de la reponse\n");
                    pthread_exit(NULL);
                }
                printf("Reponse envoyee\n");
            }

            if(strcmp(protocol, "CHECK_LUGGAGE") == 0)
            {
                strcpy(token1, strtok(NULL, "#"));
                
                printf("Demande d ajout de bagages recue !\nPassager : %s\n", token1);

                endLuggage = 0;
                poids = 0;
                prix = 0;
                while(endLuggage == 0)
                {
                    strcpy(token2, strtok(NULL, "#"));
                    if(strcmp(token2, "END") == 0)
                    {
                        endLuggage = 1;
                    }
                    else
                    {
                        if(atoi(token2) > 20)
                            prix += (atoi(token2) - 20)*10;
                        poids += atoi(token2);
                        strcpy(token3, strtok(NULL, "#"));
                        printf("Poids : %s Valise : %s\n", token2, token3);
                    }
                }

                printf("Bagages trop lourds\n");
                strcpy(Message, "LUGGAGE_HEAVY");
                strcat(Message, "#");
                sprintf(str, "%d", poids);
                strcat(Message, str);
                strcat(Message, "#");
                sprintf(str, "%d", prix);
                strcat(Message, str);

                if(Send(hSocketService[num], Message, strlen(Message)+1) == -1)
                {
                    printf("Erreur d'envoi de la reponse\n");
                    pthread_exit(NULL);
                }
                printf("Reponse envoyee\n");
            }
            
            if(strcmp(protocol, "PAYMENT_DONE") == 0)
            {
                strcpy(token1, strtok(NULL, "#"));
                
                printf("Paiement accepte !\nPassager : %s\n", token1);

                endLuggage = 0;
                while(endLuggage == 0)
                {
                    strcpy(token2, strtok(NULL, "#"));
                    if(strcmp(token2, "END") == 0)
                    {
                        endLuggage = 1;
                    }
                    else
                    {
                        strcpy(token3, strtok(NULL, "#"));
                        printf("Poids : %s Valise : %s\n", token2, token3);
                        if(AddLuggage(token1, token2, token3) == 0)
                        {
                            printf("Bagage ajoute !\n");
                        }
                    }
                }

                strcpy(Message, "LUGGAGE_OK");
                if(Send(hSocketService[num], Message, strlen(Message)+1) == -1)
                {
                    printf("Erreur d'envoi de la reponse\n");
                    pthread_exit(NULL);
                }
                printf("Reponse envoyee\n");
            }
        }
    }

    pthread_exit(0);
}

void HandlerSigInt(int sig)
{
    int i;

    printf("Reception du signal SIGINT\n");

    if(pthread_cancel(handleServeur_CIMP) == 0)
        printf("Arret de ThreadServeur_CIMP\n");

    for(i = 0; i < NB_CLIENTS; i++)
    {
        if(pthread_cancel(handleClient_CIMP[i]) == 0)
            printf("Arret de ThreadClient_CIMP %d\n", i);
    }

    pthread_mutex_unlock(&MutexConnexion);
    if(pthread_mutex_destroy(&MutexConnexion))
        printf("Destruction de MutexConnexion\n");

    pthread_cond_broadcast(&CondConnexion);
    if(pthread_cond_destroy(&CondConnexion) == 0)
        printf("Destruction de CondConnexion\n");

    close(hSocketEcoute);
    close(hSocketDupliquee);

    for(i = 0; i < NB_CLIENTS; i++)
    {
        if(hSocketService[i] != -1)
            close(hSocketService[i]);
    }

    exit(0);
}