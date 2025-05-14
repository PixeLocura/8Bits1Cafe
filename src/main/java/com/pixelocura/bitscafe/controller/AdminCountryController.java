package com.pixelocura.bitscafe.controller;

import com.pixelocura.bitscafe.model.entity.Country;
import com.pixelocura.bitscafe.service.AdminCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class AdminCountryController {
    private final AdminCountryService adminCountryService;

    @GetMapping
    public ResponseEntity<List<Country>> list() {
        List<Country> countries = adminCountryService.findAll();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Country>> paginate(@PageableDefault(size = 5, sort = "name") Pageable pageable) {
        Page<Country> countries = adminCountryService.paginate(pageable);
        return ResponseEntity.ok(countries);
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody Country country) {
        Country createdCountry = adminCountryService.create(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCountry);
    }

    @GetMapping("/{isoCode}")
    public ResponseEntity<Country> getByIsoCode(@PathVariable String isoCode) {
        Country country = adminCountryService.findByIsoCode(isoCode);
        return ResponseEntity.ok(country);
    }

    @PutMapping("/{isoCode}")
    public ResponseEntity<Country> update(@PathVariable String isoCode, @RequestBody Country country) {
        Country updatedCountry = adminCountryService.update(isoCode, country);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping("/{isoCode}")
    public ResponseEntity<Void> delete(@PathVariable String isoCode) {
        adminCountryService.delete(isoCode);
        return ResponseEntity.noContent().build();
    }


}
