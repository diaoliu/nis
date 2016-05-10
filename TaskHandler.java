package de.unidue.iem.tdr.nis.client;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TaskHandler {
    // Aufgabe 2 XOR
    public static String xor(String hex1, String hex2){
        int i = Integer.parseInt(hex1,16);
        int j = Integer.parseInt(hex2,16);
        return Integer.toBinaryString(i^j);
    }
    // Aufgabe 4 Faktorisierung
    public static String factor(int n){
        for(int i=2;i<=n;i++){
            if(n % i == 0){
                return i + "*" + factor(n / i);
            }
        }
        return "";
    }
    //Aufgabe 5 Vigenere
    public static String vigenere(String cipher, String key){
        String result = "";
        int  a = (int)'a';
        cipher = cipher.toLowerCase();
        key    = key.toLowerCase();
        int cipherLen = cipher.length();
        int keyLen = key.length();
        for (int i = 0; i < cipherLen; i++) {
            int offset = (int)cipher.charAt(i) - (int)key.charAt(i % keyLen);
            char clear_text = (char) ((offset >= 0)? a + offset : a + offset + 26);
            result += clear_text;
        }
        return result;
    }
    // Aufgabe 6 DES: Rundenschluessel-Berechnung
    public static String DESkeyschedule(TaskObject task){
        int round  = task.getIntArray(0);
        int[] key = stringToBit(task.getStringArray(0));
        DES des = new DES(new int[64], key);
        des.generateKey();
        return bitToString(des.keySchedule[round - 1]);
    }

    // Aufgabe 7. DES: R-Block-Berechnung
    public static String DESRBlock(TaskObject task){
        int[] bits = stringToBit(task.getStringArray(0));
        int round  = task.getIntArray(0);
        DES des = new DES(bits, new int[64]);
        des.generateKey();
        des.cipherBits();
        return bitToString(des.bitRightBlock[round]);
    }

    // Aufgabe 8. DES: Feistel-Funktion
    public static String DESfeistel(TaskObject task){
        int[] bits  = stringToBit(task.getStringArray(0));
        int[] key   = stringToBit(task.getStringArray(1));
        int[] left  = Arrays.copyOfRange(bits, 0, 32);
        int[] right = Arrays.copyOfRange(bits, 32, 64);
        DES des = new DES(new int[64], new int[64]);
        return bitToString(des.xor(left, des.feistel(right, key)));

    }

    // Aufgabe 9. DES: Berechnung einer Runde
    public static String DEScomplete(TaskObject task){
        int[] bits  = stringToBit(
                task.getStringArray(0) + task.getStringArray(1)
        );
        int[] key   = stringToBit(task.getStringArray(2));
        int round = task.getIntArray(0);
        DES des = new DES(bits, key);
        des.generateKey();
        des.cipherBits();
        return  bitToString(des.bitLeftBlock[round -1]) + bitToString(des.bitRightBlock[round -1]);
    }

    // Utils
    private static int[] stringToBit(String str){
        int len   = str.length();
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = str.charAt(i) - '0';
        }
        return arr;
    }

    private static String bitToString(int[] arr){
        String str = "";
        for (int anArr : arr) {
            str += Integer.toString(anArr);
        }
        return str;
    }
}
