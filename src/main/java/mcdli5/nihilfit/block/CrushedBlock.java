package mcdli5.nihilfit.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import mcdli5.nihilfit.NihilFit;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public enum CrushedBlock {
    ANDESITE("crushedblock_andesite", SoundType.GROUND, "Crushed Andesite"),
    DIORITE("crushedblock_diorite", SoundType.GROUND, "Crushed Diorite"),
    DUST("crushedblock_dust", SoundType.CLOTH, "Dust Block"),
    END_STONE("crushedblock_end_stone", SoundType.GROUND, "Crushed End Stone"),
    GRANITE("crushedblock_granite", SoundType.GROUND, "Crushed Granite"),
    NETHERRACK("crushedblock_netherrack", SoundType.GROUND, "Crushed Netherrack");

    private final BlockBuilder<FallingBlock, Registrate> blockBuilder;
    private BlockEntry<FallingBlock> blockEntry;

    CrushedBlock(String name, SoundType soundType, String lang) {
        blockBuilder = NihilFit.registrate()
            .block(name, Material.SAND, FallingBlock::new)
            .properties(properties -> properties
                .hardnessAndResistance(0.7F)
                .sound(soundType)
                .harvestTool(ToolType.SHOVEL))
            .lang(lang)
            .item().build();
    }

    public final BlockEntry<FallingBlock> getBlockEntry() {
        if (blockEntry == null) blockEntry = blockBuilder.register();
        return blockEntry;
    }
}
