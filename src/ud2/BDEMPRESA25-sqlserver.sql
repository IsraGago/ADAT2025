USE [master]
GO
/****** Object:  Database [BDEMPRESA25]    Script Date: 19/01/2026 9:28:48 ******/
CREATE DATABASE [BDEMPRESA25]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'BDEMPRESA25', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\BDEMPRESA25.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'BDEMPRESA25_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\BDEMPRESA25_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [BDEMPRESA25] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BDEMPRESA25].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BDEMPRESA25] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [BDEMPRESA25] SET ANSI_NULLS OFF
GO
ALTER DATABASE [BDEMPRESA25] SET ANSI_PADDING OFF
GO
ALTER DATABASE [BDEMPRESA25] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [BDEMPRESA25] SET ARITHABORT OFF
GO
ALTER DATABASE [BDEMPRESA25] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [BDEMPRESA25] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [BDEMPRESA25] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [BDEMPRESA25] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [BDEMPRESA25] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [BDEMPRESA25] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [BDEMPRESA25] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [BDEMPRESA25] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [BDEMPRESA25] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [BDEMPRESA25] SET  ENABLE_BROKER
GO
ALTER DATABASE [BDEMPRESA25] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [BDEMPRESA25] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [BDEMPRESA25] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [BDEMPRESA25] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [BDEMPRESA25] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [BDEMPRESA25] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [BDEMPRESA25] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [BDEMPRESA25] SET RECOVERY FULL
GO
ALTER DATABASE [BDEMPRESA25] SET  MULTI_USER
GO
ALTER DATABASE [BDEMPRESA25] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [BDEMPRESA25] SET DB_CHAINING OFF
GO
ALTER DATABASE [BDEMPRESA25] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [BDEMPRESA25] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [BDEMPRESA25] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [BDEMPRESA25] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [BDEMPRESA25] SET QUERY_STORE = OFF
GO
USE [BDEMPRESA25]
GO
/****** Object:  Table [dbo].[DEPARTAMENTO]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DEPARTAMENTO](
	[NumDepartamento] [int] NOT NULL,
	[NomeDepartamento] [varchar](100) NOT NULL,
	[NSSDirector] [varchar](20) NOT NULL,
 CONSTRAINT [PK_DEPARTAMENTO] PRIMARY KEY CLUSTERED
(
	[NumDepartamento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EMPREGADO]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EMPREGADO](
	[Nome] [varchar](100) NOT NULL,
	[Apelido1] [varchar](100) NOT NULL,
	[Apelido2] [varchar](100) NULL,
	[NSS] [varchar](20) NOT NULL,
	[Rua] [varchar](255) NULL,
	[Numero_Calle] [int] NULL,
	[Piso] [varchar](20) NULL,
	[CP] [varchar](10) NULL,
	[Localidade] [varchar](100) NULL,
	[Provincia] [varchar](100) NULL,
	[DataNacemento] [datetime] NULL,
	[Sexo] [char](1) NULL,
	[NSSSupervisa] [varchar](20) NULL,
	[NumDepartamentoPertenece] [int] NULL,
 CONSTRAINT [PK_EMPREGADO] PRIMARY KEY CLUSTERED
(
	[NSS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EMPREGADO_PROXECTO]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EMPREGADO_PROXECTO](
	[NSSEmpregado] [varchar](20) NOT NULL,
	[NumProxecto] [int] NOT NULL,
	[Horas] [int] NULL,
 CONSTRAINT [PK_EMPREGADO_PROXECTO] PRIMARY KEY CLUSTERED
(
	[NSSEmpregado] ASC,
	[NumProxecto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EMPREGADOFIXO]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EMPREGADOFIXO](
	[NSS] [varchar](20) NOT NULL,
	[Salario] [decimal](10, 2) NULL,
	[DataAlta] [date] NULL,
	[Categoria] [varchar](50) NULL,
 CONSTRAINT [PK_EMPREGADOFIXO] PRIMARY KEY CLUSTERED
(
	[NSS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EMPREGADOTEMPORAL]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EMPREGADOTEMPORAL](
	[NSS] [varchar](20) NOT NULL,
	[DataInicio] [date] NULL,
	[DataFin] [date] NULL,
	[CosteHora] [decimal](10, 2) NULL,
	[NumHoras] [decimal](10, 2) NULL,
 CONSTRAINT [PK_EMPREGADOTEMPORAL] PRIMARY KEY CLUSTERED
(
	[NSS] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FAMILIAR]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FAMILIAR](
	[Codigo] [int] IDENTITY(1,1) NOT NULL,
	[Nss] [varchar](20) NOT NULL,
	[NssEmpregado] [varchar](20) NOT NULL,
	[Nombre] [varchar](100) NOT NULL,
	[Apellido1] [varchar](100) NOT NULL,
	[Apellido2] [varchar](100) NULL,
	[FechaNac] [date] NOT NULL,
	[Parentesto] [varchar](50) NOT NULL,
	[Sexo] [char](1) NOT NULL,
 CONSTRAINT [PK_FAMILIAR] PRIMARY KEY CLUSTERED
(
	[Codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LUGAR]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LUGAR](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Num_departamento] [int] NOT NULL,
	[Lugar] [varchar](100) NOT NULL,
 CONSTRAINT [PK_LUGAR] PRIMARY KEY CLUSTERED
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PROXECTO]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PROXECTO](
	[NumProxecto] [int] NOT NULL,
	[NomeProxecto] [varchar](100) NOT NULL,
	[Lugar] [varchar](100) NOT NULL,
	[NumDepartControla] [int] NOT NULL,
 CONSTRAINT [PK_PROXECTO] PRIMARY KEY CLUSTERED
(
	[NumProxecto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[VEHICULO]    Script Date: 19/01/2026 9:28:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[VEHICULO](
	[Codigo] [int] IDENTITY(1,1) NOT NULL,
	[Matricula] [varchar](15) NOT NULL,
	[Marca] [varchar](50) NOT NULL,
	[Modelo] [varchar](50) NOT NULL,
	[Tipo] [char](1) NOT NULL,
 CONSTRAINT [PK_VEHICULO] PRIMARY KEY CLUSTERED
(
	[Codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[DEPARTAMENTO] ([NumDepartamento], [NomeDepartamento], [NSSDirector]) VALUES (1, N'PERSOAL', N'1111111')
INSERT [dbo].[DEPARTAMENTO] ([NumDepartamento], [NomeDepartamento], [NSSDirector]) VALUES (2, N'CONTABILIDAD', N'2525252')
INSERT [dbo].[DEPARTAMENTO] ([NumDepartamento], [NomeDepartamento], [NSSDirector]) VALUES (3, N'TÉCNICO', N'2221111')
INSERT [dbo].[DEPARTAMENTO] ([NumDepartamento], [NomeDepartamento], [NSSDirector]) VALUES (4, N'INFORMÁTICA', N'8888889')
INSERT [dbo].[DEPARTAMENTO] ([NumDepartamento], [NomeDepartamento], [NSSDirector]) VALUES (5, N'ESTADÍSTICA', N'4444444')
INSERT [dbo].[DEPARTAMENTO] ([NumDepartamento], [NomeDepartamento], [NSSDirector]) VALUES (6, N'INNOVACIÓN', N'7777777')
GO
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Rocio', N'López', N'Ferreiro', N'0010010', N'Montero Ríos', 145, N'6-G', N'36208', N'Vigo', NULL, CAST(N'1975-05-21T00:00:00.000' AS DateTime), N'M', N'1010001', 1)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Javier', N'Quintero', N'Alvarez', N'0110010', N'Montevideo', 10, N'2-F', N'36209', N'Vigo', NULL, CAST(N'1972-09-23T00:00:00.000' AS DateTime), N'H', N'1010001', 1)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Germán', N'Gómez', N'Rodríguez', N'0999900', N'Sanjurjo Badía', 98, N'3-D', N'36212', N'Vigo', NULL, CAST(N'1965-08-14T00:00:00.000' AS DateTime), N'H', N'8888889', 4)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Diego', N'Lamela', N'Bello', N'1010001', N'Camelias', 123, N'4-A', N'36211', N'Vigo', NULL, CAST(N'1959-04-15T00:00:00.000' AS DateTime), N'H', N'1111111', 1)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Felix', N'Barreiro', N'Valiña', N'1100222', N'Rinlo', 5, NULL, N'27709', N'Ribadeo', NULL, CAST(N'1968-10-01T00:00:00.000' AS DateTime), N'H', N'7777777', 6)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Pepe', N'López', N'López', N'1111111', N'Olmo', 23, N'4', N'27003', N'Lugo', NULL, CAST(N'1969-07-07T00:00:00.000' AS DateTime), N'H', NULL, 1)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Xiao', N'Vecino', N'Vecino', N'1122331', N'Brasil', 10, N'2', NULL, N'Vigo', NULL, CAST(N'1959-12-10T00:00:00.000' AS DateTime), N'H', N'2525252', 2)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Eligio', N'Rodrigo', NULL, N'1231231', N'Espiño', 3, N'', N'15708', N'Santiago', NULL, CAST(N'1973-01-02T00:00:00.000' AS DateTime), N'H', N'4444444', 5)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Mariña', N'Bello', N'Arias', N'1341431', N'Gran Vía', 23, N'4-D', NULL, N'Vigo', NULL, CAST(N'1970-11-01T00:00:00.000' AS DateTime), N'M', N'2525252', 2)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Duarte', N'Xil', N'Torres', N'2221111', N'Sol', 44, N'1-A', N'27002', N'Lugo', NULL, CAST(N'1965-03-29T00:00:00.000' AS DateTime), N'H', N'1111111', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'José Manuel', N'García', N'Graña', N'2525252', N'Illas Canarias', 101, N'2-B', NULL, N'Vigo', NULL, CAST(N'1966-09-02T00:00:00.000' AS DateTime), N'H', N'1111111', 2)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Javier', N'Lamela', N'López', N'3330000', N'Avda de Vigo', 120, N'4-C', NULL, N'Pontevedra', NULL, CAST(N'1959-09-20T00:00:00.000' AS DateTime), N'H', N'2221111', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Paula', N'Mariño', N'López', N'3338883', N'Piñeira', 10, NULL, N'27400', N'Monforte', NULL, CAST(N'1969-01-01T00:00:00.000' AS DateTime), N'M', N'6000006', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Rosa', N'Mariño', N'Rivera', N'4044443', N'Piñeira', 25, NULL, N'27400', N'Monforte', NULL, CAST(N'1972-03-02T00:00:00.000' AS DateTime), N'M', N'6000006', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Sara', N'Plaza', N'Marín', N'4444444', N'Ciruela', 10, N'6-B', N'15705', N'Santiago', NULL, CAST(N'1969-07-29T00:00:00.000' AS DateTime), N'M', N'1111111', 5)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Antonia', N'Romero', N'Boo', N'4444999', N'Olmedo', 10, NULL, NULL, N'Santiago', NULL, CAST(N'1967-09-10T00:00:00.000' AS DateTime), N'M', N'8888889', 4)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Uxío', N'Cabado', N'Penalta', N'5000000', N'Nueva', 20, N'3-C', NULL, N'Santiago', NULL, CAST(N'1970-02-19T00:00:00.000' AS DateTime), N'H', N'2221111', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Israel', N'Gago', N'Acuña', N'54321342', N'Doctor Otero Ulloa 135', 135, NULL, N'1234', N'Seixo', N'pontevedra', CAST(N'2025-12-15T09:00:04.000' AS DateTime), N'H', NULL, 1)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Anxos', N'Loures', N'Freire', N'5555000', N'Rosalía de Castro', 105, N'4-F', NULL, N'Santiago', NULL, CAST(N'1982-12-16T00:00:00.000' AS DateTime), N'M', N'5000000', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Beatríz', N'Mallo', N'López', N'6000006', N'Cardenal Quiroga', 10, N'2-A', N'27400', N'Monforte', NULL, CAST(N'1965-03-10T00:00:00.000' AS DateTime), N'M', N'2221111', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Carme', N'Jurado', N'Vega', N'6000600', N'Oliva', 10, N'2', NULL, N'Pontevedra', NULL, CAST(N'1964-05-11T00:00:00.000' AS DateTime), N'M', N'3330000', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Valeriano', N'Boo', N'Boo', N'6565656', N'Marina', 23, N'2', NULL, N'Ribadeo', NULL, CAST(N'1973-05-06T00:00:00.000' AS DateTime), N'H', N'1100222', 6)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Alex', N'Méndez', N'García', N'7000007', N'Peregrina', 3, N'1', NULL, N'Pontevedra', NULL, CAST(N'1965-12-01T00:00:00.000' AS DateTime), N'H', N'3330000', 3)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Ana María', N'Ramilo', N'Barreiro', N'7777777', N'Virxe da cerca', 23, NULL, NULL, N'Santiago', NULL, CAST(N'1962-03-04T00:00:00.000' AS DateTime), N'M', N'1111111', 6)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Rubén', N'Guerra', N'Vázquez', N'8888877', N'Preguntoiro', 11, N'1', NULL, N'Santiago', NULL, CAST(N'1969-05-29T00:00:00.000' AS DateTime), N'H', N'7777777', 6)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Agostiño', N'Cerviño', N'Seoane', N'8888889', N'Montero Ríos', 120, N'4-D', N'36208', N'Vigo', NULL, CAST(N'1954-10-12T00:00:00.000' AS DateTime), N'H', N'1111111', 4)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Angeles', N'López', N'Arias', N'9876567', N'San Telmo', 5, N'2-C', N'36680', N'A Estrada', NULL, CAST(N'1957-05-04T00:00:00.000' AS DateTime), N'M', N'4444444', 5)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Breixo', N'Pereiro', N'Lamela', N'9900000', N'Sar', 29, N'1', NULL, N'Santiago', NULL, CAST(N'1980-02-19T00:00:00.000' AS DateTime), N'H', N'4444999', 4)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Celia', N'Bueno', N'Valiña', N'9990009', N'Montero Ríos', 120, N'4-D', N'36208', N'Vigo', NULL, CAST(N'1973-07-20T00:00:00.000' AS DateTime), N'M', N'1010001', 1)
INSERT [dbo].[EMPREGADO] ([Nome], [Apelido1], [Apelido2], [NSS], [Rua], [Numero_Calle], [Piso], [CP], [Localidade], [Provincia], [DataNacemento], [Sexo], [NSSSupervisa], [NumDepartamentoPertenece]) VALUES (N'Paulo', N'Máximo', N'Guerra', N'9998888', N'Montero Ríos', 29, N'2-A', NULL, N'Santiago', NULL, CAST(N'1968-04-27T00:00:00.000' AS DateTime), N'H', N'7777777', 6)
GO
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'0010010', 8, 20)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'0110010', 7, 20)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'0999900', 1, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'0999900', 3, 20)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'0999900', 11, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1010001', 1, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1010001', 7, 15)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1010001', 11, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1100222', 5, 30)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1111111', 2, NULL)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1122331', 8, 35)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1231231', 4, 20)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1231231', 9, 20)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'1341431', 3, 15)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'2221111', 6, 20)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'2221111', 8, 10)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'3330000', 10, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'3338883', 8, 30)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'4044443', 8, 15)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'4444999', 2, NULL)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'6000006', 8, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'7000007', 10, 40)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'8888889', 1, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'8888889', 2, NULL)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'8888889', 7, 5)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'8888889', 11, 25)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'9876567', 4, 35)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'9876567', 9, 10)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'9900000', 2, NULL)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'9990009', 1, 6)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'9990009', 2, NULL)
INSERT [dbo].[EMPREGADO_PROXECTO] ([NSSEmpregado], [NumProxecto], [Horas]) VALUES (N'9990009', 7, 20)
GO
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'0010010', CAST(1500.00 AS Decimal(10, 2)), CAST(N'2014-02-12' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'0110010', CAST(1000.00 AS Decimal(10, 2)), CAST(N'2012-01-11' AS Date), N'Categoria 2')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'0999900', CAST(2500.00 AS Decimal(10, 2)), CAST(N'2000-03-22' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'1010001', CAST(3500.00 AS Decimal(10, 2)), CAST(N'2013-12-12' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'1100222', CAST(5500.00 AS Decimal(10, 2)), CAST(N'2014-05-11' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'1111111', CAST(600.00 AS Decimal(10, 2)), CAST(N'2013-03-12' AS Date), N'Categoria 4')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'1122331', CAST(1000.00 AS Decimal(10, 2)), CAST(N'2012-01-11' AS Date), N'Categoria 2')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'1231231', CAST(2500.00 AS Decimal(10, 2)), CAST(N'2000-03-22' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'2525252', CAST(1100.00 AS Decimal(10, 2)), CAST(N'2000-12-12' AS Date), N'Categoria 2')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'3330000', CAST(1100.00 AS Decimal(10, 2)), CAST(N'2001-06-07' AS Date), N'Categoria 2')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'6000006', CAST(2600.00 AS Decimal(10, 2)), CAST(N'2001-06-07' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'6565656', CAST(3500.00 AS Decimal(10, 2)), CAST(N'2000-12-12' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'7777777', CAST(1200.00 AS Decimal(10, 2)), CAST(N'2000-03-10' AS Date), N'Categoria 3')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'8888889', CAST(1000.00 AS Decimal(10, 2)), CAST(N'2012-07-17' AS Date), N'Categoria 2')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'9900000', CAST(2600.00 AS Decimal(10, 2)), CAST(N'2014-05-11' AS Date), N'Categoria 1')
INSERT [dbo].[EMPREGADOFIXO] ([NSS], [Salario], [DataAlta], [Categoria]) VALUES (N'9998888', CAST(3500.00 AS Decimal(10, 2)), CAST(N'2013-12-12' AS Date), N'Categoria 1')
GO
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'1341431', CAST(N'2007-02-23' AS Date), CAST(N'2025-02-13' AS Date), CAST(10.00 AS Decimal(10, 2)), CAST(25.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'2221111', CAST(N'2006-06-07' AS Date), CAST(N'2020-02-02' AS Date), CAST(15.00 AS Decimal(10, 2)), CAST(20.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'3338883', CAST(N'2006-06-07' AS Date), CAST(N'2024-02-02' AS Date), CAST(18.00 AS Decimal(10, 2)), CAST(32.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'4044443', CAST(N'2007-02-13' AS Date), CAST(N'2024-06-12' AS Date), CAST(11.00 AS Decimal(10, 2)), CAST(20.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'4444444', CAST(N'2007-06-17' AS Date), CAST(N'2017-02-02' AS Date), CAST(11.00 AS Decimal(10, 2)), CAST(25.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'4444999', CAST(N'2008-06-17' AS Date), CAST(N'2019-05-05' AS Date), CAST(12.00 AS Decimal(10, 2)), CAST(32.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'5000000', CAST(N'2003-06-21' AS Date), CAST(N'2020-12-11' AS Date), CAST(10.00 AS Decimal(10, 2)), CAST(30.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'5555000', CAST(N'2006-06-07' AS Date), CAST(N'2020-02-02' AS Date), CAST(15.00 AS Decimal(10, 2)), CAST(20.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'6000600', CAST(N'2007-02-13' AS Date), CAST(N'2020-02-02' AS Date), CAST(11.00 AS Decimal(10, 2)), CAST(20.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'7000007', CAST(N'2007-01-30' AS Date), CAST(N'2017-02-02' AS Date), CAST(12.00 AS Decimal(10, 2)), CAST(35.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'8888877', CAST(N'2008-06-17' AS Date), CAST(N'2019-05-05' AS Date), CAST(12.00 AS Decimal(10, 2)), CAST(30.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'9876567', CAST(N'2003-06-21' AS Date), CAST(N'2020-12-11' AS Date), CAST(10.00 AS Decimal(10, 2)), CAST(30.00 AS Decimal(10, 2)))
INSERT [dbo].[EMPREGADOTEMPORAL] ([NSS], [DataInicio], [DataFin], [CosteHora], [NumHoras]) VALUES (N'9990009', CAST(N'2001-06-07' AS Date), CAST(N'2020-02-02' AS Date), CAST(10.00 AS Decimal(10, 2)), CAST(30.00 AS Decimal(10, 2)))
GO
SET IDENTITY_INSERT [dbo].[LUGAR] ON

INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (1, 1, N'VIGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (2, 2, N'VIGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (3, 3, N'LUGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (4, 3, N'MONFORTE')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (5, 3, N'PONTEVEDRA')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (6, 3, N'SANTIAGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (7, 4, N'SANTIAGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (8, 4, N'VIGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (9, 5, N'A ESTRADA')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (10, 5, N'SANTIAGO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (11, 6, N'RIBADEO')
INSERT [dbo].[LUGAR] ([ID], [Num_departamento], [Lugar]) VALUES (12, 6, N'SANTIAGO')
SET IDENTITY_INSERT [dbo].[LUGAR] OFF
GO
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (1, N'XESTION DE PERSOAL', N'VIGO', 4)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (2, N'PORTAL', N'SANTIAGO', 4)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (3, N'APLICACIÓN CONTABLE', N'VIGO', 4)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (4, N'INFORME ESTADISTICO ANUAL', N'A ESTRADA', 5)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (5, N'PRODUCIÓN NOVO PRODUTO', N'RIBADEO', 6)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (6, N'DESEÑO NOVO CPD LUGO', N'LUGO', 3)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (7, N'MELLORAS SOCIAIS', N'VIGO', 1)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (8, N'DESEÑO NOVA TENDA VIGO', N'MONFORTE', 3)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (9, N'PROXECTO X', N'SANTIAGO', 5)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (10, N'PROXECTO Y', N'PONTEVEDRA', 3)
INSERT [dbo].[PROXECTO] ([NumProxecto], [NomeProxecto], [Lugar], [NumDepartControla]) VALUES (11, N'Proyecto Z', N'Badajoz', 1)
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_DEPARTAMENTO_NOME]    Script Date: 19/01/2026 9:28:48 ******/
ALTER TABLE [dbo].[DEPARTAMENTO] ADD  CONSTRAINT [UQ_DEPARTAMENTO_NOME] UNIQUE NONCLUSTERED
(
	[NomeDepartamento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_FAMILIAR_NSS]    Script Date: 19/01/2026 9:28:48 ******/
ALTER TABLE [dbo].[FAMILIAR] ADD  CONSTRAINT [UQ_FAMILIAR_NSS] UNIQUE NONCLUSTERED
(
	[Nss] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_LUGAR]    Script Date: 19/01/2026 9:28:48 ******/
ALTER TABLE [dbo].[LUGAR] ADD  CONSTRAINT [UK_LUGAR] UNIQUE NONCLUSTERED
(
	[Num_departamento] ASC,
	[Lugar] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_PROXECTO_NOME]    Script Date: 19/01/2026 9:28:48 ******/
ALTER TABLE [dbo].[PROXECTO] ADD  CONSTRAINT [UQ_PROXECTO_NOME] UNIQUE NONCLUSTERED
(
	[NomeProxecto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ_VEHICULO_MATRICULA]    Script Date: 19/01/2026 9:28:48 ******/
ALTER TABLE [dbo].[VEHICULO] ADD  CONSTRAINT [UQ_VEHICULO_MATRICULA] UNIQUE NONCLUSTERED
(
	[Matricula] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[EMPREGADO] ADD  DEFAULT ('M') FOR [Sexo]
GO
ALTER TABLE [dbo].[FAMILIAR] ADD  DEFAULT ('M') FOR [Sexo]
GO
ALTER TABLE [dbo].[DEPARTAMENTO]  WITH NOCHECK ADD  CONSTRAINT [FK_DEPARTAMENTO_EMPLEADO] FOREIGN KEY([NSSDirector])
REFERENCES [dbo].[EMPREGADO] ([NSS])
GO
ALTER TABLE [dbo].[DEPARTAMENTO] CHECK CONSTRAINT [FK_DEPARTAMENTO_EMPLEADO]
GO
ALTER TABLE [dbo].[EMPREGADO]  WITH NOCHECK ADD  CONSTRAINT [FK_EMPLEADO_DEPARTAMENTO] FOREIGN KEY([NumDepartamentoPertenece])
REFERENCES [dbo].[DEPARTAMENTO] ([NumDepartamento])
GO
ALTER TABLE [dbo].[EMPREGADO] CHECK CONSTRAINT [FK_EMPLEADO_DEPARTAMENTO]
GO
ALTER TABLE [dbo].[EMPREGADO]  WITH NOCHECK ADD  CONSTRAINT [FK_EMPLEADO_EMPLEADO] FOREIGN KEY([NSSSupervisa])
REFERENCES [dbo].[EMPREGADO] ([NSS])
GO
ALTER TABLE [dbo].[EMPREGADO] CHECK CONSTRAINT [FK_EMPLEADO_EMPLEADO]
GO
ALTER TABLE [dbo].[EMPREGADO_PROXECTO]  WITH CHECK ADD  CONSTRAINT [FK_EMPLEADO_PROYECTO_EMPLEADO] FOREIGN KEY([NSSEmpregado])
REFERENCES [dbo].[EMPREGADO] ([NSS])
GO
ALTER TABLE [dbo].[EMPREGADO_PROXECTO] CHECK CONSTRAINT [FK_EMPLEADO_PROYECTO_EMPLEADO]
GO
ALTER TABLE [dbo].[EMPREGADO_PROXECTO]  WITH CHECK ADD  CONSTRAINT [FK_EMPLEADO_PROYECTO_PROYECTO] FOREIGN KEY([NumProxecto])
REFERENCES [dbo].[PROXECTO] ([NumProxecto])
GO
ALTER TABLE [dbo].[EMPREGADO_PROXECTO] CHECK CONSTRAINT [FK_EMPLEADO_PROYECTO_PROYECTO]
GO
ALTER TABLE [dbo].[EMPREGADOFIXO]  WITH CHECK ADD  CONSTRAINT [FK_EMPLEADOFIXO_EMPLEADO] FOREIGN KEY([NSS])
REFERENCES [dbo].[EMPREGADO] ([NSS])
GO
ALTER TABLE [dbo].[EMPREGADOFIXO] CHECK CONSTRAINT [FK_EMPLEADOFIXO_EMPLEADO]
GO
ALTER TABLE [dbo].[EMPREGADOTEMPORAL]  WITH CHECK ADD  CONSTRAINT [FK_EMPLEADOTEMP_EMPLEADO] FOREIGN KEY([NSS])
REFERENCES [dbo].[EMPREGADO] ([NSS])
GO
ALTER TABLE [dbo].[EMPREGADOTEMPORAL] CHECK CONSTRAINT [FK_EMPLEADOTEMP_EMPLEADO]
GO
ALTER TABLE [dbo].[FAMILIAR]  WITH CHECK ADD  CONSTRAINT [FK_FAMILIAR_EMPREGADO] FOREIGN KEY([NssEmpregado])
REFERENCES [dbo].[EMPREGADO] ([NSS])
GO
ALTER TABLE [dbo].[FAMILIAR] CHECK CONSTRAINT [FK_FAMILIAR_EMPREGADO]
GO
ALTER TABLE [dbo].[LUGAR]  WITH CHECK ADD  CONSTRAINT [FK_LUGAR_DEPARTAMENTO] FOREIGN KEY([Num_departamento])
REFERENCES [dbo].[DEPARTAMENTO] ([NumDepartamento])
GO
ALTER TABLE [dbo].[LUGAR] CHECK CONSTRAINT [FK_LUGAR_DEPARTAMENTO]
GO
ALTER TABLE [dbo].[PROXECTO]  WITH CHECK ADD  CONSTRAINT [FK_PROYECTO_DEPARTAMENTO] FOREIGN KEY([NumDepartControla])
REFERENCES [dbo].[DEPARTAMENTO] ([NumDepartamento])
GO
ALTER TABLE [dbo].[PROXECTO] CHECK CONSTRAINT [FK_PROYECTO_DEPARTAMENTO]
GO
ALTER TABLE [dbo].[EMPREGADO]  WITH NOCHECK ADD  CONSTRAINT [CK_SEXO] CHECK  (([Sexo]='M' OR [Sexo]='H'))
GO
ALTER TABLE [dbo].[EMPREGADO] CHECK CONSTRAINT [CK_SEXO]
GO
ALTER TABLE [dbo].[FAMILIAR]  WITH CHECK ADD  CONSTRAINT [CHK_FAMILIAR_SEXO] CHECK  (([Sexo]='M' OR [Sexo]='H'))
GO
ALTER TABLE [dbo].[FAMILIAR] CHECK CONSTRAINT [CHK_FAMILIAR_SEXO]
GO
ALTER TABLE [dbo].[VEHICULO]  WITH NOCHECK ADD  CONSTRAINT [CHK_VEHICULO_TIPO] CHECK  (([Tipo]='D' OR [Tipo]='G'))
GO
ALTER TABLE [dbo].[VEHICULO] CHECK CONSTRAINT [CHK_VEHICULO_TIPO]
GO
USE [master]
GO
ALTER DATABASE [BDEMPRESA25] SET  READ_WRITE
GO