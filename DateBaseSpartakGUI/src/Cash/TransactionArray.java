package Cash;

public class TransactionArray {

    private String[] names = {
            "Покупка",
            "Списание с баланса тренера",
            "Пополнение баланса тренера",
            "Пополнение товара / абонемента",
            "Добавление новой услуги",
            "Удаление услуги",
            "Списание посещения по абонементу",
            "Добавление нового клиента",
            "Редактирование клиента",
            "Удаление клиента",};

    public TransactionArray(){
    }

    public String getName(int idTransaction){
        return names[idTransaction - 1];
    }
}
