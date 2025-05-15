
package com.pixelocura.bitscafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CountryDTO {
    @Pattern(regexp = "^[A-Z]{2,3}$")
    @Size(min = 2, max = 3)
    private String isoCode;

    @NotBlank(message = "Country name is required.")
    @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Flag URL is required.")
    @URL(message = "Invalid URL format.")
    private String flagUrl;
}
