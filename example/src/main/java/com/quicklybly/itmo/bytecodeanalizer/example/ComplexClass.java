package com.quicklybly.itmo.bytecodeanalizer.example;

import java.util.ArrayList;

public class ComplexClass extends ArrayList<String> {

    private final int x = 0;
    private final int y = 0;

    public void heavyMethod() {
        if (size() > 10) {
            System.out.println("List is big");
        } else {
            System.out.println("List is small");
        }
        new Thread(() -> System.out.println("Async")).start();
    }

    @Override
    public int size() {
        return super.size();
    }
}
