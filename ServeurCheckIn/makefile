.SILENT:

CC = g++ -DLINUX -Wall

All:	Serveur_CheckIn	Application_CheckIn	Socket_lib.o	AccessBillBag.o

Serveur_CheckIn:	Socket_lib.o	AccessBillBag.o
	echo Creation de Serveur_CheckIn...
	$(CC) Serveur_CheckIn.c -o Serveur_CheckIn Socket_lib.o AccessBillBag.o -lpthread

Application_CheckIn:	Socket_lib.o
	echo Creation de Application_CheckIn...
	$(CC) Application_CheckIn.c -o Application_CheckIn Socket_lib.o

AccessBillBag.o:	AccessBillBag.c	AccessBillBag.h
	echo Creation de AccessBillBag.o...
	$(CC) -c AccessBillBag.c -o AccessBillBag.o

Socket_lib.o:	Socket_lib.c	Socket_lib.h
	echo Creation de Socket_lib.o...
	$(CC) -c Socket_lib.c -o Socket_lib.o
