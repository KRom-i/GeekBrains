package Lesson_07;

public class Human {
    private String privateField ;
    String defaultField ;
    protected String protectedField ;
    public String publicField ;

    public void publicGo(){
        System.out.println("publicGo");
    }

    private void privateGo(){
        System.out.println("privateGo");
    }

    @Override
    public String toString (){
        return "Human{" +
                "privateField='" + privateField + '\'' +
                ", defaultField='" + defaultField + '\'' +
                ", protectedField='" + protectedField + '\'' +
                ", publicField='" + publicField + '\'' +
                '}';
    }
}
