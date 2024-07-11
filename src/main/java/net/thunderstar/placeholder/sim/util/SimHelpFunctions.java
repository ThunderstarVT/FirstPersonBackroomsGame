package net.thunderstar.placeholder.sim.util;

import java.util.List;
import java.util.Random;

public class SimHelpFunctions {
    public static  <T> T randomElement(T[] array) {
        Random generator = new Random();
        int randomIndex = generator.nextInt(array.length);
        return array[randomIndex];
    }
}
