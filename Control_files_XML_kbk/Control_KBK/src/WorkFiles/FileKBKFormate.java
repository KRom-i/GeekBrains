package WorkFiles;

import javafx.scene.control.Button;

public class FileKBKFormate {

    private int numcodeKBK;
    private String name;
    private String codeKBK;
    private int incomeCode;
    private Button buttonKBK;

    public Button getButtonKBK (){
        return buttonKBK;
    }

    public FileKBKFormate() {
        this.buttonKBK = new Button("Удалить");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeKBK() {
        return codeKBK;
    }

    public void setCodeKBK(String codeKBK) {
        this.codeKBK = codeKBK;
    }

    public int getNumcodeKBK() {
        return numcodeKBK;
    }

    public void setNumcodeKBK(int numcodeKBK) {
        this.numcodeKBK = numcodeKBK;
    }

    public int getIncomeCode() {
        return incomeCode;
    }

    public void setIncomeCode(int incomeCode) {
        this.incomeCode = incomeCode;
    }


}
