## Ureca_DB 학습 레포지토리

Java 기반 DB 연동 및 ORM, SQL 실습, 디자인 패턴 예제를 모아둔 학습용 레포지토리입니다. MySQL, JPA(Hibernate), MyBatis, JDBC, MongoDB, Swing 등 다양한 기술을 예제 중심으로 다룹니다.

### 📂 디렉터리 개요
- `DesignPatterns`: Adapter/Decorator/Factory/Observer 패턴 예제 (`src/*/Test.java` 실행)
- `JavaBasic`: 간단한 JDBC/트랜잭션 실습
- `JPA_project`, `JPABasic_*`: JPA/Hibernate 기본, 매핑, 연관관계, JPQL 예제
- `JPABasic_BookManager`: 간단한 Swing UI 기반 Book Manager (JPA 연동)
- `MybatisBasic`, `Mybatis_project`: MyBatis 기본 및 프로젝트 예제
- `MongoDBBasic_BookManager`: MongoDB 드라이버 사용 예제
- `miniproject_prac`, `Project`: 콘솔/간단 앱 실습 모음
- `_02_25` ~ `_03_11`: 수업용 SQL 스크립트 모음
- `schema.sql`: 샘플 상점(PhoneStoreDB) 스키마/데이터

## ⚙️ 사전 준비 (Prerequisites)
- JDK 17 이상 권장
- Maven 3.9+ (Maven 기반 모듈에 해당)
- MySQL 8.0+ (기본: `localhost:3306`)
- MongoDB 6.0+ (Mongo 예제 사용 시)

## 🗄️ 데이터베이스 설정
기본적으로 로컬 MySQL을 사용하며, 다음과 같은 기본 연결 정보가 설정되어 있습니다. 필요 시 각 파일의 값을 수정하세요.

- JPA 설정: `JPABasic_1/src/main/resources/META-INF/persistence.xml`
  - URL: `jdbc:mysql://localhost:3306/jpa_basic`
  - USER: `root`
  - PASSWORD: (빈 값)

- MyBatis 설정: `MybatisBasic/src/main/resources/config/mybatis-config.xml`
  - URL: `jdbc:mysql://localhost:3306/mybatis_basic`
  - USER: `root`
  - PASSWORD: (빈 값)

필요한 데이터베이스를 미리 생성합니다.

```sql
CREATE DATABASE IF NOT EXISTS jpa_basic;
CREATE DATABASE IF NOT EXISTS mybatis_basic;
```

샘플 전자상거래 스키마/데이터는 루트의 `schema.sql`을 MySQL에서 실행하세요.

```bash
mysql -u root -p < schema.sql
```

## 🛠️ 빌드 및 실행
이 저장소는 여러 개의 독립 모듈로 구성되어 있습니다. Maven 기반 모듈은 각 디렉터리에서 별도로 빌드하세요.

### Maven 모듈 예시
- `JPABasic_*`, `JPA_project`, `Mybatis_project`, `MybatisBasic`, `MongoDBBasic_BookManager`

```bash
# 예) 특정 모듈 빌드
cd JPABasic_5
mvn -q -DskipTests package
```

실행은 IDE에서 각 모듈의 `main` 메서드가 있는 클래스를 선택해 실행하는 것을 권장합니다. (exec 플러그인이 기본 설정되어 있지 않을 수 있음)

### 실행 가능한 메인 클래스(예시)
- 디자인 패턴 예제: `DesignPatterns/src/*/Test.java`
  - `adapter.Test`, `decorator.Test`, `factory.Test`, `observer.Test`
- JPA 예제: 각 `JPABasic_*` 모듈의 `Test.java`(다수)
- Book Manager (JPA + Swing): `JPABasic_BookManager/src/main/java/app/book/ui/BookManager.java`

## 🔌 의존성 주요 버전
- Hibernate: `6.6.10.Final`
- MyBatis: `3.5.16`
- MySQL Connector/J: `8.3.0`
- HikariCP: `5.1.0`
- MongoDB Java Driver (Sync): `5.1.2`

## 📑 SQL 스크립트 모음
- `_02_25` ~ `_03_11`: DDL/DML, 집계, 조인, 함수, 트랜잭션/격리수준, CTE, 키워드/대소문자 등 실습 스크립트
- `schema.sql`: 샘플 상점 도메인(상품/고객/주문/쿠폰) 스키마 및 데이터, FK/ON DELETE CASCADE 포함

## 🔧 트러블슈팅
- MySQL 접속/권한 오류: 설정 파일의 URL/USER/PASSWORD를 환경에 맞게 수정
- 포트 충돌: `3306` 포트 사용 중인 경우 MySQL 포트 변경 후 설정 파일도 함께 변경
- 드라이버 누락: Maven 빌드 실패 시 각 모듈의 `pom.xml` 의존성 확인 후 `mvn -U clean package`

## 🗒️ 참고
본 레포지토리는 학습/실습 목적이며, 모듈 간 독립적으로 구성되어 있습니다. 필요한 모듈만 골라 빌드/실행해도 무방합니다.
