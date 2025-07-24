import java.util.*;

class Solution {
    ArrayList<ArrayList<Integer>> adj;
    int[] numsXor, value;
    boolean[][] isAncestor;

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        numsXor = new int[n];
        value = nums.clone();
        isAncestor = new boolean[n][n];

        dfs(0, -1);
        int ans = Integer.MAX_VALUE;

        for (int c1 = 1; c1 < n; c1++) {
            for (int c2 = c1 + 1; c2 < n; c2++) {
                int val1, val2, val3;

                if (isAncestor[c1][c2]) {
                    // c1 is ancestor of c2
                    val1 = numsXor[c2];
                    val2 = numsXor[c1] ^ numsXor[c2];
                    val3 = numsXor[0] ^ numsXor[c1];
                } else if (isAncestor[c2][c1]) {
                    // c2 is ancestor of c1
                    val1 = numsXor[c1];
                    val2 = numsXor[c2] ^ numsXor[c1];
                    val3 = numsXor[0] ^ numsXor[c2];
                } else {
                    // disjoint
                    val1 = numsXor[c1];
                    val2 = numsXor[c2];
                    val3 = numsXor[0] ^ val1 ^ val2;
                }

                ans = Math.min(ans, getScore(val1, val2, val3));
            }
        }

        return ans;
    }

    private void dfs(int src, int par) {
        numsXor[src] = value[src];
        markAncestors(src, par, src);

        for (int child : adj.get(src)) {
            if (child == par) continue;
            dfs(child, src);
            numsXor[src] ^= numsXor[child];
        }
    }

    private void markAncestors(int src, int par, int ancestor) {
        isAncestor[ancestor][src] = true; // ✅ Corrected: ancestor of src
        for (int child : adj.get(src)) {
            if (child == par) continue;
            markAncestors(child, src, ancestor);
        }
    }

    private int getScore(int a, int b, int c) {
        int[] arr = {a, b, c};
        Arrays.sort(arr);
        return arr[2] - arr[0];
    }
}
