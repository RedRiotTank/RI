package ri.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class utils {

    public static String rmExtension(String filename){
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    public static List<Map.Entry<String, Integer>> orderTerms(Map<String, Integer> termFrequencyMap){
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(termFrequencyMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return sortedEntries;
    }

    public static boolean isHTML(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".html") || name.endsWith(".htm");
    }


}