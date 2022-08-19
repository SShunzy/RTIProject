#include <stdlib.h>
#include <stdio.h>
#include <sys/socket.h>
#include <errno.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <string.h>
#include <arpa/inet.h>

int SocketServeur(int port);
int Accept(int s, char* ipClient);
int SocketClient(char* ipServeur, int port);
int Send(int s, const char* data, int n);
int Receive(int s, char* data);