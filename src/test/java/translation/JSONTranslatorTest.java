package translation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONTranslatorTest {

    JSONTranslator jsonTranslator = new JSONTranslator();

    @Test
    public void allCountryLanguageCombinationsHaveTranslations() {
        for (String country : jsonTranslator.getCountryCodes()) {
            for (String language : jsonTranslator.getLanguageCodes()) {
                assertNotNull(jsonTranslator.translate(country, language), country + "-" + language);
            }
        }
        assertEquals("Canadá", jsonTranslator.translate("can", "es"));
        assertEquals("Canada", jsonTranslator.translate("can", "fr"));
    }

    @Test
    public void languageCodesAreUniqueAndExcludeMetadata() {
        List<String> languages = jsonTranslator.getLanguageCodes();
        assertEquals(languages.size(), new HashSet<>(languages).size());
        assertTrue(languages.contains("zh-tw"));
        assertFalse(languages.contains("id"));
        assertFalse(languages.contains("alpha2"));
        assertFalse(languages.contains("alpha3"));
    }

    @Test
    public void unavailableTranslationsReturnNull() {
        assertNull(jsonTranslator.translate("unknown", "en"));
        assertNull(jsonTranslator.translate("can", "unknown"));
        assertNull(jsonTranslator.translate("can", "alpha3"));
    }

    @Test
    public void returnedListsAreIndependent() {
        jsonTranslator.getLanguageCodes().clear();
        jsonTranslator.getCountryCodes().clear();
        assertEquals(35, jsonTranslator.getLanguageCodes().size());
        assertEquals(193, jsonTranslator.getCountryCodes().size());
        assertEquals("Canada", jsonTranslator.translate("can", "en"));
    }

    @Test
    public void getLanguageCodes() {
       List<String> countryLanguages = jsonTranslator.getLanguageCodes();
       assertEquals(35, countryLanguages.size(),
               "There should be 35 languages but got " + countryLanguages.size());
    }

    @Test
    public void getCountryCodes() {
        List<String> languages = jsonTranslator.getCountryCodes();
        assertEquals(193, languages.size(),
                "There should be 193 countries but got " + languages.size());

    }

    @Test
    public void translate() {
        assertEquals("Canada", jsonTranslator.translate("can", "en"));
    }
}
