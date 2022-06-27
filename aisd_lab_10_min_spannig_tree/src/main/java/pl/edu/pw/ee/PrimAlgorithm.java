package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithm implements MinSpanningTree { 

    public String findMST(String pathToFile) {
        if( pathToFile == null){
            throw new IllegalArgumentException("Path connot be null!");
        }
        List<Link> graph = new ArrayList<>();
        List<String> visitedNodes = new ArrayList<>();
        Utils.readGraph(pathToFile, graph);

        StringBuilder result = new StringBuilder();
        String currentNode = graph.get(0).getStartNode();
        visitedNodes.add(currentNode);
        LinkPriorityQueue priorityQueue = new LinkPriorityQueue();
        while (graph.size() > 0) {
            for (int i = graph.size() - 1; i >= 0; i--) {
                Link link = graph.get(i);
                if (link.getStartNode().compareTo(currentNode) == 0) {
                    priorityQueue.add(link);
                    graph.remove(i);
                }
                if (link.getEndNode().compareTo(currentNode) == 0) {
                    priorityQueue.add(link);
                    graph.remove(i);
                }
            }
            while (priorityQueue.size() > 0) {
                Link link = priorityQueue.pop();
                if (link.getStartNode().compareTo(currentNode) == 0 && !visitedNodes.contains(link.getEndNode())) {
                    result.append(link.getStartNode()).append('_').append(link.getWeight()).append('_').append(link.getEndNode())
                            .append('|');
                    currentNode = link.getEndNode();
                    break;
                } else if (link.getEndNode().compareTo(currentNode) == 0 && !visitedNodes.contains(link.getStartNode())) {
                    result.append(link.getStartNode()).append('_').append(link.getWeight()).append('_').append(link.getEndNode())
                            .append('|');
                    currentNode = link.getStartNode();
                    break;
                }
            }
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
