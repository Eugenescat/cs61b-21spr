package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private int size;
    private T[] array;
    private int firstIndex;
    private int lastIndex;

    public ArrayDeque(){
        size = 0;
        array = (T[]) new Object[8];
        firstIndex = 0;
        lastIndex = 1;
    }
    public void addFirst(T item){
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }
        array[firstIndex] = item;
        firstIndex = (firstIndex - 1 + array.length) % array.length;
        size += 1;
    }

    public void addLast(T item){
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }
        array[lastIndex] = item;
        lastIndex = (lastIndex + 1) % array.length;
        size += 1;
    }

    private void resize(int n){
        T[] biggerArray = (T[]) new Object[n];
        /*
        if (firstIndex > 0 && lastIndex < firstIndex){
            System.arraycopy(array, 0, biggerArray, 0, lastIndex + 1);
            System.arraycopy(array, firstIndex, biggerArray, firstIndex + n / 2, array.length - firstIndex);
            firstIndex += n / 2;
        }else{
            System.arraycopy(array, firstIndex, biggerArray, firstIndex, size);
        }
        array = biggerArray;
        */
        // Start from firstIndex and copy elements in order, wrapping around if necessary
        for (int i = 0; i < size; i++) {
            biggerArray[i] = array[(firstIndex + 1 + i) % array.length];
        }
        array = biggerArray;
        // Elements now start from the beginning of the new array
        if (size == 0){
            firstIndex = 0;
            lastIndex = 1;
        }else{
            firstIndex = (array.length - 1) % array.length;
            lastIndex = size;
        }

    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int p = 0; p < size; p++){
            System.out.print(array[(firstIndex + 1 + p) % array.length] + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        if (size == 0){
            return null;
        }
        int theFirstElementIndex = (firstIndex + 1) % array.length;
        T theFirstElement = array[theFirstElementIndex];
        array[theFirstElementIndex] = null;
        firstIndex = theFirstElementIndex;
        size -= 1;
        if (size < (array.length / 4)){
            resize(array.length / 4);
        }
        return theFirstElement;
    }

    public T removeLast(){
        if (size == 0){
            return null;
        }
        int theLastElementIndex = (lastIndex - 1 + array.length) % array.length;
        T theLastElement = array[theLastElementIndex];
        array[theLastElementIndex] = null;
        lastIndex = theLastElementIndex;
        size -= 1;
        if (size < (array.length / 4)){
            resize(array.length / 4);
        }
        return theLastElement;
    }

    public T get(int index){
        if (index > size - 1) {
            return null;
        }
        int actualIndex = (index + firstIndex + 1) % array.length;
        return array[actualIndex];
    }

    private class ArrayDequeIterator implements Iterator<T> {
        int cur;
        int count;
        public ArrayDequeIterator() {
            cur = firstIndex;
            count = 0;
        }

        public boolean hasNext() {
            return count < size;
        }
        public T next() {
            cur = (cur + 1) % array.length;
            count += 1;
            return array[cur];
        }
    }

    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        // if o not deque, return false
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (this.size() != other.size()){
            return false;
        }
        for (int i = 0; i < size; i++){
            if (!this.get(i).equals(other.get(i))){
                return false;
            }
        }
        return true;
    }
}
