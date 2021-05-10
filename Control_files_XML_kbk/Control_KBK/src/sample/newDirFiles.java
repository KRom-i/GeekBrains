package sample;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.concurrent.Semaphore;

public class newDirFiles {

    public newDirFiles (File file, TreeItem<File> treeItem, Semaphore semaphore){

        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(File f: file.listFiles()) {

                TreeItem<File> treeItem2 = new TreeItem<File>(f);
                treeItem.getChildren().add(treeItem2);

                if (f.isDirectory()){
                    System.out.println(f);
                    new newDirFiles(f, treeItem2, semaphore);

                }

            }
            semaphore.release();
        }).start();

    }

}
