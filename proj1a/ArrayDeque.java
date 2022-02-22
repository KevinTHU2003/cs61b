public class ArrayDeque<type> {
    private type[] items;
    private int first;
    private int last;
    private int size;

    public ArrayDeque() {
        items = (type[]) new Object[8];
        first = 0;
        last = 0;
        size = 0;
    }

    private void resize(int len) {
        type[] newitems = (type[]) new Object[len];
        System.arraycopy(items, first, newitems, 0, items.length - first);
        System.arraycopy(items, 0, newitems, items.length - first, len);
        items = newitems;
    }

    public void addFirst(type x) {
        size += 1;
        if(size >= items.length) {
            resize(size * 2);
        }
        if(first == 0) {
            first = items.length - 1;
        } else {
            first -= 1;
        }
        items[first] = x;
    }

    public void addLast(type x) {
        size += 1;
        if(size >= items.length) {
            resize(size * 2);
        }
        if(last == items.length - 1) {
            last = 0;
        } else {
            last += 1;
        }
        items[last] = x;
    }

    public boolean isEmpty() {
        if(size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int pos = first;
        for(int i = 0; i < size; i++) {
            System.out.println(items[pos]);
            if(pos == last) {
                break;
            }
            pos = (pos + 1) % items.length;
        }
    }

    public type removeFirst() {
        if(size == 0) {
            return null;
        }
        size -= 1;
        type firstitem = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        return firstitem;
    }

    public type removeLast() {
        if(size == 0) {
            return null;
        }
        size -= 1;
        type lastitem = items[last];
        items[last] = null;
        if(last == 0){
            last = items.length - 1;
        } else {
            last -= 1;
        }
        return lastitem;
    }

    public type get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        return items[(first + index) % items.length];
    }
}
