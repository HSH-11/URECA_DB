use test;

select * from customer;

insert into customer values(1,'홍길동'); 
insert into customer values(2,'고길동');
insert into customer values(3,'박길동');
-- 위 3개의 insert는 모두 종료 후 자동 commit 됨
select @@autocommit; -- 1: on, 0: off

-- autocommit을 off
-- 같은 세션에서는 추가되지만 다른 세션에서는 commit을 해줘야만 반영
insert into customer values(4,'김길동');
commit;

insert into customer values(5,'최길동');
commit;

update customer set name = '이길동' where id = 5;
commit;

delete from customer where id = 4;
commit;

set autocommit = 1;
