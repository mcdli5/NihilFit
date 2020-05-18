package mcdli5.nihilfit.item.blockreplacer;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.block.leaves.InfestingLeavesBlock;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum BlockReplacer {
    ANCIENT_SPORES((block) -> block.equals(Blocks.DIRT), () -> Blocks.MYCELIUM),
    GRASS_SEEDS((block) -> block.equals(Blocks.DIRT), () -> Blocks.GRASS_BLOCK),
    SILKWORM(InfestingLeavesBlock::isNormalLeaves, NF_Blocks.INFESTING_LEAVES::get);

    private final ItemBuilder<BlockReplacerItem, Registrate> itemBuilder;
    private ItemEntry<BlockReplacerItem> itemEntry;

    BlockReplacer(NonNullFunction<Block, Boolean> checker, NonNullSupplier<Block> blockSupplier) {
        itemBuilder = NihilFit.registrate()
            .item(this.name().toLowerCase(), (b) -> new BlockReplacerItem(b, checker, blockSupplier))
            .properties(properties -> properties.group(NihilFit.NF_GROUP));
    }

    public final ItemEntry<BlockReplacerItem> getItemEntry() {
        if (itemEntry == null) itemEntry = itemBuilder.register();
        return itemEntry;
    }
}
