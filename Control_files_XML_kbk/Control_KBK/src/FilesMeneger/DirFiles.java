package FilesMeneger;

import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class DirFiles {


    public static void main(String[] args) {

    }

    public DirFiles(File file, List<File> stringList) {

        if (file.isDirectory ()
                && !file.getName ().equalsIgnoreCase ("Файлы для разделения")
                && !file.getName ().equalsIgnoreCase ("Файлы после объединения")
                && !file.getName ().equalsIgnoreCase ("Отзывы")
                && !file.getName ().equalsIgnoreCase ("Годовые отчёты")
                && !file.getName ().equalsIgnoreCase ("Загрузка")
                && !file.getName ().equalsIgnoreCase ("УМЕРШИЕ")
                && !file.getName ().equalsIgnoreCase ("АРХИВ")){

                for (File f: file.listFiles ()
                ) {
                    if (f.isDirectory ()){
                        new DirFiles (f, stringList);
                    } else if (f.isFile ()) {
                        stringList.add (f);
                        System.out.println (f.getAbsolutePath ());
                    }
                }


        }

    }
}
