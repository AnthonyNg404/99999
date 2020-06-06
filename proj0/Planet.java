public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

    }

    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;

    }

    public double calcDistance(Planet p2) {
        double distanceX = Math.abs(this.xxPos - p2.xxPos);
        double distanceY = Math.abs(this.yyPos - p2.yyPos);
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    public double calcForceExertedBy(Planet p2) {
        double mP = this.mass;
        double mP2 = p2.mass;
        double G = 6.67e-11;
        return G * mP * mP2 / Math.pow(calcDistance(p2), 2);
    }

    public double calcForceExertedByX(Planet p2) {
        return calcForceExertedBy(p2) * (p2.xxPos - this.xxPos) / calcDistance(p2);
    }

    public double calcForceExertedByY(Planet p2) {
        return calcForceExertedBy(p2) * (p2.yyPos - this.yyPos) / calcDistance(p2);
    }

    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double totalX = 0;
        for (Planet p : allPlanets) {
            if (p == this) {
                continue;
            }
            totalX += this.calcForceExertedByX(p);
        }
        return totalX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double totalY = 0;
        for (Planet p : allPlanets) {
            if (p == this) {
                continue;
            }
            totalY += this.calcForceExertedByY(p);
        }
        return totalY;
    }

    public void update(double dt, double fX, double fY){
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel = this.xxVel + dt * aX;
        this.yyVel = this.yyVel + dt * aY;
        this.xxPos = this.xxPos + this.xxVel * dt;
        this.yyPos = this.yyPos + this.yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }


}
