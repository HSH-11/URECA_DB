use madang;

# <SELECT> 
-- 컬럼 지정
select * from book; -- 전체 컬럼
select price,bookname,bookid,publisher from book; -- 순서 지정
select bookname, price from book; -- 일부 컬럼
select distinct publisher from book; -- 중복 제거
# <WHERE>
-- 모든 행 중 조건에 맞는 행만 추출
select * from book where price > 20000; -- 가격이 20000보다 큰 행만 추출
select * from book where publisher = '굿스포츠'; 
select * from book where bookid % 2 = 0; -- id가 짝수인 행만 추출
-- between(이상~이하)
select * from book where price between 5000 and 15000;
-- or
select * from book where publisher = '굿스포츠' or publisher = '대한미디어';
-- in, not in
select * from book where publisher in ('굿스포츠','대한미디어');
select * from book where publisher not in('굿스포츠','대한미디어');
-- like 
select * from book where bookname like '축구의 역사'; -- 와일드 카드가 없으므로 =과 동일한 비교
select * from book where bookname like '%축구%'; -- '축구'라는 단어 포함
select * from book where bookname like '%축구'; -- '축구'라는 단어로 종료
select * from book where bookname like '골프%'; -- '골프'라는 단어로 시작
-- 별칭
select bookid, publisher as "출판사" from book where publisher != '굿스포츠';
-- 복합 조건
select * from book where bookname like '%축구%' and price >= 20000;
select * from book where price <= 10000 or price >= 30000; -- 가독성을 위해서 ()활용

# Order By
-- 항상 맨 마지막에 수행되도록 query 작성
-- 결과물을 만드는 중간에 order by 포함되면 성능 하락의 원인이 된다
select * from book order by bookname; -- asc, desc(생략하면 오름차순)
