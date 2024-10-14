# Miagie - Serpentard

### Informations sur le projet

* La partie sur le puissance 4 a été faite par Hongjin Gu, mais a été commit par Etienne Coyac par manque de temps (rendons à César ce qui est à César)
* Deux branches séparées pour les jeux en local : ```BombesNumeriques``` et ```jeu_cartes```

### Script de création de la base de données : 
```SQL
CREATE TABLE Maison(
   nomMaison VARCHAR(50),
   PRIMARY KEY(nomMaison)
);

CREATE TABLE Matiere(
   nomMatiere VARCHAR(50),
   PRIMARY KEY(nomMatiere)
);

CREATE TABLE Jeu(
   nomJeu VARCHAR(50),
   PRIMARY KEY(nomJeu)
);

CREATE TABLE Eleve(
   idEleve INT AUTO_INCREMENT,
   totalPoints INT DEFAULT 0,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   nomMaison VARCHAR(50) NOT NULL,
   PRIMARY KEY(idEleve),
   FOREIGN KEY(nomMaison) REFERENCES Maison(nomMaison)
);

CREATE TABLE PropositionPartie(
   idProposition INT AUTO_INCREMENT,
   refuse BOOLEAN DEFAULT FALSE,
   mise INT,
   nomJeu VARCHAR(50) NOT NULL,
   idEleve_vainqueur INT,
   idEleve_receveur INT NOT NULL,
   idEleve_lanceur INT NOT NULL,
   PRIMARY KEY(idProposition),
   FOREIGN KEY(nomJeu) REFERENCES Jeu(nomJeu),
   FOREIGN KEY(idEleve_vainqueur) REFERENCES Eleve(idEleve),
   FOREIGN KEY(idEleve_receveur) REFERENCES Eleve(idEleve),
   FOREIGN KEY(idEleve_lanceur) REFERENCES Eleve(idEleve)
);


CREATE TABLE Professeur(
   idProfesseur INT AUTO_INCREMENT,
   isDirecteur BOOLEAN DEFAULT FALSE,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   nomMatiere VARCHAR(50) NOT NULL,
   PRIMARY KEY(idProfesseur),
   FOREIGN KEY(nomMatiere) REFERENCES Matiere(nomMatiere)
);

CREATE TABLE Partie(
   idPartie INT AUTO_INCREMENT,
   idProposition INT NOT NULL,
   PRIMARY KEY(idPartie),
   UNIQUE(idProposition),
   FOREIGN KEY(idProposition) REFERENCES PropositionPartie(idProposition)
);

CREATE TABLE Mouvement(
   idEleve INT,
   idMouv INT AUTO_INCREMENT,
   mouv VARCHAR(50),
   timestampp VARCHAR(50),
   idPartie INT NOT NULL,
   PRIMARY KEY(idMouv),
   FOREIGN KEY(idEleve) REFERENCES Eleve(idEleve),
   FOREIGN KEY(idPartie) REFERENCES Partie(idPartie)
);

CREATE TABLE evaluer(
   idEleve INT,
   nomMatiere VARCHAR(50),
   note INT,
   dateEval DATE,
   PRIMARY KEY(idEleve, nomMatiere),
   FOREIGN KEY(idEleve) REFERENCES Eleve(idEleve),
   FOREIGN KEY(nomMatiere) REFERENCES Matiere(nomMatiere)
);


-- Insertion des données dans Maison (les 4 maisons de Harry Potter)
INSERT INTO Maison (nomMaison) VALUES ('Gryffondor');
INSERT INTO Maison (nomMaison) VALUES ('Poufsouffle');
INSERT INTO Maison (nomMaison) VALUES ('Serdaigle');
INSERT INTO Maison (nomMaison) VALUES ('Serpentard');

-- Insertion des données dans la table Matiere (5 matières de Harry Potter)
INSERT INTO Matiere (nomMatiere) VALUES ('Sortilèges');
INSERT INTO Matiere (nomMatiere) VALUES ('Défense contre les forces du Mal');
INSERT INTO Matiere (nomMatiere) VALUES ('Potions');
INSERT INTO Matiere (nomMatiere) VALUES ('Métamorphose');
INSERT INTO Matiere (nomMatiere) VALUES ('Botanique');

-- Insertion des élèves dans la maison Gryffondor
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Potter', 'Harry', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Weasley', 'Ron', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Granger', 'Hermione', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Longbottom', 'Neville', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Weasley', 'Ginny', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Finnigan', 'Seamus', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Thomas', 'Dean', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Brown', 'Lavande', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Patil', 'Parvati', 'Gryffondor');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Wood', 'Oliver', 'Gryffondor');

-- Insertion des élèves dans la maison Poufsouffle
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Diggory', 'Cedric', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Bones', 'Susan', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Macmillan', 'Ernie', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Abbott', 'Hannah', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Smith', 'Zacharias', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Jones', 'Megan', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Hopkins', 'Wayne', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Stebbins', 'Owen', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Perks', 'Sally', 'Poufsouffle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Warrington', 'Emily', 'Poufsouffle');

-- Insertion des élèves dans la maison Serdaigle
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Lovegood', 'Luna', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Chang', 'Cho', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Patil', 'Padma', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Boot', 'Terry', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Corner', 'Michael', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Goldstein', 'Anthony', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Turpin', 'Lisa', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Edgecombe', 'Marietta', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Avery', 'Edgar', 'Serdaigle');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Cadwallader', 'Myrtle', 'Serdaigle');

-- Insertion des élèves dans la maison Serpentard
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Malfoy', 'Drago', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Parkinson', 'Pansy', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Goyle', 'Gregory', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Crabbe', 'Vincent', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Nott', 'Theodore', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Bulstrode', 'Millicent', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Zabini', 'Blaise', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Greengrass', 'Daphne', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Rosier', 'Evan', 'Serpentard');
INSERT INTO Eleve (nom, prenom, nomMaison) VALUES ('Selwyn', 'Thaddeus', 'Serpentard');

-- Insertion des professeurs pour la matière Sortilèges
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Flitwick', 'Filius', 'Sortilèges');
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Dumont', 'Aurélien', 'Sortilèges');

-- Insertion des professeurs pour la matière Défense contre les forces du Mal
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Lupin', 'Remus', 'Défense contre les forces du Mal');
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Ombrage', 'Dolores', 'Défense contre les forces du Mal');

-- Insertion des professeurs pour la matière Potions
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Rogue', 'Severus', 'Potions');
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Slughorn', 'Horace', 'Potions');

-- Insertion des professeurs pour la matière Métamorphose
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('McGonagall', 'Minerva', 'Métamorphose');
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Duchesne', 'Hélène', 'Métamorphose');

-- Insertion des professeurs pour la matière Botanique
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Chourave', 'Pomona', 'Botanique');
INSERT INTO Professeur (nom, prenom, nomMatiere) VALUES ('Green', 'Abigail', 'Botanique');

-- Insertion du professeur Dumbledore responsable
INSERT INTO Professeur (nom, prenom, nomMatiere, isDirecteur) VALUES ('Dumbeldore', 'Albus', 'Métamorphose', TRUE);

-- Insertion des jeux
INSERT INTO Jeu (nomJeu) VALUES ("Chifoumi");
INSERT INTO Jeu (nomJeu) VALUES ("Puissance4");


```
