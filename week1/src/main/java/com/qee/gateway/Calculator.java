package com.qee.gateway;

import java.util.function.BiFunction;

/**
 * @ProjectName: learning
 * @Package: com.qee
 * @ClassName: Calculator
 * @Description:
 * @Date: 2021/9/14 10:07 上午
 * @Version: 1.0
 */
public class Calculator {

    public static int plus(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multi(int a, int b) {
        return a * b;
    }

    public void forEach(int a, int b, BiFunction<Integer, Integer, Integer>... biFunctions) {
        for (BiFunction bf : biFunctions) {
            Object c = bf.apply(a, b);
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.forEach(100, 200, Calculator::plus, Calculator::subtract, Calculator::multi);
    }
}
