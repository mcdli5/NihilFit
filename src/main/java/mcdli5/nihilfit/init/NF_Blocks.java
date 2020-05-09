package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.CrushedBlock;
import mcdli5.nihilfit.block.crucible.CrucibleStoneBlock;
import mcdli5.nihilfit.block.crucible.CrucibleWoodBlock;
import net.minecraft.block.FallingBlock;

import static mcdli5.nihilfit.block.crucible.CrucibleSetup.registerStoneCrucible;
import static mcdli5.nihilfit.block.crucible.CrucibleSetup.registerWoodCrucible;

public final class NF_Blocks {
    // Crushed Blocks
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_ANDESITE = CrushedBlock.ANDESITE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DIORITE = CrushedBlock.DIORITE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DUST = CrushedBlock.DUST.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_END_STONE = CrushedBlock.END_STONE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_GRANITE = CrushedBlock.GRANITE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_NETHERRACK = CrushedBlock.NETHERRACK.getRegistryEntry();

    // Crucibles
    public static final RegistryEntry<CrucibleStoneBlock> CRUCIBLE_STONE = registerStoneCrucible();
    public static final RegistryEntry<CrucibleWoodBlock> CRUCIBLE_WOOD = registerWoodCrucible();

    public static void setup() {
    }
}
