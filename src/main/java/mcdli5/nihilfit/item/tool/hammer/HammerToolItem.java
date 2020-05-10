package mcdli5.nihilfit.item.tool.hammer;

import mcdli5.nihilfit.NihilFit;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ToolItem;
import net.minecraftforge.common.ToolType;

import static mcdli5.nihilfit.registry.NF_Registry.HAMMER_REGISTRY;

public final class HammerToolItem extends ToolItem {
    protected HammerToolItem(ItemTier tier) {
        super(1, -2.8F, tier, HAMMER_REGISTRY.EFFECTIVE_ON,
            new Item.Properties().group(NihilFit.NF_GROUP).addToolType(ToolType.get("hammer"), tier.getHarvestLevel())
        );
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return HAMMER_REGISTRY.EFFECTIVE_ON.contains(blockIn.getBlock());
    }
}
