import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.*;

/**
 * 
 * @author sudharshan Narasimhan Vasudevan
 *
 */
 
 /*The aim of the project is to perform efficient multidimensional search. 
 * The dataset has information about an item with ID, name and price and 
 * the requirement here is to support the different operations provided 
 * in the specifications efficiently in terms of execution time and space 
 * complexity. This project requires the use of parallel data structures 
 * to facilitate search and retrieval of fields of different types and to 
 * maintain consistency of these access structures as inserts/deletes and 
 * updates to the data is being done.*/
 
 
// class for node that stores the data of each product namely id name and price
class Node
{
	long id;
	long[] name;
	long priceInCents;
	
	public Node(long id,long price,long[] name){
		this.id=id;
		this.priceInCents=price;
		this.name=name;
	}
}


public class MultidimensionalSearch {

	/*
	 * The main Map that stores product ids and their corresponding node
	 * references
	 */
	static TreeMap<Long, Node> mainTable = new TreeMap<Long, Node>();
	// Another Map that stores the name parts of each product and in each value
	// column of the name is a Map that stores the price as key and the list of
	// nodes as value
	static HashMap<Long, TreeMap<Long, HashSet<Node>>> nameTable = new HashMap<Long, TreeMap<Long, HashSet<Node>>>();

	/**
	 * function that performs the insertion of new product into the storage
	 * 
	 * @param id
	 *            - The id of the product that needs to be inserted
	 * @param priceInCents
	 *            - The price of the product to be inserted
	 * @param name
	 *            - The name of the product(array of long ints)(optional)
	 * @return The method returns 1 if the inserted item is new and returns 0
	 *         otherwise If an entry with the same id already exists, its name
	 *         and price are replaced by the new values. If name is empty, then
	 *         just the price is updated.
	 */
	public static int Insert(long id, long priceInCents, long[] name) {

		// If the item already exists in the storage
		if (mainTable.containsKey(id)) {
			Node n = mainTable.get(id);
			int flag = 0;
			long tempprice = n.priceInCents;
			long[] tempname = n.name;

			mainTable.get(id).priceInCents = priceInCents; // updating the node
															// price in the main
															// table with new
															// price

			if (name != null) {

				mainTable.get(id).name = name; // updating main table node name
												// with the new name
				flag = 0;

			} else {
				flag = 1;
			}

			// if the name is null then the name that is already present is
			// retrieved and the price is updated accordingly
			if (flag == 1) {

				// for each part of the name
				for (int i = 0; i < tempname.length; i++) {

					// remove the node from the list of nodes in the price entry
					nameTable.get(tempname[i]).get(tempprice).remove(n);

					// if the price entry had only the current node then remove
					// the price entry from the map
					if (nameTable.get(tempname[i]).get(tempprice).size() == 0) {
						nameTable.get(tempname[i]).remove(tempprice);
					}

					// if and entry for the price does not exist in the map
					// then we creare a new entry for the same

					if (!nameTable.get(tempname[i]).containsKey(priceInCents)) {
						n.priceInCents = priceInCents;
						HashSet<Node> listOfNode = new HashSet<Node>();
						listOfNode.add(n);
						nameTable.get(tempname[i])
								.put(priceInCents, listOfNode);
					} else {
						// else the new price is already present in the map and
						// so we
						// just add the current node to the list of
						// corresponding
						// nodes
						n.priceInCents = priceInCents;
						nameTable.get(tempname[i]).get(priceInCents).add(n);
					}
				}

			}

			// if the name is not equal to null
			else {

				// for each part of the name
				for (int i = 0; i < tempname.length; i++) {

					// remove the entry of old name if and only if it has the
					// current node only
					if (nameTable.get(tempname[i]).get(tempprice) != null
							&& nameTable.get(tempname[i]).size() == 1
							&& nameTable.get(tempname[i]).get(tempprice).size() == 1) {

						nameTable.remove(tempname[i]);

					} else {

						if (nameTable.get(tempname[i]).get(tempprice) != null) {
							// remove the node from the list of nodes
							// corresponding to the price
							nameTable.get(tempname[i]).get(tempprice).remove(n);
							// remove the entry for price if it corresponds to
							// only the current node
							if (nameTable.get(tempname[i]).get(tempprice)
									.size() == 0) {
								nameTable.get(tempname[i]).remove(tempprice);
							}
						}

					}
				}

				for (int i = 0; i < name.length; i++) {

					// if the name already exists then update the price
					// correspondingly
					if (nameTable.containsKey(name[i])) {

						if (nameTable.get(name[i]).containsKey(priceInCents)) {
							n.name = name;
							n.priceInCents = priceInCents;
							nameTable.get(name[i]).get(priceInCents).add(n);
						} else {

							HashSet<Node> listOfNode = new HashSet<Node>();
							n.name = name;
							n.priceInCents = priceInCents;
							listOfNode.add(n);
							nameTable.get(name[i])
									.put(priceInCents, listOfNode);
						}

					}
					// create an entry for each part of the new name if it is
					// not
					// present and add all the necessary fields to it
					else {
						HashSet<Node> listOfNode = new HashSet<Node>();
						n.name = name;
						n.priceInCents = priceInCents;
						listOfNode.add(n);
						TreeMap<Long, HashSet<Node>> treeElement = new TreeMap<Long, HashSet<Node>>();
						treeElement.put(priceInCents, listOfNode);
						nameTable.put(name[i], treeElement);
					}

				}

			}

			return 0;

		}
		// if the item is new then create a new entry in the main table and add
		// new node as value.
		// Updating in the name table same as above
		else {
			Node n = new Node(id, priceInCents, name);
			mainTable.put(id, n);
			for (int i = 0; i < name.length; i++) {
				if (!nameTable.containsKey(name[i])) {
					HashSet<Node> listOfNode = new HashSet<Node>();
					listOfNode.add(n);
					TreeMap<Long, HashSet<Node>> treeElement = new TreeMap<Long, HashSet<Node>>();
					treeElement.put(priceInCents, listOfNode);
					nameTable.put(name[i], treeElement);

				} else {

					if (nameTable.get(name[i]).containsKey(priceInCents)) {
						nameTable.get(name[i]).get(priceInCents).add(n);
					} else {
						HashSet<Node> listOfNode = new HashSet<Node>();
						listOfNode.add(n);
						nameTable.get(name[i]).put(priceInCents, listOfNode);
					}
				}
			}
			return 1;
		}
	}

