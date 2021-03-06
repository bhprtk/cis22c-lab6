
/**
 * HashTableTest.java
 * @author Naqib Khan
 * @author Pratik Bhandari 
 * CIS 22C, Lab 6
 */
public class HashTableTest {
	public static void main(String[] args) {
		System.out.println("********************************** Constructor, put(), toString() \n");

		HashTable<Integer> H1 = new HashTable<>(10);
		HashTable<Integer> H1_empty = new HashTable<>(10);
		H1.put(88);
		H1.put(7);
		H1.put(42);
		H1.put(59);
		H1.put(13);
		H1.put(6);
		H1.put(8);
		H1.put(78);

		System.out.println("Should print error: ");
		try {
			H1.put(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

		System.out.println(H1.toString());
		System.out.println(H1_empty.toString());

		System.out.println("********************************************************* printTable()\n");
		H1.printTable();
		System.out.println("**************************************************************************");
		H1_empty.printTable();

		System.out.println("********************************************************* countBucket()\n");
		System.out.println("Should print 1: " + H1.countBucket(2));
		System.out.println("Should print 3: " + H1.countBucket(8));
		System.out.println("Should print error: ");
		try {
			H1.countBucket(13);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		System.out.println("********************************************************* getNumElements()\n");
		System.out.println("Should print 8: " + H1.getNumElements());
		System.out.println("Should print 0: " + H1_empty.getNumElements());
		System.out.println();

		System.out.println("***************************************************************** get()\n");
		System.out.println("Should print 42: " + H1.get(42));
		System.out.println("Should print null: " + H1_empty.get(42));
		System.out.println("Should print error: ");
		try {
			H1.get(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		System.out.println("*************************************************************** contains()\n");
		System.out.println("Should print true: " + H1.contains(42));
		System.out.println("Should print false: " + H1_empty.contains(42));
		System.out.println("Should print error: ");
		try {
			H1.contains(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();

		System.out.println("******************************************************** printBucket(), remove()\n");
		System.out.println("Should print 88 8 78");
		H1.printBucket(8);
		H1.remove(8);
		H1.remove(78);
		System.out.println("Should print 88");
		H1.printBucket(8);

		System.out.println("Should print error: ");
		try {
			H1.remove(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println("****************************************************************** clear()\n");

		H1.clear();
		H1_empty.clear();
		H1.printTable();
		System.out.println("**************************************************************************\n");
		H1_empty.printTable();

		System.out.println("*************************************************************** End of HashTableTest");
	}
}
