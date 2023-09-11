package com.astro.smitesolver.utils;

import java.lang.reflect.Array;

public class ServiceUtils {
    public static <T> T[] appendArray(T[] arr1, T[] arr2) {
        int arr1Len = arr1.length;
        int arr2Len = arr2.length;

        @SuppressWarnings("unchecked")
        T[] newArr = (T[]) Array.newInstance(arr1.getClass().getComponentType(), arr1Len + arr2Len);
        System.arraycopy(arr1, 0, newArr, 0, arr1Len);
        System.arraycopy(arr2, 0, newArr, arr1Len, arr2Len);

        return newArr;
    }

    public static <T> T parseSingleEntry(T[] arr) {
        return arr[0];
    }

    public static <T> T parseSingleEntry(T[] arr, int index) {
        return arr[index];
    }
}
