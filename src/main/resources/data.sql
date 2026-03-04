-- users: username 기준으로 upsert
MERGE INTO users (username, password, enabled) KEY(username)
    VALUES ('admin',  '{bcrypt}$2b$10$7YPSet.lPpne025vlX39Ju35b2bWsf5fkAjOKxe8KeB3TSiHahkS6', TRUE);

MERGE INTO users (username, password, enabled) KEY(username)
    VALUES ('manager','{bcrypt}$2b$10$Wpf4Ls3gKyIphyUEHMQoF.86jxUqYz7GmYcZ/c5KPEsILpZo/RdXa', TRUE);

MERGE INTO users (username, password, enabled) KEY(username)
    VALUES ('user1',  '{bcrypt}$2b$10$e99i1iUAWugsgsUExVKX6.8nzPsgKQ75ARdF2J6pP.o0jSbLDnvD6', TRUE);

-- authorities: (username, authority) 조합이 유니크하도록 되어있으니 동일하게 MERGE
MERGE INTO authorities (username, authority) KEY(username, authority)
    VALUES ('admin', 'ROLE_ADMIN');

MERGE INTO authorities (username, authority) KEY(username, authority)
    VALUES ('admin', 'ROLE_USER');

MERGE INTO authorities (username, authority) KEY(username, authority)
    VALUES ('manager', 'ROLE_MANAGER');

MERGE INTO authorities (username, authority) KEY(username, authority)
    VALUES ('user1', 'ROLE_USER');

MERGE INTO users (username, password, enabled) KEY(username)
    VALUES ('user2', '{bcrypt}$2b$10$YOUR_BCRYPT_HASH_HERE', TRUE);

MERGE INTO authorities (username, authority) KEY(username, authority)
    VALUES ('user2', 'ROLE_USER');