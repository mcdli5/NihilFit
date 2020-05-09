package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;

public final class CrucibleSetup {
    // TODO: This need some refactor to avoid duplication
    public static RegistryEntry<CrucibleStoneBlock> registerStoneCrucible() {
        return NihilFit.registrate()
            .block("crucible_stone", Material.ROCK, CrucibleStoneBlock::new)
            .properties(properties -> properties.hardnessAndResistance(2.0f))
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.modLoc("block/crucible_template")))
                    .texture("texture", prov.mcLoc("block/stone_bricks"))))
            .lang("Stone Crucible")
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

    public static RegistryEntry<CrucibleWoodBlock> registerWoodCrucible() {
        return NihilFit.registrate()
            .block("crucible_wood", Material.ROCK, CrucibleWoodBlock::new)
            .properties(properties -> properties.hardnessAndResistance(2.0f))
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.modLoc("block/crucible_template")))
                    .texture("texture", prov.mcLoc("block/oak_log"))))
            .lang("Wood Crucible")
            .item().build()
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                .shapedRecipe(ctx.getEntry())
                .patternLine("B B")
                .patternLine("B B")
                .patternLine("BBB")
                .key('B', Items.OAK_LOG)
                .addCriterion("has_oak_log", prov.hasItem(Items.OAK_LOG))
                .build(prov))
            .register();
    }

    public static RegistryEntry<TileEntityType<CrucibleStoneTile>> registerStoneCrucibleTile() {
        return NihilFit.registrate()
            .tileEntity("crucible_stone", CrucibleStoneTile::new)
            .validBlock(NonNullSupplier.of(NF_Blocks.CRUCIBLE_STONE))
            .register();
    }

    public static RegistryEntry<TileEntityType<CrucibleWoodTile>> registerWoodCrucibleTile() {
        return NihilFit.registrate()
            .tileEntity("crucible_wood", CrucibleWoodTile::new)
            .validBlock(NonNullSupplier.of(NF_Blocks.CRUCIBLE_WOOD))
            .register();
    }
}
