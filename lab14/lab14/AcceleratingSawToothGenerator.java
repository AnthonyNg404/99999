package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator{


    private int period;
    private int state;
    private double fractal;

    public AcceleratingSawToothGenerator(int period, double fractal) {
        state = 0;
        this.period = period;
        this.fractal = fractal;
    }

    public double next() {
        state = (state + 1);
        if (state >= period) {
            this.period = (int) Math.round(this.period * this.fractal);
            state = 0;
        }
        double a = period;
        return (state % a) / a * 2 - 1;
    }
}

