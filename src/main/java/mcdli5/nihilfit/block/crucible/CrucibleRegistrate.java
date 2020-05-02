package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;

public final class CrucibleRegistrate {
    public static RegistryEntry<TileEntityType<CrucibleBaseTile>> registerCrucibleTile() {
        return NihilFit.registrate()
            .tileEntity("crucible", CrucibleBaseTile::new)
            .validBlock(NonNullSupplier.of(ModBlocks.CRUCIBLE))
            .register();
    }

    public static RegistryEntry<CrucibleBaseBlock> registerCrucible() {
        return NihilFit.registrate()
            .block("crucible", Material.ROCK, CrucibleBaseBlock::new)
            .properties(properties -> properties.hardnessAndResistance(2.0f))
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.modLoc("block/crucible_template")))
                    .texture("texture", prov.mcLoc("block/stone_bricks"))))
            .item().build()
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                .shapedRecipe(ctx.getEntry())
                .patternLine("B B")
                .patternLine("B B")
                .patternLine("BBB")
                .key('B', Items.STONE_BRICKS)
                .addCriterion("has_stone_brick", prov.hasItem(Items.STONE_BRICKS))
                .build(prov))
            .register();
    }
}
