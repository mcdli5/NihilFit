package mcdli5.exnihilo.setup;

import com.tterrag.registrate.Registrate;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static mcdli5.exnihilo.ExNihilo.MODID;

public class ModRegistrate {
    public static final Registrate REGISTRATE =
            Registrate.create(MODID);
            // TODO: Fix IconGroup
            //.itemGroup(ExNihiloItemGroup::new, "Ex Nihilo");

    public static void init() {}

    private static class ExNihiloItemGroup extends ItemGroup {
        public ExNihiloItemGroup() {
            super(MODID);
        }

        // TODO: Fix createIcon
        @Override
        public ItemStack createIcon() {
            return null;
        }
    }
}
