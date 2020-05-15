package mcdli5.nihilfit.block.leaves;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import mcdli5.nihilfit.NihilFit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Items;
import net.minecraft.world.storage.loot.ItemLootEntry;

public final class Leaves {
    public static final LeavesProvider<InfestingLeavesBlock> INFESTING;
    public static final LeavesProvider<LeavesBlock> INFESTED;

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
            .block("infested_leaves", LeavesBlock::new)
            .initialProperties(() -> Blocks.OAK_LEAVES)
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.mcLoc("block/leaves")))
                    .texture("all", prov.mcLoc("block/oak_leaves"))))
            .loot((prov, type) -> prov
                .registerLootTable(type, RegistrateBlockLootTables
                    .droppingWithSilkTouchOrShears(type, ItemLootEntry.builder(Items.STRING))))
            .item().build()
        );
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
