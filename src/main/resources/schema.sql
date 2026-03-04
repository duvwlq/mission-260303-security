-- JdbcUserDetailsManager (MySQL)

DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       username VARCHAR(50) NOT NULL PRIMARY KEY,
                       password VARCHAR(500) NOT NULL,
                       enabled BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE authorities (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             CONSTRAINT fk_authorities_users
                                 FOREIGN KEY (username) REFERENCES users(username)
                                     ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);


-- UserDetailsService Customization

DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(128) NOT NULL,
                        email VARCHAR(256) NOT NULL UNIQUE,
                        password VARCHAR(256)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE authority (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           authority VARCHAR(256),
                           member_id BIGINT,
                           CONSTRAINT fk_authority_member
                               FOREIGN KEY (member_id) REFERENCES member(id)
                                   ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- PersistentTokenRepository

DROP TABLE IF EXISTS persistent_logins;

CREATE TABLE persistent_logins (
                                   username VARCHAR(64) NOT NULL,
                                   series VARCHAR(64) PRIMARY KEY,
                                   token VARCHAR(64) NOT NULL,
                                   last_used TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;