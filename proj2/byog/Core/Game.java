package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private int playerX;
    private int playerY;
    private boolean win = false;
    private String code = "";
    private int menuStatus;
    private TETile[][] finalWorldFrame;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playerControl(char key) {
        if (key == 's' || key == 'S') {
            finalWorldFrame = movePlayer(finalWorldFrame, 0);
        } else if (key == 'd' || key == 'D') {
            finalWorldFrame = movePlayer(finalWorldFrame, 1);
        } else if (key == 'w' || key == 'W') {
            finalWorldFrame = movePlayer(finalWorldFrame, 2);
        } else if (key == 'a' || key == 'A') {
            finalWorldFrame = movePlayer(finalWorldFrame, 3);
        }
        if (win) {
            winShow();
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TO*DO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        code = input;
        if (input.charAt(0) == 'l' || input.charAt(0) == 'L') {
            code = loadWorld() + input.substring(1);
        }
        String initCode = "";
        String moveCode = "";
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == 's' || code.charAt(i) == 'S') {
                initCode += code.substring(i, i + 1);
                finalWorldFrame = initialize(seed(initCode));
                moveCode = code.substring(i + 1);
                System.out.println(moveCode);
                break;
            }
            initCode += code.substring(i, i + 1);
        }
        for (int j = 0; j < moveCode.length(); j++) {
            if (moveCode.charAt(j) == ':'
                    && (moveCode.charAt(j + 1) == 'q' || moveCode.charAt(j + 1) == 'Q')) {
                code = code.substring(0, code.length() - 2);
                saveWorld(code);
                break;
            }
            playerControl(moveCode.charAt(j));
        }
        menuStatus = 2;
        return finalWorldFrame;
    }

    public void playWithKeyboard() {
        String input = code;
        menu();
        if (menuStatus == 2) {
            ter.renderFrame(finalWorldFrame);
        }
        while (!win) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if ((key == 'l' || key == 'L') && menuStatus == 0) {
                String save = loadWorld();
                input = save;
                finalWorldFrame = playWithInputString(save);
                ter.renderFrame(finalWorldFrame);
                menuStatus = 2;
            } else if ((key == 'q' || key == 'Q') && menuStatus == 0) {
                System.exit(0);
            } else if ((key == 'n' || key == 'N') && menuStatus == 0) {
                input += String.valueOf(key);
                drawCode(input);
                menuStatus = 1;
            } else if ((key - '0' >= 0 && key - '0' <= 9) && menuStatus == 1) {
                input += String.valueOf(key);
                drawCode(input);
            } else if ((key == 's' || key == 'S') && menuStatus == 1) {
                menuStatus = 2;
                input += String.valueOf(key);
                drawCode(input);
                StdDraw.pause(500);
                finalWorldFrame = initialize(seed(input));
                ter.renderFrame(finalWorldFrame);
            } else if (key != ':' && menuStatus == 2) {
                input += String.valueOf(key);
                playerControl(key);
                ter.renderFrame(finalWorldFrame);
            } else if (key == ':' && menuStatus == 2) {
                menuStatus = 3;
            } else if ((key == 'q' || key == 'Q') && menuStatus == 3) {
                saveWorld(input);
                System.exit(0);
            }
        }
    }


    public void menu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font smallFont = new Font("Monaco", Font.PLAIN, 15);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(bigFont);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "CS61B: THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game   (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 1, "Load Game   (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Quit   (Q)");
        StdDraw.show();
    }

    private void drawCode(String s) {
        StdDraw.clear(Color.black);
        Font smallFont = new Font("Monaco", Font.PLAIN, 15);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(bigFont);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "CS61B: THE GAME");
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game   (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 1, "Load Game   (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Quit   (Q)");
        int midWidth = WIDTH / 2;
        int qHeight = HEIGHT / 4;
        StdDraw.text(midWidth, qHeight, s);
        StdDraw.show();
    }

    /**public String solicitNCharsInput(int n) {
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            drawFrame(input);
        }
        StdDraw.pause(1000);
        return input;
    }*/


    private TETile[][] initialize(long seed) {
        TETile[][] init = new TETile[WIDTH][HEIGHT];
        Random random = new Random(seed);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                init[x][y] = Tileset.NOTHING;
            }
        }
        int cap = random.nextInt((WIDTH + HEIGHT) / 20) + 30;
        RoomCache rc = new RoomCache(cap);
        for (int z = 0; z < cap; z++) {
            init = rooms(init, random, rc, z);
        }
        /**for (int z = 0; z < cap; z++) {
            for (int i = z + 1; i < cap + 1; i++) {
                if (i == cap) {
                    i = 0;
                    if (!isConnected(rc, i, z)) {
                        parentConnect(rc, i, z);
                        System.out.println(rc.rck[z].parent + "   " + z + "  zth parent");
                        System.out.println(rc.rck[i].parent + "   " + i + "  ith parent");
                        System.out.println("special");
                    }
                    break;
                }
                if (!isConnected(rc, i, z)) {
                    parentConnect(rc, i, z);
                    System.out.println(rc.rck[z].parent + "   " + z + "  zth parent");
                    System.out.println(rc.rck[i].parent + "   " + i + "  ith parent");
                }
            }
        }
        for (int i = 0; i < cap; i++) {
            System.out.println(rc.rck[i].parent + "   " + i + "  ith   final Z parent");
        }*/
        for (int z = 0; z < cap; z++) {
            for (int i = cap / 2; i < cap; i++) {
                if (!isConnected(rc, i, z)) {
                    init = doConnect(init, random, rc, i, z);
                    parentConnect(rc, i, z);
                }
            }
        }
        init = walls(init);
        init = addPlayer(init, random);
        init = addDoor(init, random);
        return init;
    }

    private void winShow() {
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(bigFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "YOU LOSE! IDIOT!");
        StdDraw.show();
    }

    private void win() {
        win = true;
    }

    private TETile[][] checkWin(TETile[][] checkWin, int dir) {
        switch (dir) {
            case 0:
                if (checkWin[playerX][playerY - 1] == Tileset.LOCKED_DOOR) {
                    win();
                }
                return checkWin;
            case 1:
                if (checkWin[playerX + 1][playerY] == Tileset.LOCKED_DOOR) {
                    win();
                }
                return checkWin;

            case 2:
                if (checkWin[playerX][playerY + 1] == Tileset.LOCKED_DOOR) {
                    win();
                }
                return checkWin;

            case 3:
                if (checkWin[playerX - 1][playerY] == Tileset.LOCKED_DOOR) {
                    win();
                }
                return checkWin;
            default:
                return checkWin;
        }
    }

    private TETile[][] movePlayer(TETile[][] movePlayer, int dir) {
        switch (dir) {
            case 0:
                if (movePlayer[playerX][playerY - 1] == Tileset.FLOOR) {
                    movePlayer[playerX][playerY] = Tileset.FLOOR;
                    movePlayer[playerX][playerY - 1] = Tileset.PLAYER;
                    playerY = playerY - 1;
                } else {
                    checkWin(movePlayer, dir);
                }
                return movePlayer;
            case 1:
                if (movePlayer[playerX + 1][playerY] == Tileset.FLOOR) {
                    movePlayer[playerX][playerY] = Tileset.FLOOR;
                    movePlayer[playerX + 1][playerY] = Tileset.PLAYER;
                    playerX = playerX + 1;
                } else {
                    checkWin(movePlayer, dir);
                }
                return movePlayer;
            case 2:
                if (movePlayer[playerX][playerY + 1] == Tileset.FLOOR) {
                    movePlayer[playerX][playerY] = Tileset.FLOOR;
                    movePlayer[playerX][playerY + 1] = Tileset.PLAYER;
                    playerY = playerY + 1;
                } else {
                    checkWin(movePlayer, dir);
                }
                return movePlayer;
            case 3:
                if (movePlayer[playerX - 1][playerY] == Tileset.FLOOR) {
                    movePlayer[playerX][playerY] = Tileset.FLOOR;
                    movePlayer[playerX - 1][playerY] = Tileset.PLAYER;
                    playerX = playerX - 1;
                } else {
                    checkWin(movePlayer, dir);
                }
                return movePlayer;
            default:
                return movePlayer;
        }
    }

    private TETile[][] addDoor(TETile[][] addDoor, Random seed) {
        int i = 0;
        int q = 0;
        while (i == 0) {
            int x = seed.nextInt(WIDTH - 2) + 1;
            int y = seed.nextInt(HEIGHT - 2) + 1;
            if (addDoor[x][y] == Tileset.WALL) {
                if (addDoor[x + 1][y] == Tileset.FLOOR
                        && addDoor[x - 1][y] == Tileset.NOTHING) {
                    q += 1;
                } else if (addDoor[x - 1][y] == Tileset.FLOOR
                        && addDoor[x + 1][y] == Tileset.NOTHING) {
                    q += 1;
                }
                if (addDoor[x][y + 1] == Tileset.FLOOR
                        && addDoor[x][y - 1] == Tileset.NOTHING) {
                    q += 1;
                } else if (addDoor[x][y - 1] == Tileset.FLOOR
                        && addDoor[x][y + 1] == Tileset.NOTHING) {
                    q += 1;
                }
                if (q > 0) {
                    addDoor[x][y] = Tileset.LOCKED_DOOR;
                    i = 1;
                }
            }
        }
        return addDoor;
    }

    private TETile[][] addPlayer(TETile[][] addPlayer, Random seed) {
        int i = 0;
        while (i == 0) {
            int x = seed.nextInt(WIDTH - 2) + 1;
            int y = seed.nextInt(HEIGHT - 2) + 1;
            if (addPlayer[x][y] == Tileset.FLOOR) {
                addPlayer[x][y] = Tileset.PLAYER;
                playerX = x;
                playerY = y;
                i = 1;
            }
        }
        return addPlayer;
    }


    private int find(RoomCache rc, int p) {
        int r = p;
        while (rc.rck[r].parent >= 0) {
            r = rc.rck[r].parent;
        }
        return r;
    }

    private boolean isConnected(RoomCache rc, int p, int q) {
        return find(rc, p) == find(rc, q);
    }

    private void parentConnect(RoomCache rc, int p, int q) {
        int i = find(rc, p);
        int j = find(rc, q);
        rc.rck[i].parent = j;
    }

    private int belongs(RoomCache rc, int z, int x, int y) {
        for (int i = 0; i < rc.rck.length; i++) {
            if (i != z
                    && x >= rc.rck[i].x
                    && x < rc.rck[i].x + rc.rck[i].w
                    && y >= rc.rck[i].y
                    && y < rc.rck[i].y + rc.rck[i].h) {
                return i;
            }
        }
        return -1;
    }

    /**private TETile[][] connects(TETile[][] addConnect, Random seed, RoomCache rc, int z) {
        int x;
        int y;
        int x1;
        int y1;
        int c = 0;
        int k;
        int roll = seed.nextInt(4);
        if (roll == 0) {
            x = rc.rck[z].x + seed.nextInt(rc.rck[z].w);
            y = rc.rck[z].y;
            x1 = x;
            y1 = y;
            while (y - 1 > 0) {
                if (addConnect[x][y - 1] == Tileset.FLOOR) {
                    k = belongs(rc, z, x, y - 1);
                    if (k != -1
                            && !isConnected(rc, z, k)) {
                        c += 1;
                        addConnect = doConnect(addConnect, roll, x1, y1);
                        System.out.println(100000000);
                        parentConnect(rc, k, z);
                        System.out.println(k + "   connect to   " + z);
                    }
                    System.out.println(k + "   fail connect to   " + z);
                }
                y -= 1;
            }
        } else if (roll == 1) {
            x = rc.rck[z].x + rc.rck[z].w - 1;
            y = rc.rck[z].y + seed.nextInt(rc.rck[z].h);
            x1 = x;
            y1 = y;
            while (x + 2 < WIDTH) {
                if (addConnect[x + 1][y] == Tileset.FLOOR) {
                    k = belongs(rc, z, x + 1, y);
                    if (k != -1
                            && !isConnected(rc, z, k)) {
                        c += 1;
                        addConnect = doConnect(addConnect, roll, x1, y1);
                        System.out.println(1111111111);
                        parentConnect(rc, k, z);
                        System.out.println(k + "   connect to   " + z);
                    }
                    System.out.println(k + "   fail connect to   " + z);
                }
                x += 1;
            }
        } else if (roll == 2) {
            x = rc.rck[z].x + seed.nextInt(rc.rck[z].w);
            y = rc.rck[z].y + rc.rck[z].h - 1;
            x1 = x;
            y1 = y;
            while (y + 2 < HEIGHT) {
                if (addConnect[x][y + 1] == Tileset.FLOOR) {
                    k = belongs(rc, z, x, y + 1);
                    if (k != -1
                            && !isConnected(rc, z, k)) {
                        c += 1;
                        addConnect = doConnect(addConnect, roll, x1, y1);
                        System.out.println(222222222);
                        parentConnect(rc, k, z);
                        System.out.println(k + "   connect to   " + z);
                    }
                    System.out.println(k + "   fail connect to   " + z);
                }
                y += 1;
            }
        } else {
            x = rc.rck[z].x;
            y = rc.rck[z].y + seed.nextInt(rc.rck[z].h);
            x1 = x;
            y1 = y;
            while (x - 1 > 0) {
                if (addConnect[x - 1][y] == Tileset.FLOOR) {
                    k = belongs(rc, z, x - 1, y);
                    if (k != -1
                            && !isConnected(rc, z, k)) {
                        c += 1;
                        addConnect = doConnect(addConnect, roll, x1, y1);
                        System.out.println(33333333);
                        parentConnect(rc, k, z);
                        System.out.println(k + "   connect to   " + z);
                    }
                    System.out.println(k + "   fail connect to   " + z);
                }
                x -= 1;
            }
        }
        if (c == 0) {
            for (int t = z + 1; t < rc.rck.length; t++) {
                for (int u = 0; u < 5; u++) {
                    addConnect = connects(addConnect, seed, rc, t);
                }
            }
            return  addConnect;
        }
        return addConnect;
    }*/

    private TETile[][] doConnect(TETile[][] addConnect, Random seed, RoomCache rc, int k, int z) {
        int xZ = rc.rck[z].x + seed.nextInt(rc.rck[z].w);
        int yZ = rc.rck[z].y + seed.nextInt(rc.rck[z].h);
        int xK = rc.rck[k].x + seed.nextInt(rc.rck[k].w);
        int yK = rc.rck[k].y + seed.nextInt(rc.rck[k].h);
        int dirX;
        int dirY;
        if (xK == xZ) {
            dirX = 0;
        } else {
            dirX = (xK - xZ) / Math.abs(xK - xZ);
        }
        if (yK == yZ) {
            dirY = 0;
        } else {
            dirY = (yK - yZ) / Math.abs(yK - yZ);
        }
        while (xZ != xK && yZ != yK) {
            if (seed.nextInt(2) == 0) {
                addConnect[xZ + dirX][yZ] = Tileset.FLOOR;
                xZ = xZ + dirX;
            } else {
                addConnect[xZ][yZ + dirY] = Tileset.FLOOR;
                yZ = yZ + dirY;
            }
        }
        while (xZ != xK) {
            addConnect[xZ + dirX][yZ] = Tileset.FLOOR;
            xZ = xZ + dirX;
        }
        while (yZ != yK) {
            addConnect[xZ][yZ + dirY] = Tileset.FLOOR;
            yZ = yZ + dirY;
        }
        return addConnect;
    }

    /**private TETile[][] doConnect(TETile[][] addConnect, int option, int x, int y) {
        System.out.println("DO");
        if (option == 0) {
            while (y - 1 > 0 && addConnect[x][y - 1] == Tileset.NOTHING) {
                addConnect[x][y - 1] = Tileset.FLOOR;
                y -= 1;
            }
        } else if (option == 1) {
            while (x + 2 < WIDTH && addConnect[x + 1][y] == Tileset.NOTHING) {
                addConnect[x + 1][y] = Tileset.FLOOR;
                x += 1;
            }
        } else if (option == 2) {
            while (y + 2 < HEIGHT && addConnect[x][y + 1] == Tileset.NOTHING) {
                addConnect[x][y + 1] = Tileset.FLOOR;
                y += 1;
            }
        } else {
            while (x - 1 > 0 && addConnect[x - 1][y] == Tileset.NOTHING) {
                addConnect[x - 1][y] = Tileset.FLOOR;
                x -= 1;
            }
        }
        return addConnect;
    }*/



    private TETile[][] walls(TETile[][] addWall) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (addWall[x][y] != Tileset.FLOOR) {
                    for (int k = x - 1; k < x + 2; k++) {
                        if (k >= WIDTH || k < 0) {
                            continue;
                        }
                        for (int l = y - 1; l < y + 2; l++) {
                            if (l >= HEIGHT || l < 0) {
                                continue;
                            }
                            if (addWall[k][l] == Tileset.FLOOR) {
                                addWall[x][y] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
        return addWall;
    }


    private TETile[][] rooms(TETile[][] addRoom, Random seed, RoomCache rc, int z) {
        int x = seed.nextInt(WIDTH - 2) + 1;
        int y = seed.nextInt(HEIGHT - 2) + 1;
        int w = seed.nextInt(WIDTH / 10) + 1;
        int h = seed.nextInt(WIDTH / 10) + 1;
        rc.addLast(z, x, y, w, h, -1);
        if (x + w >= WIDTH || y + h >= HEIGHT) {
            rc.removeLast(z);
            addRoom = rooms(addRoom, seed, rc, z);
        } else if (rc.isAnyJoint(rc.rck[z])) {
            rc.removeLast(z);
            addRoom = rooms(addRoom, seed, rc, z);
        } else {
            for (int i = x; i < x + w; i++) {
                for (int j = y; j < y + h; j++) {
                    addRoom[i][j] = Tileset.FLOOR;
                }
            }
        }
        return addRoom;
    }

    private long seed(String s) {
        return Long.parseLong(s.substring(1, s.length() - 1));
    }

    public class RoomCache {

        private RoomCheck[] rck;

        public RoomCache(int cap) {
            rck = new RoomCheck[cap];
        }

        private class RoomCheck {
            int x;
            int y;
            int w;
            int h;
            int parent;
            RoomCheck(int x, int y, int w, int h, int p) {
                this.x = x;
                this.y = y;
                this.w = w;
                this.h = h;
                this.parent = p;
            }
        }

        public void addLast(int z, int x, int y, int w, int h, int p) {
            rck[z] = new RoomCheck(x, y, w, h, p);
        }

        public void removeLast(int z) {
            rck[z] = null;
        }

        private boolean isJoint(RoomCheck a, RoomCheck b) {
            boolean x = (a.x - (b.x + b.w)) * ((a.x + a.w) - b.x) <= 0;
            boolean y = (a.y - (b.y + b.h)) * ((a.y + a.h) - b.y) <= 0;
            return x && y;
        }

        private boolean isAnyJoint(RoomCheck a) {
            for (RoomCheck p : this.rck) {
                if (a == p) {
                    break;
                } else if (isJoint(a, p)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static String loadWorld() {
        try {
            String pathName = "./world.txt";
            File filename = new File(pathName);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            return line;
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        return null;
    }


    private static void saveWorld(String s) {
        try {
            File writeName = new File("./world.txt");
            writeName.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
            out.write(s);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
