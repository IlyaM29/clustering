package com.example.clustering.dbscan;

public class Point extends Number {
    private final Double x;
    private final Double y;

    public Point(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public int intValue() {
        return (int) Math.abs(x-y);
    }

    @Override
    public long longValue() {
        return (long) Math.abs(x-y);
    }

    @Override
    public float floatValue() {
        return (float) Math.abs(x-y);
    }

    @Override
    public double doubleValue() {
        return Math.sqrt(Math.pow(x, 2)-Math.pow(y, 2));
    }
}
