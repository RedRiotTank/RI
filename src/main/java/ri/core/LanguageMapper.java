package ri.core;

import java.util.HashMap;
import java.util.Map;

// clase que mapea los lenguajes.
public class LanguageMapper {
    private final Map<String, String> languageMap;

    public LanguageMapper() {
        languageMap = new HashMap<>();

        languageMap.put("zh", "Chino");
        languageMap.put("es", "Español");
        languageMap.put("en", "Inglés");
        languageMap.put("hi", "Hindi");
        languageMap.put("ar", "Árabe");
        languageMap.put("bn", "Bengalí");
        languageMap.put("pt", "Portugués");
        languageMap.put("ru", "Ruso");
        languageMap.put("ja", "Japonés");
        languageMap.put("pa", "Punjabi");
        languageMap.put("de", "Alemán");
        languageMap.put("jv", "Javanés");
        languageMap.put("id", "Indonesio");
        languageMap.put("te", "Telugu");
        languageMap.put("vi", "Vietnamita");
        languageMap.put("ko", "Coreano");
        languageMap.put("fr", "Francés");
        languageMap.put("mr", "Maratí");
        languageMap.put("ta", "Tamil");
        languageMap.put("ur", "Urdu");
        languageMap.put("it", "Italiano");
        languageMap.put("tr", "Turco");
        languageMap.put("nl", "Holandés");
        languageMap.put("pl", "Polaco");
        languageMap.put("uk", "Ucraniano");
        languageMap.put("ro", "Rumano");
        languageMap.put("el", "Griego");
        languageMap.put("hu", "Húngaro");
        languageMap.put("sv", "Sueco");
        languageMap.put("cs", "Checo");
        languageMap.put("th", "Tailandés");
        languageMap.put("da", "Danés");
        languageMap.put("fi", "Finlandés");
        languageMap.put("he", "Hebreo");
        languageMap.put("no", "Noruego");
        languageMap.put("sk", "Eslovaco");
        languageMap.put("hr", "Croata");
        languageMap.put("bg", "Búlgaro");
        languageMap.put("lt", "Lituano");
        languageMap.put("sl", "Esloveno");

    }

    public String mapLanguage(String languageCode) {
        return languageMap.getOrDefault(languageCode, "Desconocido");
    }
}

