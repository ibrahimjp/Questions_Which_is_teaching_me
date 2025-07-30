import java.util.*;

class Solution {
    final int MOD = (int) 1e9 + 7;
    final int P = 31;

    long[] pwr_p;
    long[] inv_p;
    long[] pref;

    public int FastPower(long a, long b) {
        a %= MOD;
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) ans = (ans * a) % MOD;
            a = (a * a) % MOD;
            b >>= 1;
        }
        return (int) ans;
    }

    public boolean exist(int sz, String s) {
        int n = s.length();
        long sum = 0;
        for (int j = 1; j <= sz; j++) {
            sum = (sum + pwr_p[j]) % MOD;
        }

        long[] expected_hash = new long[27];
        for (int j = 1; j <= 26; j++) {
            expected_hash[j] = (sum * j) % MOD;
        }

        int[] hash_cnt = new int[27];

        for (int l = 1, r = sz; r <= n; l++, r++) {
            int ch = s.charAt(l - 1) - 'a' + 1;
            long hash = (pref[r] - pref[l - 1] + MOD) % MOD;
            hash = (hash * inv_p[l - 1]) % MOD;

            if (hash != expected_hash[ch]) continue;

            hash_cnt[ch]++;
            if (hash_cnt[ch] == 3) return true;
        }

        return false;
    }

    public int maximumLength(String s) {
        int n = s.length();
        pwr_p = new long[n + 2];
        inv_p = new long[n + 2];
        pref = new long[n + 2];

        pwr_p[0] = 1;
        for (int j = 1; j <= n; j++) {
            pwr_p[j] = (pwr_p[j - 1] * P) % MOD;
        }

        inv_p[n] = FastPower(pwr_p[n], MOD - 2);
        for (int j = n - 1; j >= 0; j--) {
            inv_p[j] = (inv_p[j + 1] * P) % MOD;
        }

        for (int j = 1; j <= n; j++) {
            int val = s.charAt(j - 1) - 'a' + 1;
            pref[j] = (pref[j - 1] + (val * pwr_p[j]) % MOD) % MOD;
        }

        int l = 1, r = n - 2;
        while (l < r) {
            int m = (l + r) / 2;
            if (exist(m + 1, s)) l = m + 1;
            else r = m;
        }

        return exist(l, s) ? l : -1;
    }
}
