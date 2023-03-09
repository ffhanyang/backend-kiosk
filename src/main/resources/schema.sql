DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
	`member_id`	BIGINT	NOT NULL,
	`member_name`	VARCHAR(20)	NULL,
	`member_email`	VARCHAR(40)	NOT NULL,
	`member_age`	INT	NOT NULL,
	`member_gender`	VARCHAR(10)	NOT NULL,
	`member_password`	VARCHAR(30)	NOT NULL,
	`member_phone_number`	VARCHAR(15)	NULL,
	`member_role`	VARCHAR(10)	NOT NULL,
	`member_login_count`	INT	NOT NULL,
	`member_created_at`	DATETIME	NOT NULL,
	`member_last_login_at`	DATETIME	NULL	DEFAULT null,
	`member_deleted`	BOOLEAN	NOT NULL	DEFAULT false,
	`location_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
	`order_id`	BIGINT	NOT NULL,
	`order_status`	VARCHAR(10)	NOT NULL	DEFAULT preparing	COMMENT 'preparing, ready, served',
	`order_time`	DATETIME	NOT NULL,
	`wait_number`	CHAR(3)	NOT NULL	COMMENT '16진수, 무조건 세 자리 이하',
	`member_id`	BIGINT	NOT NULL,
	`recommend_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
	`order_item_id`	BIGINT	NOT NULL,
	`order_item_count`	INT	NOT NULL,
	`order_item_price`	INT	NOT NULL,
	`order_id`	BIGINT	NOT NULL,
	`item_id`	BIGINT	NOT NULL,
	`payment_method_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
	`item_id`	BIGINT	NOT NULL,
	`item_name`	VARCHAR(50)	NOT NULL,
	`item_stock`	INT	NOT NULL,
	`item_detail`	VARCHAR(1000)	NULL,
	`item_price`	INT	NOT NULL,
	`item_available`	BOOLEAN	NOT NULL	DEFAULT true,
	`nutrient_id`	BIGINT	NOT NULL,
	`item_img_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `recommend`;

CREATE TABLE `recommend` (
	`recommend_id`	BIGINT	NOT NULL,
	`member_id`	BIGINT	NOT NULL,
	`recommend_algorithm`	VARCHAR(30)	NOT NULL,
	`recommend_order_frequency`	INT	NOT NULL
);

DROP TABLE IF EXISTS `category_item`;

