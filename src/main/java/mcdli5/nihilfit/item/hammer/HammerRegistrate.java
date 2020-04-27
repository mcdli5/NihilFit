package mcdli5.nihilfit.item.hammer;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.item.ItemTier;

import static mcdli5.nihilfit.setup.ModRegistrate.REGISTRATE;
import static mcdli5.nihilfit.setup.ModRegistrate.buildFullName;

public class HammerRegistrate {
    public static final RegistryEntry<HammerToolItem> WOOD_HAMMER = registerHammer(ItemTier.WOOD);
    public static final RegistryEntry<HammerToolItem> STONE_HAMMER = registerHammer(ItemTier.STONE);
    public static final RegistryEntry<HammerToolItem> IRON_HAMMER = registerHammer(ItemTier.IRON);
    public static final RegistryEntry<HammerToolItem> GOLD_HAMMER = registerHammer(ItemTier.GOLD);
    public static final RegistryEntry<HammerToolItem> DIAMOND_HAMMER = registerHammer(ItemTier.DIAMOND);

    public static void init() {
    }

    private static RegistryEntry<HammerToolItem> registerHammer(ItemTier itemTier) {
        String fullName = buildFullName(null, itemTier.name(), "hammer");

        return REGISTRATE.object(fullName)
                .item(b -> new HammerToolItem(itemTier))
                .defaultLang()
                .register();
    }
}
