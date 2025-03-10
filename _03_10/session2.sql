use madang;


-- 동시성 제어(쓰기, 쓰기)
-- Lock은 row 단위로 처리

select * from book where bookid = 2;
select * from book where bookid = 1;


select @@autocommit;
set autocommit = 0;

start transaction;

update book set price = 10000 where bookid = 8;

commit;

-- 데드락 (Dead Lock)
-- 1,2 book에 대해서 테스트

start transaction;

update book set price = 10000 where bookid = 2;

update book set price = 10000 where bookid = 1;

commit;

-- 고립 수준
use test;

start transaction;
update users set age = 21 where id = 1; -- uncommited 상태
rollback;

start transaction;
update users set age = 21 where id = 1; -- uncommited 상태
commit;

start transaction;
INSERT INTO Users VALUES (2, 'Bob', 21);
commit;