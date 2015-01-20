import java.util.LinkedList;
import java.util.Scanner;


public class HashLinkedList {
	static LinkedList<long[]> l1 = new LinkedList<long[]>();

	/**
	 * Will find the element in the list using the key.
	 * @param key
	 * @return = return value corresponding to that key else return 0.
	 */
	public static long find(long key) {
		for (int i = 0; i < l1.size(); i++) {
			long[] temp = l1.get(i);
			if (temp[0] == key) {
				return temp[1] % 997;
			}
		}
		return 0;

	}

	
	/**
	 * The insert function will insert the element in the list.
	 * 
	 * @param key
	 * @param value = returns the value of the key.
	 * @return
	 */
	public static long Insert(long key, long value) {
		for (int i = 0; i < l1.size(); i++) {
			long[] temp = l1.get(i);
			if (temp[0] == key) {
				long oldval = temp[1];
				temp[1] = value;
				l1.add(i, temp);
				return oldval % 997;
			}
		}
		long[] newarr = new long[2];
		newarr[0] = key;
		newarr[1] = value;
		l1.add(newarr);
		return (value % 997);
	}

	/**
	 * The Delete function will delete the element from the list.
	 * @param key
	 * @return = returns value of the key else return 0.
	 */
	public static long Delete(long key) {
		for (int i = 0; i < l1.size(); i++) {
			long[] temp = l1.get(i);
			if (temp[0] == key) {
				long oldval = temp[1];
				l1.remove(i);
				return oldval % 997;
			}
		}
		return 0;
	}

	/**
	 * The main function contains the Scanner class that checks for the input
	 * the from the console and performs the operations.
	 * 
	 * It also gives the time and a value.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		String operation;
		long output = 0;
		String inputs[];
		long start = System.currentTimeMillis();

		while (sc.hasNextLine()) {
			operation = sc.nextLine();
			if (!operation.contains("#")) {
				if (operation.contains("break")) {
					break;
				}

				else if (operation.contains("Insert")) {
					inputs = operation.split(" ");

					output += Insert(Long.parseLong(inputs[1]),
							Long.parseLong(inputs[2]));
				}

				else if (operation.contains("Find")) {

					inputs = operation.split(" ");
					output += find(Long.parseLong(inputs[1]));
				}

				else if (operation.contains("Delete")) {

					inputs = operation.split(" ");
					output += Delete(Long.parseLong(inputs[1]));
				}

			}
		}

		long last = System.currentTimeMillis();
		System.out.println("Running Time: "+(last - start));
		System.out.println("Output: "+(output % 997));

	}

}
