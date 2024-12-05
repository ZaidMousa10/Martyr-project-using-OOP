package com.example.project0;
public class Martyr<T extends Comparable<T>> {
    private T[] list;
    private int count;

    public Martyr(int size) {
        list = (T[]) new Comparable[size];
        count = 0;
    }

    public void add(T data) {
        if (count == list.length) {
            resize();
        }
        list[count++] = data;
    }

    public boolean delete(int index) {
        if (index >= 0 && index < count) {
            System.arraycopy(list, index + 1, list, index, count - index - 1);
            count--;
            return true;
        }
        return false;
    }

    public T get(int index) {
        if (index >= 0 && index < count) {
            return list[index];
        }
        return null;
    }

    public int find(T data) {
        for (int i = 0; i < count; i++) {
            if (list[i].compareTo(data) == 0) {
                return i;
            }
        }
        return -1;
    }

    public T[] getList() {
        return list;
    }

    public int getCount() {
        return count;
    }


    private void resize() {
        T[] newList = (T[]) new Comparable[list.length * 2];
        System.arraycopy(list, 0, newList, 0, list.length);
        list = newList;
    }

    public void clear() {
        count = 0;
    }
}