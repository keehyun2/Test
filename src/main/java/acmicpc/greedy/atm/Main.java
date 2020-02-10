package acmicpc.greedy.atm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = null;
    
    public static void solution() throws IOException {
        
        st = new StringTokenizer(br.readLine()); 
        int N = Integer.parseInt(st.nextToken()); // 첫째 줄에 사람의 수 N
        
        st = new StringTokenizer(br.readLine()); // Pi
        
        List<Integer> list = new ArrayList<Integer>(); 
        for (int i = 0; i < N; i++) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        
        Collections.sort(list); 
        
        int sum = 0;
        
        int size = list.size();
        
        for (int i = 0; i < size; i++) {
            sum = sum + list.get(i) * (size - i);
        }
        
        bw.write(String.valueOf(sum) + '\n');
        bw.flush();
    }
    
    public static void main(String[] args) throws IOException {
        solution();
    }
}
