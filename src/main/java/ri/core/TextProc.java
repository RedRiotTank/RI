package ri.core;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.*;

public class TextProc {
    private Tika tika = new Tika();
    private Set<File> files = new HashSet<>() ;

    public TextProc(String folderPath){
        File folder = new File(folderPath);

        if(folder.isDirectory()){
            files.addAll(Arrays.asList(folder.listFiles()));
        } else {
            System.out.println("No se pudo abrir el directorio");
        }
    }
    // metodo que devuelve el nombre del fichero
    private ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        String [] arrayAux;
        String aux = "";
        for(File f : files){
            arrayAux = f.toString().split("/");
            aux = arrayAux[arrayAux.length - 1];
            names.add(aux);
        }
        return names;
    }
    // metodo que devuelve los formatos de los archivos
    private ArrayList<String> getFormats() throws IOException {
        ArrayList<String> formats = new ArrayList<>();
        for(File f : files){
            formats.add(tika.detect(f));
        }
        return formats;
    }

    private ArrayList<String> getEncodings(){
        ArrayList<String> codifications = new ArrayList<>();
        for(File f : files){
            try{
                InputStream is = new FileInputStream(f);
                Metadata metadata = new Metadata();
                BodyContentHandler ch = new BodyContentHandler();
                ParseContext parseContext = new ParseContext();

                AutoDetectParser parser = new AutoDetectParser();
                parser.parse(is, ch, metadata, parseContext);

                String encoding = metadata.get("Content-Encoding");
                //System.out.println(encoding);
                if(encoding == null){ // para los archivos sin codificacion
                    encoding = "without encoding (ex: txt)";
                }
                codifications.add(encoding);
                is.close();
            } catch (FileNotFoundException e){
                System.out.println("No se encontró el archivo");
            } catch (TikaException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
        return codifications;
    }

    private ArrayList<String> getLanguages(){
        ArrayList<String> languages = new ArrayList<>();
        String idioma_aux = "";
        for (File f : files) {
            try{
                InputStream is = new FileInputStream(f);
                Metadata metadata = new Metadata();
                BodyContentHandler ch = new BodyContentHandler();

                AutoDetectParser parser = new AutoDetectParser();
                parser.parse(is, ch, metadata);

                idioma_aux = detectLanguage(ch.toString());
                languages.add(idioma_aux);

            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (TikaException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
        return languages;
    }
    // metodo que devuelve el idioma de un texto
    private String detectLanguage(String text) throws IOException {
        LanguageDetector languageDetector = LanguageDetector.getDefaultLanguageDetector().loadModels();

        return languageDetector.detect(text).getLanguage();
    }

    // metodo que pinta la tabla con los datos de cada archivo
    public void makeTable() throws IOException { // param -d
        ArrayList<String> names = getNames();
        ArrayList<String> formats = getFormats();
        ArrayList<String> encodings = getEncodings();
        ArrayList<String> languages = getLanguages();
        try{

            System.out.println("names: \n");
            for(String name : names)
                System.out.println(name);
            System.out.println("formats: \n");
            for(String format : formats)
                System.out.println(format);
            System.out.println("encodings: ");
            for(String encoding : encodings){
                System.out.println(encoding);
            }
            System.out.println("languages: \n");
            for(String language : languages)
                System.out.println(language);



            /*
            System.out.println("+-----------------------+-----------------------+");
            System.out.println("|        names          |        formats        |");
            System.out.println("+-----------------------+-----------------------+");

            for (int i = 0; i < Math.max(names.size(), formats.size()); i++) {
                String name = i < names.size() ? names.get(i) : "";
                String format = i < formats.size() ? formats.get(i) : "";

                System.out.printf("| %-21s | %-21s |\n", name, format);
            }

            System.out.println("+-----------------------+-----------------------+");

*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAllLinks(){ // param -l
        List< Set<String> > links = new ArrayList<>();
        String text = "";
        for(File f: files){
            try{
                // Sets, uno auxiliar para almacenar los elementos completos y luego extraer lo que queramos y guardarlo en el final
                Set<String> conjunto_enlaces_aux = new HashSet<>();
                Set<String> conjunto_enlaces = new HashSet<>();

                InputStream is = new FileInputStream(f);
                Metadata metadata = new Metadata();
                // Si es html se necesita otro parser y por lo tanto otros handlde...
                if(isHTML(f)){
                    List<Link> links_file; // para guardar los enlaces tal cual nos viene del contentHandler
                    LinkContentHandler ch = new LinkContentHandler();
                    ContentHandler textHandler = new BodyContentHandler();
                    ToHTMLContentHandler toHTMLContentHandler = new ToHTMLContentHandler();

                    TeeContentHandler teeContentHandler = new TeeContentHandler(ch, textHandler, toHTMLContentHandler);
                    ParseContext parseContext = new ParseContext();
                    HtmlParser parser = new HtmlParser();
                    parser.parse(is,teeContentHandler, metadata, parseContext);

                    // para obtener todos los enlaces en una lista
                    links_file = ch.getLinks();
                    // para cada enlace contenido en la lista, nos quedamos con aquellos que sean etiquetas <a> y tengan href
                    for(Link link : links_file){
                        String link_aux = "";
                        link_aux = link.toString();
                        if(link_aux.contains("<a href=\""))
                            conjunto_enlaces_aux.add(link_aux); // se meten en un set auxiliar para luego procesarlos
                    }

                    // y para cada uno de los enlaces guardados en el set auxiliar, nos quedamos con la url
                    for(String enlace : conjunto_enlaces_aux) {
                        Document doc = Jsoup.parse(enlace);
                        Elements enlaces = doc.select("a[href]");

                        for (Element element : enlaces) {
                            String href = element.attr("href");
                            conjunto_enlaces.add(href);
                        }
                    }
                // en caso de que no sea html:
                } else {
                    // se necesitan otros contentHandler
                    BodyContentHandler ch = new BodyContentHandler();
                    AutoDetectParser parser = new AutoDetectParser();
                    parser.parse(is, ch, metadata);
                    text = ch.toString(); // se obtiene todoo el texto

                    String[] links_file_no_html = text.split("\\s"); // se hacen "tokens"
                    // y nos quedamos con aquellos que parezcan un enlace
                    for(String link : links_file_no_html){
                        if(link.startsWith("http://") || link.startsWith("https://")){
                            conjunto_enlaces.add(link);
                        }
                    }
                }
                links.add(conjunto_enlaces); // se añade el conjunto a la lista
            } catch (TikaException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        }

        // y se saca por pantalla
        for (int i = 0; i < links.size(); i++) {
            Set<String> file_links = links.get(i);
            ArrayList<String> names = getNames();
            System.out.println("Enlaces en el archivo " + names.get(i) + ":");

            for (String link : file_links) {
                System.out.println(link);
            }

            System.out.println();
        }

    }

    private boolean isHTML(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".html") || name.endsWith(".htm");
    }
}
