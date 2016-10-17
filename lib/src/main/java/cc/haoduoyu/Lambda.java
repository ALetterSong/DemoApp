package cc.haoduoyu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JButton;

/**
 * http://www.importnew.com/16436.html
 *
 * Created by xiepan on 2016/10/17.
 */

public class Lambda {

    /**
     * 使用lambda表达式替换匿名类
     */
    private static void example1() {
        System.out.println("\n【使用lambda表达式替换匿名类】");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8 ");
            }
        }).start();

        new Thread(() -> {
            System.out.println("In Java8");
        }).start();
    }

    /**
     * lambda表达式进行事件处理
     */
    private static void example2() {
        JButton show = new JButton("Show");

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event handler without lambda");
            }
        });

        show.addActionListener((e) -> {
            System.out.println("Event handler with lambda");
        });
    }

    /**
     * 使用lambda表达式对列表进行迭代
     */
    private static void example3() {
        System.out.println("\n【使用lambda表达式对列表进行迭代】");

        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.println(feature);
        }

        features.forEach(n -> System.out.println(n));
        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        // 看起来像C++的作用域解析运算符
        features.forEach(System.out::println);
    }

    /**
     * 使用lambda表达式和函数式接口Predicate
     */
    @SuppressWarnings("unchecked")
    private static void example4() {
        System.out.println("\n【使用lambda表达式和函数式接口Predicate】");

        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("Languages which ends with a :");
        filter(languages, (str) -> str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language :");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4 :");
        filter(languages, (str) -> str.length() > 4);
    }

    private static void filter(List<String> names, Predicate<String> condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    private static void lambdaFilter(List<String> names, Predicate<String> condition) {
        names.stream()
                .filter(condition)
                .forEach(name -> System.out.println(name + " "));
    }

    /**
     * 在lambda表达式中加入Predicate
     */
    @SuppressWarnings("unchecked")
    private static void example5() {
        System.out.println("\n【在lambda表达式中加入Predicate】");

        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        Predicate<String> startWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        languages.stream()
                .filter(startWithJ.and(fourLetterLong))
                .forEach((n) -> System.out.println(n));
    }

    /**
     * 使用lambda表达式的Map和Reduce
     */
    private static void example6() {
        System.out.println("\n【使用lambda表达式的Map和Reduce】");

        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            System.out.println(price);
        }

        costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.stream()
                .map((cost) -> cost + .12 * cost)
                .forEach(System.out::println);
    }

    /**
     * 通过过滤创建一个String列表
     */
    private static void example7() {
        System.out.println("\n【通过过滤创建一个String列表】");


        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        List<String> filtered = languages
                .stream()
                .filter(x -> x.length() > 5)
                .collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", languages, filtered);
    }

    /**
     * 对列表的每个元素应用函数
     */
    private static void example8() {
        System.out.println("\n【对列表的每个元素应用函数】");
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");

        String G7Countries = G7.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    /**
     * 复制不同的值，创建一个子列表
     */
    private static void example9() {
        System.out.println("\n【复制不同的值，创建一个子列表】");

        List<Integer> numbers = Arrays.asList(8, 9, 10, 11, 12, 7, 8, 9);
        List<Integer> distinct = numbers.stream()
                .map(i -> i * i)
                .distinct()
                .collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    }

    /**
     * 计算集合元素的最大值、最小值、总和以及平均值
     */
    private static void example10() {
        System.out.println("\n【计算集合元素的最大值、最小值、总和以及平均值】");

        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 23, 29);
        IntSummaryStatistics stats =
                primes.stream()
                        .mapToInt((x) -> x)
                        .summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }


    public static void main(String args[]) {
        example1();
        example2();
        example3();
        example4();
        example5();
        example6();
        example7();
        example8();
        example9();
        example10();
    }
}
