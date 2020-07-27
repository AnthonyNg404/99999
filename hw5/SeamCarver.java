/**


import edu.princeton.cs.algs4.Picture;
import java.util.ArrayList;

public class SeamCarver {

    private Picture pic;
    private ArrayList<Double> energy;
    //ArrayList<Double> energy1;

    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
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
                        double a = Math.min(energy.get(xyToInt(x, y - 1)),
                                energy.get(xyToInt(x + 1, y - 1)));
                        energy.add(energy(x, y) + a);
                    } else if (x == pic.width() - 1) {
                        double b = Math.min(energy.get(xyToInt(x, y - 1)),
                                energy.get(xyToInt(x - 1, y - 1)));
                        energy.add(energy(x, y) + b);
                    } else {
                        double c = Math.min(energy.get(xyToInt(x, y - 1)),
                                energy.get(xyToInt(x - 1, y - 1)));
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
        }*//**
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
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
        if (x < 0 || x >= pic.width()) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (y < 0 || y >= pic.height()) {
            throw new java.lang.IndexOutOfBoundsException();
        }
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
        Picture backup = new Picture(pic);
        Picture p = new Picture(pic.height(), pic.width());
        for (int col = 0; col < p.width(); col++) {
            for (int row = 0; row < p.height(); row++) {
                p.set(col, row, pic.get(row, col));
            }
        }
        pic = new Picture(p);
        energy.clear();
        //energy1.clear();
        energySum();
        int[] b = findVerticalSeam();
        pic = new Picture(backup);
        energy.clear();
        /**energy1.clear();*//**
        energySum();
        return b;
    }

    // sequence of indices for vertical seam.
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
        }*//**
        pic = new Picture(pic);
        return a;
    }

    // remove horizontal seam from picture.
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length > pic.width() || seam.length == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new java.lang.IllegalArgumentException();
            }
        }
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
        pic = new Picture(p);
        energySum();
    }

    // remove vertical seam from picture.
    public void removeVerticalSeam(int[] seam) {
        if (seam.length > pic.height() || seam.length == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new java.lang.IllegalArgumentException();
            }
        }
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
        pic = new Picture(p);
        energySum();
    }
}*/



