--1
SELECT *
From DEPARTAMENTO
where NumDepartamento in (select distinct NumDepartControla from PROXECTO);

--2
select d.NumDepartamento, d.NomeDepartamento, Nome, Apelido1, Apelido2
from EMPREGADO e
         inner join DEPARTAMENTO d
                    on e.NSS = d.NSSDirector
where NumDepartamento in (select distinct NumDepartControla from PROXECTO);

--3 //
Select NSS,
       Nome,
       Apelido1,
       Apelido2,
       (strftime('%Y', 'now') - strftime('%Y', DataNacemento))
           - (strftime('%m-%d', 'now') < strftime('%m-%d', DataNacemento)) AS edad
from EMPREGADO;

--4 // TODO: hacer case
SELECT
    e.Nome || ' ' || e.Apelido1 || ' ' || COALESCE(e.Apelido2, '') AS Empregado,
    CASE
        WHEN ef.NSS IS NOT NULL THEN 'Fixo'
        WHEN et.NSS IS NOT NULL THEN 'Temporal'
        ELSE 'Sen categoría'
    END AS Tipo
FROM EMPREGADO e
JOIN DEPARTAMENTO d
    ON e.NumDepartamentoPertenece = d.NumDepartamento
LEFT JOIN EMPREGADOFIXO ef
    ON e.NSS = ef.NSS
LEFT JOIN EMPREGADOTEMPORAL et
    ON e.NSS = et.NSS
WHERE d.NomeDepartamento = 'PERSOAL';


--5
select e.nss, concat(Nome, " ", Apelido1, " ", Apelido2) as nombreCompleto, Salario, NomeDepartamento
from PROXECTO p
         inner join EMPREGADO_PROXECTO ep on ep.NumProxecto = p.NumProxecto
         inner join EMPREGADO e on e.NSS = ep.NSSEmpregado
         inner join EMPREGADOFIXO ef on e.NSS = ef.NSS
         inner join DEPARTAMENTO d on d.NumDepartamento = e.NumDepartamentoPertenece
where e.Localidade = "Vigo"
  AND p.NomeProxecto = "MELLORAS SOCIAIS";

--6
SELECT d.NumDepartamento,
       d.NomeDepartamento,
       COUNT(DISTINCT ef.NSS) AS NumEmpregadosFixos,
       COUNT(DISTINCT et.NSS) AS NumEmpregadosTemporais
FROM DEPARTAMENTO d
LEFT JOIN EMPREGADO e
    ON e.NumDepartamentoPertenece = d.NumDepartamento
LEFT JOIN EMPREGADOFIXO ef ON ef.NSS = e.NSS
LEFT JOIN EMPREGADOTEMPORAL et ON et.NSS = e.NSS
GROUP BY d.NumDepartamento, d.NomeDepartamento;


--7
select NumDepartamento, count(NomeDepartamento) as numEpleados
from DEPARTAMENTO d
         inner join EMPREGADO e
                    on d.NumDepartamento = e.NumDepartamentoPertenece
group by NumDepartamento
having numEpleados > 5;

--8
select *
from EMPREGADO e
         inner join EMPREGADOFIXO ef on e.nss = ef.NSS
where salario > 1000;

--9
    SELECT
    d.NumDepartamento,
    d.NomeDepartamento,
    e.Nome,
    e.Apelido1,
    e.Apelido2,
    ef.Salario
FROM EMPREGADO e
JOIN EMPREGADOFIXO ef
    ON e.NSS = ef.NSS
JOIN DEPARTAMENTO d
    ON e.NumDepartamentoPertenece = d.NumDepartamento
WHERE ef.Salario = (
    SELECT MAX(ef2.Salario)
    FROM EMPREGADO e2
    JOIN EMPREGADOFIXO ef2
        ON e2.NSS = ef2.NSS
    WHERE e2.NumDepartamentoPertenece = e.NumDepartamentoPertenece
)
ORDER BY d.NomeDepartamento ASC;


--10
SELECT
    d.NumDepartamento,
    d.NomeDepartamento,
    COUNT(p.NumProxecto) AS NumProxectos
FROM DEPARTAMENTO d
LEFT JOIN PROXECTO p
    ON d.NumDepartamento = p.NumDepartControla
GROUP BY d.NumDepartamento, d.NomeDepartamento
HAVING COUNT(p.NumProxecto) = (
    SELECT MAX(NumProxectos)
    FROM (
        SELECT COUNT(*) AS NumProxectos
        FROM PROXECTO
        GROUP BY NumDepartControla
    )
);
