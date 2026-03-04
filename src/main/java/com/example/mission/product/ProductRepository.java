package com.example.mission.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Product> MAPPER = new RowMapper<>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("owner_username"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }
    };

    /** keyword가 비어있으면 전체, 있으면 name like 검색 */
    public List<Product> findAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return jdbcTemplate.query(
                    "select id, name, price, owner_username, created_at from products order by id desc",
                    MAPPER
            );
        }
        String like = "%" + keyword.trim() + "%";
        return jdbcTemplate.query(
                "select id, name, price, owner_username, created_at " +
                        "from products where name like ? order by id desc",
                MAPPER,
                like
        );
    }

    public void save(String name, int price, String ownerUsername) {
        jdbcTemplate.update(
                "insert into products(name, price, owner_username) values (?, ?, ?)",
                name, price, ownerUsername
        );
    }

    /** 상품 작성자(username) 조회 (없으면 null) */
    public String findOwnerById(long id) {
        List<String> owners = jdbcTemplate.query(
                "select owner_username from products where id = ?",
                (rs, rowNum) -> rs.getString("owner_username"),
                id
        );
        return owners.isEmpty() ? null : owners.get(0);
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from products where id = ?", id);
    }
}