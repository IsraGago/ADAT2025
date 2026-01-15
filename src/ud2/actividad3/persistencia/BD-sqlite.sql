    create table DEPARTAMENTO
(
    NumDepartamento  INTEGER not null
        primary key,
    NomeDepartamento TEXT    not null
        unique,
    NSSDirector      TEXT    not null
        constraint FK_DEPARTAMENTO_EMPLEADO
            references EMPREGADO
);

create table EMPREGADO
(
    Nome                     TEXT not null,
    Apelido1                 TEXT not null,
    Apelido2                 TEXT,
    NSS                      TEXT not null
        primary key,
    Rua                      TEXT,
    Numero_Calle             INTEGER,
    Piso                     TEXT,
    CP                       TEXT,
    Localidade               TEXT,
    Provincia                TEXT,
    DataNacemento            DATE,
    Sexo                     TEXT default 'M',
    NSSSupervisa             TEXT
        constraint FK_EMPLEADO_EMPLEADO
            references EMPREGADO,
    NumDepartamentoPertenece INTEGER
        constraint FK_EMPLEADO_DEPARTAMENTO
            references DEPARTAMENTO,
    constraint CK_SEXO
        check (Sexo IN ('H', 'M'))
);

create table EMPREGADO_PROXECTO
(
    NSSEmpregado TEXT    not null
        constraint FK_EMPLEADO_PROYECTO_EMPLEADO
            references EMPREGADO,
    NumProxecto  INTEGER not null
        constraint FK_EMPLEADO_PROYECTO_PROYECTO
            references PROXECTO,
    Horas        INTEGER,
    primary key (NSSEmpregado, NumProxecto)
);

create table EMPREGADOFIXO
(
    NSS       TEXT not null
        primary key
        constraint FK_EMPLEADOFIXO_EMPLEADO
            references EMPREGADO,
    Salario   REAL,
    DataAlta  DATE,
    Categoria TEXT
);

create table EMPREGADOTEMPORAL
(
    NSS        TEXT not null
        primary key
        constraint FK_EMPLEADOTEMP_EMPLEADO
            references EMPREGADO,
    DataInicio DATE,
    DataFin    DATE,
    CosteHora  REAL,
    NumHoras   REAL
);

create table FAMILIAR
(
    Codigo       INTEGER
        primary key autoincrement,
    Nss          TEXT             not null
        unique,
    NssEmpregado TEXT             not null,
    Nombre       TEXT             not null,
    Apellido1    TEXT             not null,
    Apellido2    TEXT,
    FechaNac     TEXT             not null,
    Parentesto   TEXT             not null,
    Sexo         TEXT default 'M' not null,
    check (Sexo in ('H', 'M'))
);

create table PROXECTO
(
    NumProxecto       INTEGER not null
        primary key,
    NomeProxecto      TEXT    not null
        unique,
    Lugar             TEXT    not null,
    NumDepartControla INTEGER not null
        constraint FK_PROYECTO_DEPARTAMENTO
            references DEPARTAMENTO
);

create table VEHICULO
(
    Codigo    INTEGER
        primary key autoincrement,
    Matricula TEXT not null
        unique,
    Marca     Text not null,
    Modelo    TEXT not null,
    Tipo      TEXT not null,
    check (Tipo in ('G', 'D'))
);



