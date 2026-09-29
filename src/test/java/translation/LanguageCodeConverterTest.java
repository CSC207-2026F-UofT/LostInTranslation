package translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LanguageCodeConverterTest {

    @Test
    public void fromLanguageEnglish() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("en", converter.fromLanguage("English"));
    }

    @Test
    public void unknownEntriesReturnNull() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertNull(converter.fromLanguageCode("unknown"));
        assertNull(converter.fromLanguage("Unknown language"));
    }

    @Test
    public void fromLanguageCodeEN() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals("English", converter.fromLanguageCode("en"));
    }

    @Test
    public void fromLanguageCodeAllLoaded() {
        LanguageCodeConverter converter = new LanguageCodeConverter();
        assertEquals(184, converter.getNumLanguages());
    }
}
