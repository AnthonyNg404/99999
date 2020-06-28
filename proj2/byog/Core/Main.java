package byog.Core;

//import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.ter.initialize(game.WIDTH, game.HEIGHT);
        game.menu();
        game.ter.renderFrame(game.playWithInputString(solicitNCharsInput(game)));

        /**if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }*/
    }

    private static String solicitNCharsInput(Game game) {
        String input = "";
        game.drawCode(input);
        int b = 0;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (key != 'n' && key != 'N' && b == 0) {
                continue;
            }
            if (key != 's' && key != 'S') {
                b = 1;
                input += String.valueOf(key);
                game.drawCode(input);
            } else {
                input += String.valueOf(key);
                game.drawCode(input);
                StdDraw.pause(500);
                break;
            }
        }
        return input;
    }
}
