package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {

    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        double a = period;
        return (state % a) / a * 2 - 1;
    }
}
