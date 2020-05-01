package mcdli5.nihilfit.block;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.block.FallingBlock;

import static mcdli5.nihilfit.block.crushedblock.CrushedBlockRegistrate.registerCrushedBlock;

public final class Blocks {
    // Crushed Blocks
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_ANDESITE = registerCrushedBlock("Andesite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DIORITE = registerCrushedBlock("Diorite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_DUST = registerCrushedBlock("Dust");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_END_STONE = registerCrushedBlock("End Stone");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_GRANITE = registerCrushedBlock("Granite");
    public static final RegistryEntry<FallingBlock> CRUSHEDBLOCK_NETHERRACK = registerCrushedBlock("Netherrack");

    public static void init() {
    }
}
