package lime.transsib;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class TranssibWorldGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int cx, int cz, World w, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (w.provider.dimensionId) {
            case 0: //Overworld
                TracksStructure tracks = new TracksStructure();
                tracks.generate(w, random, cx, Config.depth, cz);
                break;
            case -1: //Nether

                break;
            case 1: //End

                break;
        }
    }


}
