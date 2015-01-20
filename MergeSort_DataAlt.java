import java.io.*;

/*
 * Program to create a single auxiliary array B in main and pass it to MergeSort and Merge. Data alternates between A and B. With insertion sort
 * 
 * */
/**
 * @author sudharshan Narasimhan Vasudevan
 */
public class MergeSort_DataAltt {

	/*
	 * Input : Input array A, Auxiliary array B , start and end points p and r of the array
	 * output : returns the height of the tree
	 */
	static int MergeSort(int[] A, int[] B, int p, int r) {

		if (p < r) {

			if (r - p > 11) {
				int q = (p + r) / 2;// array mid point calculation
				// h1 and h2 are the depth of the recursion
				int h1 = MergeSort(A, B, p, q);
				int h2 = MergeSort(A, B, q + 1, r);

				if (h1 != h2) // case where data to be merged are on different
								// arrays
				{
					if (h1 % 2 != 0) // if odd
					{

						// copying the right half of array A to B and then
						// calling merge on B
						for (int i = q + 1; i <= r; i++)
							B[i] = A[i];
						Merge(B, A, p, q, r);
					} else // if even
					{

						// copying the right half of array B to A and then
						// calling merge on A
						for (int i = q + 1; i <= r; i++)
							A[i] = B[i];
						Merge(A, B, p, q, r);
					}

				}
				// cases where data to be merged are on the same array
				else if (h1 % 2 != 0) {
					Merge(B, A, p, q, r);

				} else {
					Merge(A, B, p, q, r);

				}

				return (h1 + 1);

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
				return 0;
			}
		} else {
			return 0;
		}
	}

	/*
	 * Input : Source and destination arrays, start , mid and end points p,q,r respectively
	 * output : sorted elements in the destination array
	 */
	static void Merge(int[] src, int[] dest, int p, int q, int r) {

		// merge algorithm
		int i = p;
		int j = q + 1;
		for (int k = p; k <= r; k++) {
			if ((j > r) || ((i <= q) && (src[i] <= src[j])))
				dest[k] = src[i++];
			else
				dest[k] = src[j++];
		}
		return;
	}

	public static void main(String[] args) throws IOException {

		int n = Integer.parseInt(args[0]);
		int[] A = new int[n];
		int[] B = new int[n]; // auxiliary array

		// storing data in descending order
		for (int i = 0; i < n; i++) {
			A[i] = n - i;
		}
		int height = 0;
		long start = System.currentTimeMillis();

		height = MergeSort(A, B, 0, n - 1);

		long last = System.currentTimeMillis();

		if (height % 2 != 0) { // if height is odd then final output resides in
								// array B
			for (int j = 0; j <= A.length - 1; j++) {
				A[j] = B[j];
			}
		}

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
