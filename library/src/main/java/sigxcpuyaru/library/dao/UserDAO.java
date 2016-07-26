package sigxcpuyaru.library.dao;

import java.util.List;

import sigxcpuyaru.library.entity.User;

public interface UserDAO {

    public User getUser(int id);
    public void insertUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public List<User> getAllUsersSortedByName();
}
