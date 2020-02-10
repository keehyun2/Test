package acmicpc.greedy.coin0;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

//    준규가 가지고 있는 동전은 총 N종류이고, 각각의 동전을 매우 많이 가지고 있다.
//    
//    동전을 적절히 사용해서 그 가치의 합을 K로 만들려고 한다. 이때 필요한 동전 개수의 최솟값을 구하는 프로그램을 작성하시오.
// int  -2,147,483,648 ~ 2147483647
//    첫째 줄에 N과 K가 주어진다. (1 ≤ N ≤ 10, 1 ≤ K ≤ 100000000)
//    
//    둘째 줄부터 N개의 줄에 동전의 가치 Ai가 오름차순으로 주어진다. (1 ≤ Ai ≤ 1,000,000, A1 = 1, i ≥ 2인 경우에 Ai는 Ai-1의 배수)

//    첫째 줄에 K원을 만드는데 필요한 동전 개수의 최솟값을 출력한다.

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = null;
    
    static List<Integer> list = new ArrayList<Integer>();
    
    static int N;
    static int K;

    public static void main(String[] args) throws NumberFormatException, IOException {

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); 
        K = Integer.parseInt(st.nextToken());
        
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(br.readLine()));
        }
        
        Collections.reverse(list); // 금액 큰 동전이 앞으로 오도록 처리
        
        int sum = 0;
        for (int i = N - 1; -1 < i ; i--) {
            // System.out.println(list.remove(0));
            int el = list.remove(0);
            sum = sum + K / el;
//            System.out.println(K + ", " + sum);
            if(K % el == 0) {
                break;
            }
            K = K % el;
        }
        System.out.println(sum);
        
//        System.out.println(dp(K));

        bw.flush();
    }
    
    // 재귀함수 - 만들고자 하는 금액을 파라미터로 전달받아서 사용되는 동전의 갯수를 반환
//    public static int dp(int targetAmt){
//        int sum = 0;
//        
//        for (int i = 0; i < N; i++) {
//            int rest = targetAmt - list.get(i); // 잔여금액  
//            if(rest > -1) { // 잔여 금액이 음수 가 아니면   
//                sum = targetAmt/list.get(i) + dp(targetAmt % list.get(i));
//                break;
//            }
//        }
//        
//        return sum;
//    }
}
