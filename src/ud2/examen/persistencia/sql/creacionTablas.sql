/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */
DROP TABLE IF EXISTS Laboratorio;
CREATE TABLE Laboratorio(
    codLaboratorio int IDENTITY (1,1),
    nombre varchar(50) not null,
    anoInauguracion int not null,
    CONSTRAINT PK_Laboratorio PRIMARY KEY (codLaboratorio),
    CONSTRAINT UQ_NOMBRE_LABORATORIO UNIQUE(nombre)
)

DROP TABLE IF EXISTS FOTOGRAFO_LABORATORIO;
CREATE TABLE FOTOGRAFO_LABORATORIO(
    codFotografoLaboratorio int IDENTITY (1,1),
    codFotografo int not null,
    codLaboratorio int not null,
    fechaInicio DATE not null,
    fechaFin DATE,
    CONSTRAINT FK_FOTOGRAFO_LABORATORIO FOREIGN KEY (codFotografo) references FOTOGRAFO(CODIGO),
    CONSTRAINT FK_LABORATORIO_FOTOGRAFO FOREIGN KEY (codLaboratorio) references Laboratorio(codLaboratorio),
    CONSTRAINT PK_FOTOGRAFO_LABORATORIO PRIMARY KEY (codFotografoLaboratorio)

)