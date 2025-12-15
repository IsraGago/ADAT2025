-- MYSQL
Create table VEHICULO(
    Codigo int AUTO_INCREMENT,
    Matricula Char(8) not null,
    Marca varchar(50) not null,
    Modelo varchar(50) not null,
    Tipo char(1) not null,
    CONSTRAINT PK_CODIGOVEHICULO PRIMARY KEY (Codigo),
    CONSTRAINT  UK_MATRICULA UNIQUE(Matricula),
    CONSTRAINT CK_TIPOVEHICULO CHECK (Tipo in ('G','D'))
)

-- SQLITE
Create Table VEHICULO(
    Codigo INTEGER PRIMARY KEY AUTOINCREMENT,
    Matricula TEXT NOT NULL UNIQUE,
    Marca Text NOT NULL,
    Modelo TEXT NOT NULL,
    Tipo TEXT NOT NULL CHECK (Tipo in ('G','D'))
)

Create table FAMILIAR(
    Codigo INTEGER PRIMARY KEY AUTOINCREMENT,
    Nss TEXT NOT NULL UNIQUE,
    NssEmpregado TEXT NOT NULL,
    Nombre TEXT NOT NULL,
    Apellido1 TEXT NOT NULL,
    Apellido2 TEXT,
    FechaNac TEXT NOT NULL,
    Parentesto TEXT NOT NULL,
    Sexo TEXT NOT NULL CHECK (Sexo in ('H','M')) DEFAULT 'M'
)
