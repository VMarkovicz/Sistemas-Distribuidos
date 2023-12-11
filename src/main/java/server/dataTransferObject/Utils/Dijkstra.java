package server.dataTransferObject.Utils;


import server.controller.SegmentManager;
import server.dataTransferObject.NodeDTO;
import server.models.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dijkstra {
    private static NodeDTO startNode;
    public static List<NodeDTO> dijkstra(Long pdi_inicial, Long pdi_final){

        startNode = new NodeDTO(pdi_inicial, pdi_inicial, 0.0, null, null, new ArrayList<>());
        NodeDTO currentNode = startNode;

        while (!Objects.equals(currentNode.getPdiFinal(), pdi_final)){
            List<Segment> segmentList = SegmentManager.getInstance().findNeighbors(currentNode);
            List<NodeDTO> neighbors = new ArrayList<>();
            for (Segment segment : segmentList) {
                if (!Objects.equals(currentNode.getPdiInicial(), segment.getPdi_final())) {
                    neighbors.add(new NodeDTO(segment.getPdi_inicial(), segment.getPdi_final(), segment.getDistancia()+currentNode.getDistancia(), segment.getAviso(), currentNode, new ArrayList<>()));
                }
            }
            currentNode.setNeighbors(neighbors);
            currentNode = findShortestLeaf(startNode, pdi_final);
        }

        return buildPath(currentNode);
    }

    private static List<NodeDTO> findLeafs(NodeDTO currentNode){
        List<NodeDTO> leafs = new ArrayList<>();

        if (currentNode.getNeighbors().isEmpty() || currentNode.getNeighbors() == null){
            leafs.add(currentNode);
        }
        else {
            for (NodeDTO node : currentNode.getNeighbors()) {
                leafs.addAll(findLeafs(node));
            }
        }
        return leafs;
    }

    private static NodeDTO findShortestLeaf(NodeDTO startNode, Long pdi_final) {
        List<NodeDTO> leafs = findLeafs(startNode);
        NodeDTO shortestLeaf = leafs.get(0);
        for (NodeDTO leaf : leafs) {
            if (leaf.getDistancia() < shortestLeaf.getDistancia()){
                shortestLeaf = leaf;
            }
            if (Objects.equals(leaf.getPdiFinal(), pdi_final)){
                return leaf;
            }
        }
        return shortestLeaf;
    }

    private static List<NodeDTO> buildPath(NodeDTO node){
        List<NodeDTO> path = new ArrayList<>();

        while (node.getFather() != null){
            path.add(0, node);
            node = node.getFather();
        }

        return path;
    }
}
