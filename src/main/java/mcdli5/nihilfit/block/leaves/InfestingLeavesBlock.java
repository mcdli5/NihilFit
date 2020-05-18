package mcdli5.nihilfit.block.leaves;

import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public final class InfestingLeavesBlock extends LeavesBlock {
    public static final BooleanProperty NEARBY_LEAVES = BooleanProperty.create("nearby_leaves");
    public static final IntegerProperty INFESTING_STAGE = IntegerProperty.create("infesting_stage", 0, 9);

    public static final int[] COLOR_MAP = new int[]{
        -7690401, -6704776, -5719154, -4864608, -4075855,
        -3287104, -2629682, -1906469, -1249304, -657421
    };

    public static final int UPDATE_FREQUENCY = 5;
    private static final int SPREAD_PERCENT = 15;
    private static final float SPREAD_CHANCE_FLOAT = 0.5F;

    public InfestingLeavesBlock(Properties properties) {
        super(properties);

        this.setDefaultState(stateContainer.getBaseState()
            .with(DISTANCE, 7)
            .with(PERSISTENT, false)
            .with(INFESTING_STAGE, 0)
            .with(NEARBY_LEAVES, true)
        );
    }

    private static void infestLeafBlock(World world, BlockState blockState, BlockPos blockPos) {
        world.setBlockState(blockPos, NF_Blocks.INFESTING_LEAVES.getDefaultState()
            .with(LeavesBlock.PERSISTENT, blockState.get(LeavesBlock.PERSISTENT))
            .with(LeavesBlock.DISTANCE, blockState.get(LeavesBlock.DISTANCE))
        );
    }

    protected static void spread(BlockState state, World world, BlockPos pos, Random rand) {
        if (!world.isRemote && state != null) {
            if (state.get(NEARBY_LEAVES)) {
                NonNullList<BlockPos> nearbyLeaves = getNearbyLeaves(world, pos);
                if (nearbyLeaves.isEmpty()) {
                    world.setBlockState(pos, state.with(NEARBY_LEAVES, false), 7);
                } else {
                    TileEntity te = world.getTileEntity(pos);
                    if (te instanceof InfestingLeavesTile && ((InfestingLeavesTile) te).getProgress() > SPREAD_PERCENT) {
                        nearbyLeaves.stream()
                            .filter(leaves -> rand.nextFloat() >= SPREAD_CHANCE_FLOAT)
                            .findAny()
                            .ifPresent(blockPos -> infestLeafBlock(world, world.getBlockState(blockPos), blockPos));
                    }
                }
            }
        }
    }

    private static NonNullList<BlockPos> getNearbyLeaves(World world, BlockPos pos) {
        NonNullList<BlockPos> blockPos = NonNullList.create();

        BlockPos.getAllInBoxMutable(
            new BlockPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1),
            new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)
        ).forEach((checkPos) -> {
            Block block = world.getBlockState(checkPos).getBlock();
            if (isNormalLeaves(block)) {
                blockPos.add(checkPos.toImmutable());
            }
        });

        return blockPos;
    }

    public static boolean isNormalLeaves(Block block) {
        return (block instanceof LeavesBlock) && !((block instanceof InfestedLeavesBlock) || (block instanceof InfestingLeavesBlock));
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

    @Override
    public boolean ticksRandomly(BlockState state) {
        return state.get(NEARBY_LEAVES) || (state.get(DISTANCE) == 7 && !state.get(PERSISTENT));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        spread(state, worldIn, pos, random);
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return UPDATE_FREQUENCY;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            if (isNormalLeaves(blockIn)) {
                worldIn.setBlockState(pos, state.with(NEARBY_LEAVES, true), 7);
            }
        }

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
}