	/**
	 * Function to find the price of an item with given id
	 * 
	 * @param id
	 *            the id of the product whose price has to be found
	 * @return price of item with given id (or 0, if not found)
	 */
	public static long find(long id) {
		if (mainTable.containsKey(id)) {
			// look for the item in the main table and find get the price from
			// its corresponding node
			return mainTable.get(id).priceInCents;
		} else
			return 0;
	}

	/**
	 * Function to delete item from storage
	 * 
	 * @param id
	 *            - id of the item to be deleted
	 * 
	 * @return - the sum of the long ints that are in the name of the item
	 *         deleted
	 */
	public static long delete(long id) {
		// if the item does not exist then return 0
		if (!mainTable.containsKey(id)) {
			return 0;
		}

		else {

			long namesum = 0; // variable to store sum of the long ints that are
								// in the name
			Node n = mainTable.get(id);
			long[] tempname = n.name;
			long tempprice = n.priceInCents;

			// deleting the node entry for each part of the name
			// if it is the only node for the price then remove the price from
			// the map
			// if the name had just this entry alone then we delete the name
			for (int i = 0; i < tempname.length; i++) {
				// creating the sum of long ints in the name
				namesum = namesum + tempname[i];
				if (nameTable.get(tempname[i]).size() == 1
						&& nameTable.get(tempname[i]).get(tempprice).size() == 1) {
					nameTable.remove(tempname[i]);
				} else {
					nameTable.get(tempname[i]).get(tempprice).remove(n);
					if (nameTable.get(tempname[i]).get(tempprice).size() == 0) {
						nameTable.get(tempname[i]).remove(tempprice);
					}
				}

			}
			// finally remove item from main table
			mainTable.remove(id);
			return namesum;
		}
	}

	/**
	 * Function to find items whose name contains n (exact match with one of the
	 * long ints in the item's name), and return lowest price of those items.
	 * 
	 * @param n
	 *            - long int that is the part of the name
	 * 
	 * @return - lowest price of those items that contain the name
	 */
	public static long findMinPrice(long n) {

		if (nameTable.get(n) == null) {
			return 0;
		} else

			return nameTable.get(n).firstKey();
	}

	/**
	 * Function to find items whose name contains n (exact match with one of the
	 * long ints in the item's name), and return Highest price of those items.
	 * 
	 * @param n
	 *            - long int that is the part of the name
	 * 
	 * @return - Highest price of those items that contain the name
	 */
	public static long findMaxPrice(long n) {
		if (nameTable.get(n) == null) {
			return 0;
		} else

			return nameTable.get(n).lastKey();
	}

	/**
	 * given a long int n, fuction finds the number of items whose name contains
	 * n, and their prices fall within the given range, [low, high].
	 * 
	 * @param n
	 *            long int which is the part of the name
	 * @param low
	 *            the lowest price of the range
	 * @param high
	 *            the highest price of the range
	 * 
	 * @return find the number of items whose name contains n, and their prices
	 *         fall within the given range, [low, high].
	 */
	public static long findPriceRange(long n, long low, long high) {
		Map<Long, HashSet<Node>> temprange = new TreeMap<Long, HashSet<Node>>();

		temprange = nameTable.get(n).subMap(low, true, high, true);

		Set<Node> items = new HashSet<Node>();
		for (Entry<Long, HashSet<Node>> entry : temprange.entrySet()) {

			items.addAll(entry.getValue());

		}
		return items.size();

	}

