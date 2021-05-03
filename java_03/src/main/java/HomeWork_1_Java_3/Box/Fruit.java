package HomeWork_1_Java_3.Box;

public class Fruit {

    private long id;
    private String name;
    private float weight;

    public Fruit(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public Fruit(float weight) {
        this.weight = weight;
    }

    public float getWeightFruit() {
        return weight;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name=" + name  +
                "}\n";
    }
}
