# John Bryce Coupon System Project - stage 3.

Stage 3 of John Bryce course 822-120 Coupon System project. 
This stage represents previous stage coupon system project with added REST services, Spring MVC and frontend Angular SPA.


Administrator account credentials:

email: admin@admin.com

password: admin

### Using the app

To start the application you should run file: `com.jb.coupons_project/Application.java`.
To stop the application you should press 4 in console menu.

On each run the application should connect to MySQL database and look for schema **jb_coupons_3**.

This data can be changed in file: `src/main/resources/application.properties`

If the app doesn't find schema with this name, it should create new schema.
If the app finds existing schema with this name, it will use the existing schema.

If for some reason application can not create database, you can create it manually 
using MySQL commands which are posted in the bottom of this document.

**After the app starts successfully, homepage is awailable at localhost:8080/**

The database does not have any pre-saved users or entities except administrator account, 
which is hardcoded in the app.

You should first log in as administrator and create another clients from this account.

**Administrator account credentials for initial login:**

**email: admin@admin.com**

**password: admin**

This data can be changed in file: `src/main/resources/application.properties`

Likewise, for the serverside testing and debugging purposes the application retains from the project stage 2 interactive console menus. To use these tests you should follow onscreen instructions.

### MySQL statements for creating schema

MySQL command you can use to create MySQL user account account:
`mysql> CREATE USER 'jbcp'@'localhost' IDENTIFIED BY 'admin';`

**MySql user account credentials:**

**username: jbcp**

**password: admin**


```
CREATE SCHEMA IF NOT EXISTS JB_COUPONS_3;
```
```
USE JB_COUPONS;
```
```
CREATE TABLE COMPANIES 
	(ID INT UNSIGNED NOT NULL AUTO_INCREMENT, 
	NAME VARCHAR(128) UNIQUE, 
	EMAIL VARCHAR(128) UNIQUE,
	PASSWORD VARCHAR(128),
	PRIMARY KEY(ID),
	INDEX (NAME)
);
```
```
CREATE TABLE CUSTOMERS (
	  ID INT UNSIGNED NOT NULL AUTO_INCREMENT, 
	  FIRST_NAME VARCHAR(128),
	  LAST_NAME VARCHAR(128), 
	  EMAIL VARCHAR(128) UNIQUE,
	  PASSWORD VARCHAR(128),
	  PRIMARY KEY(ID),
	  INDEX (EMAIL)
	);
```
```
CREATE TABLE COUPONS (
	  ID INT UNSIGNED NOT NULL AUTO_INCREMENT, 
	  COMPANY_ID VARCHAR(128), 
	  CATEGORY_ID INT UNSIGNED,
	  TITLE VARCHAR(128),
	  DESCRIPTION VARCHAR(4096),
	  START_DATE DATE,
	  END_DATE DATE,
	  AMOUNT INT,
	  PRICE DOUBLE,
	  IMAGE VARCHAR(1024),
	  PRIMARY KEY(ID),
	  INDEX (TITLE),
	  CONSTRAINT FOREIGN KEY (COMPANY_ID) REFERENCES COMPANIES (ID)
	    ON DELETE NO ACTION ON UPDATE NO ACTION,
	  CONSTRAINT FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORIES (ID)
	    ON DELETE CASCADE ON UPDATE CASCADE
	);
```
```
CREATE TABLE CUSTOMERS_VS_COUPONS (
	  CUSTOMER_ID INT UNSIGNED, 
	  COUPON_ID INT UNSIGNED,
	  PRIMARY KEY(CUSTOMER_ID, COUPON_ID),
	  CONSTRAINT FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS (ID)
	    ON DELETE NO ACTION ON UPDATE NO ACTION,
	  CONSTRAINT FOREIGN KEY (COUPON_ID) REFERENCES COUPONS (ID)
	    ON DELETE NO ACTION ON UPDATE NO ACTION
	);
```

