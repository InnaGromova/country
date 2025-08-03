package countryservice.controller;

import countryservice.model.Country;
import countryservice.repository.CountryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final CountryRepository repository;
    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    public List<Country> getAllCountries() {
        return repository.findAll();
    }
    @PostMapping
    public Country addCountry(@RequestBody Country country) {
        return repository.save(country);
    }
    @PatchMapping("/{code}")
    public Country updateCountryName(@PathVariable String code, @RequestBody String newName) {
        Country country = repository.findById(code).orElseThrow();
        country.setName(newName);
        return repository.save(country);
    }
}

