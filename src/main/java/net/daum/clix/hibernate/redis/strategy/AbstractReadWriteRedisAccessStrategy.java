package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.region.RedisTransactionalRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        super(region,settings);
        this.versionComparator = region.getCacheDataDescription().getVersionComparator();
    }

    public Object get(Object key, long txTimestamp) throws CacheException {
        return cache.get(key);
    }

    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {

        if (minimalPutOverride && region.contains(key)) {
            return false;
        } else {
            if (region.writeLock(key)) {
                try {
                    cache.put(key, value);
                } finally {
                    region.releaseLock(key);
                }
                return true;
            }
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
                cache.put(key, value);
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
        if (region.writeLock(key)){
            try {
                cache.put(key, value);
            } finally {
                region.releaseLock(key);
            }
            return true;
        }
        return false;
    }

    protected final static class WriteLock implements Serializable {

        private static final long serialVersionUID = -9173693640486739767L;

        private final String expiresStr;

        private final Object version;

        private final long txTimestamp;

        public WriteLock(String expiresStr, Object version, long txTimestamp) {
            this.expiresStr = expiresStr;
            this.version = version;
            this.txTimestamp = txTimestamp;
        }

        public String getExpiresStr() {
            return expiresStr;
        }

        public Object getVersion() {
            return version;
        }

        public long getTxTimestamp() {
            return txTimestamp;
        }

        public boolean isReadable(long txTimestamp) {
            return txTimestamp > this.txTimestamp;
        }

        public boolean isWriteable(Object newVersion, Comparator versionComparator) {
            return version != null && versionComparator.compare( version, newVersion ) < 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WriteLock writeLock = (WriteLock) o;

            if (!expiresStr.equals(writeLock.expiresStr)) return false;
            if (version != null ? !version.equals(writeLock.version) : writeLock.version != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = expiresStr.hashCode();
            result = 31 * result + (version != null ? version.hashCode() : 0);
            return result;
        }
    }
}

