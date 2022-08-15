package com.baioretto.baiolib.api.persistence.impl;

import com.baioretto.baiolib.api.persistence.IPersistenceDataUtil;
import org.bukkit.craftbukkit.v1_18_R2.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_18_R2.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.persistence.PersistentDataContainer;

public class v1_18_R2 implements IPersistenceDataUtil {
    private final CraftPersistentDataTypeRegistry registry = new CraftPersistentDataTypeRegistry();

    @Override
    public PersistentDataContainer createPersistentDataContainer() {
        return new CraftPersistentDataContainer(registry);
    }
}
