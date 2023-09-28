package ri.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        TextProc t = new TextProc(args[0]); // se pasa primero el directorio
        // si faltan parametros se para la ejecución
        if(args.length < 2){
            System.out.println("Faltan parametros en el main");
            return;
        }
        for (String param : args) {
            if (param.equals("-d"))
                t.makeTable();
            if (param.equals("-l"))
                t.getAllLinks();
        }
        System.out.println();
        System.out.println("El programa se ha ejecutado correctamente");
    }
}