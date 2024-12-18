package org.example.assignment2demo.ADT;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class LinkedList<B>{
    public LinkedNode<B> head = null; // The head node of the linked list

    // Calculate the number of elements in the list
    public int listRange() {
        LinkedNode<B> temp = head;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    // Add an element to the end of the list
    public String addElement(B element) {
        LinkedNode<B> newNode = new LinkedNode<>();
        newNode.setContents(element);
        newNode.next = head;
        head = newNode;

        return null;
    }

    // Delete an element by index
    public void deleteElement(int index) {
        if (index < 0 || index >= listRange()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (index == 0) { // Remove the head
            head = head.next;
        } else {
            LinkedNode<B> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next; // Bypass the node to delete
        }
    }

    // Delete the entire list
    public void deleteEntireList() {
        head = null;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Get an element by index
    public B getElement(int index) {
        if (index < 0 || index >= listRange()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        LinkedNode<B> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.contents;
    }

    // Add all elements to a ListView (for JavaFX integration)
    public void addContentsToListView(ListView<B> listView) {
        listView.getItems().clear();
        LinkedNode<B> current = head;
        while (current != null) {
            listView.getItems().add(current.contents);
            current = current.next;
        }
    }

    // Add all elements to a ComboBox (for JavaFX integration)
    public void addContentsToComboBox(ComboBox<B> comboBox) {
        comboBox.getItems().clear();
        LinkedNode<B> current = head;
        while (current != null) {
            comboBox.getItems().add(current.contents);
            current = current.next;
        }
    }

    // Get the head node
    public LinkedNode<B> getHead() {
        return head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LinkedList: ");
        LinkedNode<B> current = head;
        while (current != null) {
            sb.append(current.contents).append(" -> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
