package mcdli5.exnihilo.block.fallingblock;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import static mcdli5.exnihilo.setup.ModRegistrate.REGISTRATE;

public class FallingBlockRegistrate {
    public static final RegistryEntry<FallingBlock> DUSTBLOCK
            = registerFallingBlock("dustblock", "Dust", SoundType.CLOTH);
    public static final RegistryEntry<FallingBlock> CRUSHEDNETHERRACK
            = registerFallingBlock("crushed_netherrack", "Crushed Netherrack", null);
    public static final RegistryEntry<FallingBlock> CRUSHEDENDSTONE
            = registerFallingBlock("crushed_endstone", "Crushed Endstone", null);
    public static final RegistryEntry<FallingBlock> CRUSHEDANDESITE
            = registerFallingBlock("crushed_andesite", "Crushed Andesite", null);
    public static final RegistryEntry<FallingBlock> CRUSHEDDIORITE
            = registerFallingBlock("crushed_diorite", "Crushed Diorite", null);
    public static final RegistryEntry<FallingBlock> CRUSHEDGRANITE
            = registerFallingBlock("crushed_granite", "Crushed Granite", null);

    public static void init() {
    }

    private static RegistryEntry<FallingBlock> registerFallingBlock(String name, String translation, SoundType sound) {
        return REGISTRATE.object(name)
                .block(FallingBlock::new)
                .properties(properties -> Block.Properties
                        .create(Material.SAND)
                        .hardnessAndResistance(0.7F)
                        .sound((sound == null) ? SoundType.GROUND : sound)
                        .harvestTool(ToolType.SHOVEL))
                .lang(translation)
                .item().build()
                .register();
    }
}
