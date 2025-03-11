use hr;

-- 전체 사원 리스트
select * from employees;

-- 집계 처리
select avg(salary) from employees; -- 1 row
select department_id , avg(salary) from employees group by department_id; -- null 포함 부서수만큼 row
-- 전체 사원의 리스트를 뽑되, employee_id, department_id, 부서별 평균 급여

-- #1 inline-view (null)
select e.employee_id , e.department_id, das.avg_salary from employees e,
	(select department_id , avg(salary) avg_salary from employees group by department_id) das
	where e.department_id = das.department_id; 

-- #2 scalar
select e.employee_id , e.department_id, (select avg(ee.salary) from employees ee where ee.department_id = e.department_id) avg_salary
	from employees e;
    
-- #3 partition-by
-- 일반적인 GROUP by는 데이ㅓ를 그룹별로 묶고 하나의 결과만 반환하지만, PARTITION BY는 그룹별로 윈도우를 생성하여 행마다 연산된 결과를 반환
-- 각 행이 유지되면서 그룹별 평균이 계산된다
select employee_id, department_id, avg(salary) over (partition by department_id) avg_salary
	from employees;
    
-- 2. group_concat()
-- row ==> column concat
select first_name from employees limit 5; -- 5개의 row로 출력

select group_concat(e.first_name) from (select first_name from employees limit 5) e;
select group_concat(e.first_name separator '|') from (select first_name from employees limit 5) e;

-- 3. rollup
select job_id, sum(salary) from employees group by job_id;
select job_id, sum(salary) from employees group by job_id with rollup;
select department_id, job_id, sum(salary) from employees group by department_id, job_id;
select department_id, job_id, sum(salary) from employees group by department_id, job_id with rollup;