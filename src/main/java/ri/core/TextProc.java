package ri.core;

import org.apache.tika.Tika;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TextProc {
    private Tika tika = new Tika();
    private Set files = new HashSet() ;

    public TextProc(String folderPath){
        File folder = new File(folderPath);

        if(folder.isDirectory()){
            files.addAll(Arrays.asList(folder.listFiles()));


        }


    }

    public void detectFormat(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
