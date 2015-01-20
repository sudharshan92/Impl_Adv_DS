package hashing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class 2-ChoiceHashing {

	static LinkedList<long[]>[] hasharray = new LinkedList[9];

	static int maxBucketSize;
	static int count = 0;
	static int bucketsize = 0;
	static int[] bucketSizeArray = new int[9];

	/**
	 * In this function two index values will be calculated from the given key.
	 * Once both the index values are found, it checks whether the first index
	 * is null or not and if the index is null it will insert the new element to
	 * that index and return the value else it will check for the second index
	 * value and check whether it is null or not and if it is null it will
	 * insert the element at that index and return the value. The function
	 * checks for the size of both index before the inserting the element. It
	 * will also check whether the key is already present in the list and if it
	 * is, it will replace the old value with new value and return old value. If
	 * the key is not present in the list it will insert the new element and
	 * return the value.
	 * 
	 * @param k
	 *            = key to be inserted
	 * @param v
	 *            = value along the key
	 * @return = returns the value if a new element is added to the list and
	 *         returns old value if the key is already present in the list.
	 */
	public static long insert(long k, long v) {
		long key = k;
		long value = v;
		int index = hashFunction(key);
		int index1 = hashFunction1(key);

		long[] keyvalue = new long[2];
		keyvalue[0] = key;
		keyvalue[1] = value;

		long returnvalue = 0;

		if (hasharray[index] == null) {

			LinkedList<long[]> l = new LinkedList<long[]>();

			l.add(keyvalue);
			hasharray[index] = l;
			returnvalue = value % 997;

		} else if (hasharray[index1] == null) {
			LinkedList<long[]> l = new LinkedList<long[]>();

			l.add(keyvalue);
			hasharray[index1] = l;
			returnvalue = value % 997;

		} else if (hasharray[index].size() <= hasharray[index1].size()) {

			LinkedList<long[]> temp = hasharray[index];
			// temp =
			int flag = 0;

			for (long[] keys : temp) {

				if (keys[0] == key) {
					returnvalue = keys[1] % 997;
					keys[1] = value;
					flag = 1;
				}
			}
			if (flag == 1) {
				hasharray[index] = temp;

			}

			if (flag == 0) {
				hasharray[index].add(keyvalue);
				returnvalue = value % 997;

			}
			return returnvalue;

		} else {
			LinkedList<long[]> temp = hasharray[index1];
			int flag = 0;

			for (long[] keys : temp) {

				if (keys[0] == key) {
					returnvalue = keys[1] % 997;
					keys[1] = value;
					flag = 1;
				}
			}
			if (flag == 1) {
				hasharray[index1] = temp;

			}

			if (flag != 1) {
				hasharray[index1].add(keyvalue);
				returnvalue = value % 997;

			}
		}

		return returnvalue;
	}

	/**
	 * The find function checks the specified key by retrieving the the hash
	 * value for the key. Once both the index values are found from hashing the
	 * key, it will find for the key in the list in both the index. If the index
	 * is empty or if the list dosen't have the specified key it will return 0.
	 * If the list has the specified key it will return the value of the key.
	 * 
	 * @param input
	 *            = key to be found
	 * @return = returns 0 if the list is empty or the the function does not
	 *         find the particular key and returns the value if key is found
	 */
	static long find(long input) {
		long inputs = input;
		int index = hashFunction(inputs);
		int index1 = hashFunction1(inputs);

		if (hasharray[index] == null && hasharray[index1] == null) {
			return 0;
		}

		LinkedList<long[]> temp = hasharray[index];
		LinkedList<long[]> temp1 = hasharray[index1];

		for (long[] keys : temp) {

			if (keys[0] == inputs) {
				return keys[1] % 997;
			}
		}
		for (long[] keys1 : temp1) {

			if (keys1[0] == inputs) {
				return keys1[1] % 997;
			}
		}

		return 0;
	}

	/**
	 * The delete function checks the specified key by retrieving the the hash
	 * value for the key. Once both the index values are found from hashing the
	 * key, it will find for the key in the list in both the index. If the index
	 * is empty or if the list dosen't have the specified key it will return 0.
	 * If the list has the specified key it will delete the key and return the
	 * value of the key.
	 * 
	 * @param key
	 *            = key to be deleted
	 * @return = returns 0 if the key is not found or the index does not contain
	 *         the list. Returns the value of key thats deleted.
	 */
	static long delete(long i) {
		long key = i;
		int index = hashFunction(key);
		int index1 = hashFunction1(key);

		if (hasharray[index] == null && hasharray[index1] == null) {
			return 0;
		}

		LinkedList<long[]> temp = hasharray[index];

		LinkedList<long[]> temp1 = hasharray[index1];

		if (temp != null) {
			int indexcount = 0;
			for (long[] keys : temp) {

				if (keys[0] == key) {

					long value = keys[1];
					temp.remove(indexcount);
					hasharray[index] = temp;
					return value % 997;
				}
				indexcount = indexcount + 1;
			}

		}
		if (temp1 != null) {
			int indexcount = 0;
			for (long[] keys1 : temp1) {
				if (keys1[0] == key) {
					long value = keys1[1];
					temp1.remove(indexcount);
					hasharray[index1] = temp1;
					return value % 997;

				}
				indexcount = indexcount + 1;
			}
		}

		return 0;
	}

	/**
	 * The hashFunction will return a hash value by performing modulus operation
	 * on key.
	 * 
	 * @param key
	 *            = to calculate the hash value
	 * @return = returns hashed value.
	 */
	public static int hashFunction(long key) {
		int hashedval = (int) (key % 9);
		return hashedval;
	}

	/**
	 * The hashFunction1 will return a hash value by performing addition and
	 * modulus operations on key.
	 * 
	 * @param key
	 *            = to calculate the hash value
	 * @return = returns hashed value.
	 */
	public static int hashFunction1(long key) {
		int hashedval = (int) ((key + 1) % 9);
		return hashedval;
	}

	/**
	 * @return = returns MaxBucketSize which is the size of the biggest bucket
	 *         in the array.
	 */
	public static long MaxBucketSize() {
		maxBucketSize = 0;
		
		for (int x = 0; x < hasharray.length; x++) {
			if (hasharray[x] != null)
			{
				bucketsize = hasharray[x].size();
				bucketSizeArray[x] = bucketsize;
			}
				
			if (bucketsize > maxBucketSize) {

				maxBucketSize = bucketsize;
			}
		}
		return maxBucketSize;
	}

	/**
	 * The main function contains the Scanner class that checks for the input
	 * the from the console and performs the operations.
	 * 
	 * It also gives the time and a value.
	 */
	public static void main(String[] args) throws FileNotFoundException {

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

				if (operation.contains("Insert")) {
					count++;
					inputs = operation.split(" ");

					output += insert(Long.parseLong(inputs[1]),
							Long.parseLong(inputs[2]));
				}

				else if (operation.contains("Find")) {
					count++;
					inputs = operation.split(" ");
					output += find(Long.parseLong(inputs[1]));
				}

				else if (operation.contains("Delete")) {
					count++;
					inputs = operation.split(" ");
					output += delete(Long.parseLong(inputs[1]));
				}

			}

		}
		long last = System.currentTimeMillis();
		System.out.println("Total Time Taken :"+(last - start));
		System.out.println("Output: "+ (output % 997));
		System.out.println("Average Time: "+ ((last-start)/count));
		MaxBucketSize();
		System.out.println("Maximum Bucket size :"+maxBucketSize);
		int total =0;
		for(int y =0;y<hasharray.length;y++)
		{
			total = total + bucketSizeArray[y];
		}
		System.out.println("Average Bucket Size: "+ (total/hasharray.length));
	}
}
