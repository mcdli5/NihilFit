package mcdli5.nihilfit.block.crucible;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public final class CrucibleStoneBlock extends CrucibleBaseBlock {
    public CrucibleStoneBlock(Properties builder) {
        super(builder);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CrucibleStoneTile();
    }
}
