package mcdli5.nihilfit.item.hammer;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import mcdli5.nihilfit.NihilFit;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;

public enum Hammer {
    DIAMOND("hammer_diamond", ItemTier.DIAMOND, Items.DIAMOND, "has_diamond", "Diamond Hammer"),
    GOLD("hammer_gold", ItemTier.GOLD, Items.GOLD_INGOT, "has_gold_ingot", "Gold Hammer"),
    IRON("hammer_iron", ItemTier.IRON, Items.IRON_INGOT, "has_iron_ingot", "Iron Hammer"),
    STONE("hammer_stone", ItemTier.STONE, Items.COBBLESTONE, "has_cobblestone", "Stone Hammer"),
    WOOD("hammer_wood", ItemTier.WOOD, Items.OAK_PLANKS, "has_oak_planks", "Wood Hammer");

    private final ItemBuilder<HammerToolItem, Registrate> itemBuilder;
    private ItemEntry<HammerToolItem> itemEntry;

    Hammer(String name, ItemTier itemTier, Item itemToBeMadeOf, String criterion, String lang) {
        itemBuilder = NihilFit.registrate()
            .item(name, b -> new HammerToolItem(itemTier))
            .model((ctx, prov) -> prov
                .handheld(ctx::getEntry, prov.modLoc("item/" + ctx.getName())))
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                .shapedRecipe(ctx.getEntry())
                .patternLine(" X ")
                .patternLine(" #X")
                .patternLine("#  ")
                .key('#', Items.STICK)
                .key('X', itemToBeMadeOf)
                .addCriterion(criterion, prov.hasItem(itemToBeMadeOf))
                .build(prov))
            .lang(lang);
    }

    public final ItemEntry<HammerToolItem> getItemEntry() {
        if (itemEntry == null) itemEntry = itemBuilder.register();
        return itemEntry;
    }
}
