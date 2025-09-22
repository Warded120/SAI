package com.ivan.sai.lab1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TowerOfHanoi {
    public static void main(String[] args)
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
        log.info("Move disk {} from rod {} to rod {}", n, fromRod, toRod);
        towerOfHanoi(n - 1, auxiliaryRod, toRod, fromRod);
    }
}