#include "AccessBillBag.h"

#define LINESIZE 60

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
    char Record[LINESIZE];
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