package lesson_01.ArrayNumber;

public class StatsDemoApp {

    public static void main(String[] args) {

        Stats<Integer> integerStats = new Stats<>(1,2,3,4,5);
        System.out.println("Среднее значение integerStats равно: " + (int) integerStats.avg());

        Stats<Double> doubleStats = new Stats<>(1.1,2.2,3.3,4.4,5.5);
        System.out.println("Среднее значение doubleStats равно: " + doubleStats.avg());
//       перевод комментария (translation of a comment) (meddle value equally AND the average is)

//        Stats<String> stringStats = new Stats<>("str1","str2","str3"); (error compilation)

        Stats<Integer> integerStats2 = new Stats<>(1,2,3,4,5);
        Stats<Integer> integerStats3 = new Stats<>(1,2,3,4,5);

        Stats<Integer> integerStats4 = new Stats<>(1,2,3,4,10);

        Stats<Double> doubleStats2 = new Stats<>(1.0,2.0,3.0,4.0,5.0);

//        Value TRUE
        if (integerStats2.sameAvg(integerStats3)){
            System.out.println("The average is true");
        } else {
            System.out.println("The average is false");
        }

//        Value FALSE
        if (integerStats2.sameAvg(integerStats4)){
            System.out.println("The average is true");
        } else {
            System.out.println("The average is false");
        }

//        if (integerStats2.sameAvg(doubleStats2)){
//          Error compilation

        if (integerStats2.sameAvg2(doubleStats2)){
            System.out.println("The average is true");
        } else {
            System.out.println("The average is false");
        }


    }
}
