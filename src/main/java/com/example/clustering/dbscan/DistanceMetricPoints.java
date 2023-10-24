package com.example.clustering.dbscan;

public class DistanceMetricPoints implements DistanceMetric<Point>{

    @Override
    public double calculateDistance(Point val1, Point val2) throws DBSCANClusteringException {
        return Math.sqrt(Math.pow(val1.getX()-val2.getX(), 2)+Math.pow(val1.getY()-val2.getY(), 2));
    }
}
