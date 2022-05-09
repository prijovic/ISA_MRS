INSERT INTO address (id, country, city, street, number, latitude, longitude) VALUES (1, 'Srbija', 'Novi Sad', 'Ulica Miše Dimitrijevića', 41, 45.24398, 19.83099);
INSERT INTO users (id, email, is_active, name, password, phone, surname, address_id, user_type) VALUES (1, 'prijovic.uros13@gmail.com', true, 'Uros', 'Uros12345', '066430250', 'Prijovic', 1, 0);
INSERT INTO sale_participant (points, id) VALUES (0, 1);
INSERT INTO rental_object_owner (id) VALUES (1);
INSERT INTO cancellation_fee (id) VALUES (1);
INSERT INTO rental_object (id, capacity, description, name, price, rental_object_type, address_id, cancellation_fee_id, rental_object_owner_id)
VALUES (1, 5, 'Avanturica po gradu', 'Avanturica#1', 100, 1, 1, 1, 1);
INSERT INTO adventure (id, duration)
VALUES (1, 4.5);
INSERT INTO equipment(id, name) VALUES (1, 'kaciga');
INSERT INTO adventure_equipment(id, adventure_id) VALUES (1, 1);
INSERT INTO rental_object (id, capacity, description, name, price, rental_object_type, address_id, cancellation_fee_id, rental_object_owner_id)
VALUES (2, 5, 'Avanturica po gradu', 'Avanturica#2', 50, 1, 1, 1, 1);
INSERT INTO adventure (id, duration)
VALUES (2, 4);
INSERT INTO equipment(id, name) VALUES (2, 'olovka');
INSERT INTO adventure_equipment(id, adventure_id) VALUES (2, 2);
INSERT INTO rental_object (id, capacity, description, name, price, rental_object_type, address_id, cancellation_fee_id, rental_object_owner_id)
VALUES (3, 5, 'Avanturica po gradu', 'Avanturica#3', 150, 1, 1, 1, 1);
INSERT INTO adventure (id, duration)
VALUES (3, 0.28);
INSERT INTO equipment(id, name) VALUES (3, 'pecaljka');
INSERT INTO adventure_equipment(id, adventure_id) VALUES (3, 3);
INSERT INTO rental_object (id, capacity, description, name, price, rental_object_type, address_id, cancellation_fee_id, rental_object_owner_id)
VALUES (4, 5, 'Avanturica po gradu', 'Avanturica#4', 10, 1, 1, 1, 1);
INSERT INTO adventure (id, duration)
VALUES (4, 0.5);
INSERT INTO equipment(id, name) VALUES (4, 'naocare');
INSERT INTO adventure_equipment(id, adventure_id) VALUES (4, 4);