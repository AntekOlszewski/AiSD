package pl.edu.pw.ee;

import java.util.regex.Pattern;

public class Link implements Comparable<Link> {

    private String startNode;
    private String endNode;
    private int weight;

    public Link(String data) {
        String[] info = data.split(" ");
        if (info.length != 3) {
            System.out.println("Incorrect data in file!");
            throw new IllegalArgumentException("Incorrect data in file!");
        }
        if (Pattern.matches("[a-zA-Z]+", info[0])) {
            this.startNode = info[0];
        } else {
            System.out.println("Name of node can only contain lowercase and upercase letters!");
            throw new IllegalArgumentException("Name of node can only contain lowercase and upercase letters!");
        }
        if (Pattern.matches("[a-zA-Z]+", info[1])) {
            this.endNode = info[1];
        } else {
            System.out.println("Name of node can only contain lowercase and upercase letters!");
            throw new IllegalArgumentException("Name of node can oly contain lowercase and upercase letters!");
        }
        if (this.startNode.compareTo(this.endNode) == 0) {
            System.out.println("Graph cannot contain loops!");
            throw new IllegalArgumentException("Graph cannot contain loops!");
        }
        try {
            this.weight = Integer.parseInt(info[2]);
        } catch (Exception e) {
            System.out.println("Weight must be a positive integer!");
            throw new IllegalArgumentException("Weight must be a positive integer!");
        }
        if (this.weight < 0) {
            System.out.println("Weight cannot be lower than 0!");
            throw new IllegalArgumentException("Weight cannot be lower than 0!");
        }
    }

    @Override
    public int compareTo(Link o) {
        return this.weight - o.weight;
    }

    public String getStartNode() {
        return this.startNode;
    }

    public String getEndNode() {
        return this.endNode;
    }

    public int getWeight() {
        return this.weight;
    }
}