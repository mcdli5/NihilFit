package mcdli5.nihilfit.block.crucible;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class CrucibleBaseBlock extends ContainerBlock {
    public CrucibleBaseBlock(Properties builder) {
        super(builder);
        this.setDefaultState(this.getDefaultState());
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasTileEntity() {
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
                IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hit.getFace()).orElse(null);

                // Check if the player has a fluid container item and try to interact with the fluidHandler
                if (FluidUtil.interactWithFluidHandler(player, handIn, fluidHandler)) {
                    // Success!
                    if (player instanceof ServerPlayerEntity) {
                        // Play Sound
                        ((ServerPlayerEntity) player).connection.sendPacket(new SPlaySoundEffectPacket(
                            SoundEvents.ITEM_BUCKET_FILL_LAVA,
                            SoundCategory.BLOCKS,
                            player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ,
                            1.0f, 1.0f
                        ));
                    }
                }
                // Let's try to insert the meltable (if the player is holding one)
                else {
                    IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace()).orElse(null);
                    ItemStack oneHeldItem = new ItemStack(player.getHeldItem(handIn).getItem(), 1);

                    // Insert one item at a time
                    if (itemHandler.insertItem(0, oneHeldItem, false).isEmpty()) {
                        player.getHeldItem(handIn).shrink(1);
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
    }
}
