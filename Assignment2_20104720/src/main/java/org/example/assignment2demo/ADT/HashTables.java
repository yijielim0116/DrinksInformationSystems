package org.example.assignment2demo.ADT;

public class HashTables<K,V> {
    private static final int TABLE_SIZE = 10; // Size of the hash table
    private LinkedList<Entry<K, V>>[] table;  // Array of LinkedLists for collision handling

    // Constructor: Initialize hash table and buckets
    public HashTables() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // Hash function to compute the bucket index
    private int hash(K key) {
        return Math.abs(key.hashCode() % TABLE_SIZE);
    }

    // Add a key-value pair to the hash table
    public void put(K key, V value) {
        int index = hash(key);                       // Compute bucket index
        LinkedList<Entry<K, V>> bucket = table[index];
        LinkedNode<Entry<K, V>> current = bucket.getHead();

        while (current != null) {
            if (current.getContents().key.equals(key)) {
                current.getContents().value = value;  // Update value if key exists
                return;
            }
            current = current.next;
        }

        bucket.addElement(new Entry<>(key, value));   // Add new key-value pair
    }

    // Get the value for a given key
    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        LinkedNode<Entry<K, V>> current = bucket.getHead();

        while (current != null) {
            if (current.getContents().key.equals(key)) {
                return current.getContents().value;  // Return value if key matches
            }
            current = current.next;
        }
        return null; // Key not found
    }

    // Remove a key-value pair
    public void remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        LinkedNode<Entry<K, V>> current = bucket.getHead();
        LinkedNode<Entry<K, V>> prev = null;

        while (current != null) {
            if (current.getContents().key.equals(key)) {
                if (prev == null) {
                    bucket.head = current.next; // Remove head node
                } else {
                    prev.next = current.next;   // Remove middle or last node
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    // Display hash table contents (for debugging)
    public void display() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            System.out.print("Bucket " + i + ": ");
            LinkedList<Entry<K, V>> bucket = table[i];
            LinkedNode<Entry<K, V>> current = bucket.getHead();

            while (current != null) {
                System.out.print("[" + current.getContents().key + ": " + current.getContents().value + "] -> ");
                current = current.next;
            }
            System.out.println("null");
        }
    }

    // Nested static class to store key-value pairs
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
