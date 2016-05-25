package com.andersoncarvalho.pidtp.service.util;

public class Range {
    public double min;
    public double max;

    public Range(double _min, double _max) {
        min = _min;
        max = _max;
    }

    public double GetSpan() {
        return max - min;
    }

    public double GetScalingFactor(Range map_to) {
        return map_to.GetSpan() / GetSpan();
    }

    public boolean IsInRange(double value, boolean closed_interval) {
        if (!closed_interval) {
            return (value > min && value < max);
        } else {
            return (value >= min && value <= max);
        }
    }
}
