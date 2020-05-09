package mcdli5.nihilfit.item.tool.hammer;

import com.google.common.collect.ImmutableSet;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Set;

public final class HammerRegistry {
    public static final Set<Block> EFFECTIVE_ON;
    private static final HashMap<Block, Item> MAPPED_DROPS = new HashMap<>();

    static {
        MAPPED_DROPS.put(Blocks.STONE, Items.COBBLESTONE);
        MAPPED_DROPS.put(Blocks.COBBLESTONE, Items.GRAVEL);
        MAPPED_DROPS.put(Blocks.GRAVEL, Items.SAND);
        MAPPED_DROPS.put(Blocks.SAND, NF_Blocks.CRUSHEDBLOCK_DUST.get().asItem());
        MAPPED_DROPS.put(Blocks.ANDESITE, NF_Blocks.CRUSHEDBLOCK_ANDESITE.get().asItem());
        MAPPED_DROPS.put(Blocks.DIORITE, NF_Blocks.CRUSHEDBLOCK_DIORITE.get().asItem());
        MAPPED_DROPS.put(Blocks.END_STONE, NF_Blocks.CRUSHEDBLOCK_END_STONE.get().asItem());
        MAPPED_DROPS.put(Blocks.GRANITE, NF_Blocks.CRUSHEDBLOCK_GRANITE.get().asItem());
        MAPPED_DROPS.put(Blocks.NETHERRACK, NF_Blocks.CRUSHEDBLOCK_NETHERRACK.get().asItem());

        EFFECTIVE_ON = ImmutableSet.copyOf(MAPPED_DROPS.keySet());
    }

    public static ItemStack getReward(ItemStack itemStack) {
        final Block key = Block.getBlockFromItem(itemStack.getItem());
        final int count = itemStack.getCount();
        return new ItemStack(MAPPED_DROPS.getOrDefault(key, null), count);
    }
}
