package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.crucible.CrucibleBaseTile;
import net.minecraft.tileentity.TileEntityType;

import static mcdli5.nihilfit.block.crucible.CrucibleRegistrate.registerCrucibleTile;

public final class ModTiles {
    // Crucible Tile
    public static final RegistryEntry<TileEntityType<CrucibleBaseTile>> CRUCIBLE = registerCrucibleTile();

    public static void init() {
    }
}
