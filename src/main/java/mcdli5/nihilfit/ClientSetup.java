package mcdli5.nihilfit;

import mcdli5.nihilfit.block.crucible.CrucibleBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static mcdli5.nihilfit.NihilFit.NF_ID;
import static net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus.MOD;

@Mod.EventBusSubscriber(modid = NF_ID, value = Dist.CLIENT, bus = MOD)
public final class ClientSetup {
    public static void setup(final FMLClientSetupEvent event) {
        ModelLoaderRegistry.registerLoader(
            new ResourceLocation(NF_ID, "crucible_loader"),
            new CrucibleBakedModel.CrucibleModelLoader()
        );
    }
}
