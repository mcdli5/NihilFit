package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.item.blockreplacer.BlockReplacer;
import mcdli5.nihilfit.item.blockreplacer.BlockReplacerItem;
import mcdli5.nihilfit.item.hammer.Hammer;
import mcdli5.nihilfit.item.hammer.HammerToolItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public final class NF_Items {
    // Hammers
    public static final ItemEntry<HammerToolItem> HAMMER_DIAMOND = Hammer.DIAMOND.getItemEntry();
    public static final ItemEntry<HammerToolItem> HAMMER_GOLD = Hammer.GOLD.getItemEntry();
    public static final ItemEntry<HammerToolItem> HAMMER_IRON = Hammer.IRON.getItemEntry();
    public static final ItemEntry<HammerToolItem> HAMMER_STONE = Hammer.STONE.getItemEntry();
    public static final ItemEntry<HammerToolItem> HAMMER_WOOD = Hammer.WOOD.getItemEntry();

    // Resources
    public static final ItemEntry<BlockReplacerItem> ANCIENT_SPORES = BlockReplacer.ANCIENT_SPORES.getItemEntry();
    public static final ItemEntry<BlockReplacerItem> GRASS_SEEDS = BlockReplacer.GRASS_SEEDS.getItemEntry();
    public static final ItemEntry<BlockReplacerItem> SILKWORM = BlockReplacer.SILKWORM.getItemEntry();

    // Cooked Silkworm
    public static final ItemEntry<Item> SILKWORM_COOKED = NihilFit.registrate()
        .item("silkworm_cooked", Item::new)
        .properties(properties -> properties
            .group(NihilFit.NF_GROUP)
            .food(new Food.Builder().hunger(2).saturation(0.6F).build()))
        .lang("Cooked Silkworm")
        .recipe((ctx, prov) -> prov
            .food(DataIngredient.items(NF_Items.SILKWORM.get()), ctx::getEntry, 0.7f))
        .register();

    public static void setup() {
    }
}
