#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <signal.h>
#include <pthread.h>

#include "Socket_lib.h"

#define PORT_CHCK 8080

void * ThreadListener_CIMP(void*);

char* getMessage();

void HandlerSigInt(int);

void HandlerSigTerm(int);

int hSocket;

pthread_t handleListener_CIMP;

int numMessage = 0;

char** MessageList;

pthread_mutex_t MutexListener;
pthread_cond_t CondListener;

int main()
{
    pthread_mutex_init(&MutexListener, NULL);
    pthread_cond_init(&CondListener, NULL);


    char token1[30], token2[30], token3[30], Message[100], IP_Address[16], MessageRetour[50];
    int choice, end = 0, number, i;

    struct sigaction SignalInt;
    SignalInt.sa_handler = HandlerSigInt;
    sigemptyset(&SignalInt.sa_mask);
    SignalInt.sa_flags = 0;
    sigaction(SIGINT, &SignalInt, NULL);

    struct sigaction SignalTerm;
    SignalTerm.sa_handler = HandlerSigTerm;
    sigemptyset(&SignalTerm.sa_mask);
    SignalTerm.sa_flags = 0;
    sigaction(SIGTERM, &SignalTerm, NULL);

    while(end == 0)
    {
        printf("Veuillez entrer l addresse ip du serveur : ");
        scanf("%s", IP_Address);

        if((hSocket = SocketClient(IP_Address, PORT_CHCK)) == -1)
            perror("Socket client non creee :");
        else
            end = 1;
    }

    if(pthread_create(&handleListener_CIMP, NULL,(void*(*)(void*)) ThreadListener_CIMP, NULL) != 0)
    {
        perror("Erreur de creation du thread Listener ");
        return -1;
    }
    //On créé une liste de 20 messages//
    MessageList = (char**) malloc(20*sizeof(char*));
    for(int i = 0 ; i < 20; i++)
        MessageList[i] = (char*) malloc(100*sizeof(char));

    while(strcmp(Message, "LOGIN_OK") != 0)
    {
        printf("Veuillez entrer un nom d utilisateur : ");
        scanf("%s", token1);
        printf("Mot de passe : ");
        scanf("%s", token2);

        strcpy(Message, "OFFICER_LOGIN");
        strcat(Message, "#");
        strcat(Message, token1);
        strcat(Message, "#");
        strcat(Message, token2);

        if(Send(hSocket, Message, strlen(Message)+1) == -1)
        {
            printf("Erreur d'envoi du login\n");
            return -1;
        }
        printf("Demande de login envoyee\n");

        char* MessageRetourne = getMessage();
        printf("\nMessageRetourne = %s\n",MessageRetourne);

        strcpy(Message,MessageRetourne);

        printf("Reponse recue : %s\n", Message);
    }

    end = 0;

    while(end == 0)
    {
        printf("1) Log out\n2) Encoder bagages\n");
        scanf("%d", &choice);
        switch(choice)
        {
            case 1 :
            strcpy(Message, "OFFICER_LOGOUT");
            if(Send(hSocket, Message, strlen(Message)+1) == -1)
            {
                printf("Erreur d'envoi du logout\n");
                return -1;
            }
            printf("Deconnexion effectuee!\n");
            end = 1;
            sleep(1);
            break;

            case 2 :
            printf("VOL 362 POWDER-AIRLINES - Peshawar 6h30\n\nNumero de billet : ");
            scanf("%s", token1);
            printf("Nombre d accompagnants : ");
            scanf("%s", token2);
            printf("\n");

            strcpy(Message, "CHECK_TICKET");
            strcat(Message, "#");
            strcat(Message, token1);
            strcat(Message, "#");
            strcat(Message, token2);

            if(Send(hSocket, Message, strlen(Message)+1) == -1)
            {
                printf("Erreur d'envoi du CHECK_TICKET\n");
                return -1;
            }
            printf("Envoi de CHECK_TICKET effectuee!\n");

            strcpy(Message,getMessage());

            printf("Reponse recue : %s\n", Message);

            if(strcmp(Message, "TICKET_KO") == 0)
            {
                printf("Ticket non trouve !\n");
                break;
            }
            else if(strcmp(Message, "STOP_CHECKIN") == 0)
            {
                printf("*** FIN DES OPERATIONS DE CHECK-IN ! ***\n");
                break;
            }
            printf("Ticket trouve !\n");

            printf("\nNombre de bagages : ");
            scanf("%d", &number);

            strcpy(Message, "CHECK_LUGGAGE");
            strcat(Message, "#");
            strcat(Message, token1);

            for(i = 1; i <= number; i++)
            {
                printf("\nBagage numero %d :\nPoids : ", i);
                scanf("%s", token2);
                strcpy(token3, "0");
                while(strcmp(token3, "o") != 0 && strcmp(token3, "O") != 0 && strcmp(token3, "n") != 0 && strcmp(token3, "N") != 0)
                {
                    printf("Valise ? [o/n] ");
                    scanf("%s", token3);
                }

                printf("Envoi des infos sur les bagages\n");

                strcat(Message, "#");
                strcat(Message, token2);
                strcat(Message, "#");
                strcat(Message, token3);
            }
            strcat(Message, "#");
            strcat(Message, "END");

            if(Send(hSocket, Message, strlen(Message)+1) == -1)
            {
                printf("Erreur d'envoi du CHECK_LUGGAGE\n");
                return -1;
            }
            printf("Envoi de CHECK_LUGGAGE effectuee!\n");

            strcpy(Message,getMessage());

            printf("Reponse recue : %s\n", MessageRetour);

            if(strcmp(strtok(MessageRetour, "#"), "LUGGAGE_HEAVY") == 0)
            {
                strcpy(token2, strtok(NULL, "#"));
                strcpy(token3, strtok(NULL, "\0"));
                printf("Bagages trop lourds !\nPoids total : %s\nPrix a payer : %s\n", token2, token3);

                while(strcmp(MessageRetour, "o") != 0 && strcmp(MessageRetour, "O") != 0 && strcmp(MessageRetour, "n") != 0 && strcmp(MessageRetour, "N") != 0)
                {
                    printf("Paiement effectue ? [o/n] ");
                    scanf("%s", MessageRetour);
                }

                if(strcmp(MessageRetour, "o") == 0 || strcmp(MessageRetour, "O") == 0)
                {
                    strcpy(MessageRetour, "PAYMENT_DONE");
                    strcat(MessageRetour, Message + 13);

                    if(Send(hSocket, MessageRetour, strlen(MessageRetour)+1) == -1)
                    {
                        printf("Erreur d'envoi du PAYMENT_DONE\n");
                        return -1;
                    }
                    printf("Envoi de PAYMENT_DONE effectuee!\n");

                    strcpy(Message,getMessage());

                    printf("Reponse recue : %s\n", MessageRetour);
                }

                if(strcmp(MessageRetour, "LUGGAGE_OK") != 0)
                {
                    printf("Erreur d ajout du bagage !\n");
                    break;
                }
                printf("Bagage ajoute !\n\n");
            }

            break;
        }
    }

    pthread_mutex_unlock(&MutexListener);
    if(pthread_mutex_destroy(&MutexListener))
        printf("Destruction de MutexConnexion\n");

    pthread_cond_broadcast(&CondListener);
    if(pthread_cond_destroy(&CondListener) == 0)
        printf("Destruction de CondConnexion\n");

    if(pthread_cancel(handleListener_CIMP) == 0)
        printf("Arret de ThreadListener_CIMP\n");

    close(hSocket);

    return 0;
}

