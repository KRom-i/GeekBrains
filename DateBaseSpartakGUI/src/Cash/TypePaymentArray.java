package Cash;

public class TypePaymentArray {
    private String[] names = {"Наличными", "Безнал", "QR-код"};

    public String getName(int idTypePayment){
        return names[idTypePayment - 1];
    }
}
