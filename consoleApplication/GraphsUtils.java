package com.eltech.practice.utils;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;

import java.util.*;


public class GraphsUtils {
    private GraphsUtils() {
    }

    public static <T> List<Step<T>> deepFirstSearch(Graph<Node<T>> graph, Node<T> startNode) {
        Integer time = 0;
        List<Step<T>> history = new ArrayList<>();  // список для хранения истории
        Deque<Node<T>> deque = new LinkedList<>(); // список обработанных вершин

        deque.addLast(startNode);
        startNode.setInTime(time++);

        while (!deque.isEmpty()) {
            Node<T> current = deque.getLast();
            saveToHistory(history, current, "Текущий элемент: %s");

            Set<Node<T>> nodes = graph.successors(current); // обработанный элемент
            if (nodes.isEmpty()) {
                current.setOutTime(time++);
                deque.removeLast();
                saveToHistory(history, current, "Вышли из элемента: %s");
                continue;
            }

            boolean foundNotVisited = false;
            for (Node<T> node : nodes) {

                if (node.getInTime() == Integer.MAX_VALUE) {
                    deque.addLast(node);
                    node.setInTime(time++);
                    saveToHistory(history, node, "Добавили элемент: %s");
                    foundNotVisited = true;
                    break;
                }
            }

            if (!foundNotVisited) {
                current.setOutTime(time++);
                deque.removeLast();
                saveToHistory(history, current, "Вышли из элемента: %s");
            }
        }

        return history;
    }

    public static <T> List<Step<T>> dijkstra(Graph<Node<T>> graph, Node<T> startNode) {
        HashMap<T, Integer> map = new HashMap<>(); // Map with weight corresponding to Nodes.
        List<Step<T>> history = new ArrayList<>();
        Deque<Node<T>> deque = new LinkedList<>();

        deque.addLast(startNode);
        startNode.setInTime(0);
        map.put(startNode.getValue(), 0);

        while (!deque.isEmpty()) {
            Node<T> current = deque.getFirst();
            saveToHistory(history, current, "Текущий элемент: %s");

            Set<Node<T>> nodes = new HashSet<>();
            Set<EndpointPair<Node<T>>> edges = graph.incidentEdges(current);
            for (EndpointPair<Node<T>> edge : edges) {
                if (edge.target() != current) {
                    nodes.add(edge.target());
                }
            }

            for (Node<T> node : nodes) {

                // Compare weights
                if (map.getOrDefault(node.getValue(), Integer.MAX_VALUE) > map.get(current.getValue()) + 1) {
                    deque.addLast(node);
                    // Put them to the map
                    map.put(node.getValue(), current.getInTime() + 1);
                    node.setInTime(map.get(node.getValue()));

                    saveToHistory(history, node, "Добавили элемент в очередь: %s");
                }
            }

            deque.removeFirst();

            saveToHistory(history, current, "Вышли из элемента: %s");
        }

        return history;
    }

    private static <T> void saveToHistory(List<Step<T>> history, Node<T> current, String s) {
        Node<T> toHistory = copy(current);
        history.add(new Step<>(String.format(s + "\n", toHistory), toHistory));
    }

    private static <T> Node<T> copy(Node<T> current) {
        Node<T> toHistory = new Node<>(current.getValue());
        toHistory.setInTime(current.getInTime());
        toHistory.setOutTime(current.getOutTime());
        return toHistory;
    }
}
