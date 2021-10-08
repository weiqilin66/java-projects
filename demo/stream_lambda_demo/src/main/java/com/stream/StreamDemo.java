package com.stream;


import com.stream.entity.Student;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: Stream表示数据流,提供了过滤、排序、映射、规约等多种操作方法
 * 这些方法按照返回类型被分为两类：凡是返回Stream类型的方法，称之为中间方法（中间操作），其余的都是完结方法（完结操作）,完结方法返回一个某种类型的值，而中间方法则返回新的Stream
 *
 * 中间方法的调用通常是链式的，该过程会形成一个管道，当完结方法被调用时会导致立即从管道中消费值，这里我们要记住：Stream的操作尽可能以“延迟”的方式运行，也就是我们常说的“懒操作”，
 * 这样有助于减少资源占用，提高性能。对于所有的中间操作（除sorted外）都是运行在延迟模式下
 *
 * Stream不但提供了强大的数据操作能力，更重要的是Stream既支持串行也支持并行，并行使得Stream在多核处理器上有着更好的性能。
 *
 * 操作不会改变源Stream，而是生成新Stream
 **************************************************************************************
 *
 * Stream的使用过程有着固定的模式：
 *
 * 1.创建Stream
 * 2.通过中间操作，对原始Stream进行“变化”并生成新的Stream
 * 3.使用完结操作，生成最终结果
 *
 *4个常用接口(不用自定义接口作为函数载体)
 * Predicate<T>    输入一个参数，返回一个boolean值，                                 内置了许多用于逻辑判断的默认方法
 * Function<T, R>  接受一个参数，返回单一的结果。形成复合Funtion（有输入，有输出）结果    默认的方法（andThen）可将多个函数串在一起
 * Consumer<T>     对输入的参数进行操作。有输入没输出(可用于更新入参)
 * eg:
 *  Predicate<String> predicateStr = s -> s.length()>8
 *  Function<Integer, Integer> add = (i) -> {
 *             System.out.println("frist input:" + i);
 *             return i * 2;
 *         };
 *  Consumer<Integer> add5 = (p) -> {
 *             System.out.println("old value:" + p);
 *             p = p + 5;
 *             System.out.println("new value:" + p);
 *         };
 *         add5.accept(10);
 *
 *
 * @author: LinWeiQi
 */
public class StreamDemo {

    public static void main(String[] args) {
        /**
         * 所有Stream的操作必须以lambda表达式为参数;
         */
        // 流操作的集合必须确定泛型类型，否则jvm无法通过上下文判断参数类型
        List<String> list = Arrays.asList("1","ab","aa");
        /**
         * 过滤
         */
        System.out.println("----------filter---------------");
        final Stream<String> streamBean = list.stream().filter(s -> s.startsWith("a"));
        // 返回stream 说明是中间操作 无返回值为完结操作
        streamBean.forEach(System.out::println);
        // Lambda等价于 (结合Predicate接口)
        Predicate<String> predicate = s-> s.startsWith("a");
        list.stream().filter(predicate).forEach(System.out::println);

        System.out.println("-----------------------------");
        // 连续过滤
        list.stream().filter(predicate).filter(a->a.endsWith("a")).forEach(System.out::println);

        /**
         * 排序,原集合不变，新集合排序
         */
        System.out.println("----------sort---------------");
        List<Integer> list2 = Arrays.asList(11,22,33);
        list2.stream().sorted((s1, s2) -> s2.compareTo(s1)).forEach(System.out::println);
        // 逆序
        list2.stream().sorted((s1, s2) -> s1.compareTo(s2)).forEach(System.out::println);
        System.out.println("-----------------------------");
        /**
         * 搭配Comparator
         */
        // 自然排序
        list2.stream().sorted().forEach(System.out::println);
        // 逆序
        list2.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        // 特例方法引用 Comparator.comparing()
        List<Student> list3 = Arrays.asList(
                new Student(1, "wayne", 29),
                new Student(2, "before_wayne", 28)
        );
        list3.stream().sorted(Comparator.comparing(Student::getAge)).forEach(System.out::println);
        list3.stream().sorted(Comparator.comparing(Student::getAge).reversed()).forEach(student -> System.out.println(student.getAge()));
        /**
         * 映射 流对象中的每一个元素映射为另一个元素，实现元素类型的转换
         */
        System.out.println("----------map---------------");
        List<String> list4 = Arrays.asList("CC","ab","aa");
        list4.stream().map(String::toUpperCase).sorted(Comparator.reverseOrder()).forEach(System.out::println);
        System.out.println("- - - - - - ");
        // 自定义映射规则
        Function<String,String> function = s -> s + ".map3";
        list4.stream().map(function).forEach(System.out::println);

        // limit返回Stream的前面n个元素；skip则是扔掉前n个元素
        List<String> personList2 = list3.stream().
                map(Student::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
        // min/max/distinct
        /**
         * 完结操作之Match匹配 返回boolean类型
         */
        //流对象中只要有一个元素匹配就返回true
        boolean anyStartWithA = list.stream().anyMatch(s -> s.startsWith("a"));
        System.out.println("集合中是否有以'a'来头："+ anyStartWithA);
        //流对象中每一个元素都匹配才返回true
        boolean allStartWithA = list.stream().allMatch(s -> s.startsWith("a"));
        System.out.println("集合中每一个都是以'a'开头："+ allStartWithA);
        //流对象中没有匹配时返回true
        boolean noneStartWithA = list.stream().noneMatch(s -> s.startsWith("c"));
        System.out.println("集合中没有以'c'开头："+ noneStartWithA);

        /**
         * 完结操作之收集(collect) 将变换的stream元素收集 返回变化后的集合
         */
        final List<Student> collect = list3.stream().filter(student -> student.getAge() == 1).collect(Collectors.toList());
        System.out.println(collect);
        /**
         * 完结操作之规约(reduce) 用自己的方式计算元素或者将一个stream中元素以某种规律关联
         */
        Optional<String> optional = list.stream().sorted().reduce((s, s2) -> {
            System.out.println(s+"-"+s2);
            return s+"-"+s2;
        });
        /**
         * 完结操作之计数(count)
         */
        long count = list.stream().filter(s -> s.startsWith("b")).count();
        System.out.println("以'b'开头的数量："+ count);

        /**
         * 并行Stream 多核处理器效率提高百分之50，单核下还是串行流性能比较好
         */
        // 创建大数据
        List<String> bigList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            UUID uuid = UUID.randomUUID();
            bigList.add(uuid.toString());
        }
        parallelStreamSortedTest(bigList);
        streamSortedTest(bigList);


    }
    //并行stream
    private static void parallelStreamSortedTest(List<String> list){
        long startTime = System.nanoTime();//返回最准确的可用系统计时器的当前值，以毫微秒为单位。
        long count = list.parallelStream().sorted().count();
        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.printf("并行排序花费时间：%d ms",millis);
    }
    //串行stream
    private static void streamSortedTest(List<String> list){
        long startTime = System.nanoTime();//返回最准确的可用系统计时器的当前值，以毫微秒为单位。
        long count = list.stream().sorted().count();
        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.printf("串行排序花费时间：%d ms",millis);
    }

}
