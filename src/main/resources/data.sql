--JdbcUserDetailsManager
INSERT INTO users(username, password, enabled) VALUES('admin', '$2a$10$6jwG4/gMHp/DEsJAQm9DTudYiC5.URlNhBkNzSvZzNzZwmddZ6O3K', TRUE);
INSERT INTO users(username, password, enabled) VALUES('user', '$2a$10$T7VQo03Q9lO92E/eI8svGesWgJVqc6JI7KNaYUYcBtT1m.b1EKijS', TRUE);
INSERT INTO authorities(username, authority) VALUES('user', 'ROLE_USER');
INSERT INTO authorities(username, authority) VALUES('admin', 'ROLE_USER');
INSERT INTO authorities(username, authority) VALUES('admin', 'ROLE_ADMIN');

--UserDetailsService Customization
INSERT INTO member(name, email, password) VALUES('홍길동', 'GildongDong@google.com'
        , '$2a$10$e1Fff0OjnpDWj5FHw/mbQ.xKYl9Oo8RH2zSU93iShfnxzDHrzckn.');
INSERT INTO member(name, email, password) VALUES('임꺽정', 'KkeokJeong@google.com'
    , '$2a$10$HFSvnP1pXGT4FXCnH7IcN.yVa78dhE3/JYgYu2EW9Byyr3u/Ygknq');
INSERT INTO member(name, email, password) VALUES('전우치', 'JeonUchi@google.com'
    , '$2a$10$HFSvnP1pXGT4FXCnH7IcN.yVa78dhE3/JYgYu2EW9Byyr3u/Ygknq');
INSERT INTO member(name, email, password) VALUES('장길산', 'GilSan@google.com'
    , '$2a$10$HFSvnP1pXGT4FXCnH7IcN.yVa78dhE3/JYgYu2EW9Byyr3u/Ygknq');
INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 1);
INSERT INTO authority(authority, member_id) VALUES('ROLE_ADMIN', 1);
INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 2);
INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 3);
INSERT INTO authority(authority, member_id) VALUES('ROLE_USER', 4);