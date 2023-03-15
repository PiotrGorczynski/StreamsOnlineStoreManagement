package utils;

import java.util.List;
import java.util.Map;

public class PrintingUtility
{
    public static void printingMap(final Map<?, ?> map)
    {
        map.forEach((key,value)-> System.out.println("KEY: " + key + ", VALUE: " + value));
    }

    public static void printingList(final List<?> list)
    {
        list.forEach(System.out::println);
    }

    public static void print(String separator)
    {
        System.out.println(separator);
    }
}
