/* 2 */
DROP PROCEDURE IF EXISTS sp_CambiarDomicilio;

DELIMITER //

create procedure sp_CambiarDomicilio(
    in p_nss VARCHAR(15),
    in p_rua VARCHAR(30),
    in p_numero_calle int,
    in p_piso VARCHAR(4),
    in p_cp char(5),
    in p_localidade varchar(30)
)


DELIMITER ;

/* 2 */

DELIMITER //

create procedure sp_DatosProxectos(
    IN p_NumProxecto int,
    OUT p_NomeProxecto varchar(25),
    OUT p_Lugar VARCHAR(25),
    OUT p_NumDepartControla INT
)

BEGIN
    SELECT  NomeProxecto,Lugar,NumDepartControla
    INTO p_NomeProxecto,p_Lugar,p_NumDepartControla
    FROM PROXECTO
    WHERE NumProxecto = @p_NumProxecto
end //


DELIMITER ;