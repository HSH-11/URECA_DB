use test;
-- 공통코드 테이블을 2개로 분리, 그룹(대분류)을 표현하는 테이블과 코드 및 코드값을 표현하는 테이블 2개로 나눈 방식
create table group_code( -- 회원 구분, 회원 상태, ....
    group_code char(3) not null,
    group_code_name varchar(50) not null,
    primary key(group_code)
);
create table code(
    group_code char(3) not null,
    code char(3) not null,
    code_name varchar(50) not null,
    use_yn char(1) null,
    primary key (group_code, code)
);

insert into group_code values ('001','회원구분');
insert into group_code values ('002','회원상태');
insert into group_code values ('003','주문상태');

-- 회원구분 공통코드
insert into code values ('001', '010', '일반회원', 'Y');
insert into code values ('001', '020', '준회원', 'Y');
-- insert into code values ('001', '001', '일반회원', 'Y');

-- 회원 상태 공통 코드
insert into code values ('002','010','VIP','Y');
insert into code values ('002','020','GOLD','Y');
insert into code values ('002','030','SILVER','Y');

select * from group_code;
select * from code where group_code = '001';

select code,code_name from code where group_code = '001' and use_yn = 'Y';
-- 위 결과를 백엔드에서는 프론트에세 내려주면(json) 프론트는 라디오버튼 등 화면 구성을 보여준다.
-- 회원은 선택하고 프론트는 선택된 값(이름,이메일 등 code = 010과 함께를)을 전송하게 되고
-- 백엔드는 users 테이블에 insert 한다.
-- insert into users (user_id,user_name,email,user_clsf) values (100, '백길동','back@gildong.com','010');
-- users 테이블에 등록
insert into users (user_id,user_name,email,user_clsf) values (100, '백길동','back@gildong.com','010');
select * from users;
select * from code;

-- 프론트에서 전체 회원 목록을 필요로 한다. 이때 회원구분코드 외에 회원 구분 코드명까지 함께 전달한다.
select user_id, user_name, email, user_clsf,
	(select code_name from code where group_code = '001' and code = user_clsf) user_clsf_name from users;

-- 회원 테이블에 회원 상태를 추가로 관리 지시 공통코드에는 이미 반영되어 있다. '002' 그룹코드 이용, 기본값은 silver로
select * from group_code where group_code = '002';
select * from code where group_code = '002';

-- user 테이블에 user_state 컬럼을 추가하되, 기본값을 '030'으로 
-- 프론트에서 전체 회원 목록을 필요로 한다. 
-- 이때 회원구분코드 외에 회원 구분 코드명, 회원상태코드외에 회원상태코드명까지 함께 전달한다.
select user_id, user_name, email, user_clsf, user_state,
	(select code_name from code where group_code = '001' and code = user_clsf) user_clsf_name, 
    (select code_name from code where group_code = '002' and code = user_state) user_clsf_state
    from users;
    
-- scalar 서브쿼리를 이용하는 방법은 다소 귀찮음
-- group_code = '001' <= 가독성이 떨어짐
-- =>'001'대신 'user_clsf' 변경 가능. group_code 컬럼을 varchar로 변경 필요

-- CREATE FUNCTION `fun_code` (
-- 	p_group_code char(3),
--     p_code char(3)
-- )
-- RETURNS varchar(45)
-- BEGIN
-- 	declare r_code_name varchar(45);
--     select code_name
-- 		into r_code_name
-- 		from code 
--     where group_code = p_group_code
-- 		and code = p_code;
-- RETURN r_code_name;
-- END

-- MySQL에서 스토어드 함수(Stored Function) 또는 트리거(Trigger)를 생성할 때 필요한 설정을 변경
SET GLOBAL log_bin_trust_function_creators = 1;

select user_id, user_name, email, user_clsf, user_state,
	fun_code('001',user_clsf) user_clsf_name, 
    fun_code('002',user_state) user_clsf_state
    from users;
    
-- scalar sub query는 subquery returns more than 1 row 오류가 빈번
-- 사용자가 복수개의 취미를 가지고 그 테이블이 (user_hobby)일 때, 사용자 1명 당 user_hobby 테이블에 값이 없을 수도, 1개일 수도, 여러개 일 수도 있다
-- 만약 사용자 목록을 추출하면서 사용자의 취미도 같이 추출할 경우 sub query 대신 function으로 대체하고
-- function안에서 없거나, 1개거나, 여러개 데이터에 대한 대응 코드를 1개로 return 하는 형태로 많이 만든다.