package com.pixelocura.bitscafe.service;

import com.pixelocura.bitscafe.model.enums.Country;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CountryEnumService {
    public List<Country> getAllCountries() {
        return Arrays.asList(Country.values());
    }

    public Map<String, Map<String, String>> getCountriesWithNames() {
        return Arrays.stream(Country.values())
                .collect(Collectors.toMap(
                        Country::name,
                        Country::toDetailsMap));
    }

    public Optional<Country> getCountryByIsoCode(String isoCode) {
        return Country.fromIsoCode(isoCode);
    }

    public Optional<Country> getCountryByName(String name) {
        return Country.fromName(name);
    }

    public Optional<Country> getCountryByLocalName(String localName) {
        return Country.fromLocalName(localName);
    }
}
