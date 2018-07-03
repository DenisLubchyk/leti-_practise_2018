package com.eltech.practice;

import com.eltech.practice.graphs.SimpleGraph;
import com.eltech.practice.utils.GraphsUtils;
import com.eltech.practice.utils.Node;
import com.eltech.practice.utils.Step;
import com.eltech.practice.visualizers.GraphVisualizer;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    private static JFrame frame;
    private static SimpleGraph simpleGraph = new SimpleGraph();
    private static GraphVisualizer graphVisualizer = new GraphVisualizer();
    private static List<Step<Integer>> history = new ArrayList<>();
    private static int step;


    public static void main(String[] args) {

        frame = new JFrame("Обход ориентированного графа в глубину ");

        Color blueColor = new Color(0x6495ED);
        frame.getContentPane().setBackground(blueColor);
        frame.setSize(new Dimension(970, 640));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton addVertexButton = new JButton("Добавить вершину");
        addVertexButton.setSize(new Dimension(150, 50));
        addVertexButton.setLocation(5, 5);

        JButton delVertexButton = new JButton("Удалить вершину");
        delVertexButton.setSize(new Dimension(150, 50));
        delVertexButton.setLocation(5, 60);

        JLabel addEdgeLabel = new JLabel("Добавить ребро");
        addEdgeLabel.setSize(new Dimension(150, 50));
        addEdgeLabel.setLocation(29, 100);

        JLabel fromEdgeLabel = new JLabel("из");
        fromEdgeLabel.setSize(new Dimension(75, 50));
        fromEdgeLabel.setLocation(35, 120);

        JLabel toEdgeLabel = new JLabel("в");
        toEdgeLabel.setSize(new Dimension(75, 50));
        toEdgeLabel.setLocation(125, 120);

        SpinnerModel fromSpinnerModel = new SpinnerNumberModel(1, 1, 50, 1);
        JSpinner fromSpinner = new JSpinner(fromSpinnerModel);
        fromSpinner.setSize(new Dimension(55, 25));
        fromSpinner.setLocation(10, 155);

        SpinnerModel toSpinnerModel = new SpinnerNumberModel(1, 1, 50, 1);
        JSpinner toSpinner = new JSpinner(toSpinnerModel);
        toSpinner.setSize(new Dimension(55, 25));
        toSpinner.setLocation(100, 155);

        JButton addEdgeButton = new JButton("Добавить ребро");
        addEdgeButton.setSize(new Dimension(150, 50));
        addEdgeButton.setLocation(5, 185);

        JButton loadFromFileButton = new JButton("Выбрать файл");
        loadFromFileButton.setSize(new Dimension(150, 50));
        loadFromFileButton.setLocation(5, 240);

        JButton clearFieldButton = new JButton("Очистить поля");
        clearFieldButton.setSize(new Dimension(150, 50));
        clearFieldButton.setLocation(5, 295);

        JButton startButton = new JButton("Старт");//
        startButton.setSize(new Dimension(150, 65));
        startButton.setLocation(210, 5);

        JButton resultButton = new JButton("Результат");
        resultButton.setSize(new Dimension(150, 50));
        resultButton.setLocation(210, 150);

        JButton nextButton = new JButton("Следующий шаг");
        nextButton.setSize(new Dimension(150, 50));
        nextButton.setLocation(210, 85);

        JButton prevButton = new JButton("Предыдущий шаг");
        prevButton.setSize(new Dimension(150, 50));
        prevButton.setLocation(210, 215);

        JButton dijkstraButton = new JButton("Дейкстра");
        dijkstraButton.setSize(new Dimension(150, 65));
        dijkstraButton.setLocation(210, 280);

        JTextArea infoTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        scrollPane.setBounds(5, 360, 385, 250);
        infoTextArea.setEditable(false);


//        JTextArea resultTextArea = new JTextArea();
//        resultTextArea.setSize(565, 68);
//        resultTextArea.setLocation(395, 540);
//        resultTextArea.setEditable(false);

        frame.add(addVertexButton);
        frame.add(delVertexButton);
        frame.add(addEdgeLabel);
        frame.add(fromEdgeLabel);
        frame.add(toEdgeLabel);
        frame.add(fromSpinner);
        frame.add(toSpinner);
        frame.add(addEdgeButton);
        frame.add(startButton);
        frame.add(nextButton);
        frame.add(prevButton);
        frame.add(dijkstraButton);
        frame.add(loadFromFileButton);
      //  frame.add(resultTextArea);
        frame.add(resultButton);
        frame.add(clearFieldButton);
        frame.add(scrollPane);
        frame.add(graphVisualizer);
        frame.setVisible(true);
        delVertexButton.setEnabled(false);
        startButton.setEnabled(false);
        dijkstraButton.setEnabled(false);


        loadFromFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    HashSet<Integer> vertexes = new HashSet<>();
                    File selectedFile = fileChooser.getSelectedFile();
                    for (String str : Files.readAllLines(Paths.get(selectedFile.getPath()))) {
                        String[] s = str.split(" ");
                        int v = Integer.parseInt(s[0]);
                        int w = Integer.parseInt(s[1]);
                        vertexes.add(v);
                        vertexes.add(w);
                        for (Pair<Integer, Integer> integerIntegerPair : simpleGraph.getEdges()) {
                            if (integerIntegerPair.getKey() == v && integerIntegerPair.getValue() == w) {
                                JOptionPane.showMessageDialog(null, "Некорректный ввод", "Attention", JOptionPane.ERROR_MESSAGE);
                                resultButton.setEnabled(false);
                                nextButton.setEnabled(false);
                                prevButton.setEnabled(false);
                                startButton.setEnabled(true);
                                dijkstraButton.setEnabled(true);
                                loadFromFileButton.setEnabled(true);
                                addEdgeButton.setEnabled(true);
                                addVertexButton.setEnabled(true);
                                simpleGraph.clear();
                                history = new ArrayList<>();
                                step = 0;
                                graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
                                frame.repaint();
                                return;
                            }
                        }

                        simpleGraph.addEdge(v, w);
                    }
                    vertexes.forEach(integer -> simpleGraph.addNode());
                    startButton.setEnabled(true);
                    dijkstraButton.setEnabled(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Некорректный данные!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException ex) {
                    //число пар меньше чем задано во 2 поле
                    JOptionPane.showMessageDialog(null, "Несоответствие заданного и фактического количества ребер!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IndexOutOfBoundsException ex) {
                    //связываются не существующие вершины
                    JOptionPane.showMessageDialog(null, "Попытка связать несуществующие вершины!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            graphVisualizer.visualizeGraph(simpleGraph, history,step, infoTextArea);
        });

        startButton.setEnabled(false);
        startButton.addActionListener(e -> {
            resultButton.setEnabled(true);
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
            startButton.setEnabled(false);
            dijkstraButton.setEnabled(false);
            loadFromFileButton.setEnabled(false);
            addEdgeButton.setEnabled(false);
            addVertexButton.setEnabled(false);
            delVertexButton.setEnabled(false);

            MutableGraph<Node<Integer>> graph = GraphBuilder.<Integer>directed().allowsSelfLoops(true).build();
            for (int i = 0; i < simpleGraph.getNumberOfNode(); i++) {
                graph.addNode(new Node<>(i));
            }

            for (Pair<Integer, Integer> edge : simpleGraph.getEdges()) {
                graph.putEdge(new Node<>(edge.getKey()), new Node<>(edge.getValue()));
            }

            history = GraphsUtils.deepFirstSearch(graph, new Node<>(1));
            step = 0;

            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
        });
        clearFieldButton.setEnabled(true);
        clearFieldButton.addActionListener(e -> {
            resultButton.setEnabled(false);
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
            startButton.setEnabled(true);
            dijkstraButton.setEnabled(true);
            loadFromFileButton.setEnabled(true);
            addEdgeButton.setEnabled(true);
            addVertexButton.setEnabled(true);
            simpleGraph.clear();
            history = new ArrayList<>();
            step = 0;
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
            frame.repaint();
            startButton.setEnabled(false);
            dijkstraButton.setEnabled(false);
        });
        addVertexButton.setEnabled(true);
        addVertexButton.addActionListener(e -> {
            simpleGraph.addNode();
            startButton.setEnabled(true);
            dijkstraButton.setEnabled(true);
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
            delVertexButton.setEnabled(true);
        });


        delVertexButton.addActionListener(e -> {
            simpleGraph.delNode();
            startButton.setEnabled(true);
            dijkstraButton.setEnabled(true);
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
        });

        addEdgeButton.setEnabled(true);
        addEdgeButton.addActionListener(e -> {
            for (Pair<Integer, Integer> integerIntegerPair : simpleGraph.getEdges()) {
                if (integerIntegerPair.getKey() == fromSpinner.getValue() && integerIntegerPair.getValue() == toSpinner.getValue()) {
                    JOptionPane.showMessageDialog(null, "Некорректный ввод(такое ребро уже существует)", "Attention", JOptionPane.ERROR_MESSAGE);
                    resultButton.setEnabled(false);
                    nextButton.setEnabled(false);
                    prevButton.setEnabled(false);
                    startButton.setEnabled(true);
                    dijkstraButton.setEnabled(true);


                    loadFromFileButton.setEnabled(true);
                    addEdgeButton.setEnabled(true);
                    addVertexButton.setEnabled(true);
                    //simpleGraph.clear();
                    history = new ArrayList<>();
                    step = 0;
                    graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
                    frame.repaint();
                    return;
                }
            }
            if (((Integer)fromSpinner.getValue()).compareTo(0)<0
                    || ((Integer)toSpinner.getValue()).compareTo(0)<0
                        || ((Integer)fromSpinner.getValue()).compareTo(simpleGraph.getNumberOfNode())>0
                         || ((Integer)toSpinner.getValue()).compareTo(simpleGraph.getNumberOfNode())>0){
                JOptionPane.showMessageDialog(null, "Некорректный ввод(не существует такой вершины)", "Attention", JOptionPane.ERROR_MESSAGE);
            } else {
                simpleGraph.addEdge(fromSpinner, toSpinner);
            }
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
        });
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> {
            if (step<history.size()){
                step++;
            }else {
                JOptionPane.showMessageDialog(null, "Конец алгоритма", "Attention", JOptionPane.ERROR_MESSAGE);
            }
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
        });
        resultButton.setEnabled(false);
        resultButton.addActionListener(e -> {
            step = history.size();
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
            JOptionPane.showMessageDialog(null, "Конец алгоритма", "Attention", JOptionPane.ERROR_MESSAGE);
        });
        prevButton.setEnabled(false);
        prevButton.addActionListener(e -> {
            if (step>0){
                step--;

            }
            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
        });
        dijkstraButton.setEnabled(false);
        dijkstraButton.addActionListener(e -> {
            resultButton.setEnabled(true);
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
            startButton.setEnabled(false);
            dijkstraButton.setEnabled(false);
            loadFromFileButton.setEnabled(false);
            addEdgeButton.setEnabled(false);
            addVertexButton.setEnabled(false);
            delVertexButton.setEnabled(false);

            MutableGraph<Node<Integer>> graph = GraphBuilder.<Integer>directed().allowsSelfLoops(true).build();
            for (int i = 0; i < simpleGraph.getNumberOfNode(); i++) {
                graph.addNode(new Node<>(i));
            }

            for (Pair<Integer, Integer> edge : simpleGraph.getEdges()) {
                graph.putEdge(new Node<>(edge.getKey()), new Node<>(edge.getValue()));
            }

            history = GraphsUtils.dijkstra(graph, new Node<>(1));
            step = 0;

            graphVisualizer.visualizeGraph(simpleGraph, history, step, infoTextArea);
        });



    }
}
