package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

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
            .blockstate(CrucibleRegistrate::buildBlockstates)
            .item().model((ctx, prov) -> prov
                .withExistingParent(ctx.getName(),prov.modLoc("block/crucible_level0")))
            .build()
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

    private static void buildBlockstates(DataGenContext<Block, CrucibleBaseBlock> ctx, RegistrateBlockstateProvider prov) {
        final VariantBlockStateBuilder variantBuilder = prov.getVariantBuilder(ctx.getEntry());
        for (int i = 0; i < 13; i++) {
            variantBuilder
                .partialState()
                .with(CrucibleBaseBlock.LEVEL, i)
                .addModels(ConfiguredModel.builder()
                    .modelFile(prov.models()
                        .getBuilder(ctx.getName() + "_level" + i)
                        .parent(prov.models().getExistingFile(prov.modLoc("block/crucible_template_level" + i)))
                        .texture("texture", prov.mcLoc("block/stone_bricks")))
                    .build());
        }
    }
}
