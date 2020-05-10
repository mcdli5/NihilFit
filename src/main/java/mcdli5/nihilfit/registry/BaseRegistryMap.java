package mcdli5.nihilfit.registry;

import java.util.HashMap;

public abstract class BaseRegistryMap<K, V> {
    protected final HashMap<K, V> registry = new HashMap<>();

    protected void register(K key, V value) {
        registry.put(key, value);
    }
}
