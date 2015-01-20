import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//Program to Perform arithmetic operations on large numbers using linked lists
//
/**
 * 
 * @author sudharshan Narasimhan Vasudevan
 *
 */

// Class representing the node structure
class Node {
	int data;
	Node next;

	Node() {

		this.next = null;
	}

	Node(int d) {
		this.data = d;
		this.next = null;

	}
}


// The main class containing all methods to perform arithmetic operations and the driver program
/**
 * @author sudharshan
 *
 */
class BigNumArithmetic {
	
	// base id fixed as 10 (decimal)
	static int base = 10;

	// inserts the next node to the tail of the linked list without traversing the entire list
	/**
	 * @param n - node referring to the tail node of the linked list
	 * @param value value to inserted at the tail
	 * @return inserted node (new tail node)
	 */
	public static Node append(Node n, int value) {

		Node newNode = new Node(value);

		n.next = newNode;

		return newNode;
	}

	/**
	 * converts the string to linked list representation
	 * @param input string(representing the number) to be converted
	 * @return Head of the linked list 
	 */
	public static Node StrToNum(String input) {
		Node n = new Node();
		Node start = n;
		for (int i = input.length() - 1; i >= 0; i--) {
			start = append(start, Character.getNumericValue(input.charAt(i)));

		}

		return n;
	}

	/**
	 * converts the linked list to a string
	 * @param n the linked list to be converted to string
	 * @return converted string
	 */
	public static String NumToStr(Node n) {
		String linkstr;
		StringBuilder sb = new StringBuilder();
		while (n != null) {
			sb.append(n.data);
			n = n.next;
		}

		linkstr = sb.reverse().toString();
		return linkstr;

	}

	/**
	 * Performs multiplication of two input linked lists
	 * @param L1 first input linked list
	 * @param L2 second input linked list
	 * @return Head of the product linked list
	 */
	public static Node Multiply(Node L1, Node L2) {
		Node mullist = new Node();
		Node itr1 = L1;
		Node itr2 = L2;
		Node mulitr = mullist;
		Node temp = mullist;
		Node itr = temp;
		int carry = 0;
		int sum = 0;
		int level = 0;
		while ((itr2.next != null)) {
			while (itr1.next != null) {

				sum = (itr1.next.data * itr2.next.data) + carry;

				itr1 = itr1.next;

				if (level == 0) {
					mulitr = append(mulitr, (sum % base));
					carry = sum / base;
					temp = mullist.next;
				} else {
					sum = sum + itr.data;

					itr.data = (sum % base);

					carry = sum / base;

					if (itr.next != null) {
						itr = itr.next;

					} else if (itr1.next != null) {
						mulitr = append(mulitr, 0);

						itr = itr.next;

					}

				}

			}
			if (carry > 0) {

				mulitr = append(mulitr, carry);
				carry = 0;
			}
			if (temp.next != null) {
				temp = temp.next;

				itr = temp;
			}

			level = level + 1;
			itr2 = itr2.next;
			itr1 = L1;

		}

		return mullist;
	}

