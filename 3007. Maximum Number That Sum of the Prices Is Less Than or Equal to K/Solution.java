public class Solution {
    // Define a long long type in Java
    static class LL {
        long value;
        
        LL(long value) {
            this.value = value;
        }
    }

    // Equivalent of vector<ll> bitCount
    static long[] bitCount;

    public static void getBits(long number) {
        if (number == 0)
            return;

        if (number == 1) {
            bitCount[0]++;
            return;
        }

        if (number == 2) {
            bitCount[0]++;
            bitCount[1]++;
            return;
        }

        long bitLen = (long) (Math.log(number) / Math.log(2));
        long nearestPowerTwo = (1L << bitLen);
        bitCount[(int) bitLen] += (number - nearestPowerTwo + 1);

        for (long i = 0; i < bitLen; i++) {
            bitCount[(int) i] += (nearestPowerTwo / 2);
        }

        number = number - nearestPowerTwo;
        getBits(number);
    }

    public static long findMaximumNumber(long threshold, int divisor) {
        long low = 0, high = (long) 1e15;

        long result = 0;
        while (low <= high) {
            long mid = low + (high - low) / 2;

            // Equivalent of initializing bitCount in C++
            bitCount = new long[65];
            Arrays.fill(bitCount, 0);

            getBits(mid);

            long accumulatedCount = 0;

            for (long i = 0; i < (long) (Math.log(mid) / Math.log(2)) + 1; i++) {
                if ((i + 1) % divisor == 0)
                    accumulatedCount += bitCount[(int) i];
            }

            if (accumulatedCount <= threshold) {
                result = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }
}