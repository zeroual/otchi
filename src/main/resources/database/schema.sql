DROP TABLE IF EXISTS RECIPE_INGREDIENT;
DROP TABLE IF EXISTS INGREDIENT;
DROP TABLE IF EXISTS INSTRUCTION;
DROP TABLE IF EXISTS RECIPE;
DROP TABLE IF EXISTS POST;
DROP TABLE IF EXISTS USER;


CREATE TABLE  INGREDIENT(
  ID INTEGER  PRIMARY KEY AUTO_INCREMENT,
  NAME varchar(255),
  QUANTITY DOUBLE,
  UNIT varchar(255)
);

CREATE TABLE  INSTRUCTION(
  ID INTEGER  PRIMARY KEY AUTO_INCREMENT,
  CONTENT varchar(255),
  RECIPE_ID INTEGER
);


CREATE TABLE  RECIPE(
  ID INTEGER PRIMARY KEY AUTO_INCREMENT,
  TITLE varchar(255),
  DESCRIPTION varchar(255),
  COOK_TIME INTEGER,
  PREPARATION_TIME INTEGER
);

CREATE TABLE  RECIPE_INGREDIENT(
  RECIPE_ID INTEGER,
  INGREDIENT_ID INTEGER
);

CREATE TABLE POST (
  ID         INTEGER PRIMARY KEY AUTO_INCREMENT,
  CREATED_AT DATETIME,
  RECIPE_ID INTEGER
);

CREATE TABLE USER (
  ID         INTEGER PRIMARY KEY,
  EMAIL      VARCHAR(100),
  PASSWORD   VARCHAR(60) NOT NULL,
  FIRST_NAME VARCHAR(50),
  LAST_NAME  VARCHAR(50),
  ACTIVATED  BOOLEAN     NOT NULL
);