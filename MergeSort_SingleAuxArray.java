import java.io.*;

/*
 * Create a single auxiliary array B in main and pass it to MergeSort and Merge. 
 * In each instance of Merge, data is copied from A to B and merged back into A.
 * 
 * */
/**
 * @author sudharshan Narasimhan Vasudevan
 */

public class MergeSort_SingleAuxArray {

	/*
	 * Input : Input array A, Auxiliary array B , start and end points p and r
	 * Function : recursively calls the merge sort algorithm and finally the
	 * merge algorithm to sort and combine elements
	 */
	static void MergeSort(int[] A, int[] B, int p, int r) {
		if (p < r) {
			if (r - p > 11) {
				int q = (p + r) / 2;
				MergeSort(A, B, p, q);
				MergeSort(A, B, q + 1, r);
				Merge(A, B, p, q, r);
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
	 * Input : arrays A and B and start mid and end points p,q and r respectively
	 * output : sorted elements in the array A
	 */
	static void Merge(int[] A, int[] B, int p, int q, int r) {

		// data copied from A to B
		for (int i = p; i <= q; i++)
			B[i] = A[i];
		for (int i = q + 1; i <= r; i++)
			B[i] = A[i];
		int i = p;
		int j = q + 1;
		// sorting and merging done to A
		for (int k = p; k <= r; k++) {
			if ((j > r) || ((i <= q) && (B[i] <= B[j])))
				A[k] = B[i++];
			else
				A[k] = B[j++];
		}
		return;
	}

	public static void main(String[] args) throws IOException {

		int n = Integer.parseInt(args[0]);
		int[] A = new int[n];
		int[] B = new int[n];// auxiliary array
		for (int i = 0; i < n; i++) {
			A[i] = n - i;
		}
		long start = System.currentTimeMillis();

		MergeSort(A, B, 0, n - 1);

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
