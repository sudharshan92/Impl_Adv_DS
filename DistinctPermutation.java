
/**
 * @author Sudharshan Narasimhan Vasudevan
 * Program that visits all permutations/combinations
 *         of k objects from a set of n distinct objects, numbered 1...n.
 */
public class DistinctPermutation {
	static int n;
	static int count = 0;
	static int flag = 0;

	/**
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
		if (verbose > 1) {
			for (int i = 0; i < n; i++) {
				if (A[i] > 0) {
					System.out.print(A[i] + " ");
				}
			}
			System.out.println();

		}

		count = count + 1;
	}

	/**
	 * Method permute(): Generates permutation of k objects from n distinct objects
	 * Precondition: The permutation/combination has not been printed/counted so far
	 * Postcondition: The permutation/combination is now counted/printed. 
	 * @param A
	 *            is the input array (integer array)
	 * @param i
	 *            index at which the number is to be placed
	 * @param k
	 *            length of the permutation to be generated
	 * @param verbose
	 */
	static void permute(int[] A, int i, int k, int verbose) {

		/*
		 * If all the elements in the array are non zero , then it will be a
		 * valid permutation , so we call the visit procedure
		 */
		for (int ctr = 0; ctr < A.length; ctr++) {
			{
				if (A[ctr] == 0) {
					flag = 1;
					break;
				}
			}

		}
		if (flag != 1)
			visit(A, k, verbose);
		flag = 0;

		//Iterating through array of length k to store our nPk values
		for (int it = 0; it < A.length; it++) {
			if (A[it] == 0) {
				 /*Choosing one of the possible values from i to 1 
				 to be placed in the array at index "it"
				 This value keeps changing with each recursive call*/
				
				for (int x = i; x > 0; x--) {
					A[it] = x;
					permute(A, x - 1, k, verbose);
					A[it] = 0;
					
				}
				
			}
		}
	}

	/**
	 * Method combine(): Generates combination of k objects from n distinct objects
	 * @param A
	 *            is the input array (integer array)
	 * @param i
	 *            index at which the number is to be placed
	 * @param k
	 *            length of the combination to be generated
	 * @param verbose
	 */
	static void combine(int[] A, int i, int k, int verbose) {

		if (k == 0) {
			visit(A, n, verbose);
		} else if (i < k) {
			return;
		} else {

			A[i - 1] = i;
			// recursive call with i as i-1 and k as k-1 as one
			// element is chosen
			combine(A, i - 1, k - 1, verbose);
			A[i - 1] = 0;
			// recursive call with i as i-1 and k as k
			// element is not chosen

			combine(A, i - 1, k, verbose);
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		n = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		int verbose = Integer.parseInt(args[2]);
		
		long startPermute = 0, finishPermute = 0;
		long startCombine = 0, finishCombine = 0;
		if (verbose == 0 || verbose == 2) {
			int[] A = new int[k];
			for (int i = 0; i < k; i++) {
				A[i] = 0;
			}
			startPermute = System.currentTimeMillis();
			permute(A, n, k, verbose);
			finishPermute = System.currentTimeMillis();
			System.out.println(count + " " + (finishPermute - startPermute));

		}
		if (verbose == 1 || verbose == 3) {
			int[] A = new int[n];
			for (int i = 0; i < n; i++) {
				A[i] = 0;
			}
			startCombine = System.currentTimeMillis();
			combine(A, n, k, verbose);
			finishCombine = System.currentTimeMillis();
			System.out.println(count + " " + (finishCombine - startCombine));
		}
	}
}
