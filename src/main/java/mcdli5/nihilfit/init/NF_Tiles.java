package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import mcdli5.nihilfit.block.crucible.Crucible;
import mcdli5.nihilfit.block.crucible.CrucibleTileEntity;
import net.minecraft.tileentity.TileEntityType;

public final class NF_Tiles {
    // Crucible Tile
    public static final RegistryEntry<TileEntityType<CrucibleTileEntity>> CRUCIBLE = Crucible.getTileEntry();

    public static void init() {
    }
}
