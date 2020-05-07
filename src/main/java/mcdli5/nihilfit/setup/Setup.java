package mcdli5.nihilfit.setup;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.NonNullLazyValue;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.item.ItemGroup;

import static mcdli5.nihilfit.NihilFit.MODID;

public final class Setup {
    public static final ItemGroup MOD_GROUP = new ModGroup(MODID);

    private static final NonNullLazyValue<Registrate> REGISTRATE = new NonNullLazyValue<Registrate>(
        () -> Registrate
            .create(MODID)
            .itemGroup(NonNullSupplier.of(() -> MOD_GROUP), "Nihil Fit")
    );

    public static void setup() {
        ModBlocks.setup();
        ModItems.setup();
        ModTiles.setup();
    }

    public static Registrate registrate() {
        return REGISTRATE.getValue();
    }
}
