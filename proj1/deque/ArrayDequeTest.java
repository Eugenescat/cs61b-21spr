package deque;

import edu.princeton.cs.algs4.StdRandom;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hug.
 */

public class ArrayDequeTest {
  public static void testThreeAddThreeRemove(){
      LinkedListDeque<Integer> al = new LinkedListDeque<>();
      ArrayDeque<Integer> bl = new ArrayDeque<>();

      al.addLast(4);
      bl.addLast(4);
      al.addLast(5);
      bl.addLast(5);
      al.addLast(6);
      bl.addLast(6);

      boolean u = al.equals(bl);
      assertTrue(u);

      int i = al.removeLast();
      int t = bl.removeLast();
      assertEquals(i, t);

      int p = al.removeLast();
      int q = bl.removeLast();
      assertEquals(p, q);

      int r = al.removeLast();
      int s = bl.removeLast();
      assertEquals(r, s);

      System.out.print(al.getClass());
  }


  public static void randomizedTest(){
      LinkedListDeque<Integer> L = new LinkedListDeque<>();
      ArrayDeque <Integer> suspected = new ArrayDeque<>() ;

      int N = 5000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 4);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              suspected.addLast(randVal);
              assertEquals(L.size(), suspected.size());
          } else if (operationNumber == 1) {
              // size
              int sizeL = L.size();
              int sizeS = suspected.size();
              assertEquals(sizeL, sizeS);
          } else if (operationNumber == 2){
              // get
              if (L.size() < 6){continue;}
              int lastL = L.get(5);
              int lastS = suspected.get(5);
              assertEquals(lastL, lastS);
          } else if (operationNumber == 3){
              // removeLast
              if (L.size() == 0){continue;}
              int lastL = L.removeLast();
              int lastS = suspected.removeLast();
              assertEquals(lastL, lastS);
          }
      }
  }

  public static void main(String[] args) {
      //randomizedTest();
      testThreeAddThreeRemove();
  }
}
