create table DEPARTAMENTO
(
    NumDepartamento  INTEGER not null
        primary key,
    NomeDepartamento TEXT    not null
        unique,
    NSSDirector      TEXT    not null
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
            references main.EMPREGADO,
    NumDepartamentoPertenece INTEGER
        constraint FK_EMPLEADO_DEPARTAMENTO
            references main.DEPARTAMENTO,
    constraint CK_SEXO
        check (Sexo IN ('H', 'M'))
);

alter table DEPARTAMENTO
    add constraint FK_DEPARTAMENTO_EMPLEADO
        foreign key (NSSDirector) references main.EMPREGADO;

create table EMPREGADOFIXO
(
    NSS       TEXT not null
        primary key
        constraint FK_EMPLEADOFIXO_EMPLEADO
            references main.EMPREGADO,
    Salario   REAL,
    DataAlta  DATE,
    Categoria TEXT
);

create table EMPREGADOTEMPORAL
(
    NSS        TEXT not null
        primary key
        constraint FK_EMPLEADOTEMP_EMPLEADO
            references main.EMPREGADO,
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

create table LUGAR
(
    ID               INTEGER
        primary key autoincrement,
    Num_departamento INTEGER not null
        constraint FK_LUGAR_DEPARTAMENTO
            references main.DEPARTAMENTO,
    Lugar            TEXT    not null,
    constraint UK_LUGAR
        unique (Num_departamento, Lugar)
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
            references main.DEPARTAMENTO
);

create table EMPREGADO_PROXECTO
(
    NSSEmpregado TEXT    not null
        constraint FK_EMPLEADO_PROYECTO_EMPLEADO
            references main.EMPREGADO,
    NumProxecto  INTEGER not null
        constraint FK_EMPLEADO_PROYECTO_PROYECTO
            references main.PROXECTO,
    Horas        INTEGER,
    primary key (NSSEmpregado, NumProxecto)
);

