insert into users (username, password, enabled)
    values  ('db2_user', '{noop}db2 user', true),
            ('db2_admin', '{noop}db2 admin', true);

insert into authorities (username, authority)
    values  ('db2_user', 'ROLE_USER'),
            ('db2_admin', 'ROLE_ADMIN');