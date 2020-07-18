package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Clorus extends Creature {

    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        this.energy -= 0.03;
    }

    public void stay() {
        this.energy -= 0.01;
    }

    public Clorus replicate() {
        Clorus n = new Clorus(energy / 2);
        n.color();
        this.energy /= 2;
        return n;
    }

    public String name() {
        return "clorus";
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        int k = 0;
        for (Direction d : neighbors.keySet()) {
            if (!neighbors.get(d).name().equals("empty")) {
                k += 1;
            }
        }
        if (k == neighbors.size()) {
            return new Action(Action.ActionType.STAY);
        }
        ArrayList<Direction> dir = new ArrayList<>();
        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("plip")) {
                dir.add(d);
            }
        }
        if (dir.size() > 0) {
            Direction e = dir.get((int) Math.floor(Math.random() * dir.size()));
            return new Action(Action.ActionType.ATTACK, e);
        }
        if (this.energy >= 1) {
            ArrayList<Direction> dir1 = new ArrayList<>();
            for (Direction d : neighbors.keySet()) {
                if (neighbors.get(d).name().equals("empty")) {
                    dir1.add(d);
                }
            }
            if (dir1.size() > 0) {
                Direction e = dir1.get((int) Math.floor(Math.random() * dir1.size()));
                return new Action(Action.ActionType.REPLICATE, e);
            }
        }
        ArrayList<Direction> dir2 = new ArrayList<>();
        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                dir2.add(d);
            }
        }
        if (dir2.size() > 0) {
            Direction e = dir2.get((int) Math.floor(Math.random() * dir2.size()));
            return new Action(Action.ActionType.MOVE, e);
        }
        return null;
    }
}
