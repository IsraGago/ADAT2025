-- 1. Departamentos que controlan algún proyecto
SELECT *
FROM DEPARTAMENTO
WHERE NumDepartamento IN (SELECT DISTINCT NumDepartControla FROM PROXECTO);

-- Con EXISTS (Completando el hueco que tenías)
SELECT d.NumDepartamento, d.NomeDepartamento
FROM DEPARTAMENTO d
WHERE EXISTS (
    SELECT 1 FROM PROXECTO p WHERE p.NumDepartControla = d.NumDepartamento
);

-- 2. Directores de departamentos que controlan proyectos
SELECT d.NumDepartamento, d.NomeDepartamento, Nome, Apelido1, Apelido2
FROM EMPREGADO e
         INNER JOIN DEPARTAMENTO d ON e.NSS = d.NSSDirector
WHERE NumDepartamento IN (SELECT DISTINCT NumDepartControla FROM PROXECTO);

-- Consulta Pepa (Directores que participan en algún proyecto)
SELECT d.NumDepartamento, d.NomeDepartamento, Nome, Apelido1, Apelido2
FROM DEPARTAMENTO d
         JOIN EMPREGADO e ON d.NSSDirector = e.NSS
WHERE EXISTS (
    SELECT 1 FROM EMPREGADO_PROXECTO ep WHERE ep.NSSEmpregado = e.NSS
);

-- 3. Cálculo de edad (Cambiado strftime por DATEDIFF y lógicas de SQL Server)
SELECT NSS,
       Nome,
       Apelido1,
       Apelido2,
       DATEDIFF(YEAR, DataNacemento, GETDATE()) -
       CASE
           WHEN (MONTH(DataNacemento) > MONTH(GETDATE())) OR
                (MONTH(DataNacemento) = MONTH(GETDATE()) AND DAY(DataNacemento) > DAY(GETDATE()))
               THEN 1 ELSE 0
           END AS edad
FROM EMPREGADO;

-- 4. Empleados de 'PERSOAL' con tipo (Cambiado || por + y COALESCE)
SELECT e.Nome + ' ' + e.Apelido1 + ' ' + ISNULL(e.Apelido2, '') AS empleado,
    CASE
        WHEN ef.NSS IS NOT NULL THEN 'Fixo'
        WHEN et.NSS IS NOT NULL THEN 'Temporal'
        ELSE 'Sen categoría'
        END AS tipo
FROM EMPREGADO e
         JOIN DEPARTAMENTO d ON e.NumDepartamentoPertenece = d.NumDepartamento
         LEFT JOIN EMPREGADOFIXO ef ON e.NSS = ef.NSS
         LEFT JOIN EMPREGADOTEMPORAL et ON e.NSS = et.NSS
WHERE d.NomeDepartamento = 'PERSOAL';

-- 5. Empleados de Vigo en proyecto MELLORAS SOCIAIS (Cambiado CONCAT/comillas)
SELECT e.NSS,
       (e.Nome + ' ' + e.Apelido1 + ' ' + ISNULL(e.Apelido2, '')) AS nombreCompleto,
       ef.Salario,
       d.NomeDepartamento
FROM PROXECTO p
         INNER JOIN EMPREGADO_PROXECTO ep ON ep.NumProxecto = p.NumProxecto
         INNER JOIN EMPREGADO e ON e.NSS = ep.NSSEmpregado
         INNER JOIN EMPREGADOFIXO ef ON e.NSS = ef.NSS
         INNER JOIN DEPARTAMENTO d ON d.NumDepartamento = e.NumDepartamentoPertenece
WHERE e.Localidade = 'Vigo'
  AND p.NomeProxecto = 'MELLORAS SOCIAIS';

-- 6. Recuento de fijos y temporales por departamento
SELECT d.NumDepartamento,
       d.NomeDepartamento,
       COUNT(DISTINCT ef.NSS) AS NumEmpregadosFixos,
       COUNT(DISTINCT et.NSS) AS NumEmpregadosTemporais
FROM DEPARTAMENTO d
         LEFT JOIN EMPREGADO e ON e.NumDepartamentoPertenece = d.NumDepartamento
         LEFT JOIN EMPREGADOFIXO ef ON ef.NSS = e.NSS
         LEFT JOIN EMPREGADOTEMPORAL et ON et.NSS = e.NSS
GROUP BY d.NumDepartamento, d.NomeDepartamento;

-- 7. Departamentos con más de 5 empleados
SELECT d.NumDepartamento, COUNT(e.NSS) AS numEmpleados
FROM DEPARTAMENTO d
         INNER JOIN EMPREGADO e ON d.NumDepartamento = e.NumDepartamentoPertenece
GROUP BY d.NumDepartamento
HAVING COUNT(e.NSS) > 5;

-- 8. Empleados fijos con salario > 1000
SELECT e.*, ef.Salario
FROM EMPREGADO e
         INNER JOIN EMPREGADOFIXO ef ON e.NSS = ef.NSS
WHERE ef.Salario > 1000;

-- 9. Empleado con salario máximo por departamento
SELECT
    d.NumDepartamento,
    d.NomeDepartamento,
    e.Nome,
    e.Apelido1,
    e.Apelido2,
    ef.Salario
FROM EMPREGADO e
         JOIN EMPREGADOFIXO ef ON e.NSS = ef.NSS
         JOIN DEPARTAMENTO d ON e.NumDepartamentoPertenece = d.NumDepartamento
WHERE ef.Salario = (
    SELECT MAX(ef2.Salario)
    FROM EMPREGADO e2
             JOIN EMPREGADOFIXO ef2 ON e2.NSS = ef2.NSS
    WHERE e2.NumDepartamentoPertenece = e.NumDepartamentoPertenece
)
ORDER BY d.NomeDepartamento ASC;

-- 10. Departamento con el mayor número de proyectos (Subconsulta con Alias obligatorio)
SELECT
    d.NumDepartamento,
    d.NomeDepartamento,
    COUNT(p.NumProxecto) AS NumProxectos
FROM DEPARTAMENTO d
         LEFT JOIN PROXECTO p ON d.NumDepartamento = p.NumDepartControla
GROUP BY d.NumDepartamento, d.NomeDepartamento
HAVING COUNT(p.NumProxecto) = (
    SELECT MAX(T.NumProxectos)
    FROM (
             SELECT COUNT(*) AS NumProxectos
             FROM PROXECTO
             GROUP BY NumDepartControla
         ) AS T
);