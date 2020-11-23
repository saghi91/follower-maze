package clients;

import events.BaseEvent;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class User implements Comparable<User>{
    public boolean offlineUser;
    private final Integer id;
    private final Set<Integer> followers = new HashSet<>();
    private final PrintWriter writer;

    public User(int id, PrintWriter writer) {
        this.id = id;
        this.writer = writer;
        this.offlineUser = false;
    }

    public Collection<Integer> getFollowers() {
        return followers;
    }

    public boolean emit(BaseEvent baseEvent) {
        writer.println(baseEvent);
        return !writer.checkError();
    }

    public void addFollower(int id) {
        followers.add(id);
    }

    public Integer getId() {
        return id;
    }

    public void removeFollower(int id) {
        followers.remove(id);
    }

    @Override
    public String toString() {
        return String.format("%d", id);
    }

    @Override
    public int compareTo(User user) {
        return id.compareTo(user.id);
    }
}
