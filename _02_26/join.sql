use madang;

select * from customer; -- 5건
select * from orders; -- 10건
select * from customer, orders; -- 5 * 10건

select * from customer, orders where customer.custid = orders.custid;

-- 두 테이블에 중복되는 컬럼은 table명을 생략 x(custid)
-- 한 테이블에만 사용되는 컬럼은 table명 생략 o
-- 테이블명을 모두 명시하는 것이 가독성이 좋다

select customer.custid, name, orders.saleprice, orders.orderdate
 from customer, orders where customer.custid = orders.custid;

-- join 경우, 테이블 alias를 사용 권장 (단, alias를 사용할 경우 컬럼명에도 alias를 함께 사용)
select c.custid, c.name, o.saleprice, o.orderdate
 from customer c, orders o where c.custid = o.custid;
 
-- order by
select c.custid, c.name, o.saleprice, o.orderdate
 from customer c, orders o where c.custid = o.custid
 Order by c.custid;
 
-- sum(고객이름 <= 사실상 고객별 처리)
select c.name, sum(o.saleprice)
from customer c, orders o where c.custid = o.custid
group by c.name
order by c.name;

-- 고객별 sum을 구하는 데 동명이인이 있으면?
-- 고객의 구분자(식별자)인 Primary Key로 group by 필요
select c.name, sum(o.saleprice)
from customer c, orders o where c.custid = o.custid
group by c.custid -- Key는 group by에 올 수 있다.
order by c.name;

-- 3개의 테이블
select * from customer; -- 5건
select * from book; -- 10건
select * from orders; -- 10건
select * from customer,book,orders; -- 5 * 10건

-- 각 테이블 별 조건 추가
select c.name, c.address, b.bookname, o.orderdate
from customer c, book b, orders o
where c.custid = o.custid
	and b.bookid = o.bookid
    and c.name like '김%'
    and o.saleprice < 10000 ;-- select 항목 포함 X
    
-- 표준 SQL JOIN(ANSI SQL JOIN)
select c.custid, c.name, o.saleprice, o.orderdate
from customer c, orders o
where c.custid = o.custid;

-- 위 커리를 ANSI sql join으로 변경
select c.name, c.address,  b.bookname, o.orderdate -- * 로 카티젼프로덕트를 만들고 난 후 원하는 컬럼만 선택
  from orders o inner join customer c on o.custid = c.custid
                    inner join book b on o.bookid = b.bookid
where c.name like '김%' -- 고객이름이 김 으로 시작 ( select 항목 포함 )
   and o.saleprice < 10000; -- select 항목 포함 X

-- outer join
-- 모든 고객 대상으로 고객 이름 총 구매금액을 구하라
-- 단, 구매하지 않은 고객도 포함
select c.name, o.saleprice
from customer c left outer join orders o on c.custid  = o.custid;

-- 모든 도서 대상으로 도서 이름, 판매금액을 구하라 (단, 판매하지 않은 도서도 포함)
select b.bookname, o.saleprice
from book b left join orders o on b.bookid = o.bookid;

-- self join
-- hr db employee
-- first_name = 'Den' and last_name = 'Raphaely'인 사원이 관리하는 부하 사원의 이름,직급
-- 별칭을 정하지 않으면 한 행에 대해 manager_id와 employee_id가 같은 애들만 선택
-- 즉, 최상의 상사만(manager가 없는 경우 자신이 manager인 경우)선택됨
-- 따라서 별칭을 두어 마치 2개의 테이블 인 것처럼 조건을 설정해야 함  
use hr;
select *
from employees staff ,employees manager
where staff.manager_id = manager.employee_id
and manager.first_name = 'Den'
and manager.last_name = 'Raphaely';
 