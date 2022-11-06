BEGIN TRANSACTION;

INSERT INTO store (id, name, location) VALUES (1, 'Electronics', 'Somewhere');

INSERT INTO section (id, name, store_id) VALUES (1, 'Tablets', 1);
INSERT INTO section (id, name, store_id) VALUES (2, 'Phones', 1);
INSERT INTO section (id, name, store_id) VALUES (3, 'Laptops', 1);

INSERT INTO product (id, name, price, section_id) VALUES (1, 'Samsung TabPro', 250.5, 1);
INSERT INTO product (id, name, price, section_id) VALUES (2, 'Apple iPad', 300, 1);
INSERT INTO product (id, name, price, section_id) VALUES (3, 'Samsung Galaxy', 950.7, 2);
INSERT INTO product (id, name, price, section_id) VALUES (4, 'Apple iPhone', 1000, 2);
INSERT INTO product (id, name, price, section_id) VALUES (5, 'Lenovo ThinkPad', 1800.5, 3);
INSERT INTO product (id, name, price, section_id) VALUES (6, 'Asus ROG', 2200.50, 3);

COMMIT;