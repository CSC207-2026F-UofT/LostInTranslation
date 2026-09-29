package translation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CanadaTranslatorTest {

    private final CanadaTranslator translator = new CanadaTranslator();

    @Test
    public void translatesAllSupportedLanguages() {
        assertEquals(List.of("de", "en", "zh", "es", "fr"), translator.getLanguageCodes());
        assertEquals("Kanada", translator.translate("can", "de"));
        assertEquals("Canada", translator.translate("can", "en"));
        assertEquals("加拿大", translator.translate("can", "zh"));
        assertEquals("Canadá", translator.translate("can", "es"));
        assertEquals("Canada", translator.translate("can", "fr"));
    }

    @Test
    public void unsupportedCombinationsReturnNull() {
        assertNull(translator.translate("usa", "es"));
        assertNull(translator.translate("can", "unknown"));
    }

    @Test
    public void returnedListsAreIndependent() {
        translator.getLanguageCodes().clear();
        translator.getCountryCodes().clear();
        assertEquals(List.of("de", "en", "zh", "es", "fr"), translator.getLanguageCodes());
        assertEquals(List.of("can"), translator.getCountryCodes());
        assertEquals("Canadá", translator.translate("can", "es"));
    }
}
