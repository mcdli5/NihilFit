package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.item.tool.hammer.HammerToolItem;

import static mcdli5.nihilfit.item.tool.hammer.HammerSetup.registerHammer;

public final class NF_Items {
    // Hammers
    public static final RegistryEntry<HammerToolItem> HAMMER_DIAMOND = registerHammer("Diamond");
    public static final RegistryEntry<HammerToolItem> HAMMER_GOLD = registerHammer("Gold");
    public static final RegistryEntry<HammerToolItem> HAMMER_IRON = registerHammer("Iron");
    public static final RegistryEntry<HammerToolItem> HAMMER_STONE = registerHammer("Stone");
    public static final RegistryEntry<HammerToolItem> HAMMER_WOOD = registerHammer("Wood");

    public static void setup() {
    }
}
