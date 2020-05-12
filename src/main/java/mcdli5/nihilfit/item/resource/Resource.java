package mcdli5.nihilfit.item.resource;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import mcdli5.nihilfit.NihilFit;

public enum Resource {
    ANCIENT_SPORES,
    GRASS_SEEDS,
    SILKWORM;

    private final ItemBuilder<ResourceItem, Registrate> itemBuilder;
    private ItemEntry<ResourceItem> itemEntry;

    Resource() {
        itemBuilder = NihilFit.registrate()
            .item(this.name().toLowerCase(), ResourceItem::new)
            .properties(properties -> properties.group(NihilFit.NF_GROUP));
    }

    public final ItemEntry<ResourceItem> getItemEntry() {
        if (itemEntry == null) itemEntry = itemBuilder.register();
        return itemEntry;
    }
}
