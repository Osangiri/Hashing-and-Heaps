
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;
import java.lang.*;
/**
 * JUnit Tests for Chapter 18
 */
import java.util.*;
public class Ch18Test {
	
	private HashIntSet Set1;
    private HashIntSet Set2;
    private HashIntSet Expected;
    private HashIntSet HashSet1;
    private PriorityQueue<Integer> empty;
    private PriorityQueue<Integer> pq;
    private PriorityQueue<Integer> pq2;
	private PriorityQueue<Integer> pq3;

	/**
	 * Reset the base data structures just in case
	 */
	@BeforeEach 
	void reset(){
		Set1= new HashIntSet();
        Set1.add(-2);
        Set1.add(3);
        Set1.add(5);
        Set1.add(6);
        Set1.add(8);
		Set2 = new HashIntSet();
        Set2.add(2);
        Set2.add(3);
        Set2.add(6);
        Set2.add(8);
        Set2.add(11);
        
        
        HashSet1 = new HashIntSet();
        empty = new PriorityQueue<>();
		pq = new PriorityQueue<>();
        pq.add(7);
        pq.add(8);
        pq.add(10);
        pq.add(45);
        pq2 = new PriorityQueue<>();
        pq2.add(7);
        pq2.add(8);
        pq2.add(9);
        pq2.add(10);
        pq3 = new PriorityQueue<>();
        pq3.add(45);
        
		
	}
	
	
	/**
	 * Tests Equals method
	 */
	 @Test
	 public void testEquals(){
		assertEquals(HashSet1, HashSet1); 
		 
	 }
	 /**
	  * 
	  * Test  retainAll method
	  */
	 @Test
	 public void testretainAll(){
		Set1.retainAll(Set2); 
		assertEquals("[3, 6, 8]", Set1.toString(), "It should retain only the numbers in both sets");
		assertTrue(Set1.contains(3));
        assertTrue(Set1.contains(6));
        assertTrue(Set1.contains(8));
        assertFalse(Set1.contains(-2));
        assertFalse(Set1.contains(5));
        assertEquals(3, Set1.size());
		 
	 }  
	 /**
	  * 
	  * Test  isConsecutive method
	  */
	 @Test
	 public void testisConsecutive(){
		 assertFalse(HashSet1.isConsecutive(pq), "should return a False because numbers are not consecutive");
		 assertTrue(HashSet1.isConsecutive(pq2), "should return a true because numbers are consecutive");
		 pq3 = new PriorityQueue<>();
		 pq3.add(1);
		 assertTrue(HashSet1.isConsecutive(pq3), "should return a true because because there is single element");
		 assertTrue(HashSet1.isConsecutive(empty), "should return a true because because there is no element");
	 }
	 /**
	  * Test stutter method
	  * 
	  */
	 @Test
	 public void testStutter(){
		HashSet1.stutter(pq);
		assertEquals( "[7, 7, 8, 8, 10, 10, 45, 45]",pq.toString(), "should double each integer"); 
        HashSet1.stutter(pq3);
        assertEquals( "[45, 45]",pq3.toString(), "should double each integer");
        HashSet1.stutter(empty);
        assertEquals( "[]",empty.toString(), "should remain the same for an empty queue"); 
		 
	 }
}