	/**
	 * Performs subtraction of two input linked lists
	 * @param L1 first input linked list
	 * @param L2 second input linked list
	 * @return Head of the difference linked list
	 * if a larger linked list is subtracted from a smaller linked list the output is zero
	 * 
	 */
	public static Node Subtract(Node L1, Node L2) {
		Node Sublist = new Node();
		Node itr1 = L1;
		Node itr2 = L2;
		Node subitr = Sublist;
		Node finallist = new Node();
		int sub = 0;
		int borrow = 0;
		while ((itr1.next != null) && (itr2.next != null)) {
			if ((itr1.next.data - borrow) >= itr2.next.data) {
				if (borrow == 0) {
					sub = itr1.next.data - itr2.next.data;
				} else {
					sub = itr1.next.data - itr2.next.data - borrow;

				}
				borrow = 0;
			}

			else {
				if (itr1.next.next != null || (itr1.next.data > itr2.next.data)) {
					if (borrow == 0) {
						sub = itr1.next.data + 10 - itr2.next.data;

					} else {
						sub = (itr1.next.data + 10 - borrow) - itr2.next.data;
					}

					borrow = 1;
				} else
					sub = -999;

			}
			itr1 = itr1.next;
			itr2 = itr2.next;
			if (sub != -999)
				subitr = append(subitr, sub);

			else {

				finallist.next = new Node(0);
				finallist.next.next = null;
				return finallist;
			}

		}
		while (itr1.next != null) {

			if (borrow == 0) {
				sub = itr1.next.data;
			} else if ((itr1.next.data - borrow) >= 0) {
				sub = itr1.next.data - borrow;
				borrow = 0;
			} else {
				
				sub = itr1.next.data + 10 - borrow;
				borrow = 1;
			}
			
			itr1 = itr1.next;
			subitr = append(subitr, sub);

		}

		String temp = NumToStr(Sublist.next);
		if (!temp.equals("0")) {
			temp = temp.replaceAll("^0+", "");
		}
		
		finallist = StrToNum(temp);
		return finallist;

	}

	/**
	 * Power computers the first number raised to second number where both are represented as linked list
	 * It is implemented as combination of multiplication and subtraction
	 * @param L1 first input linked list
	 * @param L2 second input linked list
	 * @return Head of the result linked list
	 * 
	 */
	public static Node Power(Node L1, Node L2) {

		Node pwrlist = new Node();
		Node sublist = new Node(1);
		Node one = new Node();
		Node zero = new Node();
		zero.next = new Node(0);
		one.next = new Node(1);
		one.next.next = null;
		zero.next.next = null;

		if ((L2.next.data == 1) && (L2.next.next == null)) {
			return L1;
		} else {

			pwrlist = Multiply(L1, one);
			sublist = Subtract(L2, zero);

			while (sublist.next.data != 1) {
			
				pwrlist = Multiply(pwrlist, L1);
				sublist = Subtract(sublist, one);
				
			}
			return pwrlist;
		}

	}

	/**
	 * Add performs addition of two linked lists
	 * @param L1 first input linked list
	 * @param L2 second input linked list
	 * @return Head of the result linked list
	 * 
	 */
	public static Node Add(Node L1, Node L2) {
		Node Sumlist = new Node();

		Node itr1 = L1;
		Node itr2 = L2;
		Node sumitr = Sumlist;
		int carry = 0;
		int sum = 0;
		while ((itr1.next != null) && (itr2.next != null)) {
			sum = itr1.next.data + itr2.next.data + carry;

			itr1 = itr1.next;
			itr2 = itr2.next;
			sumitr = append(sumitr, (sum % base));
			carry = sum / base;

		}
		while (itr1.next != null) {

			sum = itr1.next.data + carry;
			itr1 = itr1.next;
			sumitr = append(sumitr, (sum % base));
			carry = sum / base;

		}
		while (itr2.next != null) {
			sum = itr2.next.data + carry;
			itr2 = itr2.next;
			sumitr = append(sumitr, (sum % base));
			carry = sum / base;

		}
		if (carry > 0) {
			sumitr = append(sumitr, carry);
		}
		return Sumlist;

	}

	
	/**
	 * used to check for pattern matching
	 * @param regexPattern - The pattern to be tested
	 * @param stringToCheck - the input instruction
	 * @return true if pattern is matched. else false
	 */
	public static boolean regexCheck(String regexPattern, String stringToCheck) {
		Pattern checkRegex = Pattern.compile(regexPattern);
		Matcher regexMatch = checkRegex.matcher(stringToCheck);
		boolean match = false;
		if (regexMatch.find()) {
			match = true;
			
		}

		return match;
	}

