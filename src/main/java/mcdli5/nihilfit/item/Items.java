package mcdli5.nihilfit.item;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.item.hammer.HammerToolItem;

import static mcdli5.nihilfit.item.hammer.HammerRegistrate.registerHammer;

public final class Items {
    // Hammers
    public static final RegistryEntry<HammerToolItem> HAMMER_DIAMOND = registerHammer("Diamond");
    public static final RegistryEntry<HammerToolItem> HAMMER_GOLD = registerHammer("Gold");
    public static final RegistryEntry<HammerToolItem> HAMMER_IRON = registerHammer("Iron");
    public static final RegistryEntry<HammerToolItem> HAMMER_STONE = registerHammer("Stone");
    public static final RegistryEntry<HammerToolItem> HAMMER_WOOD = registerHammer("Wood");

    public static void init() {
    }
}
