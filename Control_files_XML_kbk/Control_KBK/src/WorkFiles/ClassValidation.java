package WorkFiles;

public class ClassValidation {

    private int viewKBK;
    private int codeKBK;

    public int getViewKBK() {
        return viewKBK;
    }

    public void setViewKBK(int viewKBK) {
        this.viewKBK = viewKBK;
    }

    public int getCodeKBK() {
        return codeKBK;
    }

    public void setCodeKBK(int codeKBK) {
        this.codeKBK = codeKBK;
    }

    @Override
    public String toString() {
        return "ClassValidation{" +
                "viewKBK=" + viewKBK +
                ", codeKBK=" + codeKBK +
                '}';
    }
}
