package lime.transsib;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {
    private static final String CATEGORY_GENERAL = "general";

    public static int depth = 60;
    public static int frequency = 16;

    public static void readConfig() {
        Configuration cfg = Transsib.config;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
            depth = cfg.getInt("Y level for tracks", CATEGORY_GENERAL, depth, 1, 250, "Depth at which the tracks should generate. Default is the sea level.");
            frequency = cfg.getInt("Place tunnels every N chunks", CATEGORY_GENERAL, frequency, 2, 999, "Tracks will be generated once every n-th chunk. Basically, distance between track lines multiplied by 16.");
        } catch (Exception e1) {
            Transsib.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
}
