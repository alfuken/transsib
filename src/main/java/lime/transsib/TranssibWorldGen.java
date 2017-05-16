package lime.transsib;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TranssibWorldGen implements IWorldGenerator {
    public void generate(Random random, int cx, int cz, World w, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (w.provider.getDimension()) {
            case 0: //Overworld
                TracksStructure tracks = new TracksStructure();
                tracks.generate(w, random, new BlockPos(cx, Config.depth, cz));
                break;
            case -1: //Nether

                break;
            case 1: //End

                break;
        }
    }
}
