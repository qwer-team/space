package space;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArrayMix {

    public static void main(String[] argv) {
        HashMap<Integer, Integer> types = new HashMap<Integer, Integer>();
        ArrayMix.mix(1000000000, types);
    }

    public static int[] mix(int total, Map<Integer, Integer> types){
        return mix( total, types, 1);
    }
    public static int[] mix(int total, Map<Integer, Integer> types, Integer defValue) {
        int[] arr = new int[total];

        int cnt = 0;
        for (Integer key : types.keySet()) {
            int value = types.get(key);
            for (int i = 1; i <= value; i++) {
                arr[cnt] = key;
                cnt++;
            }
        }
        for (int i = cnt; i < total; i++) {
            arr[cnt] = defValue;
            cnt++;
        }

        int lastIndex = total - 1;

        Random rand = new Random();
        for (int it = lastIndex; it >= 1; it--) {
            int index = rand.nextInt(it);
            int tmp = arr[index];
            arr[index] = arr[it];
            arr[it] = tmp;
        }
        return arr;
    }
}
