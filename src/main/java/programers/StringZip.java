package programers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//문제 설명
//데이터 처리 전문가가 되고 싶은 어피치는 문자열을 압축하는 방법에 대해 공부를 하고 있습니다. 최근에 대량의 데이터 처리를 위한 간단한 비손실 압축 방법에 대해 공부를 하고 있는데, 문자열에서 같은 값이 연속해서 나타나는 것을 그 문자의 개수와 반복되는 값으로 표현하여 더 짧은 문자열로 줄여서 표현하는 알고리즘을 공부하고 있습니다.
//간단한 예로 aabbaccc의 경우 2a2ba3c(문자가 반복되지 않아 한번만 나타난 경우 1은 생략함)와 같이 표현할 수 있는데, 이러한 방식은 반복되는 문자가 적은 경우 압축률이 낮다는 단점이 있습니다. 예를 들면, abcabcdede와 같은 문자열은 전혀 압축되지 않습니다. 어피치는 이러한 단점을 해결하기 위해 문자열을 1개 이상의 단위로 잘라서 압축하여 더 짧은 문자열로 표현할 수 있는지 방법을 찾아보려고 합니다.
//
//예를 들어, ababcdcdababcdcd의 경우 문자를 1개 단위로 자르면 전혀 압축되지 않지만, 2개 단위로 잘라서 압축한다면 2ab2cd2ab2cd로 표현할 수 있습니다. 다른 방법으로 8개 단위로 잘라서 압축한다면 2ababcdcd로 표현할 수 있으며, 이때가 가장 짧게 압축하여 표현할 수 있는 방법입니다.
//
//다른 예로, abcabcdede와 같은 경우, 문자를 2개 단위로 잘라서 압축하면 abcabc2de가 되지만, 3개 단위로 자른다면 2abcdede가 되어 3개 단위가 가장 짧은 압축 방법이 됩니다. 이때 3개 단위로 자르고 마지막에 남는 문자열은 그대로 붙여주면 됩니다.
//
//압축할 문자열 s가 매개변수로 주어질 때, 위에 설명한 방법으로 1개 이상 단위로 문자열을 잘라 압축하여 표현한 문자열 중 가장 짧은 것의 길이를 return 하도록 solution 함수를 완성해주세요.
//
//제한사항
//s의 길이는 1 이상 1,000 이하입니다.
//s는 알파벳 소문자로만 이루어져 있습니다.
//입출력 예
//s   result
//"aabbaccc"  7
//"ababcdcdababcdcd"  9
//"abcabcdede"    8 
//"abcabcabcabcdededededede"  14
//"xababcdcdababcdcd" 17
//입출력 예에 대한 설명
//입출력 예 #1
//
//문자열을 1개 단위로 잘라 압축했을 때 가장 짧습니다.
//
//입출력 예 #2
//
//문자열을 8개 단위로 잘라 압축했을 때 가장 짧습니다.
//
//입출력 예 #3
//
//문자열을 3개 단위로 잘라 압축했을 때 가장 짧습니다.
//
//입출력 예 #4
//
//문자열을 2개 단위로 자르면 abcabcabcabc6de 가 됩니다.
//문자열을 3개 단위로 자르면 4abcdededededede 가 됩니다.
//문자열을 4개 단위로 자르면 abcabcabcabc3dede 가 됩니다.
//문자열을 6개 단위로 자를 경우 2abcabc2dedede가 되며, 이때의 길이가 14로 가장 짧습니다.
//
//입출력 예 #5
//
//문자열은 제일 앞부터 정해진 길이만큼 잘라야 합니다.
//따라서 주어진 문자열을 x / ababcdcd / ababcdcd 로 자르는 것은 불가능 합니다.
//이 경우 어떻게 문자열을 잘라도 압축되지 않으므로 가장 짧은 길이는 17이 됩니다.

// 1개 단위부터 (문자열 length/2)개 단위까지 각각 압축해서 최소값을 출력   
// https://programmers.co.kr/learn/courses/30/lessons/60057

