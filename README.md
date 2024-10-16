# Application MIAGIE - SPRINGBOOT

## I - Présentation
Cette application est un projet réalisé en SpringBoot. Elle permet de gérer des élèves, des maisons, des professeurs, des matières, des évaluations et des parties de jeux.
Pour l'instant, la partie concernant le jeu n'est pas complètement codée.
Les autres fonctionnalités sont opérationnelles (voir la partie III : services fournis par l'application)

## II - Setup du projet
S'agissant d'un projet WEB, l'application se base sur plusieurs éléments pour fonctionner
- Un serveur Tomcat (géré par le framework)
- Un gestionnaire de dependances, ici Maven, pour installer toutes les dépendances nécessaires au bon fonctionnement de l'application. (plugin jbdc, framework springboot et utilitaires...)

### Installation
- Cloner le projet Git 
- L'importer dans un IDE (Intellij, Eclipse, VSC...)
- Importer le projet Maven (pom.xml) et installer les applications
- Lancer l'application
- L'application est accessible via l'url : http://localhost:8080/ (port par defaut)
- ATTENTION : la base de données MySQL / MariaDB doit être lancée au préalable pour que l'application démarre (voir configurations de la BDD dans le fichier resources --> application.properties)

### Communication avec les API
- Pour accéder aux ressources fournis par le backend, il y a deux possibilités: 
1. Utiliser un client REST (Postman, Insomnia, ThunderClient...)
2. Installer et lancer l'application MIAGIE FRONT (projet Git à cloner) et interagir avec le serveur grâce à l'IHM

## III - Services fournis par l'application

### Eleve
GET /eleve : récupère tous les élèves

GET /eleve/id : Récupère un élève avec par son ID

GET /eleve/fromOtherHouses : Récupère tous les élèves n'appartenant pas à Serdaigle

GET /eleve/voirPropositionsPartieRecues/id : Récupère les proposition de parties reçues par l'élève avec son id.

GET /eleve/voirPropositionsPartieProposees/id : Récupère les proposition de parties proposées par l'élève avec son id.

POST /eleve : Crée un élève.
Paramètres du body :
nom: String
prenom: String
nomMaison: String

POST /eleve/propositionPartie : Crée une proposition de partie d'un élève vers un élève d'une autre maison, avec un jeu et une mise.
Paramètres du body :
idJoueurSource : int
idJoueurCible : int
nomJeu : String
mise : int

POST /eleve/accepterUnePartie : Accepte une partie.
Paramètres du body :
idProposition : int
idEleveCible : int

DELETE /eleve/id : Supprime l'élève par son ID

### Evaluer
GET /evaluer : Récupère toutes les évaluations => Useless.

POST /evaluer : Crée une évaluation pour un élève, avec un nbPoint et effectuée par un professeur. (L'identifiant professeur permet de récupérer le nom de la matière dans le service)
Paramètres du body :
idEleve : int
idProfesseur : int
nbPoints : int

### Maison
GET /maison : Racupère toutes les maisons (avec leurs élèves)

GET /maison/nomMaison : Récupère une maison via son nom (avec ses élèves)

Get /maison/nomMaisonGagnante : Récupère le nom de la maison gagnante (avec ses élèves)

### Matiere
GET /matiere : Récupère toutes les matières

GET /matiere/nomMatiere : Récupère une matière via son nom

### Professeur
GET /professeur : Récupère tous les professeurs (avec la matière qu'ils enseignent)

GET /professeur/id : Récupère un professeur via son id (avec la matière qu'il enseigne)

POST /professeur : Crée un professeur.
Paramètres du body :
nom : String
prenom : String
nomMatiere : String

DELETE /professeur/id : Supprime un professeur via son ID.