package com.whl.algorithms.sort;

import java.util.Arrays;

/**
 * 〈快速排序〉
 *
 * @author whl
 * @create 2019/2/12
 * @since 1.0.0
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {5, 1, 9, 2, 3, 5, 6, 9, 3, 2, 7, 3, 5, 4, 9, 8, 3, 2};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }


    public void sort(int[] arr, int start, int end) {
        if (arr == null || arr.length <= 1 || start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int val = arr[(left + right) / 2];

        while(left <= right){
            while(arr[left] > val){
                left++;
            }
            while(arr[right] < val){
                right--;
            }

            if(left < right){
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }else if(left == right){
                left++;
            }
        }

        sort(arr,start,right);
        sort(arr,left,end);
    }


}