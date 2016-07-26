package sigxcpuyaru.library.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;
import java.sql.ResultSet;

import sigxcpuyaru.library.entity.User;

public class JdbcTemplateUserDAO implements UserDAO {

    private static final String GET = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_ORDER_BY_NAME = "SELECT * FROM users ORDER BY name";
    private static final String INSERT = "INSERT INTO users(id, name) values(?, ?)";
    private static final String UPDATE = "UPDATE users SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUser(int id) {
        return jdbcTemplate.queryForObject(GET, this::mapUser, id);
    }

    @Override
    public List<User> getAllUsersSortedByName() {
        return jdbcTemplate.query(GET_ALL_ORDER_BY_NAME, this::mapUser);
    }

    @Override
    public void insertUser(User user) {
        jdbcTemplate.update(INSERT, user.getId(), user.getName());
    }

    @Override
    public void updateUser(User user) {
        if (user != null) {
            jdbcTemplate.update(UPDATE, user.getName(), user.getId());
        }
    }

    @Override
    public void deleteUser(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    private User mapUser(ResultSet rs, int row) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("name"));
    }
}
