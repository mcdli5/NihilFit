package mcdli5.exnihilo.setup;

import com.tterrag.registrate.Registrate;
import mcdli5.exnihilo.block.fallingblock.FallingBlockRegistrate;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static mcdli5.exnihilo.ExNihilo.MODID;
import static mcdli5.exnihilo.block.fallingblock.FallingBlockRegistrate.DUSTBLOCK;

public class ModRegistrate {
    public static final Registrate REGISTRATE =
            Registrate.create(MODID).itemGroup(ExNihiloItemGroup::new, "Ex Nihilo");

    public static void init() {
        FallingBlockRegistrate.init();
    }

    private static class ExNihiloItemGroup extends ItemGroup {
        public ExNihiloItemGroup() {
            super(MODID);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(DUSTBLOCK.get());
        }
    }
}
