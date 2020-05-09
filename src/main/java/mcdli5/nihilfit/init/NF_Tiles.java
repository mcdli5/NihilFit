package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.crucible.Crucible;
import mcdli5.nihilfit.block.crucible.CrucibleTile;
import net.minecraft.tileentity.TileEntityType;

public final class NF_Tiles {
    // Crucible Tile
    public static final RegistryEntry<TileEntityType<CrucibleTile>> CRUCIBLE = Crucible.registerCrucibleTile();

    public static void setup() {
    }
}
