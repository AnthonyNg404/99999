package byog.Core;

import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {


    public static void main(String[] args) {
        int menuStatus = 0;
        Game game = new Game();
        game.menu();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key == 'l' || key == 'L') {
                args = new String[1];
                args[0] = "n2424436252sddd";
                break;
            } else if (key == 'q' || key == 'Q') {
                System.exit(0);
            } else if (key == 'n' || key == 'N') {
                break;
            }
        }
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            TETile[][] worldState = game.playWithInputString(args[0]);
            game.ter.renderFrame(worldState);
            //System.out.println(TETile.toString(worldState));
            game.playWithKeyboard(1);
        } else {
            game.playWithKeyboard(0);
        }
    }
}
