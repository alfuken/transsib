package lime.transsib;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {
    private static final String CATEGORY_GENERAL = "general";

    public static int depth = 55;
    public static int frequency = 16;
    public static int booster_frequency = 9;
    public static boolean build_beacons = true;

    public static void readConfig() {
        Configuration cfg = Transsib.config;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
            depth             = cfg.getInt("Y level for tracks",                  CATEGORY_GENERAL, depth, 1, 250, "Depth at which the tracks should generate. Default is the sea level.");
            frequency         = cfg.getInt("Place tunnels every N chunks",        CATEGORY_GENERAL, frequency, 2, 999, "Tracks will be generated once every n-th chunk. Basically, distance between track lines multiplied by 16.");
            booster_frequency = cfg.getInt("Place boosters rails every N blocks", CATEGORY_GENERAL, booster_frequency, 0, 15,  "Set to 0 to disable placing booster tracks.");
            build_beacons     = cfg.getBoolean("Place lit pumpkin beacon on x-roads", CATEGORY_GENERAL, build_beacons, "Placed on top of the highest solid block (if possible).");
        } catch (Exception e1) {
            Transsib.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
}
