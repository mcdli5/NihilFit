package mcdli5.templatemod.setup;

import com.tterrag.registrate.Registrate;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static mcdli5.templatemod.TemplateMod.MODID;

public class ModRegistrate {
    public static final Registrate REGISTRATE =
            Registrate.create(MODID);
            // TODO: Fix IconGroup
            //.itemGroup(TemplateModItemGroup::new, "Template Mod");

    public static void init() {}

    private static class TemplateModItemGroup extends ItemGroup {
        public TemplateModItemGroup() {
            super(MODID);
        }

        // TODO: Fix createIcon
        @Override
        public ItemStack createIcon() {
            return null;
        }
    }
}
