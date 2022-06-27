package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.List;

public class KruskalAlgorithm {
    public String findMST(String pathToFile) {
        if( pathToFile == null){
            throw new IllegalArgumentException("Path connot be null!");
        }
        List<Link> graph = new ArrayList<>();
        Utils.readGraph(pathToFile, graph);

        StringBuilder result = new StringBuilder();
        LinkPriorityQueue priorityQueue = new LinkPriorityQueue();
        priorityQueue.add(graph);

        List<String> forest = new ArrayList<>();
        for (Link link : graph) {
            if (!forest.contains(link.getStartNode())) {
                forest.add(link.getStartNode());
            }
            if (!forest.contains(link.getEndNode())) {
                forest.add(link.getEndNode());
            }
        }

        while (priorityQueue.size() > 0) {
            Link link = priorityQueue.pop();
            String startNode = link.getStartNode();
            String endNode = link.getEndNode();
            int weight = link.getWeight();

            String treeA = "";
            int treeAindex = -1;
            String treeB = "";
            int treeBindex = -1;

            for (int i = forest.size() - 1; i >= 0; i--) {
                for (String string : forest.get(i).split(" ")) {
                    if (string.compareTo(startNode) == 0) {
                        treeA = forest.get(i);
                        treeAindex = i;
                    }
                    if (string.compareTo(endNode) == 0) {
                        treeB = forest.get(i);
                        treeBindex = i;
                    }
                }
            }

            if (treeA.compareTo("") != 0 && treeB.compareTo("") != 0 && treeAindex != treeBindex) {
                String newTree = treeA + " " + treeB;
                forest.add(newTree);
                if(treeAindex > treeBindex){
                    forest.remove(treeAindex);
                    forest.remove(treeBindex);
                }
                else{
                    forest.remove(treeBindex);
                    forest.remove(treeAindex);
                }
                result.append(startNode).append('_').append(weight).append('_').append(endNode).append('|');
            }
        }

        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
