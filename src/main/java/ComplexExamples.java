import java.util.*;
import java.util.stream.Collectors;

public class ComplexExamples {



    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        for (Map.Entry<String, Integer> persons : task1().entrySet()) {
            System.out.println(
                    "Key: " + persons.getKey() + "\n"
                            + "Value: " + persons.getValue()
            );
        }
        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
                Key:Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        //task2
        int[] array = new int[]{3, 4, 2, 7};
        System.out.println(Arrays.toString(Arrays.stream(task2(array, 10)).toArray()));



        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        System.out.println(task3("cwheeel", "cartwheel"));



        /*
        Task3
            Реализовать функцию нечеткого поиска
                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */
    }

    private static boolean task3(String str1, String str2) {
        Stack<Character> chars = new Stack<>();
        for (int i = 0; i < str1.length(); i++) {
            chars.push(str1.charAt(i));
        }

        int i = str2.length()-1;
        while (!chars.empty() && i != -1) {
            if (str2.charAt(i) == chars.peek()) {
                chars.pop();
            }
            i--;
        }
        if (chars.empty())
            return true;
        return false;
    }


    private static int[] task2(int[] array, int number) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int sum = array[left] + array[right];
            if (sum == number)
                return new int[]{array[left], array[right]};
            if (sum < number)
                left++;
            else right--;
        }
        return new int[0];
    }
    private static Map<String, Integer> task1() {

        Map<String, Integer> map = new HashMap<>();

        for (Person person : Arrays.stream(RAW_DATA)
                .distinct().toList()) {
            if(!map.containsKey(person.getName()))
                map.put(person.getName(), 1);
            else {
                int i =  map.get(person.getName());
                map.put(person.getName(), ++i);
            }
        }

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap((s)-> s.getKey(), (s)-> s.getValue()));
    }
}


