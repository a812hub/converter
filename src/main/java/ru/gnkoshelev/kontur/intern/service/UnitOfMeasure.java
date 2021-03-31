/*
 * UnitOfMeasure
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern.service;

import java.util.Objects;

public class UnitOfMeasure {

    private String name;
    private double value;

    public UnitOfMeasure(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitOfMeasure that = (UnitOfMeasure) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
