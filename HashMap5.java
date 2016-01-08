import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashSet;

/**
 * @author Riley Auten
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {
        table = new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        int code;
        int pos;
        V val = null;
        if (key == null || value == null) {
            throw new IllegalArgumentException("Invalid entry");
        }
        if ((size + 1) / (double) table.length >= MAX_LOAD_FACTOR) {
            MapEntry<K, V>[] other = new MapEntry[(2 * table.length) + 1];
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    if (table[i].getNext() == null) {
                        K tableKey = table[i].getKey();
                        V tableValue = table[i].getValue();
                        code = Math.abs(tableKey.hashCode());
                        pos = code % other.length;
                        if (other[pos] == null) {
                            other[pos] =
                                new MapEntry<K, V>(tableKey, tableValue);
                        } else {
                            MapEntry temp = other[pos];
                            while (temp.getNext() != null) {
                                temp = temp.getNext();
                            }
                            temp.setNext(
                                new MapEntry<K, V>(tableKey, tableValue));
                        }
                    } else {
                        MapEntry entry = table[i];
                        boolean truth = true;
                        while (entry.getNext() != null) {
                            K tableKey = (K) entry.getKey();
                            code = Math.abs(tableKey.hashCode());
                            pos = code % other.length;
                            if (other[pos] == null) {
                                other[pos] = new MapEntry(
                                    (K) entry.getKey(), (V) entry.getValue());
                            } else {
                                MapEntry temp = other[pos];
                                while (temp.getNext() != null) {
                                    temp = temp.getNext();
                                }
                                temp.setNext(
                                    new MapEntry((K) entry.getKey(),
                                        (V) entry.getValue()));
                            }
                            entry = entry.getNext();
                        }
                        K tableKey = (K) entry.getKey();
                        code = Math.abs(tableKey.hashCode());
                        pos = code % other.length;
                        MapEntry temp = other[pos];
                        if (other[pos] != null) {
                            while (temp != entry && temp.getNext() != null) {
                                temp = temp.getNext();
                            }
                            temp.setNext(
                                new MapEntry(
                                    (K) entry.getKey(), (V) entry.getValue()));
                        } else {
                            other[pos] = new MapEntry(
                                (K) entry.getKey(), (V) entry.getValue());
                        }
                    }
                }
            }
            table = other;
        }
        pos = Math.abs(key.hashCode() % table.length);
        if (table[pos] == null) {
            table[pos] = new MapEntry(key, value);
            size++;
        } else if (contains(key)) {
            code = Math.abs(key.hashCode());
            pos = code % table.length;
            if (table[pos].getKey().equals(key)) {
                val = table[pos].getValue();
                table[pos].setValue(value);
                return val;
            } else {
                MapEntry temp = table[pos];
                while (temp.getNext() != null) {
                    if (temp.getNext().getKey().equals(key)) {
                        val = (V) temp.getNext().getValue();
                        temp.getNext().setValue(value);
                        return val;
                    }
                    temp = temp.getNext();
                }
            }
        } else {
            code = Math.abs(key.hashCode());
            pos = code % table.length;

            MapEntry temp = table[pos];
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(new MapEntry<K, V>(key, value));
            size++;
            return null;
        }
        return null;
    }


    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        int code;
        int pos;
        V val = null;
        code = Math.abs(key.hashCode());
        pos = code % table.length;
        if (table[pos] != null) {
            if (table[pos].getNext() == null) {
                val = table[pos].getValue();
                table[pos] = null;
                size--;
                return val;
            } else {
                MapEntry temp = table[pos];
                if (table[pos].getKey().equals(key)) {
                    val = table[pos].getValue();
                    table[pos] = table[pos].getNext();
                    size--;
                    return val;
                } else {
                    while (temp.getNext() != null) {
                        if (temp.getNext().getKey().equals(key)
                            && temp.getNext().getNext() != null) {
                            val = (V) temp.getNext().getValue();
                            temp.setNext(temp.getNext().getNext());
                            size--;
                            return val;
                        } else if (temp.getNext().getKey().equals(key)
                            && temp.getNext().getNext() == null) {
                            val = (V) temp.getNext().getValue();
                            temp.setNext(null);
                            size--;
                            return val;
                        }
                        temp = temp.getNext();
                    }
                }
            }
        }
        throw new NoSuchElementException("Key doesn't exist");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Your key is null");
        }
        int code = Math.abs(key.hashCode());
        int pos = code % table.length;
        V val = null;
        if (table[pos] != null) {
            if (table[pos].getKey().equals(key)) {
                return table[pos].getValue();
            } else {
                MapEntry temp = table[pos];
                while (temp.getNext() != null) {
                    if (temp.getNext().getKey().equals(key)) {
                        val = (V) temp.getNext().getValue();
                        return val;
                    } else {
                        temp = temp.getNext();
                    }
                }
            }
        } else if (val == null) {
            throw new NoSuchElementException("Your entry is not in the map");
        }
        return val;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid key");
        }
        int code;
        int pos;
        code = Math.abs(key.hashCode());
        pos = code % table.length;
        if (table[pos] != null) {
            if (table[pos].getKey().equals(key)) {
                return true;
            } else {
                MapEntry temp = table[pos];
                while (temp.getNext() != null) {
                    if (temp.getNext().getKey().equals(key)) {
                        return true;
                    } else {
                        temp = temp.getNext();
                    }

                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        table = new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                keys.add(table[i].getKey());
                MapEntry temp = table[i];
                while (temp.getNext() != null) {
                    keys.add((K) temp.getNext().getKey());
                    temp = temp.getNext();
                }
            }
        }
        return keys;
    }

    @Override
    public List<V> values() {
        List<V> vals = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                vals.add(table[i].getValue());
                MapEntry temp = table[i];
                while (temp.getNext() != null) {
                    vals.add((V) temp.getNext().getValue());
                    temp = temp.getNext();
                }
            }
        }
        return vals;
    }

    /**
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] toArray() {
        return table;
    }

}
