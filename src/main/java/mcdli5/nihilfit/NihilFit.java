package mcdli5.nihilfit;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.NonNullLazyValue;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.client.ClientSetup;
import mcdli5.nihilfit.init.NF_Blocks;
import mcdli5.nihilfit.init.NF_Items;
import mcdli5.nihilfit.init.NF_Tiles;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(NihilFit.NF_ID)
public final class NihilFit {
    public static final String NF_ID = "nihilfit";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup NF_GROUP = new NF_Group(NF_ID);

    private static final NonNullLazyValue<Registrate> REGISTRATE = new NonNullLazyValue<Registrate>(
        () -> Registrate
            .create(NF_ID)
            .itemGroup(NonNullSupplier.of(() -> NF_GROUP), "Nihil Fit")
    );

    public NihilFit() {
        final Marker marker = MarkerManager.getMarker("LOADING");
        LOGGER.debug(marker, "Hello from Nihil Fit!");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        NF_Blocks.setup();
        NF_Items.setup();
        NF_Tiles.setup();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::setup);
    }

    public static Registrate registrate() {
        return REGISTRATE.getValue();
    }

    final static class NF_Group extends ItemGroup {
        public NF_Group(String label) {
            super(label);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(NF_Items.HAMMER_DIAMOND.get());
        }
    }
}
