package mcdli5.nihilfit.item.hammer;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.NihilFit;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;

public final class HammerRegistrate {
    public static RegistryEntry<HammerToolItem> registerHammer(String name) {
        switch (name) {
            case "Diamond":
                return register("Diamond", ItemTier.DIAMOND, Items.DIAMOND);
            case "Gold":
                return register("Gold", ItemTier.GOLD, Items.GOLD_INGOT);
            case "Iron":
                return register("Iron", ItemTier.IRON, Items.IRON_INGOT);
            case "Stone":
                return register("Stone", ItemTier.STONE, Items.COBBLESTONE);
            default:
                return register("Wood", ItemTier.WOOD, Items.OAK_PLANKS);
        }
    }

    private static RegistryEntry<HammerToolItem> register(String name, ItemTier itemTier, Item itemToBeMadeOf) {
        String registryName = "hammer_" + name.toLowerCase();
        String lang = name + " Hammer";
        String criterion = "has_" + itemToBeMadeOf.toString();

        return NihilFit.registrate()
            .item(registryName, b -> new HammerToolItem(itemTier))
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                .shapedRecipe(ctx.getEntry())
                .patternLine(" X ")
                .patternLine(" #X")
                .patternLine("#  ")
                .key('#', Items.STICK)
                .key('X', itemToBeMadeOf)
                .addCriterion(criterion, prov.hasItem(itemToBeMadeOf))
                .build(prov))
            .lang(lang)
            .register();
    }
}
