import edu.princeton.cs.algs4.Picture;
import java.util.ArrayList;

public class SeamCarver {

    private Picture pic;
    ArrayList<Double> energy;
    //ArrayList<Double> energy1;

    public SeamCarver(Picture picture) {
        pic = picture;
        energy = new ArrayList<>(pic.width() * pic.height());
        //energy1 = new ArrayList<>(pic.width() * pic.height());
        energySum();
    }

    private void energySum() {
        for (int y = 0; y < pic.height(); y++) {
            for (int x = 0; x < pic.width(); x++) {
                if (y == 0) {
                    energy.add(energy(x, y));
                } else {
                    if (x == 0) {
                        double a = Math.min(energy.get(xyToInt(x, y - 1)), energy.get(xyToInt(x + 1, y - 1)));
                        energy.add(energy(x, y) + a);
                    } else if (x == pic.width() - 1) {
                        double b = Math.min(energy.get(xyToInt(x, y - 1)), energy.get(xyToInt(x - 1, y - 1)));
                        energy.add(energy(x, y) + b);
                    } else {
                        double c = Math.min(energy.get(xyToInt(x, y - 1)), energy.get(xyToInt(x - 1, y - 1)));
                        double d = Math.min(energy.get(xyToInt(x + 1, y - 1)), c);
                        energy.add(energy(x, y) + d);
                    }
                }
                //System.out.print(energy.get(xyToInt(x, y)) + "    ");
            }
            //System.out.println("     energy sum");
        }
        /**for (int y = 0; y < pic.height(); y++) {
            for (int x = 0; x < pic.width(); x++) {
                energy1.add(energy(x, y));
                //System.out.print(energy1.get(xyToInt(x, y)) + "    ");
            }
            //System.out.println("     energy");
        }*/
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        int xL = Math.floorMod(x - 1, width());
        int xR = Math.floorMod(x + 1, width());
        int yU = Math.floorMod(y - 1, height());
        int yD = Math.floorMod(y + 1, height());
        return getSum(xL, y, xR, y) + getSum(x, yU, x, yD);
    }

    private double getSum(int x, int y, int x1, int y1) {
        double red = Math.pow((pic.get(x, y).getRed() - pic.get(x1, y1).getRed()), 2);
        double blue = Math.pow((pic.get(x, y).getBlue() - pic.get(x1, y1).getBlue()), 2);
        double green = Math.pow((pic.get(x, y).getGreen() - pic.get(x1, y1).getGreen()), 2);
        return red + blue + green;
    }

    private int xyToInt(int x, int y) {
        return x + y * pic.width();
    }

    private int toX(int index) {
        return index % pic.width();
    }

    private int toY(int index) {
        return index / pic.width();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture backup = new Picture(pic.width(), pic.height());
        for (int col = 0; col < pic.width(); col++) {
            for (int row = 0; row < pic.height(); row++) {
                backup.set(col, row, pic.get(col, row));
            }
        }
        Picture p = new Picture(pic.height(), pic.width());
        for (int col = 0; col < p.width(); col++) {
            for (int row = 0; row < p.height(); row++) {
                p.set(col, row, pic.get(row, col));
            }
        }
        pic = p;
        energy.clear();
        //energy1.clear();
        energySum();
        int[] b = findVerticalSeam();
        pic = backup;
        energy.clear();
        /**energy1.clear();*/
        energySum();
        return b;
    }

    /** sequence of indices for vertical seam.
     *
     * @return a
     */
    public int[] findVerticalSeam() {
        int [] a = new int[pic.height()];
        double min = Double.MAX_VALUE;
        int y = pic.height() - 1;
        for (int x = 0; x < pic.width(); x++) {
            if (energy.get(xyToInt(x, y)) < min) {
                min = energy.get(xyToInt(x, y));
                a[pic.height() - 1] = x;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            int x = a[i + 1];
            double m = Double.MAX_VALUE;
            for (int j = -1; j < 2; j++) {
                if (x + j < 0) {
                    continue;
                }
                if (x + j >= pic.width()) {
                    continue;
                }
                if (energy.get(xyToInt(x + j, i)) < m) {
                    m = energy.get(xyToInt(x + j, i));
                    a[i] = x + j;
                }
            }
        }
        /**
         * for (int c : a) {
            System.out.print(c + "   ");
        }*/
        return a;
    }

    /** remove horizontal seam from picture.
     *
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        Picture p = new Picture(pic.width(), pic.height() - 1);
        for (int x = 0; x < pic.width(); x++) {
            int i = 0;
            for (int y = 0; y < pic.height() - 1; y++) {
                if (y == seam[x]) {
                    i = 1;
                    continue;
                }
                p.set(x, y, pic.get(x, y + i));
            }
        }
        pic = p;
        energySum();
    }

    /**remove vertical seam from picture.
     *
     * @param seam a
     */
    public void removeVerticalSeam(int[] seam) {
        Picture p = new Picture(pic.width() - 1, pic.height());
        for (int y = 0; y < pic.height(); y++) {
            int i = 0;
            for (int x = 0; x < pic.width() - 1; x++) {
                if (x == seam[y]) {
                    i = 1;
                    continue;
                }
                p.set(x, y, pic.get(x + i, y));
            }
        }
        pic = p;
        energySum();
    }
}
