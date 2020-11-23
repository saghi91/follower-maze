package clients;

import events.BaseEvent;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class User implements Comparable<User>{
    private boolean offlineUser = false;
    private final Integer id;
    private final Set<Integer> followers = new ConcurrentSkipListSet<>();
    private final PrintWriter writer;

    public User(int id, PrintWriter writer) {
        this.id = id;
        this.writer = writer;
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

    public boolean isOfflineUser() {
        return offlineUser;
    }

    public void setOfflineUser(boolean offlineUser) {
        this.offlineUser = offlineUser;
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
