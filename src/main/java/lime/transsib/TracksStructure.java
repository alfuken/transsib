package lime.transsib;

import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class TracksStructure extends WorldGenerator
{
    public TracksStructure() { }
    public boolean generate(World w, Random r, BlockPos c_pos) {
        if (isValidXChunk(c_pos)) {
            generateXChunk(w, c_pos);
        }

        if (isValidZChunk(c_pos)) {
            generateZChunk(w, c_pos);
        }

        if (isValidXChunk(c_pos) && isValidZChunk(c_pos)) {
            generateCrossroad(w, c_pos);
        }

        return true;
    }

    private boolean isValidXChunk(BlockPos c_pos){
        return (c_pos.getX() == 0 || c_pos.getX() % Config.frequency == 0);
    }

    private boolean isValidZChunk(BlockPos c_pos){
        return (c_pos.getZ() == 0 || c_pos.getZ() % Config.frequency == 0);
    }

    private void generateXChunk(World w, BlockPos c_pos){
        int x = c_pos.getX() * 16;
        int z = c_pos.getZ() * 16;
        for (int tz = z; tz <= z + 16; tz++) {
            generateTracks(w, new BlockPos(x+7, c_pos.getY(), tz));
        }
    }

    private void generateZChunk(World w, BlockPos c_pos){
        int x = c_pos.getX() * 16;
        int z = c_pos.getZ() * 16;
        for (int tx = x; tx <= x + 16; tx++) {
            generateTracks(w, new BlockPos(tx, c_pos.getY(), z+7));
        }
    }

    private void generateCrossroad(World w, BlockPos c_pos){
        BlockPos pos = new BlockPos((c_pos.getX() * 16)+7, c_pos.getY(), (c_pos.getZ() * 16)+7);
        if (noWater(w, pos)) {

            // build center
            buildCeiling(w, pos.up(2));
            w.setBlockToAir(pos.up(1));
            w.setBlockState(pos, Blocks.STONE_SLAB.getDefaultState());

            // and clean up some space around
            buildCeiling(w, pos.add(-1, +2, -1));
            w.setBlockToAir(pos.add(-1, +1, -1));
            w.setBlockToAir(pos.add(-1, +0, -1));

            buildCeiling(w, pos.add(+1, +2, -1));
            w.setBlockToAir(pos.add(+1, +1, -1));
            w.setBlockToAir(pos.add(+1, +0, -1));

            buildCeiling(w, pos.add(+1, +2, +1));
            w.setBlockToAir(pos.add(+1, +1, +1));
            w.setBlockToAir(pos.add(+1, +0, +1));

            buildCeiling(w, pos.add(-1, +2, +1));
            w.setBlockToAir(pos.add(-1, +1, +1));
            w.setBlockToAir(pos.add(-1, +0, +1));

        }

    }

    private void buildCeiling(World w, BlockPos pos){
        Block b = w.getBlockState(pos).getBlock();
        if (b instanceof BlockSand || b instanceof BlockGravel || b instanceof BlockSoulSand) {
            w.setBlockState(pos, Blocks.OAK_FENCE.getDefaultState());
        }
    }

    private void generateTracks(World w, BlockPos pos){
        if (noWater(w, pos)) {
            if (pos.getX() % 10 == 0 || pos.getZ() % 10 == 0) { // every 10 blocks place the light
                w.setBlockState(pos.down(), Blocks.LIT_PUMPKIN.getDefaultState());
            }

            if (!(w.getBlockState(pos.down()).isSideSolid(w, pos.down(), EnumFacing.UP))) {
                w.setBlockState(pos.down(), Blocks.COBBLESTONE.getDefaultState());
            }
            buildCeiling(w, pos.up(2));
            w.setBlockToAir(pos.up());
            w.setBlockState(pos, Blocks.RAIL.getDefaultState());
        }
    }

    private boolean noWater(World w, BlockPos pos){
        if (w.getBlockState(pos.up(2)).getBlock() instanceof BlockLiquid) {return false;}
        if (w.getBlockState(pos.up(1)).getBlock() instanceof BlockLiquid) {return false;}
        if (w.getBlockState(pos.up(0)).getBlock() instanceof BlockLiquid) {return false;}
        return true;
    }
}
