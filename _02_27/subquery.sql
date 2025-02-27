use madang;

-- limit offset
select * from book limit 3 offset 2;
select * from book limit 3, 2; -- offset 생략 주의!
-- schema, table 대소문자 구분 ( 생성시 옵션에 따라 구성 ) binary(name) = 'ABC'
-- subquery
select max(price) from book;  -- 가장 비싼 금액(35000)
select bookname from book where price = 35000;  -- 위에서 구한 결과가 다른 쿼리에 사용
select bookname from book where price = ( select max(price) from book );
-- subquery 결과의 구분
select max(price) from book; -- 단일행, 단일열
select bookid, bookname from book; -- 다중행, 다중열
select bookid from book; -- 다중행, 단일열
select bookid, bookname from book where bookid = 3;  -- 단일행, 다중열

-- Error Code: 1242. Subquery returns more than 1 row	0.000 sec
select bookname from book where price = (select price from book);

-- Error Code: 1241
select bookname from book where price in ( select bookid, price  from book );
select bookname from book where price in ( select price  from book );  -- price 만 in 비교
select bookname from book where (bookid, price) in ( select bookid, price  from book ); -- bookid, price 함께 비교


-- subquery 사용 위치에 따른 구분
-- select (subquery) : scalar subquery (select 된 row건건 별로 subquery를 수행) 무조건 단일 행
-- from(subquery) : inline-view subquery (가상의 테이블) 모든 단일다중 다 가능
-- where(subquery) : nested subquery (사용되는 조건에 맞게 케바케)

select name from customer where custid in (select distinct custid from orders); -- orders에는 중복되는 custid가 있기 때문에 distinct를 이용하면 더 빠름

-- subquery를 join으로 바꾼다면?
select customer.name from customer, orders where customer.custid = orders.custid; -- 10건
select distinct customer.name from customer, orders where customer.custid = orders.custid; -- 4건 
-- 위 join은 여러 건의 카티젼 프로덕트를 만든 다음 다시 distinct로 줄인다.alter

-- 실행계획 (execution plan)
-- 어렵다 이유
-- 1. 동일 데이터에 대한 동일 쿼리의 비용이 DB마다 다 다르다.
-- 2. 동일 테이블에 데이터 건수가 변경이 되면 비용이 달라진다.
-- 3. 좋은(비싼) DBMS는 실행 계획을 만드는 나름대로의 비책이 있음ㅋㅋㅋㅋ

-- 어떤 쿼리를 작성할 때, 조인 또는 서브쿼리로 할 건지 판단해야 하고 이때 실행계획을 기본으로 선택
-- 조인이 더 빠르다. 서브쿼리가 더 빠르다. 선입견 ㄴㄴ
-- 조인으로 작성된 쿼리는 DBMS가 실행 계획을 작성할 때, 능동적으로 개입
-- 서브쿼리로 작성된 쿼리는 DBMS가 실행 계획을 작성할 때, 능동적으로 개입하기 어렵다. <- 쿼리 자체가 순서가 정해져 있기 때문

select bookid from book where publisher = '대한미디어';
select custid from orders where bookid in (select bookid from book where publisher = '대한미디어');
select name from customer where custid in (
	select custid from orders where bookid in (
		select bookid from book where publisher = '대한미디어')
	);
    
-- correlated subquery
-- 모든 도서 중에 해당 도서의 출판사로부터 발행된 도서의 평균가격보다 큰 가격의 도서를 구하시오.

select * from book b1
where b1.price > (select avg(b2.price) from book b2 where b1.publisher = b2.publisher);

-- subquery with 연산자
-- = (select)
-- in (select)
-- > all(select ...): 왼쪽의 항목이 오른쪽 값 전부 만족
-- > some(any) (select...) : 왼쪽의 항목이 오른쪽 값 중 하나라도 만족하면 만족

-- p234
select orderid,saleprice from orders where saleprice <= (select avg(saleprice) from orders);

-- 각 고객의 평균 주문금액보다 큰...
-- 고객마다 평균 금액이 다 다르다
-- 각각의 주문 건에 대해서서 서브쿼리에 custid가 전달되고 서브쿼리에서 그 custid 별 평균금액이 계산되어야 한다.
select o1.orderid, o1.custid, o1.saleprice from orders o1 
where o1.saleprice > (select avg(o2.saleprice) from orders o2 where o1.custid = o2.custid);

select * from orders
where saleprice > (select max(saleprice) from orders where custid = 3); -- max로 최고금액

select * from orders
where saleprice > all (select saleprice from orders where custid = 3); -- all로 최고금액

-- scalar는 select 결과 전체를 1건 1건 서브쿼리를 실행하고 그 결과를 1건 row 결과에 포함alter
select o.custid, (select c.name from customer c where o.custid = c.custid) name, sum(o.saleprice) total
from orders o group by o.custid;

-- orders 테이블에 name 컬럼 추가
alter table orders add bname varchar(40);

-- orders 테이블에 각 주문에 맞는 도서이름을 입력
UPDATE orders Set bname = (SELECT bookname from book where book.bookid = orders.bookid);

-- 인라인 뷰: from 절에서 사용되는 부속 질의를 말한다.
-- 고객번호가 2 이하인 고객의 판매액을 나타내시오.(고객이름과 고객별 판매액 출력)
select c.name, sum(o.saleprice) 'total'
	from orders o, (select custid, name from customer where custid <= 2) c
	where o.custid = c.custid
    group by c.name;
    
    
-- union, union all
-- union 대상이 되는 컬럼 구성이 동일해야 한다
-- 동일 테이블에 대한 union 사용 지양(비슷한 컬럼의 구성, 비슷한 성격의 테이블 등에서 사용)
-- 전체 5건
select name from customer where address like '대한민국%' -- 3건
union -- 중복 제거
select name from customer where custid in (select distinct custid from orders); -- 4건

-- union all은 중복 제거 X

-- drop: 테이블 삭제
-- delete: 테이블 삭제 X, 데이터 삭제
-- delete from orders: <= transaction 관련 직업 동시 log 파일 생성 작업이 진행
-- truncate : 테이블의 모든 데이터 삭제, 롤백 불가능, log 파일 생성 작업 없이 바로 완전 삭제



