class Solution {
    public int minOrAfterOperations(int[] nums, int k) {
        int n=nums.length;
        int ans=0;
        int rev_ans=0;
        for(int i=29;i>=0;i--){
            int new_target_rev_ans = rev_ans | (1 << i);
            boolean[] target = new boolean[n+1];
            for(int j=0;j<n;j++){
                if((new_target_rev_ans & nums[j]) == 0)continue;
                target[j]=true;
            }
            if(isPossibleToMakeUnderK(new_target_rev_ans,nums,target) <= k){
                rev_ans  = new_target_rev_ans; // we can take this number
            }else{
                ans |= 1 << i; // we can't take this number
            }
        }
        return ans;
    }
    public int isPossibleToMakeUnderK(int target,int[] nums,boolean[] taken){
        int ans=0;
        for(int j=0;j<nums.length;){
            int st = j;
            if (!taken[j]) {
                j++;
                continue;
            }
            int all = nums[j];
            while(j < nums.length && taken[j] && (target & all) != 0){
                all &= nums[j];
                j++;
            }

            if((target & all) == 0){
                ans += (j - st - 1);
            }else{
                ans += j - st;
            }
        }
        return ans;
    }
}