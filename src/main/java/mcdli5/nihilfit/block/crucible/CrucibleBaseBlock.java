package mcdli5.nihilfit.block.crucible;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class CrucibleBaseBlock extends ContainerBlock {
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);

    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
        VoxelShapes.fullCube(),
        VoxelShapes.or(
            makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D),
            makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D),
            makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
            INSIDE
        ),
        IBooleanFunction.ONLY_FIRST
    );

    public CrucibleBaseBlock(Properties builder) {
        super(builder.hardnessAndResistance(2.0f));

        this.setDefaultState(this.getDefaultState()
            .with(LIGHT_LEVEL, 0)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new CrucibleBaseTile();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            final TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof CrucibleBaseTile) {
                CrucibleBaseTile.CrucibleFluidTank crucibleFluidTank = ((CrucibleBaseTile) tileEntity).fluidTank;

                FluidStack fluidStack = crucibleFluidTank.getFluid();
                SoundEvent soundevent = fluidStack.getFluid().getAttributes().getFillSound();
                if (soundevent == null) soundevent = (fluidStack.getFluid().isIn(FluidTags.LAVA) ?
                    SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL);

                // Check if the player has a fluid container item and try to interact with the fluidHandler
                if (FluidUtil.interactWithFluidHandler(player, handIn, crucibleFluidTank)) {
                    // Success!
                    if (player instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) player).connection.sendPacket(new SPlaySoundEffectPacket(
                            soundevent,
                            SoundCategory.BLOCKS,
                            player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ,
                            1.0f, 1.0f
                        ));
                    }
                }
                // Let's try to insert the meltable (if the player is holding one)
                else {
                    IItemHandler itemHandler = ((CrucibleBaseTile) tileEntity).itemStackHandler;
                    ItemStack heldItem = new ItemStack(player.getHeldItem(handIn).getItem(), 1);

                    // Insert one item at a time
                    if (itemHandler.insertItem(0, heldItem, false).isEmpty()) {
                        player.getHeldItem(handIn).shrink(1);
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getLightValue(BlockState state) {
        return state.get(LIGHT_LEVEL);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(LIGHT_LEVEL);
    }

    // Voxel shape and model was inspired by Vanilla's Cauldron
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return INSIDE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