	/**
	 * Function that increase the price of every product, whose id is in the
	 * range [l,h], by r% Discard any fractional pennies in the new prices of
	 * items.
	 * 
	 * @param l
	 *            - id that is the starting of the range
	 * @param h
	 *            - id that is the end of the range
	 * @param r
	 *            - the percentage of price hike
	 * 
	 * @return Returns the sum of the net increases of the prices.
	 */
	public static long priceHike(long l, long h, BigDecimal r) {
		Map<Long, Node> tempidrange = new TreeMap<Long, Node>();
		tempidrange = mainTable.subMap(l, true, h, true);
		long netIncrease = 0;
		BigDecimal ratio = (r.divide(new BigDecimal(100)))
				.add(new BigDecimal(1));
		for (Entry<Long, Node> entry : tempidrange.entrySet()) {

			Node n = entry.getValue();
			long oldprice = n.priceInCents;
			long[] oldname = n.name;
			BigDecimal newPriceB = new BigDecimal(oldprice).multiply(ratio);
			long newPrice = newPriceB.longValue();
			netIncrease += (newPrice - oldprice);
			n.priceInCents = newPrice;

			for (int i = 0; i < oldname.length; i++) {

				if (nameTable.get(oldname[i]).get(oldprice) != null) {
					nameTable.get(oldname[i]).get(oldprice).remove(n);
					if (nameTable.get(oldname[i]).get(oldprice).size() == 0) {
						nameTable.get(oldname[i]).remove(oldprice);
					}
				}

				if (!nameTable.get(oldname[i]).containsKey(newPrice)) {
					n.priceInCents = newPrice;
					HashSet<Node> listOfNode = new HashSet<Node>();
					listOfNode.add(n);
					nameTable.get(oldname[i]).put(newPrice, listOfNode);
				} else {
					n.priceInCents = newPrice;
					nameTable.get(oldname[i]).get(newPrice).add(n);

				}

			}

			mainTable.get(entry.getKey()).priceInCents = newPrice;

		}
		return netIncrease;

	}

	/**
	 * function that converts the price in input to cents for the ease of
	 * calculation
	 * 
	 * @param price
	 *            - Price that needs to be converted to cents
	 * 
	 * @return -Price in cents
	 */
	public static long priceToCents(String price) {

		if (!price.contains(".")) {
			long centsprice = Long.parseLong(price) * 100;
			return centsprice;
		} else {
			String[] words = price.split("\\.");
			long dol = Long.parseLong(words[0]) * 100;
			int strsize = words[1].length();
			if (strsize == 1) {
				long centsprice = dol + (Long.parseLong(words[1]) * 10);
				return centsprice;
			} else {
				String cen = words[1].substring(0, 2);
				long centsprice = dol + (Long.parseLong(cen));
				return centsprice;
			}
		}

	}

	/**
	 * Function that converts cents to dollars and cents
	 * 
	 * @param price
	 *            - the final output in cents
	 * 
	 * @return - the output in dollars and cents
	 */
	public static String priceToDollars(long price) {

		String Result = String.valueOf(price);
		Result = new StringBuilder(Result).insert(Result.length() - 2, ".")
				.toString();
		return Result;
	}

	/**
	 * function that process the input file and calls each function accordingly
	 * 
	 * @param br
	 *            - object to the input file
	 * @param finalAns
	 *            - variable to store the sum total of all return values of all
	 *            function
	 * 
	 * @return - sum total of all return values of all function
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static long Process(BufferedReader br, long finalAns)
			throws NumberFormatException, IOException {
		String line;
		String[] words;
		while ((line = br.readLine()) != null) {
			if (!line.contains("#")) {
				words = line.replace("\t", "").replace("  ", " ").split(" ");
				if (line.contains("Insert")) {
					if ((words.length - 4) == 0) {
						finalAns += (Insert(Long.parseLong(words[1]),
								priceToCents((words[2])), null) * 100);
					} else {
						long[] name = new long[(words.length - 4)];
						for (int x = 0; x < (words.length - 4); x++) {
							name[x] = Long.parseLong(words[x + 3]);
						}
						finalAns += (Insert(Long.parseLong(words[1]),
								priceToCents(words[2]), name) * 100);
					}

				} else if (line.contains("Delete")) {

					finalAns += (delete(Long.parseLong(words[1])) * 100);

				} else if (words[0].equals("Find")) {
					finalAns += (find(Long.parseLong(words[1])));

				} else if (line.contains("FindMinPrice")) {

					finalAns += (findMinPrice(Long.parseLong(words[1])));

				} else if (line.contains("FindMaxPrice")) {
					finalAns += (findMaxPrice(Long.parseLong(words[1])));

				} else if (line.contains("FindPriceRange")) {
					finalAns += (findPriceRange(Long.parseLong(words[1]),
							priceToCents(words[2]), priceToCents(words[3])) * 100);

				} else if (line.contains("PriceHike")) {
					BigDecimal r = new BigDecimal(words[3]);
					finalAns += (priceHike(Long.parseLong(words[1]),
							Long.parseLong(words[2]), r));

				}
			}
		}
		return finalAns;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		long finalAns = 0;

		// record start time
		long startTime = System.currentTimeMillis();

		// input file along with path needs to be specified
		BufferedReader br = new BufferedReader(new FileReader(args[0]));

		finalAns = Process(br, finalAns);

		System.out.println(priceToDollars(finalAns));

		System.out.println("Time taken: "
				+ (System.currentTimeMillis() - startTime) + " msec");

	}
}
