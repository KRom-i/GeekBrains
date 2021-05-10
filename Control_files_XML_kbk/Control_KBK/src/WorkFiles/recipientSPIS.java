package WorkFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class recipientSPIS {

    private String numberMass;
    private String codeDistrict;
    private String numberSNILS;
    private ClassValidation[] classValidations;

    public recipientSPIS() {
    }

    public String getNumberMass() {
        return numberMass;
    }

    public void setNumberMass(String numberMass) {
        this.numberMass = numberMass;
    }

    public String getCodeDistrict() {
        return codeDistrict;
    }

    public void setCodeDistrict(String codeDistrict) {
        this.codeDistrict = codeDistrict;
    }

    public String getNumberSNILS() {
        return numberSNILS;
    }

    public void setNumberSNILS(String numberSNILS) {
        this.numberSNILS = numberSNILS;
    }

    public ClassValidation[] getClassValidationList() {
        return classValidations;
    }

    public void setClassValidations(ClassValidation[] classValidations) {
        this.classValidations = classValidations;
    }

    public int getClassValidationListLength() {
        return classValidations.length;
    }


    @Override
    public String toString() {
        return "recipientSPIS{" +
                "numberMass='" + numberMass + '\'' +
                ", codeDistrict='" + codeDistrict + '\'' +
                ", numberSNILS='" + numberSNILS + '\'' +
                '}';
    }
}
