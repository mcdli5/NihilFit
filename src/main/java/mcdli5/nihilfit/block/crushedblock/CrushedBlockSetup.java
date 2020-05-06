package mcdli5.nihilfit.block.crushedblock;

import com.tterrag.registrate.util.RegistryEntry;
import mcdli5.nihilfit.setup.ModSetup;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public final class CrushedBlockSetup {
    public static RegistryEntry<FallingBlock> registerCrushedBlock(String name) {
        String registryName = "crushedblock_" + name.toLowerCase().replace(' ', '_');

        SoundType soundType = name.equals("Dust") ? SoundType.CLOTH : SoundType.GROUND;

        String lang = name.equals("Dust") ? (name + " Block") : ("Crushed " + name);

        return ModSetup.registrate()
            .block(registryName, Material.SAND, FallingBlock::new)
            .properties(properties -> properties
                .hardnessAndResistance(0.7F)
                .sound(soundType)
                .harvestTool(ToolType.SHOVEL))
            .lang(lang)
            .item().build()
            .register();
    }
}
