import java.util.Arrays;

/**
 * @author Sudharshan Narasimhan Vasudevan
 * ( Enumeration of Permutations
 *         and Combinations) Program that visits all permutations of n(not
 *         necessarily distinct) objects The program uses Prof Knuth's L
 *         algorithm from the book "The Art of Computer Programming"
 */

public class NonDistinctPermutation {
	static int count = 0;

	/**
	 * Method visit(): visits a permutation/combination, and prints it, if the
	 * parameter "verbose" is nonzero. Precondition: The permutation/combination
	 * has not been printed/counted so far Postcondition: The
	 * permutation/combination is now counted/printed.
	 * 
	 * @param A
	 *            is the input array (integer array)
	 * @param n
	 *            is the size of the array (integer)
	 * @param verbose
	 *            is the parameter to toggle between printing the entire
	 *            permutations/combinations or just their count alone verbose =
	 *            0 => Prints no. of permutations of k objects out of n distinct
	 *            objects and the time taken in ms verbose = 1 => Prints no. of
	 *            combinations of k objects out of n distinct objects and the
	 *            time taken in ms verbose = 2 => Prints actual permutations one
	 *            line at a time and then output of v=0 verbose = 3 => Prints
	 *            actual permutations one line at a time and then output of v=1
	 */
	static void visit(int[] A, int n, int verbose) {
		if (verbose > 0) {
			for (int i = 1; i < n + 1; i++) {
				if (A[i] > 0) {
					System.out.print(A[i] + " ");
				}
			}
			System.out.println();

		}

		count = count + 1;
	}

	/**
	 * Method knuth():Implementation of Knuth's Algorithm L Algorithm L: Given a
	 * sequence of n elements a0, a1, …, an-1 , generate all permutations of the
	 * sequence in lexicographically correct order. Precondition: The
	 * permutation/combination has not been printed/counted so far
	 * Postcondition: The permutation/combination is now counted/printed.
	 * 
	 * @param a
	 *            is the input array (integer array)
	 * @param n
	 *            is the size of the array (integer)
	 * @param verbose
	 *            verbose is the parameter to toggle between printing the entire
	 *            permutations/combinations or just their count alone
	 *            Reference:The Art of Computer Programming, Volume 4, Fascicle
	 *            2: Generating All Tuples and Permutations.
	 */
	static void knuth(int[] a, int n, int verbose) {
		int j, l;
		/*
		 * Initially, the visit function is called to print the sorted array
		 * Considered as the first permutation)
		 */
		visit(a, n, verbose);

		while (true) {
			/*
			 * Finding out the value of j, the smallest subscript such that all
			 * permutations A[1...j] are already visited
			 */
			for (j = n - 1; a[j] >= a[j + 1]; j--) {
				if (a[j] < a[j + 1]) {
					break;
				}
			}
			/*
			 * Main exit point out of the loop which happens when all
			 * permutations are visited
			 */
			if (j == 0) {
				break;
			}

			for (l = n; a[j] >= a[l]; l--) {
				if (a[j] < a[l]) {
					break;
				}
			}
			/*
			 * Swap A[l] with A[j], A[l] is the smallest element greater than
			 * A[j] that can follow A[1...j-1] in a permutation
			 */
			int temp = a[l];
			a[l] = a[j];
			a[j] = temp;
			/* Reverse the array A[j+1..n] */
			for (int left = j + 1, right = n; left < right; left++, right--) {
				temp = a[left];
				a[left] = a[right];
				a[right] = temp;
			}
			/* Finally, call visit function to print the permutation */
			visit(a, n, verbose);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		if ((n < 0) || (n > 1000)) {
			System.out.println("Invalid input for n");
		} else {
			int verbose = Integer.parseInt(args[1]);
			int[] a = new int[n + 1];
			int i;

			for (i = 1; i < n + 1; i++) {
				a[i] = Integer.parseInt(args[i + 1]);
			}
			// Sorting the input array in ascending order
			Arrays.sort(a);
			// Auxiliary (Sentinel) element at the start of the array such that
			// value of it is less than all elements of the array.
			a[0] = -999;
			long start = 0, finish = 0;
			start = System.currentTimeMillis();
			knuth(a, n, verbose);
			finish = System.currentTimeMillis();
			System.out.println(count + " " + (finish - start));
		}
	}
}
