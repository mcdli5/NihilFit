package mcdli5.nihilfit;

import mcdli5.nihilfit.client.ClientSetup;
import mcdli5.nihilfit.config.Config;
import mcdli5.nihilfit.setup.Setup;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(NihilFit.MODID)
public final class NihilFit {
    public static final String MODID = "nihilfit";
    public static final Logger LOGGER = LogManager.getLogger();

    public NihilFit() {
        final Marker marker = MarkerManager.getMarker("LOADING");
        LOGGER.debug(marker, "Hello from Nihil Fit!");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        Setup.setup();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::setup);
    }
}
