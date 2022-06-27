package pl.edu.pw.ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static boolean isGraphConnected(List<Link> graph) {
        List<String> nodes = new ArrayList<>();
        List<String> visitedNodes = new ArrayList<>();
        for (Link link : graph) {
            if (!nodes.contains(link.getStartNode())) {
                nodes.add(link.getStartNode());
            }
            if (!nodes.contains(link.getEndNode())) {
                nodes.add(link.getEndNode());
            }
        }

        String currentNode = graph.get(0).getStartNode();
        visitedNodes.add(currentNode);
        List<Link> stack = new ArrayList<>();
        stack.add(graph.get(0));
        while (stack.size() > 0) {
            for (int i = 0; i < graph.size(); i++) {
                if (graph.get(i).getStartNode().compareTo(currentNode) == 0
                        && !visitedNodes.contains(graph.get(i).getEndNode())) {
                    stack.add(graph.get(i));
                    currentNode = graph.get(i).getEndNode();
                    if (!visitedNodes.contains(currentNode)) {
                        visitedNodes.add(currentNode);
                    }
                    break;
                }
                if (graph.get(i).getEndNode().compareTo(currentNode) == 0
                        && !visitedNodes.contains(graph.get(i).getStartNode())) {
                    stack.add(graph.get(i));
                    currentNode = graph.get(i).getStartNode();
                    if (!visitedNodes.contains(currentNode)) {
                        visitedNodes.add(currentNode);
                    }
                    break;
                }
                if (i == graph.size() - 1) {
                    Link link = stack.get(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    if (stack.size() == 0) {
                        break;
                    }
                    if (link.getStartNode().compareTo(currentNode) == 0) {
                        currentNode = link.getEndNode();
                    } else {
                        currentNode = link.getStartNode();
                    }
                    i = 0;
                }
            }
        }

        for (String string : nodes) {
            if (!visitedNodes.contains(string)) {
                return false;
            }
        }
        return true;
    }

    public static void readGraph(String pathToFile, List<Link> graph) {
        boolean fileNotFoundExceptionFlag = false;
        try {
            File myObj = new File(pathToFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Link newLink = new Link(data);
                graph.add(newLink);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            fileNotFoundExceptionFlag = true;
        }
        if (graph.size() < 1) {
            if (fileNotFoundExceptionFlag) {
                System.out.println("There is no file with given path!");
                throw new IllegalArgumentException("There is no file with given path!");
            }
            System.out.println("Graph must have at least 1 link!");
            throw new IllegalArgumentException("Graph must have at least 1 link!");
        }
        if (!Utils.isGraphConnected(graph)) {
            System.out.println("Graph must be connected!");
            throw new IllegalArgumentException("Graph must be connected!");
        }
    }
}
