#include "AccessBillBag.h"
#include "Socket_lib.h"

#define LINESIZE 60

#define SERVER_BAGGAGE_ADDRESS "192.168.182.128"
#define SERVER_BAGGAGE_PORT 32400

int Connexion(char* UserLogin, char* UserPassword)
{
    char Record[LINESIZE];
    FILE* fp = fopen("./config.csv", "r");

    while(feof(fp) == 0)
    {
        fgets(Record, LINESIZE, fp);

        if(strcmp(strtok(Record, ";"), UserLogin) == 0 && strcmp(strtok(NULL, "\n"), UserPassword) == 0)
        {
            printf("Connexion établie !\n");
            fclose(fp);
            return 0;
        }
    }
    printf("Connexion refusee !\n");
    fclose(fp);
    return -1;
}

int CheckTicket(char* Number, char* Accompany)
{   
    char Message[LINESIZE];
    char Response[LINESIZE];
    int hSocket;
    strcpy(Message, Number);
    strcat(Message,"#");
    strcat(Message,Accompany);
    printf("Message = %s\n",Message);
    if((hSocket = SocketClient(SERVER_BAGGAGE_ADDRESS, SERVER_BAGGAGE_PORT)) == -1)
            perror("Socket client non creee :");
    else{
        if(Send(hSocket, Message, strlen(Message)+1) == -1)
        {
            printf("Erreur d'envoi du Numéro et de l'accompagnant\n");
            return -1;
        }
        printf("Demande de vérification envoyee\n");

        if(Receive(hSocket, Response) == -1)
        {
            printf("Erreur de receive\n");
            return -1;
        }
        printf("Reponse recue : %s\n", Response);
        
        if(strcmp(Response,"BILLET_OK") == 0)return 0;
        else if(strcmp(Response,"BILLET_KO") == 0)return -1;
    }
    return -1;
    /*
    FILE* fp = fopen("./tickets.csv", "r");

    while(feof(fp) == 0)
    {
        fgets(Record, LINESIZE, fp);

        if(strcmp(strtok(Record, ";"), Number) == 0 && strcmp(strtok(NULL, "\n"), Accompany) == 0)
        {
            printf("Ticket trouve !\n");
            fclose(fp);
            return 0;
        }
    }
    fclose(fp);
    return -1;
    */
}

int AddLuggage(char* Number, char* Weight, char* Suitcase)
{
    char Record[LINESIZE];
    FILE* fp = fopen("./luggage.csv", "a");

    strcpy(Record, Number);
    strcat(Record, ";");
    strcat(Record, Weight);
    strcat(Record, ";");
    if(strcmp(Suitcase, "o") == 0 || strcmp(Suitcase, "O") == 0)
        strcat(Record, "VALISE");
    else
        strcat(Record, "PASVALISE");

    strcat(Record, "\n");

    if(fputs(Record, fp) != -1)
    {
        fclose(fp);
        return 0;
    }

    fclose(fp);
    return -1;
}