package com.eltech.practice.graphs;

import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleGraph {
    private int numberOfNode;  // количество создавваемых вершин
    private List<Pair<Integer, Integer>> edges = new ArrayList<>(); // список инцидентных вершин

    public void addNode() {   // добавление вершины
        this.numberOfNode++;
    }

    public void delNode(){
        if (this.numberOfNode!=1) {
            this.numberOfNode--;
        }
    }


    public void addEdge(JSpinner from, JSpinner to) {   // добавление ребер
        this.edges.add(new Pair<>((Integer) from.getValue(), (Integer) to.getValue()));
    }

    public void addEdge(int from, int to) {
        this.edges.add(new Pair<>(from, to));
    }

    public ArrayList<Pair<Double, Double>> getNodes() {  // размещения вершин на поле для основного графа
        ArrayList<Pair<Double, Double>> points = new ArrayList<>();
        double phi0 = 0;
        double phi = 2 * Math.PI / numberOfNode;
        int r = 230;
        for (int i = 0; i <numberOfNode ; i++) {
            points.add(new Pair<>(250 + r * Math.cos(phi0), 250 + r * Math.sin(phi0)));
            phi0 += phi;
        }
        return points;
    }

    public int getNumberOfNode() {
        return numberOfNode;
    } // возвращения количества вершин

    public List<Pair<Integer, Integer>> getEdges() {
        return edges;
    } // возвращение количества ребер

    public void clear() {     // очистка основного поля
        numberOfNode = 0;
        edges = new ArrayList<>();
    }
}
