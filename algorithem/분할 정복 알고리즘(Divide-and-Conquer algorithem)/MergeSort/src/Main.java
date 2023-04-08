/**
 * 제목 : 합병정렬(Merge Sort)
 * 일자 : 2023.04.02
 * <p>
 * 입력 : A[p] ... A[q]
 * 출력 : 정렬된 A[p] ... A[q]
 */

public class Main {
    public static void main(String[] args) {
        int[] A = {2,36,76,4,55,2,35,4};
        mergeSort(A, 0, A.length - 1);

        for (int i =0;i< A.length;i++)
            System.out.printf("[%d] ", A[i]);
        System.out.println();
    }

    public static void mergeSort(int[] A, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(A, left, mid);
            mergeSort(A, mid + 1, right);

            merge(A, left, mid, right);
        }
    }

    public static void merge(int[] A, int left, int mid, int right) {
        int[] arr = new int[right + 1];
        for (int i = 0; i < right + 1; i++)
            arr[i] = A[i];

        int pos = left, l = left, r = mid + 1;
        while (l <= mid && r <= right) {
            if (arr[l] <= arr[r]) {
                A[pos] = arr[l];
                l++;
            } else if (arr[l] > arr[r]) {
                A[pos] = arr[r];
                r++;
            }
            pos++;
        }
        if (l > mid) {
            while (pos <= right) {
                A[pos] = arr[r];
                pos++;
                r++;
            }
        } else if (r > right) {
            while (pos <= right) {
                A[pos] = arr[l];
                pos++;
                l++;
            }
        }
    }
}