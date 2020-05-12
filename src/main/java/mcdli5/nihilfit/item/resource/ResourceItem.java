package mcdli5.nihilfit.item.resource;

import mcdli5.nihilfit.init.NF_Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public final class ResourceItem extends Item {
    public ResourceItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote()) {
            Item resourceItem = context.getItem().getItem();
            BlockState oldBlockState = context.getWorld().getBlockState(context.getPos());

            if (resourceItem.equals(NF_Items.SILKWORM.get()) && (oldBlockState.getBlock() instanceof LeavesBlock)) {
                // TODO: Replace dirt with infested leaves
                return shrinkAndReplace(
                    oldBlockState,
                    Blocks.DIRT.getDefaultState(),
                    context
                );
            } else if (oldBlockState.getBlock().equals(Blocks.DIRT)) {
                if (resourceItem.equals(NF_Items.ANCIENT_SPORES.get())) {
                    return shrinkAndReplace(
                        oldBlockState,
                        Blocks.MYCELIUM.getDefaultState(),
                        context
                    );
                } else {
                    return shrinkAndReplace(
                        oldBlockState,
                        Blocks.GRASS_BLOCK.getDefaultState(),
                        context
                    );
                }
            }
        }

        return ActionResultType.PASS;
    }

    private ActionResultType shrinkAndReplace(BlockState oldBlockState, BlockState newBlockState, ItemUseContext context) {
        context.getItem().shrink(1);
        Block.replaceBlock(oldBlockState, newBlockState, context.getWorld(), context.getPos(), 1);
        return ActionResultType.SUCCESS;
    }
}
