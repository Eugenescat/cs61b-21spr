public class BreakContinue {
    public static void windowPosSum(int[] a, int n) {
      int i = 0;
      while (i < a.length){
        if (a[i] <= 0){
          i = i + 1;
          continue;
        }
        else{
          int p = 1;
          while (p <= n && (i + p) < a.length){
            a[i] = a[i] + a[i + p];
            p = p + 1;
          }
          i = i + 1;
        }
      }
    }
  
    public static void main(String[] args) {
      System.out.println("Starting...");
      int[] a = {1, 2, -3, 4, 5, 4};
      int n = 3;
      windowPosSum(a, n);
  
      // Should print 4, 8, -3, 13, 9, 4
      System.out.println(java.util.Arrays.toString(a));
    }
  }