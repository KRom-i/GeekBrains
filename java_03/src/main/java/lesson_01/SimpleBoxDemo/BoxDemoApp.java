package lesson_01.SimpleBoxDemo;

public class BoxDemoApp {

    public static void main(String[] args) {

//        Simple (простой, упрощенный)
       SimpleBox intBox1 = new SimpleBox(20);
       SimpleBox intBox2 = new SimpleBox(40);



//       instanceof(экземпляр) проверяем принадлежность объекта к указанному типу

       if (intBox1.getObject() instanceof Integer &&
       intBox2.getObject() instanceof Integer){

//           Вызываем объект через get и приводим уго к типу указанному к скобках
           int sum = (Integer) intBox1.getObject() + (Integer) intBox2.getObject();
           System.out.println("Сумма содержимого коробок равна: " + sum);
       } else {
           System.out.println("Содержимое коробок отличается по типу");
       }

//       intBox1.setObject("String java");
//        int sum = (Integer) intBox1.getObject() + (Integer) intBox2.getObject();
//   Exception(Исключение) (Class Cast(роль, оттенок, литье, бросок) Exception)  так как невозможно получить значение int из суммы(int + String)

    }
}
