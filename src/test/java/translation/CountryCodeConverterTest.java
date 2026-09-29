package translation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CountryCodeConverterTest {

    @Test
    public void fromCountryReturnsLowercaseAlpha3Code() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("usa", converter.fromCountry("United States of America (the)"));
        assertEquals("can", converter.fromCountry("Canada"));
    }

    @Test
    public void unknownEntriesReturnNull() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertNull(converter.fromCountryCode("unknown"));
        assertNull(converter.fromCountry("Unknown country"));
    }

    @Test
    public void fromCountryCodeUSA() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals("United States of America (the)", converter.fromCountryCode("usa"));
    }

    @Test
    public void fromCountryCodeAllLoaded() {
        CountryCodeConverter converter = new CountryCodeConverter();
        assertEquals(249, converter.getNumCountries());
    }
}
