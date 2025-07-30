insert into roles(rolename)
values ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_SUPERVISOR');


INSERT INTO public.users (username, enabled, password) VALUES ('karelde2e', true, '$2a$10$eXvT4rWfJ.tYOBzaHNqduuZBLOnPlgl3AUY/KlrrITupluQIXE6V6');
INSERT INTO public.users (username, enabled, password) VALUES ('anssiepansie', true, '$2a$10$GoRaNiFr/hIwtdyHAkow3OHmJuz.yg8IUDQx4.MWyFCDPBuBzRGda');


INSERT INTO public.user_roles (rolename, username) VALUES ('ROLE_USER', 'karelde2e');
INSERT INTO public.user_roles (rolename, username) VALUES ('ROLE_USER', 'anssiepansie');
INSERT INTO public.user_roles (rolename, username) VALUES ('ROLE_ADMIN', 'anssiepansie');

INSERT INTO public.profiles (id_profile, gender, firstname, lastname, email, username) VALUES (1, 'female', 'Ans', 'Pans', 'ansiepansie@gmail.com', 'anssiepansie');
INSERT INTO public.profiles (id_profile, gender, firstname, lastname, email, username) VALUES (2, 'male', 'Karel', 'Grote', 'newMail@gmail.com', 'karelde2e');