	public static void main(String[] args) {
		int linenum;
		String cmd;
		Scanner in = new Scanner(System.in);
		// used to map input variables to the linked lists that is being created internally
		HashMap<String, Node> input = new HashMap<String, Node>();
		
		// each line of input is read from std in and stored in the array list
		ArrayList<String> command = new ArrayList<String>();
		
		// retrives ach input line and stores them in array list
		while (in.hasNext()) {

			linenum = in.nextInt();
			if (linenum == 1001)
				break;
			else {
				cmd = in.next();
				command.add(cmd);
			}

		}
	
		// for ach input instruction in the array list perform its aricthmetic operation accordingly
		for (int iterator = 0; iterator < command.size(); iterator++) {
			
			// checking for var = val pattern
			if (regexCheck("[a-zA-Z]{1}[=]\\d{1,}", command.get(iterator))) {

				input.put(command.get(iterator).substring(0, 1).toString(),
						StrToNum(command.get(iterator).substring(2).toString()));

			} else {
				
				// calling addition method with corresponding inputs retrived from hashmap with key as input variable
				if (command.get(iterator).contains("+")) {

					input.put(
							command.get(iterator).substring(0, 1).toString(),
							Add(input.get(command.get(iterator).substring(2, 3)
									.toString()),
									input.get(command.get(iterator)
											.substring(4).toString())));

				}
				// calling subratction method with corresponding inputs retrieved from hashmap with key as input variable
				// if the length of input 1 is lesser than the input 2 then a zero is returned
				else if (command.get(iterator).contains("-")) {
					

					Node param1 = input.get(command.get(iterator)
							.substring(2, 3).toString());
					param1 = param1.next;
					String operand1 = NumToStr(param1);
					
					Node param2 = input.get(command.get(iterator).substring(4)
							.toString());
					param2 = param2.next;

					String operand2 = NumToStr(param2);
					
					if (operand1.length() >= operand2.length()) {
						input.put(
								command.get(iterator).substring(0, 1)
										.toString(),
								Subtract(
										input.get(command.get(iterator)
												.substring(2, 3).toString()),
										input.get(command.get(iterator)
												.substring(4).toString())));
					} else {
						Node zero = new Node();
						zero.next = new Node(0);
						zero.next.next = null;
						input.put(command.get(iterator).substring(0, 1)
								.toString(), zero);

					}

				} 
				// calling multiplication method with corresponding inputs retrived from hashmap with key as input variable
				else if (command.get(iterator).contains("*")) {
					
					Node param1 = input.get(command.get(iterator)
							.substring(2, 3).toString());
					param1 = param1.next;

					String operand1 = NumToStr(param1);
				
					Node param2 = input.get(command.get(iterator).substring(4)
							.toString());
				
					param2 = param2.next;
					String operand2 = NumToStr(param2);
					
					if (operand1.length() >= operand2.length()) {
						
						input.put(
								command.get(iterator).substring(0, 1)
										.toString(),
								Multiply(
										input.get(command.get(iterator)
												.substring(2, 3).toString()),
										input.get(command.get(iterator)
												.substring(4).toString())));
					} else {

						input.put(
								command.get(iterator).substring(0, 1)
										.toString(),
								Multiply(
										input.get(command.get(iterator)
												.substring(4).toString()),
										input.get(command.get(iterator)
												.substring(2, 3).toString())));

					}
				}
				// calling exponent method with corresponding inputs retrived from hashmap with key as input variable
				else if (command.get(iterator).contains("^")) {
					
					input.put(
							command.get(iterator).substring(0, 1).toString(),
							Power(input.get(command.get(iterator)
									.substring(2, 3).toString()),
									input.get(command.get(iterator)
											.substring(4).toString())));

				}
				// handle looping case when input line is of type var?linenumber
				else if (command.get(iterator).contains("?")) {

					Node result = input.get(command.get(iterator)
							.substring(0, 1).toString());
					
					String output = NumToStr(result.next);
					
					int line = Integer.parseInt(command.get(iterator)
							.substring(2).toString());
					
					if (output.equals("0")) {

					} else
						iterator = line - 2;

				}

				// if none of the cases match we print the result
				else {
					Node result;
					result = input.get(command.get(iterator).substring(0)
							.toString());
					String output = NumToStr(result.next);
					System.out.println(output);
					System.out.println();
					
				}

			}
		}
	}

}
