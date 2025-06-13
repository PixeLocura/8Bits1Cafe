package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.enums.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CountryEnumServiceUnitTest {

    private CountryEnumService countryEnumService;

    @BeforeEach
    void setUp() {
        countryEnumService = new CountryEnumService();
    }

    @Test
    @DisplayName("CP01 - Obtener lista completa de países")
    void getAllCountries_returnsAllCountries() {
        // Act
        List<Country> countries = countryEnumService.getAllCountries();

        // Assert
        assertNotNull(countries);
        assertFalse(countries.isEmpty());
        assertEquals(Country.values().length, countries.size());
    }

    @Test
    @DisplayName("CP02 - Obtener mapa de detalles de países")
    void getCountryDetailsMap_returnsCompleteMap() {
        // Act
        Map<String, Map<String, String>> countryMap = countryEnumService.getCountryDetailsMap();

        // Assert
        assertNotNull(countryMap);
        assertFalse(countryMap.isEmpty());
        assertEquals(Country.values().length, countryMap.size());

        Map<String, String> usDetails = countryMap.get("US");
        assertNotNull(usDetails);
        assertTrue(usDetails.containsKey("isoCode"));
        assertTrue(usDetails.containsKey("name"));
        assertTrue(usDetails.containsKey("localName"));
        assertTrue(usDetails.containsKey("flagUrl"));
        assertEquals("Estados Unidos", usDetails.get("name"));
        assertEquals("United States", usDetails.get("localName"));
    }

    @Test
    @DisplayName("CP03 - Buscar país por código ISO válido")
    void getCountryByIsoCode_validCode_returnsCountry() {
        // Arrange
        String validIsoCode = "US";

        // Act
        Optional<Country> country = countryEnumService.getCountryByIsoCode(validIsoCode);

        // Assert
        assertTrue(country.isPresent());
        Map<String, String> details = country.get().toDetailsMap();
        assertEquals(validIsoCode, details.get("isoCode"));
    }

    @Test
    @DisplayName("CP04 - Buscar país por código ISO inválido retorna vacío")
    void getCountryByIsoCode_invalidCode_returnsEmpty() {
        // Arrange
        String invalidIsoCode = "XX";

        // Act
        Optional<Country> country = countryEnumService.getCountryByIsoCode(invalidIsoCode);

        // Assert
        assertTrue(country.isEmpty());
    }

    @Test
    @DisplayName("CP05 - Buscar país por nombre válido")
    void getCountryByName_validName_returnsCountry() {
        // Arrange
        String validName = "Estados Unidos";

        // Act
        Optional<Country> country = countryEnumService.getCountryByName(validName);

        // Assert
        assertTrue(country.isPresent());
        Map<String, String> details = country.get().toDetailsMap();
        assertEquals(validName, details.get("name"));
    }

    @Test
    @DisplayName("CP06 - Buscar país por nombre inválido retorna vacío")
    void getCountryByName_invalidName_returnsEmpty() {
        // Arrange
        String invalidName = "Invalid Country Name";

        // Act
        Optional<Country> country = countryEnumService.getCountryByName(invalidName);

        // Assert
        assertTrue(country.isEmpty());
    }

    @Test
    @DisplayName("CP07 - Buscar país por nombre local válido")
    void getCountryByLocalName_validLocalName_returnsCountry() {
        // Arrange
        String validLocalName = "United States";

        // Act
        Optional<Country> country = countryEnumService.getCountryByLocalName(validLocalName);

        // Assert
        assertTrue(country.isPresent());
        Map<String, String> details = country.get().toDetailsMap();
        assertEquals(validLocalName, details.get("localName"));
    }

    @Test
    @DisplayName("CP08 - Buscar país por nombre local inválido retorna vacío")
    void getCountryByLocalName_invalidLocalName_returnsEmpty() {
        // Arrange
        String invalidLocalName = "Invalid Local Name";

        // Act
        Optional<Country> country = countryEnumService.getCountryByLocalName(invalidLocalName);

        // Assert
        assertTrue(country.isEmpty());
    }
}
