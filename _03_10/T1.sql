use test;

CREATE TABLE Users
 ( id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR(20),
   age   INTEGER);
  
INSERT INTO Users VALUES (1, 'HONG GILDONG', 30);
INSERT INTO Users VALUES (2,'Bob', 27);
select * from users;

-- ReadCommitted
-- 자 이걸 사용하면 Dirty Read는 방지하지만, Non-Repeatable Read와 Phantom Read는 발생할 수 있음

-- 예제 코드
-- 더티 리드 방지
-- READ COMMITTED를 사용하면 커밋되지 않은 데이터를 읽을 수 없음
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
START TRANSACTION;
UPDATE Users SET age = 40 WHERE name = 'Bob';
select * from users;
-- 일단 commit 안함 

SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
START TRANSACTION;
SELECT age FROM Users WHERE name = 'Bob'; -- 결과 27

-- 반복 불가
-- READ COMMITTED에서는 같은 데이터를 두 번 조회했을 때 값이 변경될 수 있음
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
START TRANSACTION;
SELECT * FROM Users WHERE name = 'Bob';
-- 결과: 27
SELECT * FROM Users WHERE name = 'Bob';
-- 결과: 32 (첫 번째 조회와 값이 다름!)
COMMIT;
-- REPEATABLE READ 이상으로 격리 수준을 올리면 해결 가능

-- 팬텀 리드
-- READ COMMITTED에서는 같은 조건으로 조회했지만, 중간에 새로운 데이터가 추가될 수 있음
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;
START TRANSACTION;
SELECT * FROM Users WHERE age > 25;
-- 결과: Bob (27), HONG GILDONG (30)
SELECT * FROM Users WHERE age > 25;
-- 결과: Bob (27), HONG GILDONG (30), Charlie (29) (첫 번째 조회와 다름!)
COMMIT;
-- SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE;