void* ThreadListener_CIMP(void*){

    pthread_setcanceltype(PTHREAD_CANCEL_ASYNCHRONOUS, NULL);

    while(1){
        
        char Message[100];
        if(Receive(hSocket,Message) == -1)
        {
            printf("Erreur de receive\n");
            return NULL;
        }

        if(strcmp(Message,"STOP_CHECKIN") != 0)
        {
            pthread_mutex_lock(&MutexListener);
            numMessage++;
            strcpy(MessageList[numMessage],Message);
            pthread_mutex_unlock(&MutexListener);
            pthread_cond_signal(&CondListener);
        }
        else{
            printf("STOP_CHECKIN reçu\nArrêt de l'application\n");
            kill(0,SIGTERM);
        }
    }
    pthread_exit(0);
}

char* getMessage(){
    pthread_mutex_lock(&MutexListener);
    char* MessageRetour = (char*) malloc(100*sizeof(char));
    while(numMessage == 0)
        pthread_cond_wait(&CondListener, &MutexListener);
    strcpy(MessageRetour,MessageList[numMessage]);
    strcpy(MessageList[numMessage],"");

    numMessage--;
    pthread_mutex_unlock(&MutexListener);

    return MessageRetour;
}

void HandlerSigInt(int sig)
{
    char Message[20];

    printf("Reception du signal SIGINT\n");

    strcpy(Message, "OFFICER_LOGOUT");
    if(Send(hSocket, Message, strlen(Message)+1) == -1)
    {
        printf("Erreur d'envoi du logout\n");
        close(hSocket);
        exit(-1);
    }

    pthread_mutex_unlock(&MutexListener);
    if(pthread_mutex_destroy(&MutexListener))
        printf("Destruction de MutexListener\n");

    pthread_cond_broadcast(&CondListener);
    if(pthread_cond_destroy(&CondListener) == 0)
        printf("Destruction de CondListener\n");
    printf("Deconnexion effectuee!\n");

    if(pthread_cancel(handleListener_CIMP) == 0)
        printf("Arret de ThreadListener_CIMP\n");
    sleep(1);

    close(hSocket);
    exit(0);
}

void HandlerSigTerm(int sig)
{
    printf("Reception du signal SIGTERM\n");

    pthread_mutex_unlock(&MutexListener);
    if(pthread_mutex_destroy(&MutexListener))
        printf("Destruction de MutexListener\n");

    pthread_cond_broadcast(&CondListener);
    if(pthread_cond_destroy(&CondListener) == 0)
        printf("Destruction de CondListener\n");
    printf("Deconnexion effectuee!\n");

    if(pthread_cancel(handleListener_CIMP) == 0)
        printf("Arret de ThreadListener_CIMP\n");
    sleep(1);

    close(hSocket);
    exit(0);
}