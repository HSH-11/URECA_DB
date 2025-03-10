-- 고립 수준
-- 한 트랜잭션은 일기, 다른 트랜잭션은 쓰기를 진행
-- 읽는 트랜잭션이 쓰는 트랜잭션의 변화를 어떻게 대응할 것 인가 하는 정책에 따라 다른 결과를 보여준다.

-- set transaction isolation level ____;
-- ___에 올 수 있는 경우 
-- read uncommited: 쓰기 트랜잭션의 변화가 commit 되지 않아도 읽는다. 
-- <= 읽기 트랜잭션에서 commit 되지 않은 데이터를 읽은 후 쓰기 트랜잭션에서 rollback하면 잘못된 데이터를 읽게 된다(dirty read)
-- read commited: 쓰기 트랜잭션의 변화가 commit 되어야 만 읽는다.
-- <= 읽기 트랜잭션에서 commit된 데이터를 읽은 후 쓰기 트랜잭션에서 변경 commit하면 이전에 읽은 데이터와 달라진다.(non-repeatable read)
-- <= 읽기 트랜잭션에서 commit된 데이터를 읽은 후 (복수개가 될 수 있는) 쓰기 트랜잭션에서 등록 commit하면 이전에 읽은 데이터들과 달라진다(phantom read) 
-- 반복적으로 똑같은 값을 읽어와야 하는데 그렇지 못하는 상태
-- repeatable read
-- <= 읽기 트랜잭션에서 commit된 데이터를 읽은 후 쓰기 트랜잭션에서 변경 commit해도 이전에 읽은 데이터와 동일하게 읽는다.
start transaction;