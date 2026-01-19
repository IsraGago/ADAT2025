-- 1
if OBJECT_ID('sp_CambioDomicilio','P') IS NOT NULL
    DROP PROCEDURE sp_CambioDomicilio
GO

CREATE PROCEDURE sp_CambioDomicilio
    @nss varchar(15),
    @rua varchar(30),
    @numero_calle int,
    @piso varchar(4),
    @cp char(5),
    @localidade varchar(25)
AS
BEGIN
    UPDATE EMPREGADO
    SET Rua = @rua,
        Numero_calle = @numero_calle,
        Piso = @piso,
        CP = @cp,
        Localidade = @localidade
    WHERE NSS = @nss
END

--2
if OBJECT_ID('sp_DatosProyectos','P') IS NOT NULL
    DROP PROCEDURE sp_DatosProyectos
GO

CREATE PROCEDURE sp_DatosProyectos
    @Num_proxecto int,
    @Nome_proxecto varchar(25) OUTPUT,
    @Lugar varchar(25) OUTPUT,
    @Num_departamento_controla int OUTPUT
AS
BEGIN
    SELECT
        @Nome_proxecto = NomeProxecto,
        @Lugar = Lugar,
        @Num_departamento_controla = NumDepartControla
    FROM PROXECTO
    WHERE NumProxecto = @Num_proxecto
END

--3
if OBJECT_ID('sp_DepartControlaProxec','P') IS NOT NULL
    DROP PROCEDURE sp_DepartControlaProxec
GO
CREATE PROCEDURE sp_DepartControlaProxec
    @NUM int
AS
BEGIN
    SELECT *
    FROM DEPARTAMENTO
    WHERE NumDepartamento in (
        SELECT NumDepartControla
        From PROXECTO
        GROUP BY NumDepartControla
        HAVING count(*) >= @NUM
        );
END

--4
if OBJECT_ID('fn_nEmpDepart','FN') IS NOT NULL
    DROP FUNCTION fn_nEmpDepart
GO

CREATE FUNCTION fn_nEmpDepart(
    @nomeDepartamento varchar(25)
)
returns int
AS
    BEGIN
        RETURN (
            SELECT count(*)
            FROM EMPREGADO
            WHERE NumDepartamentoPertenece = (
                SELECT NumDepartamento
                FROM DEPARTAMENTO
                WHERE NomeDepartamento = @nomeDepartamento
                )
            );
    end
GO

-- EXTRAS

--5.1
IF OBJECT_ID('fn_ObtenerTipoEmpleado','FN') IS NOT NULL
    DROP FUNCTION fn_ObtenerTipoEmpleado
GO

CREATE FUNCTION fn_ObtenerTipoEmpleado (
    @nss VARCHAR(20)
)
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @tipo VARCHAR(20);

    IF NOT EXISTS (SELECT 1 FROM EMPREGADO WHERE NSS = @nss)
    BEGIN
        SET @tipo = 'NO_EXISTE';
        RETURN @tipo;
    END

    ELSE
    BEGIN
        SET @tipo = CASE
            WHEN EXISTS (SELECT 1 FROM EMPREGADOFIXO WHERE NSS = @nss) THEN 'FIJO'
            WHEN EXISTS (SELECT 1 FROM EMPREGADOTEMPORAL WHERE NSS = @nss) THEN 'TEMPORAL'
            ELSE 'SIN_CLASE'
        END;
    END

    RETURN @tipo;
END
-- SELECT dbo.fn_ObtenerTipoEmpleado('54321342') AS Tipo

--5.2
if OBJECT_ID('sp_ObtenerTipoEmpleado','P') IS NOT NULL
    DROP PROCEDURE sp_ObtenerTipoEmpleado
GO
Create procedure sp_ObtenerTipoEmpleado
    @nss varchar(15),
    @tipo varchar(20) output
as
    Begin
        if NOT EXISTS (Select 1 FROM EMPREGADO WHERE NSS = @nss)
        Begin
            set @tipo = 'NO_EXISTE'
            RETURN;
        end
        select @tipo =
               CASE
                   WHEN EXISTS (SELECT 1 FROM EMPREGADOFIXO WHERE NSS = @nss)
                        then 'fijo'
                        else 'temporal'
                end
    end
