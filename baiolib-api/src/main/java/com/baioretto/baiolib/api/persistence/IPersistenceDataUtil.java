package com.baioretto.baiolib.api.persistence;

import org.bukkit.persistence.PersistentDataContainer;

/**
 * Persistence Data Util
 *
 * @author baioretto
 * @since 1.2.0
 */
public interface IPersistenceDataUtil {
    /**
     * Create a new {@code PersistentDataContainer} with default registry.
     *
     * @return {@link PersistentDataContainer}
     */
    PersistentDataContainer createPersistentDataContainer();
}
