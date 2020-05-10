package mcdli5.nihilfit.registry;

import mcdli5.nihilfit.item.tool.hammer.HammerRegistry;

public final class NF_Registry {
    public static HammerRegistry HAMMER_REGISTRY;

    static {
        try {
            HAMMER_REGISTRY = new HammerRegistry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
