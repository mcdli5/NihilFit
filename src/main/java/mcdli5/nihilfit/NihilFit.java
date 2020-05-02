package mcdli5.nihilfit;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.NonNullLazyValue;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.init.ModBlocks;
import mcdli5.nihilfit.init.ModItems;
import mcdli5.nihilfit.init.ModTiles;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(NihilFit.MODID)
public final class NihilFit {
    public static final String MODID = "nihilfit";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.CRUSHEDBLOCK_DUST.get());
        }
    };

    private static final NonNullLazyValue<Registrate> REGISTRATE = new NonNullLazyValue<Registrate>(
        () -> Registrate
            .create(MODID)
            .itemGroup(NonNullSupplier.of(() -> ITEM_GROUP), "Nihil Fit")
    );

    public NihilFit() {
        // TODO: Remove unnecessary usages for release
        LOGGER.debug("Hello from Nihil Fit!");

        ModBlocks.init();
        ModItems.init();
        ModTiles.init();
    }

    public static Registrate registrate() {
        return REGISTRATE.getValue();
    }
}
