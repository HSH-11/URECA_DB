-- 내장함수
use madang;

select abs(-78), abs(78) from dual;
select @@sql_safe_updates;

select custid, round(sum(saleprice)/count(*))
from orders
group by custid;

-- 한글 utf-8(3byte), utf-16(4byte), euc-kr(2byte)
-- '축구의 역사': 한글 5개 + space 1개 (5x3+1) = 16
select bookname, length(bookname), char_length(bookname)
from book where bookid in (1, 10);

-- adddate
select adddate('2025-02-28',interval 5 day);

-- sysdate
select sysdate();

-- sysdate vs now 차이 확인하기
-- 트랜잭션 내에서 같은 시간 유지 → NOW()
-- 실행 순간의 시간 기록 (로그 기록 등) → SYSDATE()

-- null에 대한 입장
-- null을 허락 X <= not null with default value

select * from mybook;
select sum(price),avg(price), count(*), count(price)
from mybook;
-- null 여부 is null, is not null
select * from mybook where price is null;
select * from mybook where price is not null;

-- ifnull
select bookid,price from mybook;
select bookid, ifnull(price,0) price from mybook;

-- case when then else

use hr;

select department_id, sum(salary)
from employees
where department_id in (60,90)
group by department_id;

-- 1개의 row에 2개의 컬럼으로 표현
-- select 60부서 sum, 90부서 sum
-- where 절의 조건이 없어도 실행 결과는 같지만 60,90으로 한번 걸러주고 하는게 좋음
select sum(case when department_id = 60 then salary else 0 end) sum60,
	sum(case when department_id = 90 then salary else 0 end) sum90
    from employees where department_id in (60,90);


    
use madang;

-- in
-- 서브쿼리의 전체 결과를 가져와 비교
select * from customer;
select * from orders;
select * from customer where custid in (select custid from orders);
-- exists
select * from customer where exists (select custid from orders); -- null도 exist임
-- 왼쪽 subquery의 customer_order가 100건이면 오른쪽 customer 1건에 대해 왼쪽 100과 비교를 하다가 1건이라도 나오면 더이상 따지지 않고 true처리

-- not in
select * from customer where custid not in (select custid from orders);
-- not exists
select * from customer where not exists (select custid from orders);
-- 왼쪽 subquery의 customer_order가 100건이면 오른쪽 customer 1건에 대해 왼쪽 100과 비교를 하다가 1건이라도 나오면 더이상 따지지 않고 false처리

UPDATE madang.orders 
SET custid = 4
WHERE orderid = 7;
UPDATE madang.orders 
SET custid = 4 
WHERE orderid = 5;

-- not in not exists with null
-- orders custid null로 바꾸고 실행
select * from orders;
select * from customer where custid not in (select custid from orders where custid );
-- UNKNOWN 값이 하나라도 나오면 전체 조건이 FALSE가 되어 결과를 반환하지 않음!
-- 결과 null 인 애들
select * from customer where custid not in (select custid from orders where custid is not null);
select * from customer where not exists (select custid from orders where custid is not null);
-- not in : index 이용 X, null에 대한 고려
-- not exists: index 이용 O, null에 대한 고려 X

-- not exists , is not null은 null값을 제외한 목록 중에 해당 하지 않는 목록 조회
-- not in에 Null? 전체 결과 false -> is not null 포함해줘야 함
-- Null 값을 고려하지 않고 싶다? not null
-- 뷰
create view VOrders as
select o.orderid, o.custid, c.name, b.bookid, b.bookname, o.saleprice, o.orderdate
from customer c, orders o, book b
where c.custid = o.custid and b.bookid = o.bookid;

select * from VOrders;
select custid, name from VOrders;

-- 인덱스
-- 빠른 검색 목적
-- 1. 별도의 자료구조를 생성(정렬)
-- 2. 새로운 데이터가 추가되거나, 기존 데이터가 변경 또는 삭제되면 재구성
-- 3. 검색에서는 이득을 보지만, 등록,수정,삭제에서는 손해본다
-- 4. PK,FK 등은 자동으로 인덱스가 생성
-- 5. 거꾸로 특정 컬럼에 인덱스를 추가해도 검색이 개선되지 않고 오히려 더 느려진다.

-- test db에 jdbc_big 테이블 생성
use test;
select count(*) from jdbc_big;
select * from jdbc_big;

-- 100만건 데이터를 이용해서 더 큰 테이블 생성 
create table jdbc_big_2 as select * from jdbc_big; -- 이렇게 하면 pk가 지정이 안됨
select count(*) from jdbc_big_2;
-- jdbc_big_2를 이용해서 jdbc_big 더 크게 insert, upto 10,000,000
-- col_1은 auto increments
insert into jdbc_big (col_2,col_3,col_4) select col_2,col_3,col_4 from jdbc_big_2;

select * from jdbc_big where col_1 = 84563;
select * from jdbc_big where col_2 = '노상나';

use madang;
select * from orders;
select * from customer;

-- Foreign Key
-- customer, orders, book 테이블에 orders의 custid는 customer,bookid는 book의 key
-- RDB의 핵심인 데이터의 무결성을 유지하는 핵심 개념