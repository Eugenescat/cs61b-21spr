package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */

public class TestBuggyAList {
  public static void testThreeAddThreeRemove(){
      AListNoResizing<Integer> al = new AListNoResizing<>();
      BuggyAList<Integer> bl = new BuggyAList<>();

      al.addLast(4);
      bl.addLast(4);
      al.addLast(5);
      bl.addLast(5);
      al.addLast(6);
      bl.addLast(6);

      int i = al.removeLast();
      int t = bl.removeLast();
      assertEquals(i, t);

      int p = al.removeLast();
      int q = bl.removeLast();
      assertEquals(p, q);

      int r = al.removeLast();
      int s = bl.removeLast();
      assertEquals(r, s);
  }

  public static void randomizedTest(){
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> suspected = new BuggyAList<>();

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
              // getLast
              if (L.size() == 0){continue;}
              int lastL = L.getLast();
              int lastS = suspected.getLast();
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
      randomizedTest();
  }
}
