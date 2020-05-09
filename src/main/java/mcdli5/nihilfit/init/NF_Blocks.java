package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.block.CrushedBlock;
import mcdli5.nihilfit.block.crucible.Crucible;
import mcdli5.nihilfit.block.crucible.CrucibleBlock;
import net.minecraft.block.FallingBlock;

public final class NF_Blocks {
    // Crushed Blocks
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_ANDESITE = CrushedBlock.ANDESITE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DIORITE = CrushedBlock.DIORITE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DUST = CrushedBlock.DUST.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_END_STONE = CrushedBlock.END_STONE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_GRANITE = CrushedBlock.GRANITE.getRegistryEntry();
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_NETHERRACK = CrushedBlock.NETHERRACK.getRegistryEntry();

    // Crucibles
    public static final RegistryEntry<CrucibleBlock> CRUCIBLE_STONE = Crucible.STONE.getBlockRegistryEntry();
    public static final RegistryEntry<CrucibleBlock> CRUCIBLE_WOOD = Crucible.WOOD.getBlockRegistryEntry();

    public static void setup() {
    }
}
