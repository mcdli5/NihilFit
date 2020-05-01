package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.NihilFit;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

public final class CrucibleRegistrate {
    public static RegistryEntry<CrucibleBaseBlock> registerCrucible() {
        return NihilFit.registrate()
            .block("crucible", Material.ROCK, CrucibleBaseBlock::new)
            .properties(properties -> properties
                .hardnessAndResistance(2.0f)
                .notSolid())
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.modLoc("block/crucible_template_block")))
                    .texture("texture", prov.mcLoc("block/bricks"))))
            .item().build()
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                .shapedRecipe(ctx.getEntry())
                .patternLine("B B")
                .patternLine("B B")
                .patternLine("BBB")
                .key('B', Items.BRICK)
                .addCriterion("has_brick", prov.hasItem(Items.BRICK))
                .build(prov))
            .register();
    }
}
