package com.marcorp.streaming.video.server.reactor;

import java.util.AbstractQueue;
import java.util.Iterator;

public class VideoFixedSizeQueueOld<E> extends AbstractQueue<E> {
    final E[] items;
    int count;

    public VideoFixedSizeQueueOld(int capacity) {
        super();
        items = (E[]) new Object[capacity];
        count = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Queue doesn't allow nulls");
        }
        if (count == items.length) {
            this.poll();
        }
        this.items[count] = e;
        count++;
        return true;
    }

    @Override
    public E poll() {
        if (count <= 0) {
            return null;
        }
        E item = items[0];
        shiftLeft();
        count--;
        return item;
    }

    private void shiftLeft() {
        int i = 1;
        while (i < items.length) {
            if (items[i] == null) {
                break;
            }
            items[i - 1] = items[i];
            i++;
        }
    }

    @Override
    public E peek() {
        if (count <= 0) {
            return null;
        }
        return items[0];
    }
}
