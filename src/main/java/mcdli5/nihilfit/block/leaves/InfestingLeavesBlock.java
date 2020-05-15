package mcdli5.nihilfit.block.leaves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.world.IWorldReader;

public final class InfestingLeavesBlock extends LeavesBlock {
    public static final BooleanProperty NEARBY_LEAVES = BooleanProperty.create("nearby_leaves");
    public static final IntegerProperty INFESTING_STAGE = IntegerProperty.create("infesting_stage", 0, 7);

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
    public boolean ticksRandomly(BlockState state) {
        return state.get(INFESTING_STAGE) < 7 || state.get(NEARBY_LEAVES) || (state.get(DISTANCE) == 7 && !state.get(PERSISTENT));
    }

    @Override
    public int tickRate(IWorldReader world) {
        return 1;
    }
}
