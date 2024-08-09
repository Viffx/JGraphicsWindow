package com.adrianlong;

import java.util.Arrays;

public class Array {
    @SafeVarargs
    public static <T> T[] add(T[] originalArray, T... newElements) {
        T[] newArray = copyOf(originalArray, originalArray.length + newElements.length);
        int count = 0;
        for (int i = originalArray.length; i < newArray.length; i++) {
            newArray[i] = newElements[count];
            count++;
        }
        return newArray;
    }
    /**
     * Inserts a new element at the specified index
     *
     * @param <T> the class of the objects in the array
     * @param original the array to add the new element two
     * @param newElement the new element to add to the array
     * @param indx where to insert the new element in the array,
     *             an index of -1 references the last element in the array,
     *             -2 the second last and so on
     * @return a copy of the original array with the new element at the indx and the
     *         remaining value shifted one space to the right
     * @throws NegativeArraySizeException if {@code newLength} is negative
     * @throws NullPointerException if {@code original} is null
     * @since 22
     * */
    public static <T> T[] insert(T[] original, T newElement, int indx) {
        if (indx < 0) {
            indx = original.length + indx + 1;
        }
        // Create a new array with length one greater than the original array
        T[] newArray = copyOf(original,original.length + 1);

        // Shift elements from the original array to the new array, starting at the specified indx
        for (int i = newArray.length - 1; i > indx; i--) {
            newArray[i] = newArray[i - 1];
        }

        // Insert the new element at the indx
        newArray[indx] = newElement;

        return newArray;
    }
    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.  For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values.  For any indices that are valid in the
     * copy but not the original, the copy will contain {@code null}.
     * Such indices will exist if and only if the specified length
     * is greater than that of the original array.
     * The resulting array is of exactly the same class as the original array.
     *
     * @param <T> the class of the objects in the array
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls
     *     to obtain the specified length
     * @throws NegativeArraySizeException if {@code newLength} is negative
     * @throws NullPointerException if {@code original} is null
     * @since 1.6
     */
    public static <T> T[] copyOf(T[] original, int newLength) {
        return Arrays.copyOf(original,newLength);
    }

    @SafeVarargs
    public static <T, U> String printArrays(T[]... arrays) {
        int maxLen = max(Arrays.stream(arrays).mapToInt(array -> array.length).toArray());

        int[] distBetween = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            int maxLength = 0;
            for (T[] array : arrays) {
                if (i < array.length) {
                    int length = array[i].toString().length();
                    if (length > maxLength) {
                        maxLength = length;
                    }
                }
            }
            distBetween[i] = maxLength;
        }

        // Build the result string
        StringBuilder result = new StringBuilder();
        for (T[] array : arrays) {
            result.append(print(array, distBetween)).append("\n");
        }

        return result.toString();
    }
    private static int max(int... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Must provide at least one value");
        }
        int maxValue = values[0];
        for (int value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    public static boolean inBounds(int minX, int minY, int maxX, int maxY, int x, int y) {
        return x >= minX && x < minY && y >= minY && y < maxY;
    }

    public static <T> String print(T[] array, int[] distBetween) {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            String text = array[i].toString();
            str.append(text).append(" ".repeat(distBetween[i] - text.length()));
            if (i < array.length - 1) {
                str.append(",");
            }
        }
        str.append("]");
        return str.toString();
    }
    public static String[] rightAlignText(String... texts) {
        // Find the length of each string
        int[] lengths = new int[texts.length];
        for (int i = 0; i < texts.length; i++) {
            lengths[i] = texts[i].length();
        }

        // Find the maximum length using the max method
        int maxLength = max(lengths);

        // Right-align each string by padding with spaces
        String[] alignedTexts = new String[texts.length];
        for (int i = 0; i < texts.length; i++) {
            if (!texts[i].isEmpty()) {
                alignedTexts[i] = String.format("%" + maxLength + "s", texts[i]);
            } else {
                alignedTexts[i] = " ".repeat(maxLength);
            }

        }

        return alignedTexts;
    }
}