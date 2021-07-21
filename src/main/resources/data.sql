INSERT INTO BRAND (name)
VALUES ('Zara');
INSERT INTO Brand (name)
VALUES ('Tommy');

INSERT INTO PRODUCT (id, name)
VALUES (35455,'shirt');
INSERT INTO Product (id, name)
VALUES (25678,'jeans');

INSERT INTO PRICES (start_date,
                    end_date,
                    product_id,
                    brand_id,
                    priority,
                    final_price,
                    curr)
VALUES ({ts '2020-06-14 00:00:00'}, {ts '2020-12-31 23:59:59'}, 35455, 1, 0, 35.50, 'EUR');

INSERT INTO PRICES (start_date,
                    end_date,
                    product_id,
                    brand_id,
                    priority,
                    final_price,
                    curr)
VALUES ({ts '2020-06-14 15:00:00'}, {ts '2020-06-14 20:00:00'}, 35455, 1, 1, 25.45, 'EUR');


INSERT INTO PRICES (start_date,
                    end_date,
                    product_id,
                    brand_id,
                    priority,
                    final_price,
                    curr)
VALUES ({ts '2020-06-15 00:00:00'}, {ts '2020-06-15 11:00:00'}, 35455, 1, 1, 30.50, 'EUR');

INSERT INTO PRICES (start_date,
                    end_date,
                    product_id,
                    brand_id,
                    priority,
                    final_price,
                    curr)
VALUES ({ts '2020-06-15 16:00:00'}, {ts '2020-12-31 23:59:59'}, 35455, 1, 1, 38.95, 'EUR');