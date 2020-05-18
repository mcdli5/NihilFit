package mcdli5.nihilfit.item.blockreplacer;

import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public final class BlockReplacerItem extends Item {
    private final NonNullFunction<Block, Boolean> checker;
    private final Block newBlock;

    public BlockReplacerItem(Properties builder, NonNullFunction<Block, Boolean> checker, NonNullSupplier<Block> blockSupplier) {
        super(builder);
        this.checker = checker;
        this.newBlock = blockSupplier.get();
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote()) {
            BlockState oldBlockState = context.getWorld().getBlockState(context.getPos());
            Block oldBlock = oldBlockState.getBlock();

            if (checker.apply(oldBlock)) {
                context.getItem().shrink(1);

                BlockState newBlockState = newBlock.getDefaultState();
                if (oldBlock instanceof LeavesBlock) {
                    newBlockState = newBlockState
                        .with(LeavesBlock.PERSISTENT, oldBlockState.get(LeavesBlock.PERSISTENT))
                        .with(LeavesBlock.DISTANCE, oldBlockState.get(LeavesBlock.DISTANCE));
                }

                Block.replaceBlock(oldBlockState, newBlockState, context.getWorld(), context.getPos(), 1);

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
}
