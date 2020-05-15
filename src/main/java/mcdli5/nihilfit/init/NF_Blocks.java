package mcdli5.nihilfit.init;

import com.tterrag.registrate.util.entry.BlockEntry;
import mcdli5.nihilfit.block.CrushedBlock;
import mcdli5.nihilfit.block.crucible.Crucible;
import mcdli5.nihilfit.block.crucible.CrucibleBlock;
import mcdli5.nihilfit.block.leaves.InfestingLeavesBlock;
import mcdli5.nihilfit.block.leaves.Leaves;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.LeavesBlock;

public final class NF_Blocks {
    // Crushed Blocks
    public static final BlockEntry<FallingBlock> CRUSHEDBLOCK_ANDESITE = CrushedBlock.ANDESITE.getBlockEntry();
    public static final BlockEntry<FallingBlock> CRUSHEDBLOCK_DIORITE = CrushedBlock.DIORITE.getBlockEntry();
    public static final BlockEntry<FallingBlock> CRUSHEDBLOCK_DUST = CrushedBlock.DUST.getBlockEntry();
    public static final BlockEntry<FallingBlock> CRUSHEDBLOCK_END_STONE = CrushedBlock.END_STONE.getBlockEntry();
    public static final BlockEntry<FallingBlock> CRUSHEDBLOCK_GRANITE = CrushedBlock.GRANITE.getBlockEntry();
    public static final BlockEntry<FallingBlock> CRUSHEDBLOCK_NETHERRACK = CrushedBlock.NETHERRACK.getBlockEntry();

    // Crucibles
    public static final BlockEntry<CrucibleBlock> CRUCIBLE_STONE = Crucible.STONE.getBlockEntry();
    public static final BlockEntry<CrucibleBlock> CRUCIBLE_WOOD = Crucible.WOOD.getBlockEntry();

    // Leaves
    public static final BlockEntry<InfestingLeavesBlock> INFESTING_LEAVES = Leaves.INFESTING.getBlockEntry();
    public static final BlockEntry<LeavesBlock> INFESTED_LEAVES = Leaves.INFESTED.getBlockEntry();

    public static void init() {
    }
}
