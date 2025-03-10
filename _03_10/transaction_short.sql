use test;
set autocommit = 0;

select @@autocommit;

-- 여러 insert, update, delete 같은 DB에 변화를 주는 query를 여러 개 수행
-- 단 query들 전체가 하나의 작업 단위(transaction)로 처리

select * from customer;

start transaction;

insert into customer values(1,'홍길동'); 
insert into customer values(2,'고길동');
insert into customer values(3,'박길동');

commit; -- transaction 완료
rollback; 

