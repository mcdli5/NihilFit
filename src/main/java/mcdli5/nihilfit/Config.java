package mcdli5.nihilfit;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

import static mcdli5.nihilfit.NihilFit.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Bus.FORGE)
public final class Config {
    private static final String CATEGORY_MAIN = "main";
    private static final String SUBCATEGORY_REGISTRY_OPTIONS = "registry";

    private static final Builder COMMON_BUILDER = new Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue USE_JSON_REGISTRIES;
    public static ForgeConfigSpec.IntValue NUMBER_OF_TIMES_TO_TEST_VANILLA_DROPS;

    static {
        COMMON_BUILDER.comment("Main Settings").push(CATEGORY_MAIN);
        setUpMainConfig();
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setUpMainConfig() {
        COMMON_BUILDER.comment("Registry Options").push(SUBCATEGORY_REGISTRY_OPTIONS);

        USE_JSON_REGISTRIES = COMMON_BUILDER
            .comment("Decides which registry to use.")
            .define("useJSON", false);

        NUMBER_OF_TIMES_TO_TEST_VANILLA_DROPS = COMMON_BUILDER
            .comment("Decides how many times should be tested for drops.")
            .defineInRange("numberOfTimesToTestVanillaDrops", 3, 1, 10);

        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig
            .builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }
}
