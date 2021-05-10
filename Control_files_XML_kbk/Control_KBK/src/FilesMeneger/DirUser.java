package FilesMeneger;

import java.io.File;

public class DirUser {

    public static void main (String[] args){

        for(File f: File.listRoots()
            ) {
            System.out.println(f);
        }
    }

    public DirUser (){

    }

    private void userFilesToListView(File file){
        System.out.println(file.exists());
    }
}
