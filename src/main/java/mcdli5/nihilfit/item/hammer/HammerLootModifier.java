package mcdli5.nihilfit.item.hammer;

import com.google.gson.JsonObject;
import mcdli5.nihilfit.block.CrushedBlockRegistrate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

import static mcdli5.nihilfit.NihilFit.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class HammerLootModifier {
    @SubscribeEvent
    public static void registerModifierSerializers(
            @Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {

        event.getRegistry().register(
                new LootModifier.Serializer().setRegistryName(
                        new ResourceLocation(MODID, "broken_by_hammer")
                )
        );
    }

    private static class LootModifier extends net.minecraftforge.common.loot.LootModifier {
        protected LootModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        private static ItemStack setReward(ItemStack itemStack) {
            int count = itemStack.getCount();

            if (itemStack.getItem() == Items.COBBLESTONE) {
                return new ItemStack(Items.GRAVEL, count);
            } else if (itemStack.getItem() == Items.GRAVEL) {
                return new ItemStack(Items.SAND, count);
            } else if (itemStack.getItem() == Items.SAND) {
                return new ItemStack(CrushedBlockRegistrate.DUST_BLOCK.get(), count);
            } else if (itemStack.getItem() == Items.NETHERRACK) {
                return new ItemStack(CrushedBlockRegistrate.CRUSHED_NETHERRACK.get(), count);
            } else if (itemStack.getItem() == Items.END_STONE) {
                return new ItemStack(CrushedBlockRegistrate.CRUSHED_ENDSTONE.get(), count);
            } else if (itemStack.getItem() == Items.ANDESITE) {
                return new ItemStack(CrushedBlockRegistrate.CRUSHED_ANDESITE.get(), count);
            } else if (itemStack.getItem() == Items.DIORITE) {
                return new ItemStack(CrushedBlockRegistrate.CRUSHED_DIORITE.get(), count);
            } else if (itemStack.getItem() == Items.GRANITE) {
                return new ItemStack(CrushedBlockRegistrate.CRUSHED_GRANITE.get(), count);
            } else {
                return itemStack;
            }
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            return generatedLoot.stream().map(LootModifier::setReward).collect(Collectors.toList());
        }

        private static class Serializer extends GlobalLootModifierSerializer<LootModifier> {
            @Override
            public LootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
                return new LootModifier(ailootcondition);
            }
        }
    }
}
