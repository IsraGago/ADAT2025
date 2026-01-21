-- 1. Procedimiento sp_CambioDomicilio
DROP PROCEDURE IF EXISTS sp_CambioDomicilio;
DELIMITER //
CREATE PROCEDURE sp_CambioDomicilio(
    IN p_nss VARCHAR(15),
    IN p_rua VARCHAR(30),
    IN p_numero_calle INT,
    IN p_piso VARCHAR(4),
    IN p_cp CHAR(5),
    IN p_localidade VARCHAR(25)
)
BEGIN
UPDATE EMPREGADO
SET Rua = p_rua,
    Numero_calle = p_numero_calle,
    Piso = p_piso,
    CP = p_cp,
    Localidade = p_localidade
WHERE NSS = p_nss;
END //

-- 2. Procedimiento sp_DatosProyectos
DROP PROCEDURE IF EXISTS sp_DatosProyectos;
CREATE PROCEDURE sp_DatosProyectos(
    IN p_Num_proxecto INT,
    OUT p_Nome_proxecto VARCHAR(25),
    OUT p_Lugar VARCHAR(25),
    OUT p_Num_departamento_controla INT
)
BEGIN
SELECT NomeProxecto, Lugar, NumDepartControla
    INTO p_Nome_proxecto, p_Lugar, p_Num_departamento_controla
FROM PROXECTO
WHERE NumProxecto = p_Num_proxecto;
END //

-- 3. Procedimiento sp_DepartControlaProxec
DROP PROCEDURE IF EXISTS sp_DepartControlaProxec;
CREATE PROCEDURE sp_DepartControlaProxec(IN p_NUM INT)
BEGIN
SELECT *
FROM DEPARTAMENTO
WHERE NumDepartamento IN (
    SELECT NumDepartControla
    FROM PROXECTO
    GROUP BY NumDepartControla
    HAVING COUNT(*) >= p_NUM
);
END //

-- 4. Función fn_nEmpDepart
DROP FUNCTION IF EXISTS fn_nEmpDepart;
CREATE FUNCTION fn_nEmpDepart(p_nomeDepartamento VARCHAR(25))
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE v_count INT;
SELECT COUNT(*) INTO v_count
FROM EMPREGADO
WHERE NumDepartamentoPertenece = (
    SELECT NumDepartamento
    FROM DEPARTAMENTO
    WHERE NomeDepartamento = p_nomeDepartamento
    );
RETURN v_count;
END //

-- 5.1 Función fn_ObtenerTipoEmpleado (Extra)
DROP FUNCTION IF EXISTS fn_ObtenerTipoEmpleado;
CREATE FUNCTION fn_ObtenerTipoEmpleado(p_nss VARCHAR(20))
RETURNS VARCHAR(20)
READS SQL DATA
BEGIN
    DECLARE v_tipo VARCHAR(20);

IF NOT EXISTS (SELECT 1 FROM EMPREGADO WHERE NSS = p_nss) THEN
        SET v_tipo = 'NO_EXISTE';
ELSE
        IF EXISTS (SELECT 1 FROM EMPREGADOFIXO WHERE NSS = p_nss) THEN
            SET v_tipo = 'FIJO';
ELSEIF EXISTS (SELECT 1 FROM EMPREGADOTEMPORAL WHERE NSS = p_nss) THEN
            SET v_tipo = 'TEMPORAL';
ELSE
            SET v_tipo = 'SIN_CLASE';
END IF;
END IF;

RETURN v_tipo;
END //

-- 5.2 Procedimiento sp_ObtenerTipoEmpleado (Extra)
DROP PROCEDURE IF EXISTS sp_ObtenerTipoEmpleado;
CREATE PROCEDURE sp_ObtenerTipoEmpleado(
    IN p_nss VARCHAR(15),
    OUT p_tipo VARCHAR(20)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM EMPREGADO WHERE NSS = p_nss) THEN
        SET p_tipo = 'NO_EXISTE';
ELSE
SELECT
    CASE
        WHEN EXISTS (SELECT 1 FROM EMPREGADOFIXO WHERE NSS = p_nss) THEN 'fijo'
        ELSE 'temporal'
        END INTO p_tipo;
END IF;
END //

DELIMITER ;