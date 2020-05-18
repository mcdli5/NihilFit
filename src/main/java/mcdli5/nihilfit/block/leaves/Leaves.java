package mcdli5.nihilfit.block.leaves;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.storage.loot.ItemLootEntry;

public final class Leaves {
    public static final LeavesProvider<InfestingLeavesBlock> INFESTING;
    public static final LeavesProvider<InfestedLeavesBlock> INFESTED;

    private static RegistryEntry<TileEntityType<InfestingLeavesTile>> tileEntry;

    static {
        INFESTING = new LeavesProvider<>(NihilFit.registrate()
            .block("infesting_leaves", InfestingLeavesBlock::new)
            .initialProperties(() -> Blocks.OAK_LEAVES)
            .properties(Block.Properties::noDrops)
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.mcLoc("block/leaves")))
                    .texture("all", prov.mcLoc("block/oak_leaves"))))
            .item().build()
        );

        INFESTED = new LeavesProvider<>(NihilFit.registrate()
            .block("infested_leaves", InfestedLeavesBlock::new)
            .initialProperties(() -> Blocks.OAK_LEAVES)
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.mcLoc("block/leaves")))
                    .texture("all", prov.mcLoc("block/oak_leaves"))))
            .loot((prov, type) -> prov
                .registerLootTable(type, RegistrateBlockLootTables
                    .droppingWithSilkTouchOrShears(type, ItemLootEntry.builder(Items.STRING)))) // TODO: add a chance of silkworms and fix string chance
            .item().build()
        );
    }

    public static RegistryEntry<TileEntityType<InfestingLeavesTile>> getTileEntry() {
        if (tileEntry == null) {
            tileEntry = NihilFit.registrate()
                .tileEntity("infesting_leaves", InfestingLeavesTile::new)
                .validBlocks(NonNullSupplier.of(NF_Blocks.INFESTING_LEAVES))
                .register();
        }

        return tileEntry;
    }

    public static class LeavesProvider<T extends LeavesBlock> {
        private final BlockBuilder<T, Registrate> blockBuilder;
        private BlockEntry<T> blockEntry;

        private LeavesProvider(BlockBuilder<T, Registrate> blockBuilder) {
            this.blockBuilder = blockBuilder;
        }

        public final BlockEntry<T> getBlockEntry() {
            if (blockEntry == null) blockEntry = blockBuilder.register();
            return blockEntry;
        }
    }
}
