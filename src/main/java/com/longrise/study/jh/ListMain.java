package com.longrise.study.jh;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListMain {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");
        list.add("DDD");
        list.add("EEE");
        ListIterator<String> listIterator = list.listIterator();
        System.out.println("有前向后输出:");
        while (listIterator.hasNext()) {
            System.out.print(listIterator.next() + "\t");
        }
        System.out.println("\n由后向前输出:");
        while (listIterator.hasPrevious()) {
            System.out.print(listIterator.previous() + "\t");
        }
    }

    /**
     * 注意: 虽然 ListIterator 可以是实现双向的输出, 但是必须先向后输出再向前输出
     */
}