package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Requirements {

    int[] il = {1, 2, 3, 4, 5};
    ArrayList<Integer> ill = new ArrayList<>();
    
    public Requirements() {
        ill.add(1);
        ill.add(2);
        ill.add(3);
        ill.add(4);
        ali(ill);
        System.out.println();
        alr(ill);
        System.out.println();
        ar(il, 0);
        System.out.println();
        ai(il);
        sr("\nPOG I WAS HERE");
        si("\nPOG I WAS HERE");
        int[] ex = randInt();
        sort(ex, 0, ex.length - 1);
        System.out.println(Arrays.toString(ex));
        System.out.println(binarySearch(ex, 0, ex.length - 1, 727));
    }

    public static void sr(String a) {
        if (a.length() != 0) {
            System.out.print(a.substring(0, 1));
            sr(a.substring(1));
        }
    }

    public static void si(String a) {
        for (int i = 0; i < a.length(); i++) {
            System.out.print(a.substring(i, i + 1));
        }
    }

    public static void ar(int[] a, int i) {
        if (i < a.length) {
            System.out.print(a[i] + ", ");
            ar(a, i + 1);
        }
    }

    public static void ai(int[] a) {
        for (int i: a) System.out.print(i + ", ");
    }

    public static void alr(ArrayList<Integer> a) {
        if (a.size() != 0) {
            System.out.print(a.remove(0));
            alr(a);
        }
    }

    public static void ali(ArrayList<Integer> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i));
        }
    }

    public static void merge(int arr[], int l, int m, int r)
  {
    //Determine sizes of two subarrays to be merged.
    int n1 = m - l + 1;
    int n2 = r - m;
    //Create temporary arrays.
    int left[] = new int[n1];
    int right[] = new int[n2];
    //Copy data to temporary arrays.
    for (int i = 0; i < n1; i++)
      left[i] = arr[l + i];
    for (int j = 0; j < n2; ++j)
      right[j] = arr[m + 1 + j];
    //Merge the temporary arrays in ascending order.
    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2)
    {
      if (left[i] <= right[j])
      {
        arr[k] = left[i];
        i++;
      }
      else
      {
        arr[k] = right[j];
        j++;
      }
      k++;
    }
    //Copy remaining elements of the left array if any.
    while (i < n1)
    {
      arr[k] = left[i];
      i++;
      k++;
    }
    //Copy remaining elements of the right array if any.
    while (j < n2)
    {
      arr[k] = right[j];
      j++;
      k++;
    }
  }

  public static void sort(int arr[], int l, int r)
  {
    if (l < r) {
      //Find the middle.
      int m =(l + r)/2;
      //Split the array recursively.
      sort(arr, l, m);
      sort(arr, m + 1, r);
      //Merge the sorted halves.
      merge(arr, l, m, r);
    }
  }

  public static int binarySearch(int[] arr, int left, int right, int x)
    {
        if (right >= left)
        {
        int mid = (left + right) / 2;
        if (arr[mid] == x)
        {
            return mid;
        }
        else if (arr[mid] > x)
        {
            return binarySearch(arr, left, mid - 1, x);
        }
        else
        {
            return binarySearch(arr, mid + 1, right, x);
        }
        }
        return -1;
    }

    public static int[] randInt() {
        int[] r = new int[1000];
        for (int i = 0; i < r.length; i++) r[i] = (int) (Math.random() * 1000);
        return r;
    }
}
