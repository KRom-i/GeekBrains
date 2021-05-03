package lesson_01.ArrayNumber;

public class Stats<T extends Number> {
//    Stats (Статистика) extends (продлить, расширить)

    private T[] nums;

    public Stats(T ... nums) {
        this.nums = nums;
    }

    public double avg () {
//        double (двойной)

        double sum = 0.0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i].doubleValue();
        }

        return sum / nums.length;
    }

    public boolean sameAvg(Stats<T> another){
//        some (одно и тоже) another (другой)
        return Math.abs(this.avg() - another.avg()) < 0.0001;
//                Math (математика)
    }

//    метосимвольный аргумент (?) возволяет высвать метод с любым типом объекта Stats
    public boolean sameAvg2(Stats<?> another){
        return Math.abs(this.avg() - another.avg()) < 0.0001;
    }
}
