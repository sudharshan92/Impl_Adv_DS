import java.util.Scanner;

class HashEntry {
	private long key;
	private long value;

	HashEntry(long key, long value) {
		this.key = key;
		this.value = value;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long getKey() {
		return key;
	}
}

class DeletedEntry extends HashEntry {
	private static DeletedEntry entry = null;

	private DeletedEntry() {
		super(-1, -1);
	}

	public static DeletedEntry getUniqueDeletedEntry() {
		if (entry == null)
			entry = new DeletedEntry();
		return entry;
	}
}

public class LinearProbing{
	
	private final static int TABLE_SIZE = 9;

	static HashEntry[] table;

	LinearProbingHashMap() {
		table = new HashEntry[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}

	/**
	 * The get function checks the specified key by retrieving the the hash
	 * value for the key. Once the index is found from hashing the key, it will
	 * find for the key in the list in the particular index. If the index is
	 * empty or if the list dosen't have the specified key it will check for 
	 * the next index and similarly will check all the indexes. If the list has the 
	 * specified key it will return the value of the key or return 0.
	 * 
	 * @param input = key to be found
	 * @return = returns 0 if the list is empty or the the function does not
	 *         find the particular key and returns the value if key is found
	 */
	public long get(long key) {
		int hash = (int) (key % TABLE_SIZE);
		int initialHash = -1;
		while (hash != initialHash
				&& (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
						&& table[hash].getKey() != key)) {
			if (initialHash == -1)
				initialHash = hash;
			hash = (hash + 1) % TABLE_SIZE;
		}
		if (table[hash] == null || hash == initialHash)
			return 0;
		else
			return table[hash].getValue();
	}

	/**
	 * The put function checks for index by the given key and retrieving the
	 * hash value for that key. Once the index is found, it checks whether the
	 * index is null or not and if the index is null it will insert the new
	 * element to that index and return the value . If the index is not null it
	 * will check the next index and keep checking till it gets any null index or 
	 * it come backs to the actual index. If it finds any null index it will insert 
	 * the element at that index, or else will insert at the old index.It will also
	 * check if the key is already present in the list and if it is, it will replace
	 * the old value with new value and return old value.
	 * If the key is not present in the list it will insert the new element and return
	 * the value.
	 * 
	 * @param k = key to be inserted
	 * @param v = value along the key
	 * @return = returns the value if a new element is added to the list and
	 *         returns old value if the key is already present in the list.
	 */
	public void put(long key, long value) {
		int hash = (int) (key % TABLE_SIZE);
		int initialHash = -1;
		int indexOfDeletedEntry = -1;
		while (hash != initialHash
				&& (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
						&& table[hash].getKey() != key)) {
			if (initialHash == -1)
				initialHash = hash;
			if (table[hash] == DeletedEntry.getUniqueDeletedEntry())
				indexOfDeletedEntry = hash;
			hash = (hash + 1) % TABLE_SIZE;
		}
		if ((table[hash] == null || hash == initialHash)
				&& indexOfDeletedEntry != -1) {
			table[indexOfDeletedEntry] = new HashEntry(key, value);

		}

		else if (initialHash != hash)
			if (table[hash] != DeletedEntry.getUniqueDeletedEntry()
					&& table[hash] != null && table[hash].getKey() == key) {
				table[hash].setValue(value);

			}

			else {
				table[hash] = new HashEntry(key, value);
			}

	}
	
	/**
	 * The remove function checks the specified key by retrieving the the hash
	 * value for the key. Once the index is found from hashing the key, it will
	 * find for the key in the list in the particular index. If the index is
	 * empty or if the list dosen't have the specified key it will check for 
	 * the next index and similarly will check all the indexes. If
	 * the list has the specified key it will delete the key and return the
	 * value of the key or else it will return 0.
	 * 
	 * @param key = key to be deleted
	 * @return = returns 0 if the key is not found or the index does not contain
	 *         the list. Returns the value of key thats deleted.
	 */
	public void remove(long key) {

		int hash = (int) (key % TABLE_SIZE);
		int initialHash = -1;
		while (hash != initialHash
				&& (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
						&& table[hash].getKey() != key)) {
			if (initialHash == -1)
				initialHash = hash;
			hash = (hash + 1) % TABLE_SIZE;
		}
		if (hash != initialHash && table[hash] != null)
			table[hash] = DeletedEntry.getUniqueDeletedEntry();

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

		// reset();
		ajj140430_LinearProbing LPH = new LinearProbingHashMap();

		long start = System.currentTimeMillis();

		while (sc.hasNextLine()) {
			operation = sc.nextLine();
			if (!operation.contains("#")) {
				if (operation.contains("break")) {
					break;
				}

				else if (operation.contains("Insert")) {
					// count++;
					String inputs[] = operation.split(" ");

					LPH.put(Long.parseLong(inputs[1]),
							Long.parseLong(inputs[2]));
				}

				else if (operation.contains("Find")) {
					String inputs[] = operation.split(" ");
					LPH.get(Long.parseLong(inputs[1]));
				}

				else if (operation.contains("Delete")) {
					String inputs[] = operation.split(" ");
					LPH.remove(Long.parseLong(inputs[1]));
				}

			}
		}

		long last = System.currentTimeMillis();
		System.out.println("Time taken " + (last - start));

	}
}
