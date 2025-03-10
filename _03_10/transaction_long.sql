use test;
set autocommit = 0;

select @@autocommit;

-- 여러 insert, update, delete 같은 DB에 변화를 주는 query를 여러 개 수행
-- 단 query들 전체가 하나의 작업 단위(transaction)로 처리

select * from customer;
 
-- 복잡하고 긴 transaction 작업 수행 가정(5-6 시간 걸리는 작업)
-- 개발계 서버에서 코드 작성
-- A,B,C 작업 완료 시간이 3시간 걸린다는 가정
-- A,B,C는 완성. D는 개발 중.
-- A,B,C는 이미 완성되었으므로 D가 문제가 있을 경우, A,B,C는 완성된 상태로 rollback하고 싶다.
-- savepoint 지정
start transaction;

-- A 테이블 변화
-- B 테이블 조회, 결과값에 따라 다르게 처리(PL-SQL)
-- C 테이블 변화(insert)
-- D 테이블 변화(Update)
-- E 테이블 조회
-- ....

insert into customer values(1,'홍길동'); 
insert into customer values(2,'고길동');

savepoint s1;

insert into customer values(3,'박길동');

commit; -- 여기서 transaction 완료
rollback to s1; -- 에러 발생!(s1이 존재하지 않음)

-- A,B,C를 저장한 후에도 D를 롤백할 수 있도록 트랜잭션을 분리하는 방식
start transaction;
insert into customer values(1,'홍길동'); 
insert into customer values(2,'고길동');
savepoint s1;

insert into customer values(3,'박길동');
commit;  -- ✅ A, B, C는 확정적으로 저장

start transaction; -- 새로운 트랜잭션 시작
savepoint s2;  -- D 작업 시작 전 저장점 생성
insert into customer values(4,'이길동');

rollback to s2;  -- ✅ D만 롤백 가능
commit;  -- 최종 확정
