package mcdli5.nihilfit.item;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.NF_Items;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public final class CookedSilkworm {
    private static final ItemBuilder<CookedSilkwormItem, Registrate> itemBuilder;
    private static ItemEntry<CookedSilkwormItem> itemEntry;

    static {
        itemBuilder = NihilFit.registrate()
            .item("silkworm_cooked", CookedSilkwormItem::new)
            .properties(properties -> properties
                .group(NihilFit.NF_GROUP)
                .food(new Food.Builder().hunger(2).saturation(0.6F).build()))
            .lang("Cooked Silkworm")
            .recipe((ctx, prov) -> prov
                .food(DataIngredient.items(NF_Items.SILKWORM.get()), ctx::getEntry, 0.7f));
    }

    public static ItemEntry<CookedSilkwormItem> getItemEntry() {
        if (itemEntry == null) itemEntry = itemBuilder.register();
        return itemEntry;
    }

    public static final class CookedSilkwormItem extends Item {
        public CookedSilkwormItem(Properties properties) {
            super(properties);
        }
    }
}
