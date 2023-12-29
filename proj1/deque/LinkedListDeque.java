package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private static class IntNode<T>{
        public T data;
        public IntNode<T> pre;
        public IntNode<T> next;
        public IntNode(T i){
            this.data = i;
            this.pre = null;
            this.next = null;
        }
    }
    private IntNode<T> sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new IntNode<>(null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }
    public void addFirst(T item){
        IntNode<T> first = new IntNode<>(item);
        sentinel.next.pre = first;
        first.next = sentinel.next;
        first.pre = sentinel;
        sentinel.next = first;
        size += 1;
    }

    public void addLast(T item){
        IntNode<T> last = new IntNode<>(item);
        sentinel.pre.next = last;
        last.pre = sentinel.pre;
        last.next = sentinel;
        sentinel.pre = last;
        size += 1;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        IntNode<T> cur = sentinel.next;
        while (cur != sentinel){
            System.out.print(cur.data + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public T removeFirst(){
        if (isEmpty()){return null;}
        T firstData = sentinel.next.data;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size -= 1;
        return firstData;
    }

    public T removeLast(){
        if (isEmpty()){return null;}
        T lastData = sentinel.pre.data;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        size -= 1;
        return lastData;
    }

    public T get(int index){
        if (index >= size()){return null;}
        IntNode<T> cur = sentinel.next;
        for (int i = 0; i < index; i++){
            cur = cur.next;
        }
        return cur.data;
    }

    public T getRecursive(int index){
        IntNode<T> cur = this.sentinel.next;
        return recursion(cur, index);
    }

    private T recursion(IntNode<T> cur, int index){
        /* index溢出，从尾部又去到sentinel */
        if (cur == sentinel){
            return null;
        }
        if (index == 0){
            return cur.data;
        }
        return recursion(cur.next, index - 1);
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private IntNode<T> cur;
        public LinkedListDequeIterator() {
            cur = sentinel;
        }
        public boolean hasNext() {
            return cur.next != sentinel;
        }
        public T next() {
            cur = cur.next;
            return cur.data;
        }
    }

    public Iterator<T> iterator(){
        return new LinkedListDequeIterator();
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
