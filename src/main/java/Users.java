import java.util.*;

public class Users {
    private int id;
    private String name;
    private List<Integer> streams;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreams(List<Integer> streams) {
        this.streams = streams;
    }

    public String toString() {
        return id + "," + name + "," + streams;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getStreams() {
        return streams;
    }
}

class UsersBuilder {
    private final List<Integer> streams = new ArrayList<>();
    private final Users users = new Users();

    public UsersBuilder setId(int id) {
        users.setId(id);
        return this;
    }

    public UsersBuilder setName(String name) {
        users.setName(name);
        return this;
    }

    public UsersBuilder setStreams(String streams) {
        List<Integer> list = new ArrayList<>();
        String[] buf = streams.split(" ");
        for (String s : buf) {
            list.add(Integer.parseInt(s));
        }
        users.setStreams(list);
        return this;
    }

    public Users build() {
        return users;
    }
}
