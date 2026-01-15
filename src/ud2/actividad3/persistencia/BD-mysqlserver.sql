-- Creamos las tablas principales primero
CREATE TABLE DEPARTAMENTO (
    NumDepartamento  INT NOT NULL PRIMARY KEY,
    NomeDepartamento VARCHAR(100) NOT NULL UNIQUE,
    NSSDirector      VARCHAR(15) NOT NULL
);

CREATE TABLE EMPREGADO (
    Nome                     VARCHAR(50) NOT NULL,
    Apelido1                 VARCHAR(50) NOT NULL,
    Apelido2                 VARCHAR(50),
    NSS                      VARCHAR(15) NOT NULL PRIMARY KEY,
    Rua                      VARCHAR(100),
    Numero_Calle             INT,
    Piso                     VARCHAR(10),
    CP                       VARCHAR(10),
    Localidade               VARCHAR(50),
    Provincia                VARCHAR(50),
    DataNacemento            DATE,
    Sexo                     CHAR(1) DEFAULT 'M',
    NSSSupervisa             VARCHAR(15),
    NumDepartamentoPertenece INT,
    CONSTRAINT CK_SEXO CHECK (Sexo IN ('H', 'M')),
    CONSTRAINT FK_EMPLEADO_EMPLEADO FOREIGN KEY (NSSSupervisa) REFERENCES EMPREGADO(NSS),
    CONSTRAINT FK_EMPLEADO_DEPARTAMENTO FOREIGN KEY (NumDepartamentoPertenece) REFERENCES DEPARTAMENTO(NumDepartamento)
);

-- Añadimos la relación que faltaba
ALTER TABLE DEPARTAMENTO
ADD CONSTRAINT FK_DEPARTAMENTO_EMPLEADO FOREIGN KEY (NSSDirector) REFERENCES EMPREGADO(NSS);

CREATE TABLE PROXECTO (
    NumProxecto       INT NOT NULL PRIMARY KEY,
    NomeProxecto      VARCHAR(100) NOT NULL UNIQUE,
    Lugar             VARCHAR(100) NOT NULL,
    NumDepartControla INT NOT NULL,
    CONSTRAINT FK_PROYECTO_DEPARTAMENTO FOREIGN KEY (NumDepartControla) REFERENCES DEPARTAMENTO(NumDepartamento)
);

CREATE TABLE EMPREGADO_PROXECTO (
    NSSEmpregado VARCHAR(15) NOT NULL,
    NumProxecto  INT NOT NULL,
    Horas        INT,
    CONSTRAINT PK_EMPREGADO_PROXECTO PRIMARY KEY (NSSEmpregado, NumProxecto),
    CONSTRAINT FK_EMPLEADO_PROYECTO_EMPLEADO FOREIGN KEY (NSSEmpregado) REFERENCES EMPREGADO(NSS),
    CONSTRAINT FK_EMPLEADO_PROYECTO_PROYECTO FOREIGN KEY (NumProxecto) REFERENCES PROXECTO(NumProxecto)
);

CREATE TABLE EMPREGADOFIXO (
    NSS       VARCHAR(15) NOT NULL PRIMARY KEY,
    Salario   DECIMAL(18,2), -- REAL en SQL Server es flotante, DECIMAL es mejor para dinero
    DataAlta  DATE,
    Categoria VARCHAR(50),
    CONSTRAINT FK_EMPLEADOFIXO_EMPLEADO FOREIGN KEY (NSS) REFERENCES EMPREGADO(NSS)
);

CREATE TABLE EMPREGADOTEMPORAL (
    NSS        VARCHAR(15) NOT NULL PRIMARY KEY,
    DataInicio DATE,
    DataFin    DATE,
    CosteHora  DECIMAL(18,2),
    NumHoras   DECIMAL(18,2),
    CONSTRAINT FK_EMPLEADOTEMP_EMPLEADO FOREIGN KEY (NSS) REFERENCES EMPREGADO(NSS)
);

CREATE TABLE FAMILIAR (
    Codigo       INT IDENTITY(1,1) PRIMARY KEY,
    Nss          VARCHAR(15) NOT NULL UNIQUE,
    NssEmpregado VARCHAR(15) NOT NULL,
    Nombre       VARCHAR(50) NOT NULL,
    Apellido1    VARCHAR(50) NOT NULL,
    Apellido2    VARCHAR(50),
    FechaNac     DATE NOT NULL,
    Parentesto   VARCHAR(50) NOT NULL,
    Sexo         CHAR(1) DEFAULT 'M' NOT NULL,
    CONSTRAINT CK_SEXO_FAMILIAR CHECK (Sexo IN ('H', 'M')),
    CONSTRAINT FK_FAMILIAR_EMPLEADO FOREIGN KEY (NssEmpregado) REFERENCES EMPREGADO(NSS)
);

CREATE TABLE VEHICULO (
    Codigo    INT IDENTITY(1,1) PRIMARY KEY,
    Matricula VARCHAR(20) NOT NULL UNIQUE,
    Marca     VARCHAR(50) NOT NULL,
    Modelo    VARCHAR(50) NOT NULL,
    Tipo      CHAR(1) NOT NULL,
    CONSTRAINT CK_TIPO_VEHICULO CHECK (Tipo IN ('G', 'D'))
);