CREATE TABLE `category_item` (
	`category_item_id`	BIGINT	NOT NULL,
	`item_id`	BIGINT	NOT NULL,
	`category_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
	`category_id`	BIGINT	NOT NULL,
	`category_name`	VARCHAR(20)	NULL
);

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
	`location_id`	BIGINT	NOT NULL,
	`street`	VARCHAR(20)	NULL,
	`city`	VARCHAR(10)	NOT NULL,
	`province`	VARCHAR(10)	NOT NULL
);

DROP TABLE IF EXISTS `nutrient`;

CREATE TABLE `nutrient` (
	`nutrient_id`	BIGINT	NOT NULL,
	`grows_weight`	INT	NULL,
	`kalory`	INT	NULL,
	`protein`	INT	NULL,
	`natrium`	INT	NULL,
	`sugars`	INT	NULL,
	`saturated_fat`	INT	NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `item_img`;

CREATE TABLE `item_img` (
	`item_img_id`	BIGINT	NOT NULL,
	`img_name`	VARCHAR(255)	NOT NULL,
	`orig_img_name`	VARCHAR(255)	NOT NULL,
	`img_url`	VARCHAR(255)	NOT NULL	COMMENT '기본이미지경로 설정할것',
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
	`cart_id`	BIGINT	NOT NULL,
	`member_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `payment_method`;

CREATE TABLE `payment_method` (
	`payment_method_id`	BIGINT	NOT NULL,
	`payment_type`	VARCHAR(15)	NOT NULL	COMMENT 'kakao, credit_card, money'
);

DROP TABLE IF EXISTS `cart_item`;

CREATE TABLE `cart_item` (
	`cart_item_id`	BIGINT	NOT NULL,
	`cart_id`	BIGINT	NOT NULL,
	`item_id`	BIGINT	NOT NULL,
	`cart_item_count`	INT	NOT NULL
);

DROP TABLE IF EXISTS `recommend_item`;

CREATE TABLE `recommend_item` (
	`recommend_item_id`	BIGINT	NOT NULL,
	`item_id`	BIGINT	NOT NULL,
	`recommend_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `nonmember_order`;

CREATE TABLE `nonmember_order` (
	`nonmember_order_id`	BIGINT	NOT NULL,
	`nonmember_order_status`	VARCHAR(10)	NOT NULL	DEFAULT preparing	COMMENT 'preparing, ready, served',
	`nonmember_order_time`	DATETIME	NOT NULL,
	`nonmember_wait_number`	CHAR(3)	NOT NULL	COMMENT '16진수, 무조건 세 자리 이하',
	`nonmember_phone_number`	VARCHAR(15)	NOT NULL,
	`order_item_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `item_allergic_ingredient`;

CREATE TABLE `item_allergic_ingredient` (
	`allergy_allergic_ingredient`	BIGINT	NOT NULL,
	`allergic_ingredient_id`	BIGINT	NOT NULL,
	`item_id`	BIGINT	NOT NULL
);

DROP TABLE IF EXISTS `allergic_ingredient`;

CREATE TABLE `allergic_ingredient` (
	`allergic_ingredient_id`	BIGINT	NOT NULL,
	`allergic_ingredient_name`	VARCHAR(20)	NOT NULL
);

ALTER TABLE `member` ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
	`member_id`
);

ALTER TABLE `order` ADD CONSTRAINT `PK_ORDER` PRIMARY KEY (
	`order_id`
);

ALTER TABLE `order_item` ADD CONSTRAINT `PK_ORDER_ITEM` PRIMARY KEY (
	`order_item_id`
);

ALTER TABLE `item` ADD CONSTRAINT `PK_ITEM` PRIMARY KEY (
	`item_id`
);

ALTER TABLE `recommend` ADD CONSTRAINT `PK_RECOMMEND` PRIMARY KEY (
	`recommend_id`
);

ALTER TABLE `category_item` ADD CONSTRAINT `PK_CATEGORY_ITEM` PRIMARY KEY (
	`category_item_id`
);

ALTER TABLE `category` ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
	`category_id`
);

ALTER TABLE `location` ADD CONSTRAINT `PK_LOCATION` PRIMARY KEY (
	`location_id`
);

ALTER TABLE `nutrient` ADD CONSTRAINT `PK_NUTRIENT` PRIMARY KEY (
	`nutrient_id`
);

ALTER TABLE `item_img` ADD CONSTRAINT `PK_ITEM_IMG` PRIMARY KEY (
	`item_img_id`
);

ALTER TABLE `cart` ADD CONSTRAINT `PK_CART` PRIMARY KEY (
	`cart_id`
);

ALTER TABLE `payment_method` ADD CONSTRAINT `PK_PAYMENT_METHOD` PRIMARY KEY (
	`payment_method_id`
);

ALTER TABLE `cart_item` ADD CONSTRAINT `PK_CART_ITEM` PRIMARY KEY (
	`cart_item_id`
);

ALTER TABLE `recommend_item` ADD CONSTRAINT `PK_RECOMMEND_ITEM` PRIMARY KEY (
	`recommend_item_id`
);

ALTER TABLE `nonmember_order` ADD CONSTRAINT `PK_NONMEMBER_ORDER` PRIMARY KEY (
	`nonmember_order_id`
);

ALTER TABLE `item_allergic_ingredient` ADD CONSTRAINT `PK_ITEM_ALLERGIC_INGREDIENT` PRIMARY KEY (
	`allergy_allergic_ingredient`
);

ALTER TABLE `allergic_ingredient` ADD CONSTRAINT `PK_ALLERGIC_INGREDIENT` PRIMARY KEY (
	`allergic_ingredient_id`
);

