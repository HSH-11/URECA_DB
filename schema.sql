-- 데이터베이스 생성
DROP DATABASE IF EXISTS PhoneStoreDB;
CREATE DATABASE PhoneStoreDB;
USE PhoneStoreDB;

-- 1. 제품 테이블 생성 (상품코드,상품명,가격,재고)
CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL
);

-- 2. 고객 테이블 생성 (고객 코드, 고객명, 이메일, 전화번호, 주소)
CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255)
);

-- 3. 주문 테이블 생성 (주문 코드, 고객 코드, 총 금액, 주문 상태, 결제 상태,배송 주소)
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled') DEFAULT 'Pending',
    payment_status ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    shipping_address VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

-- 4. 주문 항목 테이블 (주문 코드, 상품 코드, 구매 수량, 개당 가격)
CREATE TABLE OrderItems (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);
 -- 5. 쿠폰 항목 테이블 (쿠폰 id, 쿠폰명, 할인율)
CREATE TABLE Coupons (
    coupon_id INT AUTO_INCREMENT PRIMARY KEY,
    coupon_name VARCHAR(50) NOT NULL,
    discount_rate DECIMAL(5,2) NOT NULL
);

-- 6. 고객 보유 쿠폰(고객 id,쿠폰 id, 발급일)
CREATE TABLE CustomerCoupons (
    customer_id INT,
    coupon_id INT,
    issued_date DATETIME DEFAULT NOW(),
    PRIMARY KEY (customer_id, coupon_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (coupon_id) REFERENCES Coupons(coupon_id)
);

-- 7. 관리자용 
CREATE TABLE AdminUsers (
    admin_id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(100) NOT NULL, 
    name VARCHAR(50) NOT NULL
);

INSERT INTO AdminUsers(admin_id,password,name) values
('hsh11','hsh1521','허승현');

-- 1. 제품 데이터 추가
INSERT INTO Products (name, price, stock_quantity) VALUES
('iPhone 15 Pro', 1500000, 10),
('Galaxy S24 Ultra', 1450000, 8),
('iPhone 16', 1700000, 5),
('Galaxy Z Fold 5', 2200000, 3),
('iPhone 14', 1300000, 7),
('Galaxy S23', 1250000, 6),
('OnePlus 11', 1100000, 9),
('Xiaomi 13 Pro', 900000, 4),
('Google Pixel 8', 950000, 6),
('Sony Xperia 1 IV', 1850000, 5);

-- 2. 고객 데이터 추가
INSERT INTO Customers (name, email, phone, address) VALUES
('메시', 'messi@example.com', '010-1111-1111', 'Buenos Aires'),
('호날두', 'ronaldo@example.com', '010-2222-2222', 'Lisbon'),
('네이마르', 'neymar@example.com', '010-3333-3333', 'Rio de Janeiro'),
('음바페', 'mbappe@example.com', '010-4444-4444', 'Paris'),
('모드리치', 'modric@example.com', '010-5555-5555', 'Zagreb'),
('살라', 'salah@example.com', '010-6666-6666', 'Liverpool'),
('케인', 'kane@example.com', '010-7777-7777', 'London'),
('레반도프스키', 'lewandowski@example.com', '010-8888-8888', 'Warsaw'),
('뮐러', 'muller@example.com', '010-9999-9999', 'Munchen'),
('더브라위너', 'debruyne@example.com', '010-1010-1010', 'Manchester');

-- 3. 주문 데이터 추가
INSERT INTO Orders (customer_id, total_amount, order_status, payment_status, shipping_address) VALUES
(1, 1500000, 'Pending', 'Pending', 'Buenos Aires'),
(2, 2800000, 'Shipped', 'Completed', 'Lisbon'),
(3, 1000000, 'Delivered', 'Completed', 'Rio de Janeiro'),
(4, 1300000, 'Pending', 'Pending', 'Paris'),
(5, 900000, 'Cancelled', 'Failed', 'Zagreb'),
(6, 2200000, 'Shipped', 'Completed', 'Liverpool'),
(7, 1150000, 'Pending', 'Pending', 'London'),
(8, 1050000, 'Delivered', 'Completed', 'warsaw'),
(9, 800000, 'Shipped', 'Completed', 'Munchen'),
(10, 2200000, 'Pending', 'Pending', 'Manchester');


-- 4. 주문 항목 데이터를 삽입
INSERT INTO OrderItems (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 1500000),
(2, 2, 2, 1400000),
(3, 3, 1, 1000000),
(4, 5, 1, 1300000),
(5, 6, 1, 900000),
(6, 7, 2, 1100000),
(7, 8, 1, 1150000),
(8, 9, 1, 1050000),
(9, 10, 1, 800000),
(10, 4, 1, 2200000);

-- 5. 쿠폰 생성
INSERT INTO Coupons (coupon_name, discount_rate) VALUES
('신규가입 할인', 10.00),
('봄맞이 이벤트', 15.00),
('여름세일', 20.00),
('가을감사제', 10.00),
('겨울특가', 25.00),
('VIP 고객 전용', 30.00),
('리퍼상품 전용', 5.00),
('친구추천 쿠폰', 12.00),
('리뷰작성 쿠폰', 8.00),
('단골고객 감사', 15.00);

-- 고객에게 쿠폰 할당
INSERT INTO CustomerCoupons (customer_id, coupon_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(4, 6),
(5, 7),
(6, 8),
(7, 9),
(8, 10);

ALTER TABLE Orders
ADD CONSTRAINT fk_customer_id
FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
ON DELETE CASCADE;

ALTER TABLE orderitems
DROP FOREIGN KEY orderitems_ibfk_1;

ALTER TABLE orderitems
ADD CONSTRAINT orderitems_ibfk_1 FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE;

ALTER TABLE Orders
ADD COLUMN discount_amount DECIMAL(10,2) DEFAULT 0;

select * from customers;
