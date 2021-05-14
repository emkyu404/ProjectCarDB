#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Adresse
#------------------------------------------------------------

CREATE TABLE Adresse(
        idAdresse  Int  Auto_increment  NOT NULL ,
        rue        Varchar (30) ,
        ville      Varchar (20) ,
        codePostal Numeric
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
        idClient          Int  Auto_increment  NOT NULL ,
        nom               Varchar (50) ,
        prenom            Varchar (50) ,
        email             Varchar (50) ,
        numTelephone      Numeric ,
        idAdresse         Int ,
        dateSouscription  Date NOT NULL ,
        idAdresse_habiter Int NOT NULL ,
        idPFidelite       Int
	,CONSTRAINT Client_PK PRIMARY KEY (idClient)

	,CONSTRAINT Client_Adresse_FK FOREIGN KEY (idAdresse_habiter) REFERENCES Adresse(idAdresse)
	,CONSTRAINT Client_PFidelite0_FK FOREIGN KEY (idPFidelite) REFERENCES PFidelite(idPFidelite)
	,CONSTRAINT Client_Adresse_AK UNIQUE (idAdresse_habiter)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Agence
#------------------------------------------------------------

CREATE TABLE Agence(
        idAgence         Int NOT NULL ,
        nom              Varchar (20) NOT NULL ,
        telephone        Numeric NOT NULL ,
        coordGPS         Varchar (20) NOT NULL ,
        idAdresse        Int NOT NULL ,
        idAdresse_situer Int NOT NULL
	,CONSTRAINT Agence_PK PRIMARY KEY (idAgence)

	,CONSTRAINT Agence_Adresse_FK FOREIGN KEY (idAdresse_situer) REFERENCES Adresse(idAdresse)
	,CONSTRAINT Agence_Adresse_AK UNIQUE (idAdresse_situer)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Vehicule
#------------------------------------------------------------

CREATE TABLE Vehicule(
        idVehicule    Int  Auto_increment  NOT NULL ,
        matricule     Varchar (20) ,
        marque        Varchar (20) ,
        modele        Varchar (20) ,
        kilometrage   BigInt ,
        climatise     Bool ,
        typeBoite     Enum ("Automatique","Manuel") ,
        typeCarburant Enum ("Gazole","Essence","SP95","GPL","Electrique") ,
        idCategorie   Int ,
        idAgence      Int NOT NULL
	,CONSTRAINT Vehicule_PK PRIMARY KEY (idVehicule)

	,CONSTRAINT Vehicule_Agence_FK FOREIGN KEY (idAgence) REFERENCES Agence(idAgence)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Employé
#------------------------------------------------------------

CREATE TABLE Employe(
        idEmp             Int NOT NULL ,
        nom               Varchar (10) ,
        prenom            Varchar (10) ,
        email             Varchar (10) ,
        numTelephone      Numeric ,
        login             Varchar (10) ,
        mdp               Varchar (10) ,
        typeEmp           Enum ("Chauffeur","Administrateur") ,
        idAdresse         Int ,
        idAdresse_habiter Int NOT NULL
	,CONSTRAINT Employe_PK PRIMARY KEY (idEmp)

	,CONSTRAINT Employe_Adresse_FK FOREIGN KEY (idAdresse_habiter) REFERENCES Adresse(idAdresse)
	,CONSTRAINT Employe_Adresse_AK UNIQUE (idAdresse_habiter)
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

