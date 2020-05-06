package mcdli5.nihilfit.block.crucible;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public final class CrucibleWoodBlock extends CrucibleBaseBlock {
    public CrucibleWoodBlock(Properties builder) {
        super(builder.harvestTool(ToolType.AXE));
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CrucibleWoodTile();
    }
}
