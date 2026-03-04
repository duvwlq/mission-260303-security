package com.example.mission.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByUsername(String username) {
        Integer count = jdbcTemplate.queryForObject(
                "select count(*) from users where username = ?",
                Integer.class,
                username
        );
        return count != null && count > 0;
    }

    public void insertUser(String username, String encodedPassword) {
        jdbcTemplate.update(
                "insert into users(username, password, enabled) values (?, ?, true)",
                username, encodedPassword
        );
    }

    public void insertAuthority(String username, String authority) {
        jdbcTemplate.update(
                "insert into authorities(username, authority) values (?, ?)",
                username, authority
        );
    }
}