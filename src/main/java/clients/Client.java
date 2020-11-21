package clients;

import events.BaseEvent;

import java.io.PrintWriter;
import java.util.*;

public class Client implements Comparable<Client>{
    private final Integer id;
    private final Set<Integer> followers = new HashSet<>();
    private final PrintWriter writer;

    Client(int id, PrintWriter writer) {
        this.id = id;
        this.writer = writer;
    }

    public Collection<Integer> getFollowers() {
        return followers;
    }

    public boolean useEvent(BaseEvent baseEvent) {
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
    public int compareTo(Client client) {
        return id.compareTo(client.id);
    }
}
