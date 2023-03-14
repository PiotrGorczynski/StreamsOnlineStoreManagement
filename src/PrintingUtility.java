import java.util.List;
import java.util.Map;

public class PrintingUtility
{
    static void printingMap(final Map<?,?> map)
    {
        map.forEach((key,value)-> System.out.println("KEY: " + key + ", VALUE: " + value));
    }

    static void printingList(final List<?> list)
    {
        list.forEach(System.out::println);
    }
}
