package WorkFiles;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadFileXML {

    public static void main(String[] args) {

        for (recipientSPIS SPIS: reabFileXML ()
             ) {
//            System.out.println (SPIS.toString ());

//            for (int i = 0; i < SPIS.getClassValidationList ().length; i++) {
//                System.out.println (SPIS.getClassValidationList ()[i]);
//            }

        }

    }

//    Чтение основного XML файла
    private static recipientSPIS[] reabFileXML(){

        recipientSPIS[] recipientSPISarray = null;

        File file = null;
        file = new File (
                "D:\\Control_files_XML_kbk\\" +
                        "PFR-700-Y-2021-ORG-007-001-009047-DIS-000-DCK-05892-001-DOC-SPIS-FSB-8617.XML");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
        DocumentBuilder builder;

        try {

            builder = factory.newDocumentBuilder ();
            Document document = builder.parse (file);
            document.getDocumentElement ().normalize ();

//            System.out.println ("Корневой элемент файла: " + document.getDocumentElement ().getNodeName ());

//            Создание листа из задданых тегов.
            NodeList nodeList = document.getElementsByTagName ("СведенияОполучателе");

           recipientSPISarray = new recipientSPIS[nodeList.getLength ()];

            for (int i = 0; i < nodeList.getLength (); i++) {

                Node node = nodeList.item (i);

                if (node.getNodeType () == Node.ELEMENT_NODE){
                    Element element = (Element) node;

                    recipientSPISarray[i] = new recipientSPIS ();

//                    System.out.println (getTagValue ("НомерВмассиве", element));
                    recipientSPISarray[i].setNumberMass (getTagValue ("НомерВмассиве", element));

//                    System.out.println (getTagValue ("СтраховойНомер", element));
                    recipientSPISarray[i].setNumberSNILS (getTagValue ("СтраховойНомер", element));

//                    System.out.println (getTagValue ("КодРайона", element));
                    recipientSPISarray[i].setCodeDistrict (getTagValue ("КодРайона", element));


                    NodeList nodeList1 = element.getElementsByTagName ("ВсеВыплаты");

                    recipientSPISarray[i].setClassValidations (new ClassValidation[nodeList1.getLength ()]);

                    for (int j = 0; j < nodeList1.getLength (); j++) {

                        Element element1 = (Element) node;

                        recipientSPISarray[i].getClassValidationList ()[j] = new ClassValidation ();
                        System.out.println ("Длинна массива объектов проверки: " +
                                recipientSPISarray[i].getClassValidationListLength ());

//                        System.out.println (getTagValue ("ВидВыплатыПоПЗ", element1));
                        recipientSPISarray[i].
                                getClassValidationList ()[j].
                                setViewKBK (Integer.valueOf (getTagValue ("ВидВыплатыПоПЗ", element1)));

//                        System.out.println (getTagValue ("КодВидаДохода", element1));
                        recipientSPISarray[i].
                                getClassValidationList ()[j].
                                setCodeKBK (Integer.valueOf (getTagValue ("КодВидаДохода", element1)));

//                        Тест удалить

                        if (recipientSPISarray[i].getClassValidationListLength () > 1){

                            System.out.println (recipientSPISarray[i].toString ());
                            System.out.println (recipientSPISarray[i].getClassValidationList ()[j].toString ());

                        }

                    }

                }

            }



        } catch (Exception e) {
            e.printStackTrace ();
        }

        return recipientSPISarray;
    }



    private static String getTagValue(String nameTage, Element element){

        NodeList nodeList = null;

        if ((nodeList = element.getElementsByTagName (nameTage).item (0).getChildNodes ()).getLength () > 0){
            Node node = (Node) nodeList.item (0);
            return node.getNodeValue ();
        }

        return "ПустоеЗачение";
    }

}
