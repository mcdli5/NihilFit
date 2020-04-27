package mcdli5.exnihilo.item.hammer;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ToolItem;

import java.util.Set;

import static mcdli5.exnihilo.setup.ModRegistrate.ITEM_GROUP;

public class HammerToolItem extends ToolItem {
    public static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(
            Blocks.COBBLESTONE, Blocks.GRAVEL, Blocks.SAND, Blocks.NETHERRACK,
            Blocks.END_STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE
    );

    protected HammerToolItem(ItemTier tier) {
        super(1, -2.8F, tier, EFFECTIVE_ON, new Item.Properties().group(ITEM_GROUP));
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return EFFECTIVE_ON.contains(blockIn.getBlock());
    }
}
