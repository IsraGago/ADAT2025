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

Create table VEHICULO_RENTING(
    CodVehiculo int not null,
    FechaIni date not null,
    PrecioMensual float not null,
    MesesContratados int not null,
    CONSTRAINT FK_VEHICULO_RENTING FOREIGN KEY (CodVehiculo) references VEHICULO(Codigo)
)
Create table VEHICULO_PROPIO(
    CodVehiculo int not null,
    FechaCompra date not null,
    Precio float not null,
    CONSTRAINT FK_VEHICULO_PROPIO FOREIGN KEY (CodVehiculo) references VEHICULO(Codigo)

)

Create table FAMILIAR(
    Codigo int AUTO_INCREMENT,
    Nss varchar(50) not null,
    NssEmpregado varchar(50) not null,
    Nombre varchar(50) not null,
    Apellido1 varchar(50) not null,
    Apellido2 varchar(50),
    FechaNac date not null,
    Parentesco varchar(50) not null,
    Sexo char not null default 'M',
    CONSTRAINT PK_FAMILIAR PRIMARY KEY (Codigo),
    CONSTRAINT UK_NSS UNIQUE(Nss),
    CONSTRAINT FK_FAMILIAR_EMPLEADO FOREIGN KEY (NssEmpregado) REFERENCES EMPREGADO(Nss),
    CONSTRAINT CK_SEXO_FAMILIAR CHECK ( Sexo in ('H','M') )
);

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
