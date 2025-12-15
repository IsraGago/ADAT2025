--1
SELECT *
From DEPARTAMENTO
where NumDepartamento in (select distinct NumDepartControla from PROXECTO)

--2
select d.NumDepartamento, d.NomeDepartamento, Nome, Apelido1, Apelido2
from EMPREGADO e
         inner join DEPARTAMENTO d
                    on e.NSS = d.NSSDirector
where NumDepartamento in (select distinct NumDepartControla from PROXECTO)

--3 //
Select NSS,
       Nome,
       Apelido1,
       Apelido2,
       (strftime('%Y', 'now') - strftime('%Y', DataNacemento))
           - (strftime('%m-%d', 'now') < strftime('%m-%d', DataNacemento)) AS edad
from EMPREGADO

--4 // TODO: hacer case
Select *
from Departamento
where NomeDepartamento = "PERSOAL"

--5
select e.nss,concat(Nome," ",Apelido1," ",Apelido2) as nombreCompleto,Salario,NomeDepartamento
from PROXECTO p
inner join EMPREGADO_PROXECTO ep on ep.NumProxecto = p.NumProxecto
inner join EMPREGADO e on e.NSS = ep.NSSEmpregado
inner join EMPREGADOFIXO ef on e.NSS =ef.NSS
inner join DEPARTAMENTO d on d.NumDepartamento = e.NumDepartamentoPertenece
where e.Localidade = "Vigo" AND p.NomeProxecto = "MELLORAS SOCIAIS"

--6


--7
select NumDepartamento,count(NomeDepartamento) as numEpleados
from DEPARTAMENTO d inner join EMPREGADO e
on d.NumDepartamento = e.NumDepartamentoPertenece
group by NumDepartamento
having numEpleados > 5

--8
select *
from EMPREGADO e
inner join EMPREGADOFIXO ef on e.nss = ef.NSS
where salario > 1000

--9


--10