package com.andersoncarvalho.pidtp.misc.filtros;

import com.andersoncarvalho.pidtp.service.util.Kernel;

/**
 * Created by anderson on 24/05/16.
 */
public class Gaussian extends Kernel {

    //fields
    double magnitude, radius;

    //parameterized constructor
    public Gaussian(double _magnitude, double _radius) {
        radius = _radius;
        magnitude = _magnitude;
    }

    public double GetFactor(double width_ratio, double height_ratio) {
        double exp = -(width_ratio * width_ratio + height_ratio * height_ratio) / (2 * radius * radius);
        return magnitude * Math.exp(exp);
    }
}

