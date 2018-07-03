package com.eltech.practice.visualizers;

import com.eltech.practice.graphs.SimpleGraph;
import com.eltech.practice.utils.Step;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphVisualizer extends JPanel {

    public GraphVisualizer() {
        this.setSize(565, 605);
        this.setLocation(new Point(395, 5));
    }

    public void visualizeGraph(SimpleGraph simpleGraph, List<Step<Integer>> history, int step, JTextArea info) {

        removeAll();

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        ArrayList<Pair<Double, Double>> nodes = simpleGraph.getNodes();
        ArrayList<Object> vertexes = new ArrayList<>();

        int i = 0;
        for (Pair<Double, Double> node : nodes) {
            ++i;
            vertexes.add(graph.insertVertex(parent, null, i, node.getKey(), node.getValue(), 60, 60, "shape=ellipse"));

        }

        info.selectAll();
        info.replaceSelection("");

        for (int j = 0; j < step; j++) {
            Step<Integer> stepOne = history.get(j);
            mxCell cell = (mxCell) vertexes.get(stepOne.getValue().getValue() - 1);
            cell.setValue(history.get(j).getValue().getValue().toString() +
                    "\n" + history.get(j).getValue().getInTime() +
                    ":" + (history.get(j).getValue().getOutTime() == Integer.MAX_VALUE ? "-" : history.get(j).getValue().getOutTime()));
            if (stepOne.getValue().getInTime() != Integer.MAX_VALUE && stepOne.getValue().getOutTime() == Integer.MAX_VALUE) {
                graph.setCellStyle("shape=ellipse;strokeColor=#5cff5e", new Object[]{cell});
            } else if (stepOne.getValue().getInTime() != Integer.MAX_VALUE && stepOne.getValue().getOutTime() != Integer.MAX_VALUE) {
                graph.setCellStyle("shape=ellipse;strokeColor=#ff0000", new Object[]{cell});
            }
            if (stepOne.getDescription().startsWith("Текущий")) {
                graph.setCellStyle("shape=ellipse;strokeColor=#00ffff", new Object[]{cell});
            }
            info.append(stepOne.getDescription());
        }
        for (Pair<Integer, Integer> edge : simpleGraph.getEdges()) {
            graph.insertEdge(parent, null, null, vertexes.get(edge.getKey() - 1), vertexes.get(edge.getValue() - 1));
        }


        graph.getModel().endUpdate();

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setVisible(true);
        graphComponent.setConnectable(false);
        graphComponent.setSize(565, 605);
        graphComponent.setLocation(new Point(395, 15));
        graphComponent.setBorder(new EmptyBorder(0,0,0,0));

        graph.setAllowDanglingEdges(false);
        graph.setCellsResizable(false);
        graph.setCellsDeletable(false);
        graph.setCellsEditable(false);
        graph.setCellsDisconnectable(false);
        graph.setEdgeLabelsMovable(false);
        graph.setConnectableEdges(false);

        this.add(graphComponent);
        this.revalidate();

    }
}
