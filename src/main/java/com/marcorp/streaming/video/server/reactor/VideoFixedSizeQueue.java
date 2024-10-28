package com.marcorp.streaming.video.server.reactor;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class VideoFixedSizeQueue<E> extends AbstractQueue<E> {
    final E[] items;
    private int count;
    private int start = 0;

    @SuppressWarnings("unchecked")
    public VideoFixedSizeQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        items = (E[]) new Object[capacity];
        count = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = start;

            @Override
            public boolean hasNext() {
                return index < start + count;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return items[index++ % items.length];
            }
        };
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public synchronized boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Queue doesn't allow nulls");
        }
        if (count == items.length) {
            // Podrías lanzar una excepción o manejar esto de otra forma
            throw new IllegalStateException("Queue is full");
        }
        items[(start + count) % items.length] = e;
        count++;
        return true;
    }

    @Override
    public synchronized E poll() {
        if (count <= 0) {
            return null;
        }
        E item = items[start];
        items[start] = null; // Limpieza opcional
        start++;
        count--;
        return item;
    }

    @Override
    public synchronized E peek() {
        if (count <= 0) {
            return null;
        }
        return items[start];
    }
}