insert into users (username, password)
values ('users1', 'password1');
insert into users (username, password)
values ('users2', 'password2');
insert into authorities (username, authority)
values ('users1', 'ROLE_USER');
insert into authorities (username, authority)
values ('users2', 'ROLE_USER');

commit;

