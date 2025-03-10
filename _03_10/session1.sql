use madang;

-- 동시성 제어(쓰기, 쓰기)
-- Lock은 row 단위로 처리
select * from book where bookid = 1;
select * from book where bookid = 2;


select @@autocommit;
set autocommit = 0;

start transaction;

update book set price = 8000 where bookid = 8;

commit; -- 왜 다른 세션에서 commit을 진행하고 여기서는 select를 조회를 했을 때 반영이 안되어 있고 여기서 따로 또 commit을 해야하는 지 모르겠음...

-- 데드락 (Dead Lock)
-- 1,2 book에 대해서 테스트

start transaction;

update book set price = 3000 where bookid = 1; -- 1번 lock
update book set price = 3000 where bookid = 2; -- 2번 lock

commit;

use test;

CREATE TABLE Users
 ( id INTEGER PRIMARY KEY,
  name  VARCHAR(20),
   age   INTEGER);
  
INSERT INTO Users VALUES (1, 'HONG GILDONG', 30);

select * from users;

set transaction isolation level read uncommitted;
start transaction;
select * from users where id = 1;
commit;

set transaction isolation level read committed;
start transaction;
select * from users where id = 1; -- 최초 30
-- 쓰기 committed 21
select * from users where id = 1; -- 쓰기 트랜잭션 uncommitted된 21

commit;

set transaction isolation level read committed;
start transaction;
select * from users where age between 10 and 30;
-- 여기에 쓰기 21 추가
select * from users where age between 10 and 30; -- 30과 21이 같이 보임
commit;

set transaction isolation level repeatable read;
start transaction;
select * from users where age between 10 and 30;
-- 여기에 쓰기 21 추가
select * from users where age between 10 and 30; -- 최초 30만 보임
commit;