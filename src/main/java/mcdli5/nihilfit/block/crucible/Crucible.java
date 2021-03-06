package mcdli5.nihilfit.block.crucible;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.NihilFit;
import mcdli5.nihilfit.init.NF_Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

import static mcdli5.nihilfit.block.crucible.CrucibleRegistry.CRUCIBLE_STONE_REGISTRY;
import static mcdli5.nihilfit.block.crucible.CrucibleRegistry.CRUCIBLE_WOOD_REGISTRY;

public enum Crucible {
    STONE(
        "crucible_stone", Material.ROCK, "block/stone_bricks",
        () -> new CrucibleTile(CRUCIBLE_STONE_REGISTRY), ToolType.PICKAXE,
        "Stone Crucible", Items.STONE_BRICKS, "has_stone_brick"
    ),
    WOOD(
        "crucible_wood", Material.WOOD, "block/oak_log",
        () -> new CrucibleTile(CRUCIBLE_WOOD_REGISTRY), ToolType.AXE,
        "Wood Crucible", Items.OAK_LOG, "has_oak_log"
    );

    private static RegistryEntry<TileEntityType<CrucibleTile>> tileRegistryEntry;
    private final BlockBuilder<CrucibleBlock, Registrate> blockBuilder;
    private RegistryEntry<CrucibleBlock> blockRegistryEntry;

    Crucible(
        String name, Material material, String texture,
        Supplier<CrucibleTile> tileSupplier, ToolType toolType,
        String lang, Item itemToBeMadeOf, String criterion
    ) {
        blockBuilder = NihilFit.registrate()
            .block(name, material, (b) -> new CrucibleBlock(b, tileSupplier))
            .properties(properties -> properties
                .hardnessAndResistance(2.0f)
                .harvestTool(toolType))
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
