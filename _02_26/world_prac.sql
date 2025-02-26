use world;
desc country; -- 테이블의 구조를 확인

-- 1. country 테이블에서 유럽 대륙에 속하는 모든 국가의 인구수의 총합은?
select sum(Population) from country where Continent = 'Europe';
-- 2. conutry 테이블에서 대륙(Continent)별 건수, 최대인구수, 최소 Gnp, 최대 Gnp, 평균 기대수명을 구하시오
select Continent,count(*), max(Population), min(GNP), max(GNP), avg(LifeExpectancy) 
from country
group by Continent;

-- 3. 도시명 Seoul 속한 국가의 이름, 인구수, GNP 를 조회하시오.
select co.name, co.Population, co.GNP
from country co, city ci
where co.Code = ci.CountryCode
	and ci.Name = 'Seoul';
    
-- ANSI
select co.name, co.Population, co.GNP
from country co join city ci on co.Code = ci.CountryCode
where ci.Name = 'Seoul';

-- 4. 공식언어의 사용률이 50%가 넘는 국가의 이름, 공식언어, 사용률을 조회하시오
select co.name, cl.Language, cl.Percentage
from country co join countrylanguage cl on co.Code = cl.CountryCode
where cl.Percentage >= 50.0;

-- 문제: 부서별 평균 급여와 직무별 급여 차이 계산
-- 각 부서에 대해, 해당 부서에 속한 직원들의 평균 급여를 계산해보자
-- 각 직원에 대해, 해당 직원의 급여와 부서의 평균 급여 차이를 계산해보자(salary_diff)
-- 출력 컬럼(first_naem,last_name,department_name,salary,avg_salary,salary_diff
-- 급여 차이는 (급여 - 부서 평균 급여)로 계산,음수여도 됨
-- 결과는 급여 차이를 기준으로 내림차순 정렬

-- Hint!! 
-- 서브쿼리로 employee 테이블에서 department_id를 기준으로 Group by하여 부서별 급여 평균을 구하고
-- 이 서브쿼리를 통해 만들어진 각 부서의 평균 급여를 포함하는 임시테이블(dept_avg)를 생성하여 Join!!
use hr;

SELECT
    e.first_name,
    e.last_name,
    d.department_name,
    e.salary,
    dept_avg.avg_salary,
    (e.salary - dept_avg.avg_salary) AS salary_diff
FROM
    employees e
JOIN
    departments d ON e.department_id = d.department_id
JOIN
    (SELECT department_id, AVG(salary) AS avg_salary
     FROM employees
     GROUP BY department_id) dept_avg
     ON e.department_id = dept_avg.department_id
ORDER BY
    salary_diff DESC;


-- 문제: 국가별 평균 인구보다 인구가 많은 도시를 찾아보자
-- 국가별 평균 도시 인구를 계산해보자(avg_pop)
-- 각 도시의 인구와 해당 국가의 평균 도시 인구 차이를 계산(pop_diff)
-- 인구가 국가별 평균 이상인 도시만 출력
-- 인구 차이 기준 내림차순
-- 출력 컬럼(city_name,country_name,population,avg_pop,pop_diff)
-- pop_diff는 (도시 인구 - 국가 평균 도시 인구)로 계산

USE world;



SELECT
    c.Name AS city_name,
    co.Name AS country_name,
    c.Population AS population,
    country_avg.avg_pop,
    (c.Population - country_avg.avg_pop) AS pop_diff
FROM
    City c
JOIN
    Country co ON c.CountryCode = co.Code
JOIN
    (SELECT CountryCode, AVG(Population) AS avg_pop
     FROM City
     GROUP BY CountryCode) country_avg
     ON c.CountryCode = country_avg.CountryCode
WHERE
    c.Population >= country_avg.avg_pop
ORDER BY
    pop_diff DESC;

