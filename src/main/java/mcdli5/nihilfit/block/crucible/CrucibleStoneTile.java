package mcdli5.nihilfit.block.crucible;

import mcdli5.nihilfit.init.ModTiles;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.fluids.FluidStack;

public final class CrucibleStoneTile extends CrucibleBaseTile {
    public CrucibleStoneTile() {
        super(
            ModTiles.CRUCIBLE_STONE.get(),
            Items.COBBLESTONE.getDefaultInstance(),
            new FluidStack(Fluids.LAVA, 1000)
        );
    }

    @Override
    protected int getHeatRate() {
        final IForgeBlockState stateBelow = getStateBelow();

        // TODO: Base this on a HeatRegistry;
        if (stateBelow == Blocks.TORCH.getDefaultState()) return 3;
        if (stateBelow == Blocks.LAVA.getDefaultState()) return 6;

        return 0;
    }
}
