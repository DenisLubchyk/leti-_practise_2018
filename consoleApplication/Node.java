package com.eltech.practice.utils;

import java.util.Objects;

public class Node <T> {
    private T value;
    private int inTime = Integer.MAX_VALUE;
    private int outTime = Integer.MAX_VALUE;

    public Node(T value, int inTime, int outTime) {
        this.value = value;
        this.inTime = inTime;
        this.outTime = outTime;
    }

    public Node(T value) {
        this.value = value;
    } //  присвоение значения

    public T getValue() {
        return value;
    } // возврат значения

    public void setValue(T value) {
        this.value = value;
    } // присвоение значений

    public int getInTime() {
        return inTime;
    } // возвращение времени входа

    public void setInTime(int inTime) {
        this.inTime = inTime;
    } // присвоение времени входа

    public int getOutTime() {
        return outTime;
    } //  возвращение времени выхода

    public void setOutTime(int outTime) {
        this.outTime = outTime;
    } // присвоение времени выхода

    @Override  // переопределение метода equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override  // переопределение метода hashCode
    public int hashCode() {
        return Objects.hash(value, outTime);
    }

    @Override  // переопределение метода toString
    public String toString() {
        return "Узел " +
                " со значением " + value +
                ", временем входа " + inTime +
                ", временем выхода " + (outTime == Integer.MAX_VALUE ? "" : outTime);
    }
}
