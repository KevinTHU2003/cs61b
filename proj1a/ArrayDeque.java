public class ArrayDeque<T> {
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
        int pos1 = first;
        int pos2 = first;
        for (int i = 0; i < size; i++) {
            newitems[pos2] = items[pos1];
            pos1 = (pos1 + 1) % items.length;
            pos2 = (pos2 + 1) % newitems.length;
        }
        items = newitems;
    }

    public void addFirst(T x) {
        /*if (size == 0) {
            size += 1;
            items[first] = x;
            return;
        }*/
        size += 1;
        if (size >= items.length) {
            resize(items.length * 2);
        }
        first = (first - 1 + items.length) % items.length;
        items[first] = x;
    }

    public void addLast(T x) {
        size += 1;
        if (size >= items.length) {
            resize(items.length * 2);
        }
        items[(first + size - 1) % items.length] = x;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int pos = first;
        for (int i = 0; i < size; i++) {
            System.out.println(items[pos] + " ");
            pos = (pos + 1) % items.length;
        }
    }

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

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(first + index) % items.length];
    }

    /*public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        a.addFirst(10);
        a.addFirst(9);
        a.addFirst(8);
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);
        a.addFirst(4);
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addFirst(0);
        a.addLast(11);
        a.removeFirst();
        a.removeLast();
        a.printDeque();
        System.out.println(a.get(8));
    }*/
}
