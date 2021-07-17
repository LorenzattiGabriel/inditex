CREATE TABLE IF NOT EXISTS Brand
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL

);

CREATE TABLE IF NOT EXISTS Product
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name varchar(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS Prices
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    START_DATE DATE         NOT NULL,
    END_DATE   DATE         NOT NULL,
    PRICE_LIST INT          NOT NULL,
    PRODUCT_ID INT          NOT NULL,
    BRAND_ID   INT          NOT NULL,
    PRIORITY   INT          NOT NULL,
    PRICE      FLOAT        NOT NULL,
    CURR       VARCHAR(250) NOT NULL,
    foreign key (PRODUCT_ID) references Product (id),
    foreign key (BRAND_ID) references Brand (id)
);


