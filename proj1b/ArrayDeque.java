public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int first;
    private int size;
    //last = (first + size - 1) % items.length

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = 0;  //Deque为空的时候不能指向0位置啊，否则first就出现歧义了！
        size = 0;
    }

    private void resize(int len) {
        T[] newitems = (T[]) new Object[len];
        int pos = first;
        for (int i = 0; i < size; i++) {
            newitems[i] = items[pos];
            //为防止resize down的时候first的位置已经超出newitems的范围，
            //干脆还是把first安排到0位置(只需在resize函数里统一修改即可)
            pos = (pos + 1) % items.length;
        }
        items = newitems;
        first = 0;
    }

    @Override
    public void addFirst(T x) {
        size += 1;
        if (size >= items.length) {
            resize(items.length * 2);
        }
        first = (first - 1 + items.length) % items.length;
        items[first] = x;
    }

    @Override
    public void addLast(T x) {
        size += 1;
        if (size >= items.length) {
            resize(items.length * 2);
        }
        items[(first + size - 1) % items.length] = x;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int pos = first;
        for (int i = 0; i < size; i++) {
            System.out.println(items[pos] + " ");
            pos = (pos + 1) % items.length;
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T firstitem = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        if (items.length >= 16 && items.length / size >= 4) {
            resize(items.length / 2);
        }
        return firstitem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int last = (first + size - 1) % items.length;
        size -= 1;
        T lastitem = items[last];
        items[last] = null;
        if (items.length >= 16 && items.length / size >= 4) {
            resize(items.length / 2);
        }
        return lastitem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(first + index) % items.length];
    }

    /*public static void main(String[] args) {
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        ArrayDeque.addLast(0);
        ArrayDeque.removeLast();
        ArrayDeque.addFirst(2);
        ArrayDeque.removeLast()  ;
        ArrayDeque.addFirst(4);
        ArrayDeque.addFirst(5);
        ArrayDeque.addLast(6);
        ArrayDeque.addLast(7);
        ArrayDeque.addLast(8);
        ArrayDeque.addLast(9);
        ArrayDeque.addLast(10);
        ArrayDeque.removeFirst() ;
        ArrayDeque.addLast(12);
        ArrayDeque.addLast(13);
        ArrayDeque.get(1);
        ArrayDeque.get(0);
        ArrayDeque.removeLast();
        ArrayDeque.removeFirst();
        ArrayDeque.removeFirst();
        System.out.println(ArrayDeque.removeLast());
    }*/
}

