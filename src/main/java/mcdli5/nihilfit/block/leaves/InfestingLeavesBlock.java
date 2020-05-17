package mcdli5.nihilfit.block.leaves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public final class InfestingLeavesBlock extends LeavesBlock {
    public static final BooleanProperty NEARBY_LEAVES = BooleanProperty.create("nearby_leaves");
    public static final IntegerProperty INFESTING_STAGE = IntegerProperty.create("infesting_stage", 0, 9);
    public static final int[] COLOR_MAP = new int[]{
        -10367439, -8727715, -7350150, -6169199, -5119323,
        -4134985, -3216185, -2362921, -1509915, -787726
    };

    public InfestingLeavesBlock(Properties properties) {
        super(properties);

        this.setDefaultState(stateContainer.getBaseState()
            .with(DISTANCE, 7)
            .with(PERSISTENT, false)
            .with(INFESTING_STAGE, 0)
            .with(NEARBY_LEAVES, false)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NEARBY_LEAVES, INFESTING_STAGE);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new InfestingLeavesTile();
    }
}
