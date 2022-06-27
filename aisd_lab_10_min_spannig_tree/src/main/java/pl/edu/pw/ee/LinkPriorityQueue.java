package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkPriorityQueue {
    private List<Link> queue;

    public LinkPriorityQueue() {
        this.queue = new ArrayList<>();
    }

    public void add(Link link) {
        queue.add(link);
        Collections.sort(queue);
    }

    public void add(List<Link> linkList){
        for (Link link : linkList) {
            queue.add(link);
        }
        Collections.sort(queue);
    }

    public Link pop() {
        if (queue.size() == 0) {
            return null;
        }
        Link result = queue.get(0);
        queue.remove(0);
        return result;
    }

    public int size() {
        return queue.size();
    }
}