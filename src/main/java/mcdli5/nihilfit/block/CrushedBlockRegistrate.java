package mcdli5.nihilfit.block;

import com.tterrag.registrate.util.RegistryEntry;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import static mcdli5.nihilfit.setup.ModRegistrate.REGISTRATE;

public class CrushedBlockRegistrate {
    public static final RegistryEntry<FallingBlock> DUST_BLOCK
            = registerFallingBlock("dust_block", SoundType.CLOTH);
    public static final RegistryEntry<FallingBlock> CRUSHED_NETHERRACK
            = registerFallingBlock("crushed_netherrack");
    public static final RegistryEntry<FallingBlock> CRUSHED_ENDSTONE
            = registerFallingBlock("crushed_endstone");
    public static final RegistryEntry<FallingBlock> CRUSHED_ANDESITE
            = registerFallingBlock("crushed_andesite");
    public static final RegistryEntry<FallingBlock> CRUSHED_DIORITE
            = registerFallingBlock("crushed_diorite");
    public static final RegistryEntry<FallingBlock> CRUSHED_GRANITE
            = registerFallingBlock("crushed_granite");

    public static void init() {
    }

    private static RegistryEntry<FallingBlock> registerFallingBlock(String name) {
        return registerFallingBlock(name, SoundType.GROUND);
    }

    private static RegistryEntry<FallingBlock> registerFallingBlock(String name, SoundType sound) {
        return REGISTRATE.block(name, Material.SAND, FallingBlock::new)
                .properties(properties -> properties
                        .hardnessAndResistance(0.7F)
                        .sound(sound)
                        .harvestTool(ToolType.SHOVEL))
                .defaultLang()
                .item().build()
                .register();
    }
}
