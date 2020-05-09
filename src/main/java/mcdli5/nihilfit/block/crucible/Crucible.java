package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

public enum Crucible {
    STONE(
        "crucible_stone", Material.ROCK,
        () -> new CrucibleTile(Items.COBBLESTONE.getDefaultInstance(), new FluidStack(Fluids.LAVA, 1000)),
        "block/stone_bricks", "Stone Crucible", Items.STONE_BRICKS, "has_stone_brick"
    ),
    WOOD(
        "crucible_wood", Material.WOOD,
        () -> new CrucibleTile(Items.OAK_SAPLING.getDefaultInstance(), new FluidStack(Fluids.WATER, 1000)),
        "block/oak_log", "Wood Crucible", Items.OAK_LOG, "has_oak_log"
    );

    private static RegistryEntry<TileEntityType<CrucibleTile>> tileRegistryEntry;
    private final BlockBuilder<CrucibleBlock, Registrate> blockBuilder;
    private RegistryEntry<CrucibleBlock> blockRegistryEntry;

    Crucible(
        String name, Material material, Supplier<CrucibleTile> tileSupplier,
        String texture, String lang, Item itemToBeMadeOf, String criterion
    ) {
        blockBuilder = NihilFit.registrate()
            .block(name, material, (b) -> new CrucibleBlock(b, tileSupplier))
            .properties(properties -> properties.hardnessAndResistance(2.0f))
            .blockstate((ctx, prov) -> prov.simpleBlock(
                ctx.getEntry(),
                prov.models().getBuilder(ctx.getName())
                    .parent(prov.models().getExistingFile(prov.modLoc("block/crucible_template")))
                    .texture("texture", prov.mcLoc(texture))))
            .lang(lang)
            .item().build()
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                .shapedRecipe(ctx.getEntry())
                .patternLine("B B")
                .patternLine("B B")
                .patternLine("BBB")
                .key('B', itemToBeMadeOf)
                .addCriterion(criterion, prov.hasItem(itemToBeMadeOf))
                .build(prov));
    }

    public static RegistryEntry<TileEntityType<CrucibleTile>> getTileRegistryEntry() {
        if (tileRegistryEntry == null) {
            tileRegistryEntry = NihilFit.registrate()
                .tileEntity("crucible", CrucibleTile::new)
                .validBlocks(
                    NonNullSupplier.of(NF_Blocks.CRUCIBLE_STONE),
                    NonNullSupplier.of(NF_Blocks.CRUCIBLE_WOOD))
                .register();
        }

        return tileRegistryEntry;
    }

    public final RegistryEntry<CrucibleBlock> getBlockRegistryEntry() {
        if (blockRegistryEntry == null) blockRegistryEntry = blockBuilder.register();
        return blockRegistryEntry;
    }
}
