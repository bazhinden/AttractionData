package com.example.attractions.repository;

import com.example.attractions.model.Attraction;
import com.example.attractions.model.Locality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
class AttractionRepositoryIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("password");

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private LocalityRepository localityRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    private Attraction attraction;
    private Locality locality;

    @BeforeEach
    void setUp() {
        locality = new Locality();
        locality.setName("Locality 1");
        locality = localityRepository.save(locality);

        attraction = new Attraction();
        attraction.setName("Attraction 1");
        attraction.setLocality(locality);
        attractionRepository.save(attraction);
    }

    @Test
    void testFindById() {
        Optional<Attraction> foundAttraction = attractionRepository.findById(attraction.getId());
        assertTrue(foundAttraction.isPresent());
        assertEquals(attraction.getName(), foundAttraction.get().getName());
    }

    @Test
    void testFindByLocalityId() {
        var attractions = attractionRepository.findByLocalityId(locality.getId(), PageRequest.of(0, 10));
        assertEquals(1, attractions.getTotalElements());
    }
}
