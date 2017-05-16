package lime.transsib;

import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class TracksStructure extends WorldGenerator
{
    public TracksStructure() { }
    public boolean generate(World w, Random r, int cx, int y, int cz) {
        if (isValidXChunk(cx)) {
            generateXChunk(w, cx, y, cz);
        }

        if (isValidZChunk(cz)) {
            generateZChunk(w, cx, y, cz);
        }

        if (isValidXChunk(cx) && isValidZChunk(cz)) {
            generateCrossroad(w, cx, y, cz);
        }

        return true;
    }

    private boolean isValidXChunk(int cx){
        return (cx == 0 || cx % Config.frequency == 0);
    }

    private boolean isValidZChunk(int cz){
        return (cz == 0 || cz % Config.frequency == 0);
    }

    private void generateXChunk(World w, int cx, int y, int cz){
        int x = cx * 16;
        int z = cz * 16;
        for (int tz = z; tz <= z + 16; tz++) {
            generateTracks(w, x + 7, y, tz);
        }
    }

    private void generateZChunk(World w, int cx, int y, int cz){
        int x = cx * 16;
        int z = cz * 16;
        for (int tx = x; tx <= x + 16; tx++) {
            generateTracks(w, tx, y, z + 7);
        }
    }

    private void buildCeilingIfNeeded(World w, int x, int y, int z){
        Block b = w.getBlock(x, y, z);
        if (b instanceof BlockSand || b instanceof BlockGravel || b instanceof BlockSoulSand) {
            w.setBlock(x, y, z, Blocks.fence);
        }
    }

    private void generateCrossroad(World w, int cx, int y, int cz){
        int x = (cx * 16) + 7;
        int z = (cz * 16) + 7;
        int c = y + 2; // ceiling
        int h = y + 1; // head y level

        if (noWater(w, x, y, z)) {
            buildCeilingIfNeeded(w, x, c, z);
            w.setBlock(x, h, z, Blocks.air);
            w.setBlock(x, y, z, Blocks.stone_slab);

            // and clean up some space around
            buildCeilingIfNeeded(w, x-1, c, z-1);
            w.setBlock(x-1, h, z-1, Blocks.air);
            w.setBlock(x-1, y, z-1, Blocks.air);

            buildCeilingIfNeeded(w, x+1, c, z-1);
            w.setBlock(x+1, h, z-1, Blocks.air);
            w.setBlock(x+1, y, z-1, Blocks.air);

            buildCeilingIfNeeded(w, x-1, c, z+1);
            w.setBlock(x-1, h, z+1, Blocks.air);
            w.setBlock(x-1, y, z+1, Blocks.air);

            buildCeilingIfNeeded(w, x+1, c, z+1);
            w.setBlock(x+1, h, z+1, Blocks.air);
            w.setBlock(x+1, y, z+1, Blocks.air);
        }

    }

    private void generateTracks(World w, int x, int y, int z){
        if (noWater(w, x, y, z)) {
            if (x % 10 == 0 || z % 10 == 0) { // every 10 blocks place the light
                w.setBlock(x, y - 1, z, Blocks.lit_pumpkin);
            }
            if (!(World.doesBlockHaveSolidTopSurface(w, x, y - 1, z))) {
                w.setBlock(x, y - 1, z, Blocks.cobblestone);
            }
            buildCeilingIfNeeded(w, x, y + 2, z);
            w.setBlock(x, y + 1, z, Blocks.air);
            w.setBlock(x, y, z, Blocks.rail);
        }
    }

    private boolean noWater(World w, int x, int y, int z){
        for (int l = y; l <= y + 2; l++) {
            // don't generate in the water or lava
            if (w.getBlock(x, l, z) instanceof BlockLiquid) {
                return false;
            }
        }
        return true;
    }
}
