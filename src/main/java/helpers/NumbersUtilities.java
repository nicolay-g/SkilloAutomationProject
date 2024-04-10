package helpers;

import java.util.Random;

public class NumbersUtilities {
    public static int generateRandomPositiveNumber(int maxValue) {
        Random random = new Random();
        return random.nextInt(maxValue);
    }
}
