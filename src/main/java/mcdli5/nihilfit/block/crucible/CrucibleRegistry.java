package mcdli5.nihilfit.block.crucible;

import lombok.Data;
import lombok.NonNull;
import mcdli5.nihilfit.block.CrushedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public final class CrucibleRegistry {
    public static final CrucibleRegistry CRUCIBLE_STONE_REGISTRY;
    public static final CrucibleRegistry CRUCIBLE_WOOD_REGISTRY;

    static {
        CRUCIBLE_STONE_REGISTRY = new CrucibleRegistry(
            stone_heat_registry(),
            stone_meltable_registry(),
            "CRUCIBLE_STONE_REGISTRY",
            new FluidStack(Fluids.LAVA, 1000)
        );

        CRUCIBLE_WOOD_REGISTRY = new CrucibleRegistry(
            wood_heat_registry(),
            wood_meltable_registry(),
            "CRUCIBLE_WOOD_REGISTRY",
            new FluidStack(Fluids.WATER, 1000)
        );
    }

    public final FluidStack VALID_FLUID;
    private final HashMap<Block, Integer> heat_registry;
    private final HashMap<Item, Meltable> meltable_registry;
    private final String name;

    private CrucibleRegistry(
        HashMap<Block, Integer> heat_registry,
        HashMap<Item, Meltable> meltable_registry,
        String name, FluidStack validFluidStack
    ) {
        this.heat_registry = heat_registry;
        this.meltable_registry = meltable_registry;
        this.name = name;
        VALID_FLUID = validFluidStack;
    }

    private static HashMap<Block, Integer> stone_heat_registry() {
        final HashMap<Block, Integer> registry = new HashMap<>();

        registry.put(Blocks.TORCH, 1);
        registry.put(Blocks.GLOWSTONE, 2);
        registry.put(Blocks.MAGMA_BLOCK, 3);
        registry.put(Blocks.LAVA, 4);
        registry.put(Blocks.FIRE, 5);
        registry.put(Blocks.CAMPFIRE, 6);

        return registry;
    }

    private static HashMap<Item, Meltable> stone_meltable_registry() {
        final HashMap<Item, Meltable> registry = new HashMap<>();
        final Fluid lava = Fluids.LAVA;

        registry.put(Items.STONE, new Meltable(lava, 250));
        registry.put(Items.COBBLESTONE, new Meltable(lava, 250));
        registry.put(Items.GRAVEL, new Meltable(lava, 200));
        registry.put(Items.SAND, new Meltable(lava, 100));

        for (CrushedBlock crushedblock : CrushedBlock.values()) {
            Item key = crushedblock.getBlockEntry().get().asItem();
            registry.put(key, new Meltable(lava, 50));
        }

        // 1:1 Back to lava
        registry.put(Items.NETHERRACK, new Meltable(lava, 1000));
        registry.put(Items.OBSIDIAN, new Meltable(lava, 1000));

        return registry;
    }

    private static HashMap<Block, Integer> wood_heat_registry() {
        final HashMap<Block, Integer> registry = new HashMap<>();

        registry.put(Blocks.TORCH, 1);

        return registry;
    }

    private static HashMap<Item, Meltable> wood_meltable_registry() {
        final Meltable water = new Meltable(Fluids.WATER, 250);
        final HashMap<Item, Meltable> registry = new HashMap<>();

        registry.put(Items.ACACIA_LEAVES, water);
        registry.put(Items.BIRCH_LEAVES, water);
        registry.put(Items.DARK_OAK_LEAVES, water);
        registry.put(Items.JUNGLE_LEAVES, water);
        registry.put(Items.OAK_LEAVES, water);
        registry.put(Items.SPRUCE_LEAVES, water);

        registry.put(Items.ACACIA_SAPLING, water);
        registry.put(Items.BIRCH_SAPLING, water);
        registry.put(Items.DARK_OAK_SAPLING, water);
        registry.put(Items.JUNGLE_SAPLING, water);
        registry.put(Items.OAK_SAPLING, water);
        registry.put(Items.SPRUCE_SAPLING, water);

        return registry;
    }

    public int getHeatAmount(@NonNull Block block) {
        return heat_registry.getOrDefault(block, 0);
    }

    public boolean isItemValid(@NonNull ItemStack inputStack) {
        return meltable_registry.containsKey(inputStack.getItem());
    }

    public Meltable getMeltable(@NonNull Item item) {
        return meltable_registry.get(item);
    }

    public String getName() {
        return name;
    }

    @Data
    public static class Meltable {
        private final Fluid fluid;
        private final int amount;
    }
}
