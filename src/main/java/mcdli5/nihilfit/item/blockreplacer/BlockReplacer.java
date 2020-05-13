package mcdli5.nihilfit.item.blockreplacer;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import mcdli5.nihilfit.NihilFit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;

public enum BlockReplacer {
    ANCIENT_SPORES((block) -> block.equals(Blocks.DIRT), Blocks.MYCELIUM),
    GRASS_SEEDS((block) -> block.equals(Blocks.DIRT), Blocks.GRASS_BLOCK),
    SILKWORM((block) -> block instanceof LeavesBlock, Blocks.DIRT);

    private final ItemBuilder<BlockReplacerItem, Registrate> itemBuilder;
    private ItemEntry<BlockReplacerItem> itemEntry;

    BlockReplacer(NonNullFunction<Block, Boolean> checker, Block newBlock) {
        itemBuilder = NihilFit.registrate()
            .item(this.name().toLowerCase(), (b) -> new BlockReplacerItem(b, checker, newBlock))
            .properties(properties -> properties.group(NihilFit.NF_GROUP));
    }

    public final ItemEntry<BlockReplacerItem> getItemEntry() {
        if (itemEntry == null) itemEntry = itemBuilder.register();
        return itemEntry;
    }
}
