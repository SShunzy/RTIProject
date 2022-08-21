#include "Socket_lib.h"

int SocketServeur(int port)
{
    int hSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(hSocket == -1)
    {
        perror("(Serveur) Erreur de création de la socket :");
        return -1;
    }
    printf("(Serveur) Socket creee!\n");

    struct sockaddr_in adresseSocket;
    struct hostent* infosHost;
    char HostBuff[80];

    gethostname(HostBuff, 80);
    if ((infosHost = gethostbyname(HostBuff)) == NULL)
    {
        perror("(Serveur) Erreur d'acquisition d'infos sur le host :");
        close(hSocket);
        return -1;
    }
    printf("(Serveur) Infos host acquises!\n");

    memset(&adresseSocket, 0, sizeof(struct sockaddr_in));
    adresseSocket.sin_family = AF_INET;
    adresseSocket.sin_port = htons(port);
    memcpy(&adresseSocket.sin_addr, infosHost->h_addr,infosHost->h_length);

    if (bind(hSocket, (struct sockaddr *)&adresseSocket, sizeof(struct sockaddr_in)) == -1)
    {
        perror("(Serveur) Erreur sur le bind de la socket :");
        close(hSocket);
        return -1;
    }
    printf("(Serveur) Bind adresse et port socket effectue!\n");
    return hSocket;
}

int Accept(int s, char* ipClient)
{
    int ret;
    socklen_t tailleSockaddr_in;
    struct sockaddr_in adresseSocket;

    if(listen(s,SOMAXCONN) == -1)
    {
        perror("Erreur sur le listen de la socket :");
        return -1;
    }
    else printf("Listen socket effectue!\n");

    /*gethostname(HostBuff, 80);

    if((infosHost = gethostbyname(HostBuff)) == NULL)
    {
        perror("Erreur d'acquisition d'infos sur le host :");
        return -1;
    }
    else printf("Infos host acquises!\n");*/

    memset(&adresseSocket, 0, sizeof(struct sockaddr_in));
    //getsockname(s, (struct sockaddr*)&adresseSocket, &tailleSockaddr_in);

    tailleSockaddr_in = sizeof(struct sockaddr_in);

    if((ret = accept(s, (struct sockaddr*)&adresseSocket, &tailleSockaddr_in)) == -1)
    {
        perror("Erreur sur l'accept de la socket :");
        return -1;
    }
    else printf("Socket acceptee!\n");
    strcpy(ipClient, inet_ntoa(adresseSocket.sin_addr));
    return ret;
}
int SocketClient(char* ipServeur, int port)
{
    int hSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(hSocket == -1)
    {
        perror("(Client) Erreur de creation de la socket :");
        return -1;
    }
    printf("(Client) Socket creee!\n");

    struct sockaddr_in adresseSocket;
    socklen_t tailleSockaddr_in;

    memset(&adresseSocket, 0, sizeof(struct sockaddr_in));
    adresseSocket.sin_family = AF_INET;
    adresseSocket.sin_port = htons(port);
    adresseSocket.sin_addr.s_addr = inet_addr(ipServeur);
    tailleSockaddr_in = sizeof(struct sockaddr_in);

    if (connect(hSocket, (struct sockaddr *)&adresseSocket, tailleSockaddr_in) == -1)
    {
        printf("(Client) Erreur sur le connect de la socket %d\n", errno);
        close(hSocket);
        return -1;
    }
    printf("(Client) Socket connectee!\n");
    return hSocket;
}
int Send(int s, const char* data, int n)
{
    char copie[n+1];
    strcpy(copie, data);
    strcat(copie, "@");

    printf("Message : %s\n", copie);

    if (send(s, copie, n, 0) == -1)
    {
        perror("Erreur sur le send de la socket :");
        return -1;
    }
    printf("Message envoye!\n");
    return 0;
}
int Receive(int s, char* data)
{
    int Total = 0;
    char MsgRecu = '0';
    while(recv(s, &MsgRecu, 1, 0) != -1 && MsgRecu != '@' && &data[Total] != NULL)
    {
        data[Total] = MsgRecu;
        Total++;
        printf("%d message(s) recu(s) : %c\n", Total, MsgRecu);
    }

    if(Total > 0)
    {
        data[Total] = '\0';
        printf("Message entier recu!\n");
        return Total;
    }
    else
    {
        perror("Aucun message recu :\n");
        return -1;
    }
}