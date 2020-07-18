package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator{

    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        int weirdState = state & (state >>> 3) % period;
        double a = period;
        return (weirdState % a) / a * 2 - 1;
    }

}
