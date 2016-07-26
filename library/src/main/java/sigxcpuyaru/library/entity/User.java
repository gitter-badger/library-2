package sigxcpuyaru.library.entity;

public class User {

    // private static Map<Integer, User> users = new HashMap<Integer, User>(0);

    private int id;
    private String name;

    // public static User createUser(int id, String name) {
    //     User user = users.get(id);
    //     if (user == null) {
    //         user = new User(id, name);
    //         users.put(id, name);
    //     }
    //     return user;
    // }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
