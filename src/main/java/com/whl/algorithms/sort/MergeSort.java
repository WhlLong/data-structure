package com.whl.algorithms.sort;

import java.util.Arrays;

/**
 * 〈归并排序〉
 *
 * @author whl
 * @create 2019/2/1
 * @since 1.0.0
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        MergeSort sort = new MergeSort();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public void sort(int[] arr) {
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length - 1, temp);
    }

    private void sort(int[] arr, int left, int right, int[] temp) {
        if (arr == null || arr.length <= 0 || left >= right) {
            return;
        }

        int mid = (left + right) / 2;
        sort(arr,left,mid,temp);
        sort(arr,mid+1,right,temp);
        merge(arr,left,mid,right,temp);
    }


    public void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int start1 = left;
        int start2 = mid+1;
        int tempIndex = 0;

        while (start1 <= mid && start2 <= right) {
            if(arr[start1] < arr[start2]){
                temp[tempIndex++] = arr[start1++];
            }else{
                temp[tempIndex++] = arr[start2++];
            }
        }


        while(start1 <= mid){
            temp[tempIndex++] = arr[start1++];
        }
        while(start2 <= right){
            temp[tempIndex++] = arr[start2++];
        }


        tempIndex = 0;
        while (left <= right){
            arr[left++] = temp[tempIndex++];
        }

    }


}