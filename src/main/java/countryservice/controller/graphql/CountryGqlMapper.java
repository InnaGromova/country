package countryservice.controller.graphql;


import countryservice.CountryService;
import countryservice.model.Country;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class CountryGqlMapper {

    private final CountryService countryService;

    public CountryGqlMapper(CountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping(name = "getAllCountries")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @QueryMapping(name = "getCountryByCode")
    public Optional<Country> getCountryByCode(@Argument String code) {
        return countryService.getCountryByCode(code.toUpperCase());
    }

    @QueryMapping(name = "getCountryByName")
    public Optional<Country> getCountryByName(@Argument String name) {
        return countryService.getCountryByName(name);
    }

    @QueryMapping(name = "searchCountries")
    public List<Country> searchCountries(@Argument String query) {
        return countryService.searchCountries(query);
    }

    @MutationMapping(name = "createCountry")
    public Country createCountry(@Argument String code, @Argument String name) {
        validateCountryCode(code);
        validateCountryName(name);

        return countryService.createCountry(code.toUpperCase(), name.trim());
    }

    @MutationMapping(name = "updateCountry")
    public Optional<Country> updateCountry(@Argument String code, @Argument String newName) {
        validateCountryName(newName);
        return countryService.updateCountry(code.toUpperCase(), newName.trim());
    }

    @MutationMapping(name = "deleteCountry")
    public Boolean deleteCountry(@Argument String code) {
        return countryService.deleteCountry(code.toUpperCase());
    }


    @SchemaMapping(typeName = "Country", field = "id")
    public String getId(Country country) {
        return country.getId().toString();
    }

    @SchemaMapping(typeName = "Country", field = "code")
    public String getCode(Country country) {
        return country.getCode();
    }

    @SchemaMapping(typeName = "Country", field = "name")
    public String getName(Country country) {
        return country.getName();
    }


    private void validateCountryCode(String code) {
        if (code == null || code.length() < 2 || code.length() > 3) {
            throw new IllegalArgumentException("Country code must be 2 or 3 characters long");
        }
        if (!code.matches("^[A-Za-z]+$")) {
            throw new IllegalArgumentException("Country code must contain only letters");
        }
    }

    private void validateCountryName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Country name cannot exceed 100 characters");
        }
    }
}