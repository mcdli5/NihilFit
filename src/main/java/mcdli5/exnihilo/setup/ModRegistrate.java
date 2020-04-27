package mcdli5.exnihilo.setup;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.exnihilo.block.CrushedBlockRegistrate;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static mcdli5.exnihilo.ExNihilo.MOD_ID;
import static mcdli5.exnihilo.block.CrushedBlockRegistrate.DUST_BLOCK;

public final class ModRegistrate {
    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(DUST_BLOCK.get());
        }
    };

    public static final Registrate REGISTRATE =
            Registrate.create(MOD_ID).itemGroup(NonNullSupplier.of(() -> ITEM_GROUP), "Ex Nihilo");

    public static void init() {
        CrushedBlockRegistrate.init();
    }

    public static String buildFullName(String prefix, @Nonnull String name, String suffix) {
        String fullName = "";

        if (prefix != null && !prefix.isEmpty()) {
            fullName += prefix.toLowerCase() + "_";
        }

        fullName += name.toLowerCase();

        if (suffix != null && !suffix.isEmpty()) {
            fullName += "_" + suffix.toLowerCase();
        }

        return fullName;
    }
}
