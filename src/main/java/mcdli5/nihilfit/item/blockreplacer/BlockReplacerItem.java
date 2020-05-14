package mcdli5.nihilfit.item.blockreplacer;

import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public final class BlockReplacerItem extends Item {
    private final NonNullFunction<Block, Boolean> checker;
    private final Block newBlock;

    public BlockReplacerItem(Properties builder, NonNullFunction<Block, Boolean> checker, Block newBlock) {
        super(builder);
        this.checker = checker;
        this.newBlock = newBlock;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote()) {
            BlockState oldBlockState = context.getWorld().getBlockState(context.getPos());

            if (checker.apply(oldBlockState.getBlock())) {
                context.getItem().shrink(1);

                Block.replaceBlock(oldBlockState, newBlock.getDefaultState(),
                    context.getWorld(), context.getPos(), 1);

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
}
