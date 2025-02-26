use madang;

-- 집계함수(전체row대상)
select sum(saleprice) from orders;
select avg(saleprice) from orders;
select count(*) from orders;
select max(saleprice) from orders;
select min(saleprice) from orders;

-- 집계함수(조건을 만족하는 row 대상)
select * from orders where saleprice>=10000;
select sum(saleprice) from orders where saleprice>=10000;
select * from orders where custid = 1;
select max(saleprice) from orders where custid = 1;
select * from orders where saleprice = (select max(saleprice) from orders where custid = 1);

-- 집계함수를 한꺼번에 컬럼별로 처리
select sum(saleprice) as sum_price, avg(saleprice) as avg_price, min(saleprice) as min_price,max(saleprice) as max_price from orders;
select sum(saleprice), avg(saleprice), min(custid), max(orderdate) from orders;

-- group by
select count(*) as '도서수량', sum(saleprice) as '총액' from orders -- 고객별
group by orderdate;

-- group by 복수개
-- 일자별, 고객별
select orderdate, count(*) as '도서수량', sum(saleprice) as '총액'
from orders
group by orderdate,custid;

-- having
-- group by로 생성된 새로운 row에 조건 부여
select custid, count(*) as '도서수량' from orders 
where saleprice >= 8000 group by custid having count(*)>= 2;
-- 집계 결과 문자열 alias 오류 X, 결과 X
select custid, count(*) as book_count from orders 
where saleprice >= 8000 group by custid having book_count >= 2; 
-- 집계 결과 문자열 아닌 alias 오류 X, 결과 O

-- group by select 컬럼 주의
-- group by 항목이 아닌 항목을 select에 사용
select bookid,count(*) as '도서수량' from orders where saleprice >= 8000 group by custid having count(*) >= 2;
