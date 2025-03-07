
/**
 * Implements a set of integers using a hash table.
 * The hash table uses separate chaining to resolve collisions.
 * 
 * @see <a href="https://www.buildingjavaprograms.com/code-files/5ed/ch18/HashIntSet.java">original source code</a>
 */
 import java.util.*;
public class HashIntSet {
	private static final double MAX_LOAD_FACTOR = 0.75;
	private HashEntry[] elementData;
	private int size;
	
	/**
	 * Constructs an empty set.
	 */
	public HashIntSet() {
		elementData = new HashEntry[10];
		size = 0;
	}
	
	/**
	 * Adds the given element to this set, if it was not already
	 * contained in the set.
	 * 
	 * @param value The value to add
	 */
	public void add(int value) {
		if (!contains(value)) {
			if (loadFactor() >= MAX_LOAD_FACTOR) {
				rehash();
			}
			
			// insert new value at front of list
			int bucket = hashFunction(value);
			elementData[bucket] = new HashEntry(value, elementData[bucket]);
			size++;
		}
	}
	
	/**
	 *  Removes all elements from the set.
	 */
	public void clear() {
		for (int i = 0; i < elementData.length; i++) {
			elementData[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Checks if the set contains a value
	 * 
	 * @param value the value in question
	 * @return true if the given value is found in this set
	 */
	public boolean contains(int value) {
		int bucket = hashFunction(value);
		HashEntry current = elementData[bucket];
		while (current != null) {
			if (current.data == value) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	/**
	 * Checks if the set is empty
	 * 
	 * @return true if there are no elements in this queue.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Removes the given value if it is contained in the set.
	 * If the set does not contain the value, has no effect.
	 * 
	 * @param value the value in question
	 */
	public void remove(int value) {
		int bucket = hashFunction(value);
		if (elementData[bucket] != null) {
			// check front of list
			if (elementData[bucket].data == value) {
				elementData[bucket] = elementData[bucket].next;
				size--;
			} else {
				// check rest of list
				HashEntry current = elementData[bucket];
				while (current.next != null && current.next.data != value) {
					current = current.next;
				}
				
				// if the element is found, remove it
				if (current.next != null && current.next.data == value) {
					current.next = current.next.next;
					size--;
				}
			}
		}
	}
	
	/**
	 * Gives the set's size
	 * 
	 * @return the number of elements in the queue.
	 */
	public int size() {
		return size;
	}
	/**
	 * 
	 * Compares this Hashset to another Hashset for equality
	 * Returns true if two sets contain exact elements
	 * @param other the other Hashset to compare
	 * @return true id the sets are equal and false otherwise
	 */
	 public boolean equals(HashIntSet other){
		 if (this == other){
			return true; 
		 }
		 if(other == null || this.size != other.size){
			 return false;
	    }
	    for (int i = 0; i < elementData.length; i++) {
        HashEntry current = elementData[i];
        while (current != null) {
            if (!other.contains(current.data)) {
                return false; // element in this set is not in the other set
            }
            current = current.next;
        }
    }
    return true; // all elements match
			 
		 
		 
	 }
	/**
	 * retainAll method
	 * @param other the other hashIntSet to compare with
	 * 
	 */
	 public void retainAll(HashIntSet other){
		  
		for (int i = 0; i < elementData.length; i++) {
			HashEntry current = elementData[i];
			while (current != null) {
				if (!other.contains(current.data)) {
					remove(current.data);//remove elements if not in other data set
				}
				current = current.next;
			}
		}
	}
		 
	 
	 /**
	  * isConsecutive method
	  * @param pq the PriorityQueue of integers
      * @return true if the integers are consecutive, false otherwise
	  * 
	  */
	  public boolean isConsecutive(PriorityQueue<Integer> pq){
		  if (pq.isEmpty() || pq.size() == 1) {
            return true;
        }
			Stack<Integer> stack = new Stack<>();//an auxiliary stack to restore the queue
			Integer previous = null;
			boolean isConsecutive = true;

			while (!pq.isEmpty()) {
				int current = pq.poll();
				
				
				if (previous != null && current != previous + 1) {
					isConsecutive = false;
				}
				
				// Store the element in the stack for restoration
				stack.push(current);
				previous = current;
	}
			while (!stack.isEmpty()) {//restore the queue
				pq.add(stack.pop());
			}

			return isConsecutive;
		}
		  
	  /**
	   * stutter method
	   * @param pq PriorityQueue<Integer>
	   */
	   public void stutter(PriorityQueue<Integer> pq){
		  Queue<Integer> tempQueue = new LinkedList<>();

       
			while (!pq.isEmpty()) {
				int value = pq.poll(); // Remove the smallest element

				tempQueue.add(value);//add two occurrences of value
				tempQueue.add(value);
			}

			// Restore elements from tempQueue back to the priority queue
			while (!tempQueue.isEmpty()) {
				pq.add(tempQueue.poll());
			}
		}  
	   
	
	/**
	 * Create a logical representation of the contents of the set
	 * @return a string representation of this set, such as "[10, 20, 30]". The elements are not guaranteed to be listed in sorted order or the same order for equal Sets.
	 */
	public String toString() {
		String result = "[";
		boolean first = true;
		if (!isEmpty()) {
			for (int i = 0; i < elementData.length; i++) {
				HashEntry current = elementData[i];
				while (current != null) {
					if (!first) {
						result += ", ";
					}
					result += current.data;
					first = false;
					current = current.next;
				}
			}
		}
		return result + "]";
	}
	
	
	// Returns the preferred hash bucket index for the given value.
	private int hashFunction(int value) {
		return Math.abs(value) % elementData.length;
	}
	
	private double loadFactor() {
		return (double) size / elementData.length;
	}
	
	// Resizes the hash table to twice its former size.
	private void rehash() {
		// replace element data array with a larger empty version
		HashEntry[] oldElementData = elementData;
		elementData = new HashEntry[2 * oldElementData.length];
		size = 0;

		// re-add all of the old data into the new array
		for (int i = 0; i < oldElementData.length; i++) {
			HashEntry current = oldElementData[i];
			while (current != null) {
				add(current.data);
				current = current.next;
			}
		}
	}
	
	// Represents a single value in a chain stored in one hash bucket.
	private class HashEntry {
		public int data;
		public HashEntry next;

		public HashEntry(int data) {
			this(data, null);
		}

		public HashEntry(int data, HashEntry next) {
			this.data = data;
			this.next = next;
		}
	}
}
