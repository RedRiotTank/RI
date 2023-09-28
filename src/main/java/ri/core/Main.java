package ri.core;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TextProc t = new TextProc("target/example");
        t.makeTable();
    }
}