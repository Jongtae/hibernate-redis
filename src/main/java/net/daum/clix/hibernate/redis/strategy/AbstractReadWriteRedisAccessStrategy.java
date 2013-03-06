package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisTransactionalRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

import java.io.Serializable;
import java.util.Comparator;


/**
 * Superclass for all Redis specific Hibernate AccessStrategy implementations.
 *
 * @param <T> type of the enclosed region
 * @author 84june
 */
abstract class AbstractReadWriteRedisAccessStrategy<T extends RedisTransactionalRegion> extends AbstractRedisAccessStrategy<T> {

    private final Comparator versionComparator;

    /**
     * Create an access strategy wrapping the given region.
     */
    AbstractReadWriteRedisAccessStrategy(T region, Settings settings) {
        super(region, settings);
        this.versionComparator = region.getCacheDataDescription().getVersionComparator();
    }

    public Object get(Object key, long txTimestamp) throws CacheException {
        Item item = (Item) region.get(key);
        if (null != item && item.isReadable(txTimestamp)) {
            return item.getValue();
        }
        return null;
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {

        if (region.writeLock(key)) {
            try {
                if (region.contains(key)) {
                    Item item = (Item) region.get(key);
                    if (!item.isWriteable(version, versionComparator)) {
                        return false;
                    }
                }
                region.put(key, new Item(version, txTimestamp, value));
            } finally {
                region.releaseLock(key);
            }
            return true;
        }
        return false;
    }

    /**
     * Returns <code>false</code> since this is a read/write cache access strategy
     */
    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        if (region.writeLock(key)) {
            try {
                Item item = (Item) region.get(key);
                if (null != item && !item.isWriteable(version, versionComparator)) {
                    return false;
                }
                region.put(key, new Item(version, region.nextTimestamp(), value));
            } finally {
                region.releaseLock(key);
            }
            return true;
        }
        return false;
    }

    /**
     * Returns <code>false</code> since this is a read/write cache access strategy
     */
    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
        return false;
    }

    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
        if (region.writeLock(key)) {
            try {
                Item item = (Item) region.get(key);
                if (null != item && !item.isWriteable(currentVersion, versionComparator)) {
                    return false;
                }
                region.put(key, new Item(currentVersion, region.nextTimestamp(), value));
            } finally {
                region.releaseLock(key);
            }
            return true;
        }
        return false;
    }

    protected final static class Item implements Serializable {

        private static final long serialVersionUID = -9173693640486739767L;

        private final Object version;

        private final long txTimestamp;

        private final Object value;

        public Item(Object version, long txTimestamp, Object value) {
            this.version = version;
            this.txTimestamp = txTimestamp;
            this.value = value;
        }

        public boolean isReadable(long txTimestamp) {
            return txTimestamp > this.txTimestamp;
        }

        public boolean isWriteable(Object newVersion, Comparator versionComparator) {
            return version != null && versionComparator.compare(version, newVersion) < 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (txTimestamp != item.txTimestamp) return false;
            if (!value.equals(item.value)) return false;
            if (version != null ? !version.equals(item.version) : item.version != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = version != null ? version.hashCode() : 0;
            result = 31 * result + (int) (txTimestamp ^ (txTimestamp >>> 32));
            result = 31 * result + value.hashCode();
            return result;
        }

        public Object getValue() {
            return value;
        }

    }
}

