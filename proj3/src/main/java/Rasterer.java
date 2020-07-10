import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private String[][] renderGrid;
    private double rasterUlLon;
    private double rasterUlLat;
    private double rasterLrLon;
    private double rasterLrLat;
    private double rasterUlLon1;
    private double rasterUlLat1;
    private double rasterLrLon1;
    private double rasterLrLat1;
    private double w;
    private double h;
    private int depth;
    private Boolean querySuccess = false;
    private double LonDPP;
    private double lonDPPTile;
    private Map<String, Double> params1;
    private int ulX;
    private int ulY;
    private int lrX;
    private int lrY;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        initialize(params);
        getDepth();
        getGrid();
        Map<String, Object> results = new HashMap<>();
        results.put("raster_ul_lon", rasterUlLon);
        results.put("raster_ul_lat", rasterUlLat);
        results.put("raster_lr_lon", rasterLrLon);
        results.put("raster_lr_lat", rasterLrLat);
        results.put("depth", depth);
        results.put("render_grid", renderGrid);
        results.put("query_success", querySuccess);
        /**System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");*/
        return results;
    }

    private void initialize(Map<String, Double> param) {
        params1 = param;
        rasterUlLon1 = params1.get("ullon");
        rasterUlLat1 = params1.get("ullat");
        rasterLrLon1 = params1.get("lrlon");
        rasterLrLat1 = params1.get("lrlat");
        w = params1.get("w");
        h = params1.get("h");
        depth = 0;
        //LonDPP = MapServer.ROOT_ULLON - MapServer.ROOT_LRLON)
        LonDPP = (rasterLrLon1 - rasterUlLon1) / w;
    }

    private void getDepth() {
        int pix = MapServer.TILE_SIZE;
        for (; depth < 7; pix *= 2) {
            lonDPPTile = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / pix;
            if (lonDPPTile > LonDPP) {
                depth += 1;
            } else {
                break;
            }
        }
    }

    private void getGrid() {
        double ulLonLen = (rasterUlLon1 - MapServer.ROOT_ULLON);
        double ulLatLen = (MapServer.ROOT_ULLAT - rasterUlLat1);
        double lrLonLen = (rasterLrLon1 - MapServer.ROOT_ULLON);
        double lrLatLen = (MapServer.ROOT_ULLAT - rasterLrLat1);
        //System.out.println(ulLonLen + "   " + ulLatLen);
        //System.out.println(lrLonLen + "   " + lrLatLen);
        double ulLonPre = ulLonLen / (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double ulLatPre = ulLatLen / (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        double lrLonPre = lrLonLen / (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        double lrLatPre = lrLatLen / (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        //System.out.println(ulLonPre + "   " + ulLatPre);
        //System.out.println(lrLonPre + "   " + lrLatPre);
        double total = Math.pow(2, depth);
        ulX = (int) (ulLonPre * total);
        ulY = (int) (ulLatPre * total);
        lrX = (int) (lrLonPre * total);
        lrY = (int) (lrLatPre * total);
        renderGrid = new String[lrY - ulY + 1][lrX - ulX + 1];
        for (int Y = ulY; Y <= lrY; Y++) {
            for (int X = ulX; X <= lrX; X++) {
                renderGrid[Y - ulY][X - ulX] = "d" + depth + "_x" + X + "_y" + Y + ".png";
            }
        }
        rasterUlLon = ulX / total
                * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) + MapServer.ROOT_ULLON;
        rasterUlLat = MapServer.ROOT_ULLAT - ulY / total
                * (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        rasterLrLon = (lrX + 1) / total
                * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) + MapServer.ROOT_ULLON;
        rasterLrLat = MapServer.ROOT_ULLAT - (lrY + 1) / total
                * (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        querySuccess = true;
    }

}
