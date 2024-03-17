package structures;
import java.util.Comparator;


public class Sort {
    @SuppressWarnings("unchecked")
    public static <T> void genericSort(T[] array, Comparator<T> comparator) {
        if (array.length <= 1) {
            return;
        }

        T[] leftHalf = (T[]) new Object[array.length / 2];
        T[] rightHalf = (T[]) new Object[array.length - leftHalf.length];

        System.arraycopy(array, 0, leftHalf, 0, leftHalf.length);
        System.arraycopy(array, leftHalf.length, rightHalf, 0, rightHalf.length);

        genericSort(leftHalf, comparator);
        genericSort(rightHalf, comparator);

        genericMerge(array, leftHalf, rightHalf, comparator);
    }

    private static <T> void genericMerge(T[] outputArray, T[] leftHalf, T[] rightHalf, Comparator<T> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < leftHalf.length && rightIndex < rightHalf.length) {
            if (comparator.compare(leftHalf[leftIndex], rightHalf[rightIndex]) >= 0) {
                outputArray[mergeIndex++] = leftHalf[leftIndex++];
            } else {
                outputArray[mergeIndex++] = rightHalf[rightIndex++];
            }
        }

        System.arraycopy(leftHalf, leftIndex, outputArray, mergeIndex, leftHalf.length - leftIndex);
        System.arraycopy(rightHalf, rightIndex, outputArray, mergeIndex, rightHalf.length - rightIndex);
    } 
}