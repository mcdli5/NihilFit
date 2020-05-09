package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidStack;

public final class Crucible {
    // TODO: This need some refactor to avoid duplication
    public static RegistryEntry<CrucibleBlock> registerStoneCrucible() {
        return NihilFit.registrate()
            .block("crucible_stone", Material.ROCK, (b) -> new CrucibleBlock(b, () -> new CrucibleTile(Items.COBBLESTONE.getDefaultInstance(), new FluidStack(Fluids.LAVA, 1000))))
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

    public static RegistryEntry<CrucibleBlock> registerWoodCrucible() {
        return NihilFit.registrate()
            .block("crucible_wood", Material.ROCK, (b) -> new CrucibleBlock(b, () -> new CrucibleTile(Items.OAK_SAPLING.getDefaultInstance(), new FluidStack(Fluids.WATER, 1000))))
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

    public static RegistryEntry<TileEntityType<CrucibleTile>> registerCrucibleTile() {
        return NihilFit.registrate()
            .tileEntity("crucible", CrucibleTile::new)
            .validBlocks(
                NonNullSupplier.of(NF_Blocks.CRUCIBLE_STONE),
                NonNullSupplier.of(NF_Blocks.CRUCIBLE_WOOD))
            .register();
    }
}
