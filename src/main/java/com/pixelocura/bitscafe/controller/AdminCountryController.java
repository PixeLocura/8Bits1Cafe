package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.dto.CountryDTO;
import com.pixelocura.bitscafe.service.AdminCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class AdminCountryController {
    private final AdminCountryService adminCountryService;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> list() {
        List<CountryDTO> countries = adminCountryService.findAll();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CountryDTO>> paginate(@PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<CountryDTO> countries = adminCountryService.paginate(pageable);
        return ResponseEntity.ok(countries);
    }

    @PostMapping
    public ResponseEntity<CountryDTO> create(@Valid @RequestBody CountryDTO countryDTO) {
        CountryDTO createdCountry = adminCountryService.create(countryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCountry);
    }

    @GetMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> getByIsoCode(@PathVariable String isoCode) {
        CountryDTO country = adminCountryService.findByIsoCode(isoCode);
        return ResponseEntity.ok(country);
    }

    @PutMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> update(@PathVariable String isoCode, @Valid @RequestBody CountryDTO countryDTO) {
        CountryDTO updatedCountry = adminCountryService.update(isoCode, countryDTO);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/{isoCode}")
    public ResponseEntity<Void> delete(@PathVariable String isoCode) {
        adminCountryService.delete(isoCode);
        return ResponseEntity.noContent().build();
    }


}