public class StringZip {
    public int solution(String s) {
        //System.out.println(s);
        int minLength = s.length();
        for (int unit = 1; unit <= s.length() / 2; unit++) { // for #01
            String composeStr = "";
            int repeatCnt = 1;
            
            String preStr = "";
            String postStr = s;
           
            for (int i = unit; i < s.length() + 1 ; i+=unit) { // for #02
                
                preStr = postStr.substring(0, unit);
                postStr = postStr.substring(unit); // unit ~ 문자열 끝까지 절단
//                System.out.println(unit + ", " + preStr + ", " +postStr + ", " + i);
                
                if(postStr.startsWith(preStr)) {
                    // i 위치에서 preStr 와 postStr 앞부분이 일치하는 경우
                    repeatCnt = repeatCnt + 1; // 반복횟수 1 추가
                }else {
                    // i 위치에서 preStr 와 postStr 앞부분이 일치하지 않는 경우 - 1. 문자열 끝에 길이가 안맞는 경우 , 2 문자가 다른경우 
                    if(repeatCnt > 1) { // 반복횟수가 1보다 큰경우 
                        composeStr = composeStr + String.valueOf(repeatCnt) + preStr;
                        repeatCnt = 1;
                    }else {
                        composeStr = composeStr + preStr;
                    }
                }
            }
            
            composeStr = composeStr + postStr;
            
//            System.out.println(unit + ", "+composeStr);
            
            minLength = Math.min(composeStr.length(), minLength);
        }
        
        return minLength;
    }
    
    public int solution2(String s) {
        int answer = 0;

        for(int i=1; i<=(s.length()/2)+1; i++){
            int result = getSplitedLength(s, i, 1).length();
            answer = i==1 ? result : (answer>result?result:answer);
        }

        return answer;
    }
    
    /**
     * 
     * @param s 문자열
     * @param n 단위 
     * @param repeat 반복된 횟수
     * @return
     */
    public String getSplitedLength(String s, int n, int repeat){
        if(s.length() < n) return s; // 기저 조건
        String result = "";
        String preString = s.substring(0, n);
        String postString = s.substring(n, s.length());

        // 불일치 -> 현재까지 [반복횟수 + 반복문자] 조합
        if(!postString.startsWith(preString)){
            if(repeat ==1) return result += preString + getSplitedLength(postString, n, 1); 
            return result += Integer.toString(repeat) + preString + getSplitedLength(postString, n, 1);
        }

        return result += getSplitedLength(postString, n, repeat+1);
    }
    
    public static void main(String[] args) {
        
        System.out.println(new StringZip().solution("a"));
        System.out.println(new StringZip().solution("aabbaccc"));                   // 7
        System.out.println(new StringZip().solution("ababcdcdababcdcd"));           // 9
        System.out.println(new StringZip().solution("abcabcdede"));                 // 8
        System.out.println(new StringZip().solution("abcabcabcabcdededededede"));   // 14
        System.out.println(new StringZip().solution("xababcdcdababcdc"));           // 16
        System.out.println(new StringZip().solution("aaaaaaaaaa"));                 // 3
        
//        System.out.println(new StringZip().getSplitedLength("aaaabbaccc", 2, 1));
        
//        System.out.println(new StringZip().numJewelsInStones("ab","aaaabbaccc"));
    }
    
    // https://leetcode.com/problems/jewels-and-stones
    public int numJewelsInStones(String J, String S) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        
        for (char key : S.toCharArray()) {
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        int result = 0;
        
        for (char key : J.toCharArray()) {
            result = result + map.getOrDefault(key, 0);
        }
        
        return result;
    }
    
    int[] lens;
    String s;

    public int 이효준(String s){
        if(s.length()==1) return 1;
        
        lens=new int[s.length()/2];
        this.s=s;
    
        for(int i=0; i<lens.length; i++){
            int len = getLen(i+1);
            lens[i]=len;
        }
        
        Arrays.sort(lens);

        return lens[0];
    }

    private int getLen(int slice) {

        String sum=""; String counted=""; 
        if(slice<=s.length())
            counted=s.substring(0,slice);
        else{
            return s.length();
        }

        int count=0; int i;
        for(i=0; i<s.length(); i+=slice){
            String current="";
            if(i+slice <= s.length()) current=s.substring(i,i+slice);
            else current=s.substring(i,s.length());
            
            if(current.equals(counted)){
                count++;
            }else{
                if(count>1) sum+=count;
                sum+=counted;
                counted=current;
                count=1;
            }    
        }

        if(i == s.length()){
            if(count>1) sum+=count;
            sum+=counted;
        }else{
            sum+=s.substring(i-slice,s.length());
        }

        return sum.length();
    }
}
