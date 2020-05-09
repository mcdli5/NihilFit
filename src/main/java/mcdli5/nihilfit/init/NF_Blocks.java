package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.crucible.CrucibleStoneBlock;
import mcdli5.nihilfit.block.crucible.CrucibleWoodBlock;
import net.minecraft.block.FallingBlock;

import static mcdli5.nihilfit.block.crucible.CrucibleSetup.registerStoneCrucible;
import static mcdli5.nihilfit.block.crucible.CrucibleSetup.registerWoodCrucible;
import static mcdli5.nihilfit.block.crushedblock.CrushedBlockSetup.registerCrushedBlock;

public final class NF_Blocks {
    // Crushed Blocks
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_ANDESITE = registerCrushedBlock("Andesite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DIORITE = registerCrushedBlock("Diorite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DUST = registerCrushedBlock("Dust");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_END_STONE = registerCrushedBlock("End Stone");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_GRANITE = registerCrushedBlock("Granite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_NETHERRACK = registerCrushedBlock("Netherrack");

    // Crucibles
    public static final RegistryEntry<CrucibleStoneBlock> CRUCIBLE_STONE = registerStoneCrucible();
    public static final RegistryEntry<CrucibleWoodBlock> CRUCIBLE_WOOD = registerWoodCrucible();

    public static void setup() {
    }
}
