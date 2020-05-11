package mcdli5.nihilfit.item.hammer;

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

        // Concrete
        register(Blocks.WHITE_CONCRETE, Items.WHITE_CONCRETE_POWDER);
        register(Blocks.ORANGE_CONCRETE, Items.ORANGE_CONCRETE_POWDER);
        register(Blocks.MAGENTA_CONCRETE, Items.MAGENTA_CONCRETE_POWDER);
        register(Blocks.LIGHT_BLUE_CONCRETE, Items.LIGHT_BLUE_CONCRETE_POWDER);
        register(Blocks.YELLOW_CONCRETE, Items.YELLOW_CONCRETE_POWDER);
        register(Blocks.LIME_CONCRETE, Items.LIME_CONCRETE_POWDER);
        register(Blocks.PINK_CONCRETE, Items.PINK_CONCRETE_POWDER);
        register(Blocks.GRAY_CONCRETE, Items.GRAY_CONCRETE_POWDER);
        register(Blocks.LIGHT_GRAY_CONCRETE, Items.LIGHT_GRAY_CONCRETE_POWDER);
        register(Blocks.CYAN_CONCRETE, Items.CYAN_CONCRETE_POWDER);
        register(Blocks.BLUE_CONCRETE, Items.BLUE_CONCRETE_POWDER);
        register(Blocks.PURPLE_CONCRETE, Items.PURPLE_CONCRETE_POWDER);
        register(Blocks.GREEN_CONCRETE, Items.GREEN_CONCRETE_POWDER);
        register(Blocks.BROWN_CONCRETE, Items.BROWN_CONCRETE_POWDER);
        register(Blocks.RED_CONCRETE, Items.RED_CONCRETE_POWDER);
        register(Blocks.BLACK_CONCRETE, Items.BLACK_CONCRETE_POWDER);

        // Wool
        register(Blocks.WHITE_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.WHITE_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.WHITE_WOOL, Items.WHITE_DYE, 1f / 8f, 2f);
        register(Blocks.ORANGE_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.ORANGE_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.ORANGE_WOOL, Items.ORANGE_DYE, 1f / 8f, 2f);
        register(Blocks.MAGENTA_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.MAGENTA_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.MAGENTA_WOOL, Items.MAGENTA_DYE, 1f / 8f, 2f);
        register(Blocks.LIGHT_BLUE_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.LIGHT_BLUE_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_DYE, 1f / 8f, 2f);
        register(Blocks.YELLOW_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.YELLOW_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.YELLOW_WOOL, Items.YELLOW_DYE, 1f / 8f, 2f);
        register(Blocks.LIME_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.LIME_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.LIME_WOOL, Items.LIME_DYE, 1f / 8f, 2f);
        register(Blocks.PINK_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.PINK_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.PINK_WOOL, Items.PINK_DYE, 1f / 8f, 2f);
        register(Blocks.GRAY_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.GRAY_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.GRAY_WOOL, Items.GRAY_DYE, 1f / 8f, 2f);
        register(Blocks.LIGHT_GRAY_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.LIGHT_GRAY_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_DYE, 1f / 8f, 2f);
        register(Blocks.CYAN_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.CYAN_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.CYAN_WOOL, Items.CYAN_DYE, 1f / 8f, 2f);
        register(Blocks.BLUE_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.BLUE_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.BLUE_WOOL, Items.BLUE_DYE, 1f / 8f, 2f);
        register(Blocks.PURPLE_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.PURPLE_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.PURPLE_WOOL, Items.PURPLE_DYE, 1f / 8f, 2f);
        register(Blocks.GREEN_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.GREEN_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.GREEN_WOOL, Items.GREEN_DYE, 1f / 8f, 2f);
        register(Blocks.BROWN_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.BROWN_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.BROWN_WOOL, Items.BROWN_DYE, 1f / 8f, 2f);
        register(Blocks.RED_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.RED_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.RED_WOOL, Items.RED_DYE, 1f / 8f, 2f);
        register(Blocks.BLACK_WOOL, Items.STRING, 1f, 0f);
        register(Blocks.BLACK_WOOL, Items.STRING, 0.5f, 0.25f);
        register(Blocks.BLACK_WOOL, Items.BLACK_DYE, 1f / 8f, 2f);

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
