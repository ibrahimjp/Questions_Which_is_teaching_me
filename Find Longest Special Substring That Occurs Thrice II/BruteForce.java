import java.util.*;

class BruteForce {
    public int maximumLength(String s) {
        int n=s.length();
        int left = 0;
        int right = n-1;
        while(left < right){
            int mid = left + (right - left) / 2;
            if(isThisLenSubsTringPossible(mid,s)){
                left = mid;//this can be our possible answer
            }else{
                right = mid - 1;
            }
        }
        return left;
    }
    public boolean isThisLenSubsTringPossible(int len,String substr){
        Map<String,Integer> validMap = new HashMap<>(); // to store the valid substrings of length len
        for(int i=0;i<len;i++){
            String str = substr.substring(i,i+len);
            validMap.put(str,validMap.getOrDefault(str,0) + 1);
        }
        for(String key : validMap.keySet()){
            if(validMap.get(key) >= 3){
                if(checkSubstring(key)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean checkSubstring(String str){
        char ch = str.charAt(0);
        for(int i=1;i<str.length();i++){
            if(str.charAt(i) != ch){
                return false;
            }
        }
        return true;
    }
}