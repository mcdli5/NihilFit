package mcdli5.nihilfit.block.crucible;

import mcdli5.nihilfit.setup.ModTiles;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.fluids.FluidStack;

public final class CrucibleWoodTile extends CrucibleBaseTile {
    public CrucibleWoodTile() {
        super(
            ModTiles.CRUCIBLE_WOOD.get(),
            Items.OAK_SAPLING.getDefaultInstance(),
            new FluidStack(Fluids.WATER, 1000)
        );
    }

    @Override
    protected int getHeatRate() {
        final IForgeBlockState stateBelow = getStateBelow();

        // TODO: Base this on a HeatRegistry;
        if (stateBelow == Blocks.TORCH.getDefaultState()) return 3;

        return 0;
    }
}
