/*INSERT INTO address (id, country, city, street, number, latitude, longitude) VALUES (1, 'Srbija', 'Novi Sad', 'Ulica Miše Dimitrijevića', 41, 45.24398, 19.83099);
INSERT INTO users (id, email, is_active, name, password, phone, surname, address_id, user_type) VALUES (1, 'prijovic.uros13@gmail.com', true, 'Uros', 'Uros12345', '066430250', 'Prijovic', 1, 5);
INSERT INTO request (nextval(`id`), `status`, `time_stamp`, `type`, `user_id`) VALUES (1, 0, '2022-01-01 15:39:01', 0, 1);
INSERT INTO admin (`id`) VALUES (1);
INSERT INTO request (nextval(`id`), `status`, `time_stamp`, `type`, `user_id`) VALUES (2, 0, '2022-01-02 00:00:00', 0, 2);*/
INSERT INTO users (`id`, `email`, `is_active`, `name`, `password`, `phone`, `photo`, `surname`, `user_type`, `address_id`) VALUES (3, 'volastamara@gmail.com', 1, 'Tamara', 'kljucko', '6969696969', 'null', 'Volan', 1, 1);
INSERT INTO users (id, email, is_active, name, password, phone, surname, address_id, user_type) VALUES (4, 'saskat@gmail.com', true, 'Saska', 'Saska318', '066654398', 'Topalovic', 1, 2);
INSERT INTO sale_participant (points, id) VALUES (0, 1);
INSERT INTO rental_object_owner (owner_type, id) VALUES (1, 1);
INSERT INTO test.fee (id, fee_type, value) VALUES (1, 0, 0);
INSERT INTO test.cancellation_fee (id) VALUES (1);
INSERT INTO rental_object (id, capacity, description, name, price, rental_object_type, cancellation_fee_id, rental_object_owner_id) VALUES (1, 5, 'Mala kucica na kraju grada', 'Bjutiful', 100, 1, 1, 1);
INSERT INTO vacation_rental (check_in, check_out, id) VALUES ('14:00', '12:00', 1);