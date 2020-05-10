package mcdli5.nihilfit.item.tool.hammer;

import com.google.common.collect.ImmutableSet;
import lombok.Data;
import lombok.NonNull;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

import java.util.*;

public final class HammerRegistry {
    private static final Set<String> COLORS = ImmutableSet.of(
        "WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "PINK", "GRAY",
        "LIGHT_GRAY", "CYAN", "BLUE", "PURPLE", "GREEN", "BROWN", "RED", "BLACK"
    );

    protected static final Set<Block> EFFECTIVE_ON;
    private static final HashMap<Block, List<HammerReward>> REGISTRY = new HashMap<>();

    static {
        register(Blocks.STONE, Items.COBBLESTONE);
        register(Blocks.COBBLESTONE, Items.GRAVEL);
        register(Blocks.GRAVEL, Items.SAND);
        register(Blocks.SAND, NF_Blocks.CRUSHEDBLOCK_DUST.get().asItem());
        register(Blocks.ANDESITE, NF_Blocks.CRUSHEDBLOCK_ANDESITE.get().asItem());
        register(Blocks.DIORITE, NF_Blocks.CRUSHEDBLOCK_DIORITE.get().asItem());
        register(Blocks.END_STONE, NF_Blocks.CRUSHEDBLOCK_END_STONE.get().asItem());
        register(Blocks.GRANITE, NF_Blocks.CRUSHEDBLOCK_GRANITE.get().asItem());
        register(Blocks.NETHERRACK, NF_Blocks.CRUSHEDBLOCK_NETHERRACK.get().asItem());

        try {
            for (String color : COLORS) {
                Block block = (Block) Blocks.class.getField(color + "_CONCRETE").get(null);
                Item item = (Item) Items.class.getField(color + "_CONCRETE_POWDER").get(null);
                register(block, item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (String color : COLORS) {
                Block block = (Block) Blocks.class.getField(color + "_WOOL").get(null);
                register(block, Items.STRING, 1f, 0f);
                register(block, Items.STRING, 0.5f, 0.25f);
                Item dye = (Item) Items.class.getField(color + "_DYE").get(null);
                register(block, dye, 1f / 8f, 2f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EFFECTIVE_ON = ImmutableSet.copyOf(REGISTRY.keySet());
    }

    private static void register(Block block, Item item) {
        register(block, item, 1f, 0f);
    }

    private static void register(Block block, Item item, float chance, float fortuneChance) {
        register(block, new HammerReward(item, chance, fortuneChance));
    }

    private static void register(Block block, HammerReward reward) {
        if (REGISTRY.containsKey(block)) {
            REGISTRY.get(block).add(reward);
        } else {
            NonNullList<HammerReward> drops = NonNullList.create();
            drops.add(reward);
            REGISTRY.put(block, drops);
        }
    }

    protected static List<ItemStack> getDrops(List<ItemStack> originalDrops, @NonNull Random random, int fortuneLevel) {
        final NonNullList<ItemStack> drops = NonNullList.create();

        for (ItemStack originalDrop : originalDrops) {
            REGISTRY.entrySet().stream()
                .filter(entry ->
                    entry.getKey().equals(Block.getBlockFromItem(originalDrop.getItem())))
                .map(Map.Entry::getValue)
                .forEach(rewards -> {
                    for (HammerReward reward : rewards) {
                        if (random.nextFloat() <= reward.getChance() + (reward.getFortuneChance() * fortuneLevel)) {
                            drops.add(new ItemStack(reward.getItem(), 1));
                        }
                    }
                });
        }

        return drops;
    }

    @Data
    public static class HammerReward {
        private final Item item;
        private final float chance;
        private final float fortuneChance;
    }
}
