package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.crucible.CrucibleStoneTile;
import mcdli5.nihilfit.block.crucible.CrucibleWoodTile;
import net.minecraft.tileentity.TileEntityType;

import static mcdli5.nihilfit.block.crucible.CrucibleRegistrate.registerStoneCrucibleTile;
import static mcdli5.nihilfit.block.crucible.CrucibleRegistrate.registerWoodCrucibleTile;

public final class ModTiles {
    // Crucible Tile
    public static final RegistryEntry<TileEntityType<CrucibleStoneTile>> CRUCIBLE_STONE = registerStoneCrucibleTile();
    public static final RegistryEntry<TileEntityType<CrucibleWoodTile>> CRUCIBLE_WOOD = registerWoodCrucibleTile();

    public static void init() {
    }
}
