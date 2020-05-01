package mcdli5.nihilfit.item.hammer;

import com.google.gson.JsonObject;
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
import static mcdli5.nihilfit.block.Blocks.*;

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
                return new ItemStack(CRUSHEDBLOCK_DUST.get(), count);
            } else if (itemStack.getItem() == Items.ANDESITE) {
                return new ItemStack(CRUSHEDBLOCK_ANDESITE.get(), count);
            } else if (itemStack.getItem() == Items.DIORITE) {
                return new ItemStack(CRUSHEDBLOCK_DIORITE.get(), count);
            } else if (itemStack.getItem() == Items.END_STONE) {
                return new ItemStack(CRUSHEDBLOCK_END_STONE.get(), count);
            } else if (itemStack.getItem() == Items.GRANITE) {
                return new ItemStack(CRUSHEDBLOCK_GRANITE.get(), count);
            } else if (itemStack.getItem() == Items.NETHERRACK) {
                return new ItemStack(CRUSHEDBLOCK_NETHERRACK.get(), count);
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
