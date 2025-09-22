package com.ivan.sai;

class Main {
    public static void main(String args[])
    {
        int diskNumber = 3;

        towerOfHanoi(diskNumber, 'A', 'C', 'B');
    }

    private static void towerOfHanoi(int n, char fromRod,
                                     char toRod, char auxiliaryRod)
    {
        if (n == 0) {
            return;
        }
        towerOfHanoi(n - 1, fromRod, auxiliaryRod, toRod);
        System.out.println("Move disk " + n + " from rod "
                + fromRod + " to rod "
                + toRod);
        towerOfHanoi(n - 1, auxiliaryRod, toRod, fromRod);
    }
}