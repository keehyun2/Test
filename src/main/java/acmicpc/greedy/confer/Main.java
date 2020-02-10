package acmicpc.greedy.confer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

//    한 개의 회의실이 있는데 이를 사용하고자 하는 N개의 회의들에 대하여 회의실 사용표를 만들려고 한다. 각 회의 I에 대해 시작시간과 
//    끝나는 시간이 주어져 있고, 각 회의가 겹치지 않게 하면서 회의실을 사용할 수 있는 최대수의 회의를 찾아라. 단, 회의는 한번 시작하면 
//    중간에 중단될 수 없으며 한 회의가 끝나는 것과 동시에 다음 회의가 시작될 수 있다. 회의의 시작시간과 끝나는 시간이 같을 수도 있다. 
//    이 경우에는 시작하자마자 끝나는 것으로 생각하면 된다.

//    첫째 줄에 회의의 수 N(1 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N+1 줄까지 각 회의의 정보가 주어지는데 이것은 공백을 
//    사이에 두고 회의의 시작시간과 끝나는 시간이 주어진다. 시작 시간과 끝나는 시간은 int 보다 작거나 같은 자연수 또는 0이다.

//    첫째 줄에 최대 사용할 수 있는 회의 수를 출력하여라.

public class Main {
    
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = null;
    
    // 전략 - 끝나는 시간으로 오름차순 정렬, 이후에 같은시간 끝나는 경우 시작시간으로 오름차순 정렬
    // 중첩 되지 않고 끝나는 시간이 제일 빠른 회의를 선택 함. 그 후 갯수 파악
    public static void main(String[] args) throws IOException  {
        
        // 회의 (지역 클래스) 
        class Conference {
            int start;
            int end;
            
            Conference(int start, int end) {
                this.start = start;
                this.end = end;
            }
            
            @Override
            public String toString() {
                return this.start + ", " + this.end;
            }
        }
        
        List<Conference> list = new ArrayList<Conference>(); 
        
        st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()); 
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            list.add(new Conference(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        list.sort(new Comparator<Conference>() {
            @Override
            public int compare(Conference o1, Conference o2) {
                if(o1.end > o2.end) {
                    return 1;
                } else if(o1.end == o2.end) {
                    if(o1.start > o2.start) {
                        return 1;
                    }
                }
                return -1;
            }
        });
        
        int sum = 0;
        
        int endPt = -1;
        for (int i = 0; i < N; i++) {
            if(list.get(i).start >= endPt && list.get(i).end >= endPt) { 
                endPt = list.get(i).end;
                sum += 1;
                //System.out.println(list.get(i));
            }
        }
        System.out.println(sum);
        
    }
    
}

