package activities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

// The Test class should start or end with "Test"
public class Activity1 {

	static ArrayList<String> list;
	
	@BeforeAll
    public static void setUp() {
        list = new ArrayList<String>();
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }
	
	@Test
	public void insertTest()
	{
		assertEquals(2, list.size(), "Wrong size");
		list.add("gama");
		assertEquals(3, list.size(), "Wrong size");
		
		assertEquals("alpha", list.get(0), "Wrong element");
		assertEquals("beta", list.get(1), "Wrong element");
		assertEquals("gama", list.get(2), "Wrong element");
		
	}
	
	@Test
	public void replaceTest()
	{
		list.set(1, "charle");
		
		assertEquals(3, list.size(), "Wrong size");
		
		assertEquals("alpha", list.get(0), "Wrong element");
		assertEquals("charle", list.get(1), "Wrong element");
		assertEquals("gama", list.get(2), "Wrong element");
		
	}
}
