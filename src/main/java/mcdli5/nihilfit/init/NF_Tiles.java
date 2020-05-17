package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import mcdli5.nihilfit.block.crucible.Crucible;
import mcdli5.nihilfit.block.crucible.CrucibleTile;
import mcdli5.nihilfit.block.leaves.InfestingLeavesTile;
import mcdli5.nihilfit.block.leaves.Leaves;
import net.minecraft.tileentity.TileEntityType;

public final class NF_Tiles {
    // Crucible Tile
    public static final RegistryEntry<TileEntityType<CrucibleTile>> CRUCIBLE = Crucible.getTileEntry();

    // Infesting Leaves Tile
    public static final RegistryEntry<TileEntityType<InfestingLeavesTile>> INFESTING_LEAVES = Leaves.getTileEntry();

    public static void init() {
    }
}
