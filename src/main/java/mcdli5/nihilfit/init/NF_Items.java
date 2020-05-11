package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.item.tool.hammer.Hammer;
import mcdli5.nihilfit.item.tool.hammer.HammerToolItem;

public final class NF_Items {
    // Hammers
    public static final RegistryEntry<HammerToolItem> HAMMER_DIAMOND = Hammer.DIAMOND.getRegistryEntry();
    public static final RegistryEntry<HammerToolItem> HAMMER_GOLD = Hammer.GOLD.getRegistryEntry();
    public static final RegistryEntry<HammerToolItem> HAMMER_IRON = Hammer.IRON.getRegistryEntry();
    public static final RegistryEntry<HammerToolItem> HAMMER_STONE = Hammer.STONE.getRegistryEntry();
    public static final RegistryEntry<HammerToolItem> HAMMER_WOOD = Hammer.WOOD.getRegistryEntry();

    public static void setup() {
    }
}
