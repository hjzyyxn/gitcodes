
public class SelectionSort {
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++)
				if (a[j].compareTo(a[min]) < 0) min = j;
			swap(a, i, min);
		}
	}

}
