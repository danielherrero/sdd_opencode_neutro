package com.example.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@QuarkusTest
class PersistenceTest {


    @BeforeAll
    static void enableHttpLogging() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @AfterAll
    static void resetRestAssuredConfiguration() {
        RestAssured.reset();
    }

    @Inject
    UserRepository userRepository;

    @Inject
    EntityManager entityManager;

    private static final Map<String, Object> VALID_USER = Map.of(
            "nombre", "Daniel",
            "apellidos", "Herrero",
            "fechaNacimiento", "1980-01-01",
            "contrasena", "secret-password");

    @Test
    @DisplayName("RF-001 Verifica que el usuario se persiste en la base de datos")
    void shouldPersistUserInDatabase() {
        // Create user via API
        int id = given().contentType("application/json").body(VALID_USER)
                .when().post("/users")
                .then().statusCode(201).extract().path("id");

        // Verify persistence via Repository
        User user = userRepository.findById((long) id);
        
        org.junit.jupiter.api.Assertions.assertNotNull(user);
        org.junit.jupiter.api.Assertions.assertEquals("Daniel", user.getNombre());
        org.junit.jupiter.api.Assertions.assertEquals("Herrero", user.getApellidos());
    }

    @Transactional
    void deleteAllUsers() {
        entityManager.createQuery("delete from UserReference").executeUpdate();
        entityManager.createQuery("delete from User").executeUpdate();
    }
}
