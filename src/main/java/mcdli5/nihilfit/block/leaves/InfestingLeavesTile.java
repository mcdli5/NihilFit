package mcdli5.nihilfit.block.leaves;

import mcdli5.nihilfit.init.NF_Blocks;
import mcdli5.nihilfit.init.NF_Tiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;

public class InfestingLeavesTile extends TileEntity implements ITickableTileEntity {
    private final int RATE_OF_PROGRESS = 600; // 800, 0.00125, 0.0625

    private int progress = 0;
    private int doProgress = (int) (RATE_OF_PROGRESS / 100.0);

    public InfestingLeavesTile() {
        super(NF_Tiles.INFESTING_LEAVES.get());
    }

    @Override
    public void tick() {
        if (world != null && world.isRemote) return;

        if (doProgress <= 0) {
            progress++;

            if (progress >= 100) {
                setInfested();
                return;
            }

            // TODO: trySpread
            setInfestingState();
            doProgress = (int) (RATE_OF_PROGRESS / 100.0);
        } else {
            doProgress--;
        }
    }

    private void setInfested() {
        BlockState oldBlockState = getBlockState();

        world.setBlockState(pos, NF_Blocks.INFESTED_LEAVES.getDefaultState()
            .with(LeavesBlock.PERSISTENT, oldBlockState.get(LeavesBlock.PERSISTENT))
            .with(LeavesBlock.DISTANCE, oldBlockState.get(LeavesBlock.DISTANCE))
        );

        markDirty();
    }

    private void setInfestingState() {
        final IntegerProperty stageProperty = InfestingLeavesBlock.INFESTING_STAGE;
        final int oldStage = getBlockState().get(stageProperty);
        final int stage = MathHelper.clamp(Math.round((progress * 10.0F) / 100.0F), 0, 9); // TODO: use a float!

        if (stage > oldStage) {
            world.setBlockState(pos, getBlockState().with(stageProperty, stage));
            markDirty();
        }
    }
}
