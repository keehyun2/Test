package programers;

import java.util.HashMap;
import java.util.Map;

//문제 설명
//수많은 마라톤 선수들이 마라톤에 참여하였습니다. 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.
//
//마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때, 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.
//
//제한사항
//마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
//completion의 길이는 participant의 길이보다 1 작습니다.
//참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
//참가자 중에는 동명이인이 있을 수 있습니다.

public class Hash01 {
    public String solution(String[] participant, String[] completion) {
        String answer = "";
        // 완주자 동명이인끼리 count 해서 hash 저장
        Map<String, Integer> ptMap = new HashMap<String, Integer>();
        for (String name : completion) { // 첫번째 for 문 
            if(ptMap.containsKey(name)) {
                ptMap.put(name, ptMap.get(name) + 1);
            }else {
                ptMap.put(name, 1);
            }
        }
        
        // 완주자에 없는 참가자 확인
        for (String name : participant) { // 두번쨰 for 문 
            if(ptMap.get(name) == null || ptMap.get(name) == 0 ) {
                answer = name;
                break;
            }else {
                ptMap.put(name, ptMap.get(name) - 1);
            }
        }
        return answer;
    }
    
    public static void main(String[] args) {
        String[] arr1 = new String[]{"marina", "josipa", "nikola", "vinko", "marina","filipa"};
        String[] arr2 = new String[]{"josipa", "filipa", "marina", "marina","nikola"};
        
        System.out.println(new Hash01().solution(arr1, arr2));
    }
}
