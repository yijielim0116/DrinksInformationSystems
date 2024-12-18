import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.example.assignment2demo.ADT.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    private LinkedList testlist;

    @BeforeEach
    void setUp() {
        testlist = new LinkedList();
    }

    @Test
    void testAddElement() {
        LinkedList<String> list = new LinkedList<>();
        list.addElement("First");
        list.addElement("Second");
        list.addElement("Third");

        assertEquals(3, list.listRange());
        assertEquals("Third", list.getElement(0)); // Head element
        assertEquals("Second", list.getElement(1));
        assertEquals("First", list.getElement(2));
    }

    @Test
    void testDeleteElement() {
        LinkedList<String> list = new LinkedList<>();
        list.addElement("First");
        list.addElement("Second");
        list.addElement("Third");

        assertEquals(3, list.listRange());

        // Delete head element
        list.deleteElement(0);
        assertEquals(2, list.listRange());
        assertEquals("Second", list.getElement(0));

        // Delete middle element
        list.deleteElement(1);
        assertEquals(1, list.listRange());
        assertEquals("Second", list.getElement(0));
    }

    @Test
    void testDeleteEntireList() {
        LinkedList<String> list = new LinkedList<>();
        list.addElement("First");
        list.addElement("Second");
        list.addElement("Third");

        assertFalse(list.isEmpty());
        list.deleteEntireList();
        assertTrue(list.isEmpty());
        assertEquals(0, list.listRange());
    }

    @Test
    void testIsEmpty() {
        LinkedList<String> list = new LinkedList<>();
        assertTrue(list.isEmpty());

        list.addElement("First");
        assertFalse(list.isEmpty());

        list.deleteEntireList();
        assertTrue(list.isEmpty());
    }

    @Test
    void testGetElement() {
        LinkedList<String> list = new LinkedList<>();
        list.addElement("First");
        list.addElement("Second");
        list.addElement("Third");

        assertEquals("Third", list.getElement(0));
        assertEquals("Second", list.getElement(1));
        assertEquals("First", list.getElement(2));

        assertThrows(IndexOutOfBoundsException.class, () -> list.getElement(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.getElement(-1));
    }

    @Test
    void testListRange() {
        LinkedList<String> list = new LinkedList<>();
        assertEquals(0, list.listRange());

        list.addElement("First");
        assertEquals(1, list.listRange());

        list.addElement("Second");
        list.addElement("Third");
        assertEquals(3, list.listRange());
    }

    @Test
    void testToString() {
        LinkedList<String> list = new LinkedList<>();
        assertEquals("LinkedList: null", list.toString());

        list.addElement("First");
        list.addElement("Second");
        list.addElement("Third");
        assertEquals("LinkedList: Third -> Second -> First -> null", list.toString());
    }


}
