package mcdli5.nihilfit.block.leaves;

import lombok.Getter;
import mcdli5.nihilfit.init.NF_Blocks;
import mcdli5.nihilfit.init.NF_Tiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;

public class InfestingLeavesTile extends TileEntity implements ITickableTileEntity {
    private static final int TICKS_TO_BECOME_INFESTED = 600;
    private static final int RATE_OF_PROGRESS = (int) (TICKS_TO_BECOME_INFESTED / 100.0);

    @Getter
    private int progress = 0;
    private int doProgress = RATE_OF_PROGRESS;
    private int spreadCounter = 0;

    public InfestingLeavesTile() {
        super(NF_Tiles.INFESTING_LEAVES.get());
    }

    @Override
    public void tick() {
        if (world != null && world.isRemote) return;

        if (doProgress <= 0) {
            progress++;
            spreadCounter++;

            if (progress >= 100) {
                setInfested();
                return;
            }

            if (spreadCounter >= InfestingLeavesBlock.UPDATE_FREQUENCY) {
                InfestingLeavesBlock.spread(getBlockState(), world, pos, world.rand);
                spreadCounter = 0;
            }

            updateInfestingState();
            doProgress = RATE_OF_PROGRESS;
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

    private void updateInfestingState() {
        final IntegerProperty stageProperty = InfestingLeavesBlock.INFESTING_STAGE;
        final int oldStage = getBlockState().get(stageProperty);
        final int stage = MathHelper.clamp((progress / 10), 0, 9);

        if (stage > oldStage) {
            world.setBlockState(pos, getBlockState().with(stageProperty, stage));
            markDirty();
        }
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        progress = compoundNBT.getInt("progress");
        doProgress = compoundNBT.getInt("doProgress");
        spreadCounter = compoundNBT.getInt("spreadCounter");
        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putInt("progress", progress);
        compoundNBT.putInt("doProgress", doProgress);
        compoundNBT.putInt("spreadCounter", spreadCounter);
        return super.write(compoundNBT);
    }
}
