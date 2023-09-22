package org.example;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TikaException {
        Tika tika = new Tika();

        for(String file : args){
            File f = new File(file);
            String type = tika.detect(f);
            System.out.println(file + " : " + type);

            String text = tika.parseToString(f);
            System.out.println(text);
        }


    }
}