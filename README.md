# RTIProject
Si jamais tu copies depuis le Git, il y aura certains réglages à faire

Pré-requis: configurer la base de données avec BD_Airport.mwb

1. Ajouter les librairies fournies au projet (database.utilities, Interfaces_Réseaux, bcprov, bvutil et mysql-connector
2. Changer les fichiers de destinations des clés et des certificats. Il faut juste changer la variable statique Repository dans Application_Billets, RequetePAYP et RequeteTICKMAP
3. Changer les variables de connexion à MySQL si tu les as changés.
