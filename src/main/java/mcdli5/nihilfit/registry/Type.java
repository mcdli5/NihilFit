package mcdli5.nihilfit.registry;

import lombok.Data;
import net.minecraft.item.Item;

public final class Type {
    @Data
    public static class HammerReward {
        private final Item item;
        private final float chance;
        private final float fortuneChance;
    }
}
