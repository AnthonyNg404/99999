public class NBody {
    private static double readRadius(String s) {
        In in = new In(s);
        in.readInt();
        return in.readDouble();
    }

    private static Planet[] readPlanets(String s) {
        In in = new In(s);
        int length = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[length];
        for (int i = 0; i < length; i +=1) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), in.readString());
        }
        return planets;
    }

    private static void drawBackground(String s, double size) {
        StdDraw.setScale(-size, size);
        StdDraw.picture(0, 0, s, 2 * size, 2 * size);
    }

    public static void main(String[] s) {
        double T = Double.parseDouble(s[0]);
        double dt = Double.parseDouble(s[1]);
        String filename = s[2];
        In in = new In(filename);
        int length = in.readInt();
        Planet[] planets = NBody.readPlanets(filename);
        double size = NBody.readRadius(filename);
        NBody.drawBackground("images/starfield.jpg", size);
        for (Planet p : planets ) {
            p.draw();
        }
        StdDraw.enableDoubleBuffering();
        double[] xForces = new double[length];
        double[] yForces = new double[length];
        for (double count = 0; count < T; count += dt) {
            for (int i = 0; i < length; i += 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < length; i += 1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            NBody.drawBackground("images/starfield.jpg", size);
            for (Planet p : planets ) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", size);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
