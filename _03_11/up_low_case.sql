-- autocommit, sql_safe_updates
-- 필요한 환경변수의 설정 조회,변경,원복 처리
use madang;

select @@autocommit;
select @@sql_safe_updates; -- 1로 되어 있다면 key col으로 설정해제 해야함

set sql_safe_updates = 0;
update book set price = 10000;
set sql_safe_updates = 1;

-- 대소문자 테스트 
-- default는 대소문자 구분 X
-- utf8mb4_bin은 대소문자 구분(ci가 안 붙는 거)
create table bin_table (
	user_id varchar(5) not null
)ENGINE = InnoDB default charSET = utf8mb4 collate=utf8mb4_bin;
-- 대소문자 구분 X
create table ci_table (
	user_id varchar(5) not null
)ENGINE = InnoDB default charSET = utf8mb4 collate=utf8mb4_0900_ai_ci;

insert into bin_table values('abc');
insert into ci_table values('abc');

select * from bin_table where user_id = 'ABC'; -- 나타나지 않음
select * from ci_table where user_id = 'ABC';

select * from bin_table where user_id = 'abc';
select * from ci_table where user_id = 'abc';

-- 대소문자를 구분하지 않도록 만든 테이블 또는 스키마에서 대소문자를 구분해야 한다면? binary 사용
-- ci_table
select * from ci_table where binary user_id = 'ABC'; -- 나타나지 않음
select * from ci_table where binary user_id = 'abc';
select * from ci_table where binary (user_id) = 'ABC'; -- 나타나지 않음
-- 조회조건 컬럼이 index를 가진 경우 오른쪽에 함수를 사용하는 게 바람직
select * from ci_table where user_id = binary('ABC');
