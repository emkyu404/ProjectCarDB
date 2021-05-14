#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

#-------------------------------------------------------------
#    Authors : BUI, REINO JOAQUIM, SAID IBRAHIM
#    Date de création : Mai 2021
#-------------------------------------------------------------

DROP DATABASE IF EXISTS carDB;
CREATE DATABASE carDB;
USE carDB;
drop table if exists Adresse;
drop table if exists PFidelite;
drop table if exists Client;
drop table if exists Agence;
drop table if exists Vehicule;
drop table if exists Employ�;
drop table if exists louer;
drop table if exists reserver;

#------------------------------------------------------------
# Table: Adresse
#------------------------------------------------------------

CREATE TABLE Adresse(
        idAdresse  Int  Auto_increment  NOT NULL ,
        rue        Varchar (30) NOT NULL ,
        ville      Varchar (20) NOT NULL ,
        codePostal Numeric NOT NULL
	,CONSTRAINT Adresse_PK PRIMARY KEY (idAdresse)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: PFidelite
#------------------------------------------------------------

CREATE TABLE PFidelite(
        idPFidelite Int  Auto_increment  NOT NULL ,
        description Varchar (50) NOT NULL ,
        duree       Int NOT NULL ,
        prix        DECIMAL (15,3)  NOT NULL ,
        tauxR       Float NOT NULL
	,CONSTRAINT PFidelite_PK PRIMARY KEY (idPFidelite)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Client
#------------------------------------------------------------

CREATE TABLE Client(
        idClient         Int  Auto_increment  NOT NULL ,
        nom              Varchar (50) NOT NULL ,
        prenom           Varchar (50) NOT NULL ,
        email            Varchar (50) NOT NULL ,
        numTelephone     Numeric NOT NULL ,
        dateSouscription Date  ,
        idAdresse        Int NOT NULL ,
        idPFidelite      Int
	,CONSTRAINT Client_PK PRIMARY KEY (idClient)

	,CONSTRAINT Client_Adresse_FK FOREIGN KEY (idAdresse) REFERENCES Adresse(idAdresse)
	,CONSTRAINT Client_PFidelite0_FK FOREIGN KEY (idPFidelite) REFERENCES PFidelite(idPFidelite)
	,CONSTRAINT Client_Adresse_AK UNIQUE (idAdresse)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Agence
#------------------------------------------------------------

CREATE TABLE Agence(
        idAgence  Int  Auto_increment  NOT NULL ,
        nom       Varchar (20) NOT NULL ,
        telephone Numeric NOT NULL ,
        coordGPS  Varchar (20) NOT NULL ,
        idAdresse Int NOT NULL
	,CONSTRAINT Agence_PK PRIMARY KEY (idAgence)

	,CONSTRAINT Agence_Adresse_FK FOREIGN KEY (idAdresse) REFERENCES Adresse(idAdresse)
	,CONSTRAINT Agence_Adresse_AK UNIQUE (idAdresse)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Employ�
#------------------------------------------------------------

CREATE TABLE Employe(
        idEmp        Int  Auto_increment  NOT NULL ,
        nom          Varchar (10) NOT NULL ,
        prenom       Varchar (10) NOT NULL ,
        email        Varchar (10) NOT NULL ,
        numTelephone Numeric NOT NULL ,
        login        Varchar (10) NOT NULL ,
        mdp          Varchar (10) NOT NULL ,
        typeEmp      Enum ("Chauffeur","Administrateur") NOT NULL ,
        idAdresse    Int NOT NULL
	,CONSTRAINT Employe_PK PRIMARY KEY (idEmp)

	,CONSTRAINT Employe_Adresse_FK FOREIGN KEY (idAdresse) REFERENCES Adresse(idAdresse)
	,CONSTRAINT Employe_Adresse_AK UNIQUE (idAdresse)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Categorie
#------------------------------------------------------------

CREATE TABLE Categorie(
        idCategorie Int  Auto_increment  NOT NULL ,
        label       Varchar (10) NOT NULL ,
        tarifJ      DECIMAL (15,3)  NOT NULL ,
        caution     DECIMAL (15,3)  NOT NULL
	,CONSTRAINT Categorie_PK PRIMARY KEY (idCategorie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Vehicule
#------------------------------------------------------------

CREATE TABLE Vehicule(
        idVehicule            Int  Auto_increment  NOT NULL ,
        matricule             Varchar (20) NOT NULL ,
        marque                Varchar (20) NOT NULL ,
        modele                Varchar (20) NOT NULL ,
        kilometrage           BigInt NOT NULL ,
        climatise             Boolean NOT NULL ,
        consommationCarburant Int NOT NULL ,
        typeBoite             Enum ("Automatique","Manuel") NOT NULL ,
        typeCarburant         Enum ("Gazole","Essence","SP95","GPL","Electrique") NOT NULL ,
        idCategorie           Int NOT NULL ,
        idAgence              Int NOT NULL 
	,CONSTRAINT Vehicule_PK PRIMARY KEY (idVehicule)

	,CONSTRAINT Vehicule_Agence_FK FOREIGN KEY (idAgence) REFERENCES Agence(idAgence)
	,CONSTRAINT Vehicule_Categorie0_FK FOREIGN KEY (idCategorie) REFERENCES Categorie(idCategorie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: louer
#------------------------------------------------------------

CREATE TABLE louer(
        idVehicule Int NOT NULL ,
        idClient   Int NOT NULL ,
        dateDebut  Date NOT NULL ,
        dateFin    Date NOT NULL
	,CONSTRAINT louer_PK PRIMARY KEY (idVehicule,idClient)

	,CONSTRAINT louer_Vehicule_FK FOREIGN KEY (idVehicule) REFERENCES Vehicule(idVehicule)
	,CONSTRAINT louer_Client0_FK FOREIGN KEY (idClient) REFERENCES Client(idClient)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: reserver
#------------------------------------------------------------

CREATE TABLE reserver(
        idVehicule Int NOT NULL ,
        idClient   Int NOT NULL
	,CONSTRAINT reserver_PK PRIMARY KEY (idVehicule,idClient)

	,CONSTRAINT reserver_Vehicule_FK FOREIGN KEY (idVehicule) REFERENCES Vehicule(idVehicule)
	,CONSTRAINT reserver_Client0_FK FOREIGN KEY (idClient) REFERENCES Client(idClient)
)ENGINE=InnoDB;

#-- VUE --

#-----------------------------
#CREATE VIEW clientGold
#(nom, prenom, email, numTelephone, idAdresse)
#AS 
#SELECT c.nom, c.prenom, c.email, c.numTelephone, c.idAdresse 
#FROM ((LOUER AS l INNER JOIN CLIENT AS C ON l.idClient = c.idClient) INNER JOIN VEHICULE AS V ON l.idVehicule = v.idVehicule) INNER JOIN CATEGORIE AS cg ON v.idCategorie = cg.idCategorie
#HAVING  ;
#------------------------------



#-- JEU DE TEST --

INSERT INTO ADRESSE(rue, ville, codePostal) VALUES ("Avenue Henri Ginoux", "Montrouge", 92120);
INSERT INTO CLIENT(nom, prenom, email, numTelephone, idAdresse) VALUES ("Bui", "Minh-Quân", "bmq@gmx.com", 0708099921,1);

INSERT INTO ADRESSE(rue, ville, codePostal) VALUES ("Avenue de la Paix", "Fresnes", 94260);
INSERT INTO AGENCE(nom, telephone, coordGPS, idAdresse) VALUES ("RentCar Fresnes", 0142536798, "48.8062236,2.3291225", 2);

INSERT INTO PFidelite(description, duree, prix, tauxR) VALUES ("réduction de 20 %", 30, 5, 0.20);

INSERT INTO CATEGORIE(label, tarifJ, caution) VALUES ("LUXE", 1000, 80000);
INSERT INTO CATEGORIE(label, tarifJ, caution) VALUES ("CONFORT", 300, 10000);
INSERT INTO CATEGORIE(label, tarifJ, caution) VALUES ("ECONOMIQUE", 30, 5000);
INSERT INTO VEHICULE(matricule, marque, modele, kilometrage, climatise, consommationCarburant, typeBoite, typeCarburant, idCategorie, idAgence) VALUES ("UB-235-FB", "Renault", "Zoe", 5000, true, 100, "Automatique","Electrique", 3, 1);


CREATE OR REPLACE VIEW clientSL
(nom, prenom, email, numTelephone, idAdresse)
AS
SELECT c.nom, c.prenom, c.email, c.numTelephone, c.idAdresse
FROM CLIENT AS C
WHERE c.idClient NOT IN(
        SELECT l.idClient
        FROM LOUER as l
);
