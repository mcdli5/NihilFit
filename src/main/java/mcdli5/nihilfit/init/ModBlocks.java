package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.crucible.CrucibleBaseBlock;
import net.minecraft.block.FallingBlock;

import static mcdli5.nihilfit.block.crucible.CrucibleRegistrate.registerCrucible;
import static mcdli5.nihilfit.block.crushedblock.CrushedBlockRegistrate.registerCrushedBlock;

public final class ModBlocks {
    // Crushed Blocks
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_ANDESITE = registerCrushedBlock("Andesite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DIORITE = registerCrushedBlock("Diorite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DUST = registerCrushedBlock("Dust");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_END_STONE = registerCrushedBlock("End Stone");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_GRANITE = registerCrushedBlock("Granite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_NETHERRACK = registerCrushedBlock("Netherrack");

    // Crucibles
    public static final RegistryEntry<CrucibleBaseBlock> CRUCIBLE = registerCrucible();

    public static void init() {
    }
}
