class Solution {
    public int[] maximumSubarrayXor(int[] nums, int[][] queries) {
        int n = nums.length;
        int[][] xScore = new int[n][n];
        for(int i=0;i<n;i++)xScore[i][i] = nums[i];
        for(int len = 2;len<=n;len++){
            for(int i=0;i+len-1<n;i++){
                int j = i+len-1;
                xScore[i][j] = xScore[i][j-1] ^ xScore[i+1][j];
            }
        }

        for(int len = 2;len<=n;len++){
            for(int i=0;i+len-1<n;i++){
                int j = i+len-1;
                xScore[i][j] = Math.max(xScore[i][j-1] , Math.max(xScore[i][j-1] ^ xScore[i+1][j],xScore[i+1][j]));
            }
        }
        int[] ans = new int[queries.length];
        for(int i =0;i<queries.length;i++){
            int[] que = queries[i];
            int l = que[0];
            int r = que[1];
            ans[i] = xScore[l][r];          
        }
        return ans;
    }
}