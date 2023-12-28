package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    // 这里given的c是某种Comparator interface的实现方式对应的一个object
    public MaxArrayDeque(Comparator<T> c){
        super();
        this.comparator = c;
    }
    public T max(){
        // 调用public T max(Comparator<T> c)方法，但use this.comparator
        return max(comparator);
    }
    // use 任何一个外部given的comparator
    public T max(Comparator<T> c){
        if (isEmpty()){return null;}
        T maxItem = this.get(0);
        for (int i = 0; i < this.size(); i++){
            if (c.compare(this.get(i), maxItem) > 0){
                maxItem = this.get(i);
            }
        }
        return maxItem;
    }
}
