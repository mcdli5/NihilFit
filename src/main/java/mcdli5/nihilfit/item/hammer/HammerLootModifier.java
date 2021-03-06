package mcdli5.nihilfit.item.hammer;

import com.google.gson.JsonObject;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;

import static mcdli5.nihilfit.NihilFit.NF_ID;

@Mod.EventBusSubscriber(modid = NF_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class HammerLootModifier {
    @SubscribeEvent
    public static void registerModifierSerializers(
        @Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {

        event.getRegistry().register(
            new LootModifier.Serializer().setRegistryName(
                new ResourceLocation(NF_ID, "broken_by_hammer")
            )
        );
    }

    private static class LootModifier extends net.minecraftforge.common.loot.LootModifier {
        protected LootModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            final ItemStack tool = context.get(LootParameters.TOOL);
            final int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);

            return HammerRegistry.getDrops(generatedLoot, context.getRandom(), fortuneLevel);
        }

        private static class Serializer extends GlobalLootModifierSerializer<LootModifier> {
            @Override
            public LootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
                return new LootModifier(ailootcondition);
            }
        }
    }
}