import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private void validate(Picture pic, int x, int y) {
        if (x < 0 || x >= pic.width() || y < 0 || y >= pic.height()) {
            throw new IndexOutOfBoundsException();
        }
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return energy(picture, x, y);
    }

    private double energy(Picture pic, int x, int y) {
        validate(pic, x, y);
        return quadraticSumOfX(pic, x, y) + quadraticSumOfY(pic, x, y);
    }

    private int leftOfX(Picture pic, int x) {
        if (x == 0) {
            return pic.width() - 1;
        }
        return x - 1;
    }

    private int rightOfX(Picture pic, int x) {
        if (x == pic.width() - 1) {
            return 0;
        }
        return x + 1;
    }

    private int upOfY(Picture pic, int y) {
        if (y == 0) {
            return pic.height() - 1;
        }
        return y - 1;
    }

    private int downOfY(Picture pic, int y) {
        if (y == pic.height() - 1) {
            return 0;
        }
        return y + 1;
    }

    private int rx(Picture pic, int x, int y) {
        return pic.get(rightOfX(pic, x), y).getRed()
                - pic.get(leftOfX(pic, x), y).getRed();
    }

    private int gx(Picture pic, int x, int y) {
        return pic.get(rightOfX(pic, x), y).getGreen()
                - pic.get(leftOfX(pic, x), y).getGreen();
    }

    private int bx(Picture pic, int x, int y) {
        return pic.get(rightOfX(pic, x), y).getBlue()
                - pic.get(leftOfX(pic, x), y).getBlue();
    }

    private double quadraticSumOfX(Picture pic, int x, int y) {
        return (Math.pow(rx(pic, x, y), 2) + Math.pow(gx(pic, x, y), 2)
                + Math.pow(bx(pic, x, y), 2));
    }

    private int ry(Picture pic, int x, int y) {
        return pic.get(x, downOfY(pic, y)).getRed()
                - pic.get(x, upOfY(pic, y)).getRed();
    }

    private int gy(Picture pic, int x, int y) {
        return pic.get(x, downOfY(pic, y)).getGreen()
                - pic.get(x, upOfY(pic, y)).getGreen();
    }

    private int by(Picture pic, int x, int y) {
        return pic.get(x, downOfY(pic, y)).getBlue()
                - pic.get(x, upOfY(pic, y)).getBlue();
    }

    private double quadraticSumOfY(Picture pic, int x, int y) {
        return (Math.pow(ry(pic, x, y), 2) + Math.pow(gy(pic, x, y), 2)
                + Math.pow(by(pic, x, y), 2));
    }

    private Picture transposition(Picture pic) {
        Picture trans = new Picture(pic.height(), pic.width());

        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                trans.set(j, i, pic.get(i, j));
            }
        }

        return trans;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return findVerticalSeam(transposition(picture));
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return findVerticalSeam(picture);
    }

    private int[]
        findVerticalSeam(Picture pic) {

        double[][] energy = new double[pic.width()][pic.height()];
        double[][] cost = new double[pic.width()][pic.height()];
        int[][] prevPixel = new int[pic.width()][pic.height()];
        int[] result = new int[pic.height()];

        if (pic.width() == 1) {
            for (int i = 0; i < pic.height(); i++) {
                result[i] = 0;
            }
            return result;
        }

        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                energy[i][j] = energy(pic, i, j);
            }
        }

        for (int i = 0; i < pic.width(); i++) {
            cost[i][0] = energy[i][0];
        }

        for (int j = 1; j < pic.height(); j++) {
            for (int i = 0; i < pic.width(); i++) {
                if (i == 0) {
                    if (cost[i][j - 1] <= cost[i + 1][j - 1]) {
                        cost[i][j] = cost[i][j - 1] + energy[i][j];
                        prevPixel[i][j] = i;
                    } else {
                        cost[i][j] = cost[i + 1][j - 1] + energy[i][j];
                        prevPixel[i][j] = i + 1;
                    }
                } else if (i == pic.width() - 1) {
                    if (cost[i - 1][j - 1] <= cost[i][j - 1]) {
                        cost[i][j] = cost[i - 1][j - 1] + energy[i][j];
                        prevPixel[i][j] = i - 1;
                    } else {
                        cost[i][j] = cost[i][j - 1] + energy[i][j];
                        prevPixel[i][j] = i;
                    }
                } else {
                    if (cost[i][j - 1] <= cost[i + 1][j - 1]) {
                        if (cost[i - 1][j - 1] <= cost[i][j - 1]) {
                            cost[i][j] = cost[i - 1][j - 1] + energy[i][j];
                            prevPixel[i][j] = i - 1;
                        } else {
                            cost[i][j] = cost[i][j - 1] + energy[i][j];
                            prevPixel[i][j] = i;
                        }
                    } else {
                        if (cost[i - 1][j - 1] <= cost[i + 1][j - 1]) {
                            cost[i][j] = cost[i - 1][j - 1] + energy[i][j];
                            prevPixel[i][j] = i - 1;
                        } else {
                            cost[i][j] = cost[i + 1][j - 1] + energy[i][j];
                            prevPixel[i][j] = i + 1;
                        }
                    }
                }
            }
        }

        double minCost = cost[0][pic.height() - 1];
        int index = 0;
        for (int i = 0; i < pic.width(); i++) {
            if (cost[i][pic.height() - 1] < minCost) {
                minCost = cost[i][pic.height() - 1];
                index = i;
            }
        }

        result[pic.height() - 1] = index;

        for (int k = pic.height() - 1; k > 0; k--) {
            result[k - 1] = prevPixel[result[k]][k];
        }

        return result;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }
}
