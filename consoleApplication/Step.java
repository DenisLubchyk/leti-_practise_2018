package com.eltech.practice.utils;

import java.util.Objects;

public class Step <T> {
    private String description; // описание шага
    private Node<T> value; // номер вершины

    public Step(String description, Node<T> value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    } // возврат описания

    public void setDescription(String description) {
        this.description = description;
    } // присвоение описания

    public Node<T> getValue() {
        return value;
    } // возврат номера вершины

    public void setValue(Node<T> value) {
        this.value = value;
    } // присвоение номера  вершины

    @Override
    public boolean equals(Object o) { // переопределение Equals
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step<?> step = (Step<?>) o;
        return Objects.equals(description, step.description) &&
                Objects.equals(value, step.value);
    }

    @Override // переопределение возврата (ключ, значение)
    public int hashCode() {

        return Objects.hash(description, value);
    }

    @Override // переопределение  toString для выводы информации об описании шагов алгоритма
    public String toString() {
        return "Step{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}
