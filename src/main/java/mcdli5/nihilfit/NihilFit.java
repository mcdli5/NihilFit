package mcdli5.nihilfit;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.NonNullLazyValue;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import mcdli5.nihilfit.block.crucible.CrucibleBakedModel;
import mcdli5.nihilfit.block.leaves.InfestingLeavesBlock;
import mcdli5.nihilfit.init.NF_Blocks;
import mcdli5.nihilfit.init.NF_Items;
import mcdli5.nihilfit.init.NF_Tiles;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.FoliageColors;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

        NF_Blocks.init();
        NF_Items.init();
        NF_Tiles.init();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    public static Registrate registrate() {
        return REGISTRATE.getValue();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ((FireBlock) Blocks.FIRE).setFireInfo(NF_Blocks.INFESTING_LEAVES.get(), 30, 60);
        ((FireBlock) Blocks.FIRE).setFireInfo(NF_Blocks.INFESTED_LEAVES.get(), 30, 60);

        ComposterBlock.CHANCES.put(NF_Blocks.INFESTING_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.CHANCES.put(NF_Blocks.INFESTED_LEAVES.get().asItem(), 0.3F);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModelLoaderRegistry.registerLoader(
            new ResourceLocation(NF_ID, "crucible_loader"),
            new CrucibleBakedModel.CrucibleModelLoader()
        );

        RenderTypeLookup.setRenderLayer(NF_Blocks.INFESTING_LEAVES.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(NF_Blocks.INFESTED_LEAVES.get(), RenderType.getCutoutMipped());

        BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
        ItemColors itemcolors = Minecraft.getInstance().getItemColors();

        blockcolors.register(
            (state, world, pos, tintIndex) -> {
                if (world == null || pos == null) {
                    return FoliageColors.getDefault();
                } else {
                    return InfestingLeavesBlock.COLOR_MAP[state.get(InfestingLeavesBlock.INFESTING_STAGE)];
                }
            },
            NF_Blocks.INFESTING_LEAVES.get()
        );

        itemcolors.register(
            (stack, tintIndex) -> FoliageColors.getDefault(),
            NF_Blocks.INFESTING_LEAVES.get()
        );
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
