package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.enums.Country;
import com.pixelocura.bitscafe.service.CountryEnumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryEnumController {
    private final CountryEnumService countryEnumService;

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryEnumService.getAllCountries());
    }

    @GetMapping("/names")
    public ResponseEntity<Map<String, Map<String, String>>> getCountriesWithNames() {
        return ResponseEntity.ok(countryEnumService.getCountriesWithNames());
    }

    @GetMapping("/isocode/{code}")
    public ResponseEntity<Map<String, String>> getCountryByIsoCode(@PathVariable String code) {
        Optional<Country> country = countryEnumService.getCountryByIsoCode(code);
        return country.map(c -> ResponseEntity.ok(c.toDetailsMap()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
