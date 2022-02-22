public class LinkedListDeque<type> {
    private class node {
        public type item;
        public node prev;
        public node next;

        public node(type i, node p, node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    public node sentinel;  //first_node = sentinel.next; last_node = sentinel.prev
    public int size;

    public LinkedListDeque() {
        sentinel = new node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(type x) {
        node first = new node(x, sentinel, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    public void addLast(type x) {
        node last = new node(x, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
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
        node p = sentinel.next;
        while(p != sentinel) {
            System.out.println(p.item + " ");
            p = p.next;
        }
    }

    public type removeFirst() {
        if(size == 0) {
            return null;
        }
        node first = sentinel.next;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first.item;
    }

    public type removeLast() {
        if(size == 0) {
            return null;
        }
        node last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return last.item;
    }

    public type get(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        node p = sentinel.next;
        for(int i=0; i<index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private type getRecursive(node p, int index) {
        if(index < 0 || index >= size) {
            return null;
        } else if(index == 0) {
            return p.item;
        } else {
            return getRecursive(p.next, index-1);
        }
    }

    public type getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }
}
