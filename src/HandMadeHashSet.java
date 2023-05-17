import java.util.List;
import java.util.Objects;


public class HandMadeHashSet<V> {
    private int size = 0;
    private HashSetBucket[] buckets;
    private final int CAPACITY = 10;

    public HandMadeHashSet() {
        this.buckets = new HashSetBucket[CAPACITY];
    }

    private static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    private int getHash(V value) {
        return (isNull(value) ? 0 : value.hashCode() & 0xfffffff) % CAPACITY;
    }

    private V put(V value) {
        if (contains(value)) {
            return null;
        } else {
            int hash = getHash(value);
            if (isNull(buckets[hash])) {
                buckets[hash] = new HashSetBucket();
            }
            buckets[hash].addEntry(new ValueEntry(value));
            size++;
            return value;
        }
    }

    public boolean add(V value) {
        return put(value) != null;
    }

    private V delete(V value) {
        if (contains(value)) {
            V temp = get(value);
            int hash = getHash(value);
            buckets[hash].removeEntry(getEntry(value));
            size--;
            return temp;
        }
        return null;
    }

    public boolean remove(V value) {
        return delete(value) != null;
    }

    public V get(V value) {
        return contains(value) ? (V) getEntry(value).getValue() : null;
    }

    public boolean contains(V value) {
        int hash = getHash(value);
        return !(isNull(buckets[hash]) || isNull(getEntry(value)));
    }

    private ValueEntry getEntry(V value) {
        int hash = getHash(value);
        List<ValueEntry> entries = buckets[hash].getEntries();
        for (int i = 0; i < entries.size(); i++) {
            ValueEntry valueEntry = entries.get(i);
            if (valueEntry.getValue().equals(value)) return valueEntry;
        }
        return null;
    }

    public int size() {
        return size;
    }
}
