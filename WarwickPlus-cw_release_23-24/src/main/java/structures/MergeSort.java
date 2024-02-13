package structures;

public class MergeSort {
    
    public static void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }

        int[] leftHalf = new int[array.length / 2];
        int[] rightHalf = new int[array.length - leftHalf.length];

        System.arraycopy(array, 0, leftHalf, 0, leftHalf.length);
        System.arraycopy(array, leftHalf.length, rightHalf, 0, rightHalf.length);

        sort(leftHalf);
        sort(rightHalf);

        merge(array, leftHalf, rightHalf);
    }

    private static void merge(int[] outputArray, int[] leftHalf, int[] rightHalf) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < leftHalf.length && rightIndex < rightHalf.length) {
            if (leftHalf[leftIndex] >= rightHalf[rightIndex]) {
                outputArray[mergeIndex] = leftHalf[leftIndex];
                leftIndex++;
            } else {
                outputArray[mergeIndex] = rightHalf[rightIndex];
                rightIndex++;
            }
            mergeIndex++;
        }

        System.arraycopy(leftHalf, leftIndex, outputArray, mergeIndex, leftHalf.length - leftIndex);
        System.arraycopy(rightHalf, rightIndex, outputArray, mergeIndex, rightHalf.length - rightIndex);
    }
}










