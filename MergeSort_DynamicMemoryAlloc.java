import java.io.*;

/*
 * Allocate dynamic memory for L and R within Merge
 * */
/**
  * @author sudharshan Narasimhan Vasudevan
 */

public class MergeSort_DynamicMemoryAlloc {

	/*
	 * Input : Input array A, start and end points p and r Function :
	 * recursively calls the merge sort algorithm and finally the merge
	 * algorithm to sort and combine elements
	 */
	static void MergeSort(int[] A, int p, int r) {
		if (p < r) {
			if (r - p > 11) {
				int q = (p + r) / 2;// array mid point calculation
				MergeSort(A, p, q);
				MergeSort(A, q + 1, r);
				Merge(A, p, q, r);
			} else { // Insertion sort
				for (int i = p, j = i; i < r; j = ++i) {
					int ai = A[i + 1];
					while (ai < A[j]) {
						A[j + 1] = A[j];
						if (j-- == p) {
							break;
						}
					}
					A[j + 1] = ai;
				}
			}
		}
	}

	/*
	 * Input : Source array A and start mid and end points p,q and r
	 * respectively output : sorted elements in the array A
	 */
	static void Merge(int[] A, int p, int q, int r) {
		int ls = q - p + 1;
		int rs = r - q;
		// dynamic memory allocation to arrays L and R
		int[] L = new int[ls];
		int[] R = new int[rs];

		// copying left half of array A to L
		for (int i = p; i <= q; i++)
			L[i - p] = A[i];
		// copying right half of array A to R
		for (int i = q + 1; i <= r; i++)
			R[i - (q + 1)] = A[i];
		int i = 0;
		int j = 0;
		// merging L and R to array A
		for (int k = p; k <= r; k++) {
			if ((j >= rs) || ((i < ls) && (L[i] <= R[j])))
				A[k] = L[i++];
			else
				A[k] = R[j++];
		}
		return;
	}

	public static void main(String[] args) throws IOException {
		int n = Integer.parseInt(args[0]);
		int[] A = new int[n];
		// Storing data in descending order
		for (int i = 0; i < n; i++) {
			A[i] = n - i;
		}
		long start = System.currentTimeMillis();

		MergeSort(A, 0, n - 1);

		long last = System.currentTimeMillis();

		for (int j = 0; j < A.length - 1; j++) {
			if (A[j] > A[j + 1]) {
				System.out.println("Sorting failed :-(");
				return;
			}
		}
		System.out.println("Success!");
		System.out.println(last - start);
	}
}
