#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <signal.h>

#include "Socket_lib.h"

#define PORT_CHCK 8080

void HandlerSigInt(int);

int hSocket;

int main()
{
    char token1[30], token2[30], token3[30], Message[100], IP_Address[16], MessageRetour[50];
    int choice, end = 0, number, i;

    struct sigaction SignalInt;
    SignalInt.sa_handler = HandlerSigInt;
    sigemptyset(&SignalInt.sa_mask);
    SignalInt.sa_flags = 0;
    sigaction(SIGINT, &SignalInt, NULL);

    while(end == 0)
    {
        printf("Veuillez entrer l addresse ip du serveur : ");
        scanf("%s", IP_Address);

        if((hSocket = SocketClient(IP_Address, PORT_CHCK)) == -1)
            perror("Socket client non creee :");
        else
            end = 1;
    }

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

        if(Receive(hSocket, Message) == -1)
        {
            printf("Erreur de receive\n");
            return -1;
        }
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

            if(Receive(hSocket, Message) == -1)
            {
                printf("Erreur de receive\n");
                return -1;
            }
            printf("Reponse recue : %s\n", Message);

            if(strcmp(Message, "TICKET_OK") != 0)
            {
                printf("Ticket non trouve !\n");
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

            if(Receive(hSocket, MessageRetour) == -1)
            {
                printf("Erreur de receive\n");
                return -1;
            }
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

                    if(Receive(hSocket, MessageRetour) == -1)
                    {
                        printf("Erreur de receive\n");
                        return -1;
                    }
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

    close(hSocket);

    return 0;
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
    printf("Deconnexion effectuee!\n");
    sleep(1);

    close(hSocket);
    exit(0);
}