INSERT INTO location (location_id, street, city, province) VALUES (1, "street0", "city0", "province0");

INSERT INTO member (member_id, member_name, member_email, member_age, member_gender, member_password, member_phone_number, member_role, member_login_count, member_created_at, member_last_login_at, member_deleted, location_id) VALUES (1, "tester0", "tester0@gmail.com", 25, "MALE", "$2a$12$e6spdHGT6eLBVm3OlwUOOekVLk.IM56cC9v0yAvQ7np3XlQs2KwMy", "01012341234", "USER", 1, "2023-03-01-13:10:00", "2023-03-02-13:10:00", false, 1);