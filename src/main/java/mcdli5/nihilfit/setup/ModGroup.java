package mcdli5.nihilfit.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public final class ModGroup extends ItemGroup {
    public ModGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.CRUSHEDBLOCK_DUST.get());
    }
}
