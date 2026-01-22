
/**
 * @author ISRAEL BEJAMÍN GAGO ACUÑA 54321342b
 */

--1
CREATE FUNCTION fn_obtenerNumFotos (
    @nombre VARCHAR(50)
)
RETURNS int
AS
BEGIN
    DECLARE @numFotos int;

    SELECT @numFotos = NUMFOTOGRAFIAS
    FROM FOTOGRAFO WHERE NOME = @nombre

    RETURN @numFotos;
END;


--2
if OBJECT_ID('sp_getLocalidadYProvincia', 'P') IS NOT NULL
    DROP PROCEDURE sp_getLocalidadYProvincia
GO
Create procedure sp_getLocalidadYProvincia @nombreExposicion varchar(50),
                                           @localidadYProvincia varchar(50) output
as
Begin
    select @localidadYProvincia = l.NOME + '(' + p.NOME + ')'
    from PROVINCIA p
             inner join LOCALIDADE l
                        on p.CODIGO = l.COD_PROVINCIA
    where l.CODIGO = (select e.COD_LOCALIDADE
                      from EXPOSICION e
                      where e.NOME = @nombreExposicion)
end;

--3
if OBJECT_ID('sp_getNombreTipoFotografiasExposicion', 'P') IS NOT NULL
    DROP PROCEDURE sp_getNombreTipoFotografiasExposicion
GO
Create procedure sp_getNombreTipoFotografiasExposicion @nombreExposicion varchar(50)
as
Begin
    select
        CASE
            WHEN EXISTS(SELECT 1 FROM DOCUMENTAL where DOCUMENTAL.CODIGO = f.CODIGO)
            then 'DOCUMENTAL'
            else 'ARTISTICA'
        end
        +' '+f.NOME+' -'+fg.NOME+'-'
    from EXPOSICION e inner join FOTOGRAFIA f
    on e.CODIGO = f.COD_EXPOSICION
    inner join FOTOGRAFO fg on fg.CODIGO = f.COD_FOTOGRAFO
    where e.NOME =@nombreExposicion
end;

