package com.example.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.user.entity.User;
import com.example.user.entity.UserReference;
import com.example.user.repository.UserRepository;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;

@QuarkusTest
class UserResourceTest {

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
    @DisplayName("RF-001 crea un usuario y devuelve 201 Created")
    void shouldCreateUser() {
        given().contentType("application/json").body(VALID_USER)
                .when().post("/users")
                .then().statusCode(201).body("id", notNullValue());
    }

    @Test
    @DisplayName("RF-001 genera automaticamente el identificador")
    void shouldGenerateIdentifier() {
        given().contentType("application/json").body(VALID_USER)
                .when().post("/users")
                .then().statusCode(201).body("id", notNullValue());
    }

    @Test
    @DisplayName("RF-001 rechaza datos invalidos")
    void shouldRejectInvalidCreation() {
        given().contentType("application/json").body(Map.of("nombre", "Daniel"))
                .when().post("/users")
                .then().statusCode(400);
    }

    @Test
    @DisplayName("RF-002 obtiene un usuario existente con 200 OK")
    void shouldGetExistingUser() {
        int id = createUser();

        given().when().get("/users/{id}", id)
                .then().statusCode(200).body("id", equalTo(id));
    }

    @Test
    @DisplayName("RF-002 devuelve 404 para un usuario inexistente")
    void shouldReturnNotFoundForMissingUser() {
        given().when().get("/users/999999999")
                .then().statusCode(404);
    }

    @Test
    @DisplayName("RF-006 elimina los usuarios existentes, crea cinco usuarios aleatorios y los lista mediante la API")
    void shouldListFiveRandomUsersAfterCleaningExistingUsers() {
        deleteAllUsers();

        for (int index = 0; index < 5; index++) {
            given().contentType("application/json").body(randomUser())
                    .when().post("/users")
                    .then().statusCode(201);
        }

        given().when().get("/users")
                .then().statusCode(200).body("$", hasSize(5));
    }

    @Test
    @DisplayName("RF-003 actualiza un usuario existente")
    void shouldUpdateExistingUser() {
        int id = createUser();
        Map<String, Object> update = Map.of(
                "nombre", "Ana",
                "apellidos", "Lopez",
                "fechaNacimiento", "1990-02-02",
                "contrasena", "new-secret");

        given().contentType("application/json").body(update)
                .when().put("/users/{id}", id)
                .then().statusCode(200).body("nombre", equalTo("Ana"));
    }

    @Test
    @DisplayName("RF-003 devuelve 404 al actualizar un usuario inexistente")
    void shouldReturnNotFoundWhenUpdatingMissingUser() {
        given().contentType("application/json").body(VALID_USER)
                .when().put("/users/999999999")
                .then().statusCode(404);
    }

    @Test
    @DisplayName("RF-003 actualiza solamente los campos proporcionados")
    void shouldPartiallyUpdateUser() {
        int id = createUser();

        given().contentType("application/json").body(Map.of("nombre", "Ana"))
                .when().put("/users/{id}", id)
                .then().statusCode(200).body("nombre", equalTo("Ana")).body("apellidos", equalTo("Herrero"));
    }

    @Test
    @DisplayName("RF-003 rechaza un campo proporcionado que no es valido")
    void shouldRejectInvalidPartialUpdate() {
        int id = createUser();

        given().contentType("application/json").body(Map.of("nombre", ""))
                .when().put("/users/{id}", id)
                .then().statusCode(400).body("errors.nombre",
                        anyOf(equalTo("no puede estar vacio"), equalTo("solo puede contener letras")));
    }

    @Test
    @DisplayName("RF-004 elimina un usuario y devuelve 204 No Content")
    void shouldDeleteExistingUser() {
        int id = createUser();

        given().when().delete("/users/{id}", id)
                .then().statusCode(204);
    }

    @Test
    @DisplayName("RF-004 devuelve 404 al eliminar un usuario inexistente")
    void shouldReturnNotFoundWhenDeletingMissingUser() {
        given().when().delete("/users/999999999")
                .then().statusCode(404);
    }

    @Test
    @DisplayName("RF-004 devuelve 409 cuando el usuario esta referenciado")
    void shouldReturnConflictWhenDeletingReferencedUser() {
        int id = createUser();
        createUserReference(id);

        given().when().delete("/users/{id}", id)
                .then().statusCode(409).body("error", equalTo("No se puede eliminar el usuario porque esta referenciado por otros datos"));
    }

    @Test
    @DisplayName("RF-005 rechaza un nombre ausente con 400 Bad Request")
    void shouldRejectMissingName() {
        assertBadRequestWithout("nombre", "es obligatorio y no puede estar vacio");
    }

    @Test
    @DisplayName("RF-005 rechaza unos apellidos ausentes con 400 Bad Request")
    void shouldRejectMissingSurname() {
        assertBadRequestWithout("apellidos", "es obligatorio y no puede estar vacio");
    }

    @Test
    @DisplayName("RF-005 rechaza una fecha de nacimiento ausente con 400 Bad Request")
    void shouldRejectMissingBirthDate() {
        assertBadRequestWithout("fechaNacimiento", "es obligatoria y debe tener el formato AAAA-MM-DD");
    }

    @Test
    @DisplayName("RF-005 rechaza una contrasena ausente con 400 Bad Request")
    void shouldRejectMissingPassword() {
        assertBadRequestWithout("contrasena", "es obligatoria y no puede estar vacia");
    }

    @Test
    @DisplayName("RF-005 identifica la fecha de nacimiento y su formato invalido")
    void shouldIdentifyInvalidBirthDateFormat() {
        Map<String, Object> invalid = new java.util.HashMap<>(VALID_USER);
        invalid.put("fechaNacimiento", "01-01-1980");

        given().contentType("application/json").body(invalid)
                .when().post("/users")
                .then().statusCode(400).body("errors.fechaNacimiento", equalTo("tiene un formato no valido"));
    }

    @Test
    @DisplayName("RF-007 rechaza el alta de una persona menor de edad")
    void shouldRejectCreatingAnUnderageUser() {
        Map<String, Object> underage = new java.util.HashMap<>(VALID_USER);
        underage.put("fechaNacimiento", LocalDate.now().minusYears(18).plusDays(1).toString());

        given().contentType("application/json").body(underage)
                .when().post("/users")
                .then().statusCode(400).body("errors.fechaNacimiento",
                        equalTo("debe corresponder a una persona de al menos 18 anos"));
    }

    @Test
    @DisplayName("RF-007 permite el alta de una persona que cumple 18 anos")
    void shouldCreateAnAdultUser() {
        Map<String, Object> adult = new java.util.HashMap<>(VALID_USER);
        adult.put("fechaNacimiento", LocalDate.now().minusYears(18).toString());

        given().contentType("application/json").body(adult)
                .when().post("/users")
                .then().statusCode(201);
    }

    @Test
    @DisplayName("RF-007 rechaza modificar la fecha de nacimiento a una persona menor de edad")
    void shouldRejectUpdatingBirthDateToUnderage() {
        int id = createUser();
        Map<String, Object> underage = Map.of("fechaNacimiento", LocalDate.now().minusYears(18).plusDays(1).toString());

        given().contentType("application/json").body(underage)
                .when().put("/users/{id}", id)
                .then().statusCode(400).body("errors.fechaNacimiento",
                        equalTo("debe corresponder a una persona de al menos 18 anos"));
    }

    @Test
    @DisplayName("RF-008 rechaza en el alta nombre y apellidos con espacios, numeros o simbolos")
    void shouldRejectInvalidNameAndSurnameOnCreate() {
        assertInvalidNameOrSurnameOnCreate("nombre", "   ");
        assertInvalidNameOrSurnameOnCreate("nombre", "Ana2");
        assertInvalidNameOrSurnameOnCreate("nombre", "Ana!");
        assertInvalidNameOrSurnameOnCreate("apellidos", "   ");
        assertInvalidNameOrSurnameOnCreate("apellidos", "Lopez2");
        assertInvalidNameOrSurnameOnCreate("apellidos", "Lopez!");
    }

    @Test
    @DisplayName("RF-008 rechaza en la modificacion nombre y apellidos con espacios, numeros o simbolos")
    void shouldRejectInvalidNameAndSurnameOnUpdate() {
        int id = createUser();
        assertInvalidNameOrSurnameOnUpdate(id, "nombre", "   ");
        assertInvalidNameOrSurnameOnUpdate(id, "nombre", "Ana2");
        assertInvalidNameOrSurnameOnUpdate(id, "nombre", "Ana!");
        assertInvalidNameOrSurnameOnUpdate(id, "apellidos", "   ");
        assertInvalidNameOrSurnameOnUpdate(id, "apellidos", "Lopez2");
        assertInvalidNameOrSurnameOnUpdate(id, "apellidos", "Lopez!");
    }

    @Test
    @DisplayName("RF-009 recorta nombre y apellidos al crear un usuario")
    void shouldTrimNameAndSurnameOnCreate() {
        Map<String, Object> user = new java.util.HashMap<>(VALID_USER);
        user.put("nombre", "  Ana  ");
        user.put("apellidos", "  Lopez  ");

        given().contentType("application/json").body(user)
                .when().post("/users")
                .then().statusCode(201).body("nombre", equalTo("Ana")).body("apellidos", equalTo("Lopez"));
    }

    @Test
    @DisplayName("RF-009 recorta nombre y apellidos al modificar un usuario")
    void shouldTrimNameAndSurnameOnUpdate() {
        int id = createUser();
        Map<String, Object> update = Map.of("nombre", "  Ana  ", "apellidos", "  Lopez  ");

        given().contentType("application/json").body(update)
                .when().put("/users/{id}", id)
                .then().statusCode(200).body("nombre", equalTo("Ana")).body("apellidos", equalTo("Lopez"));
    }

    @Test
    @DisplayName("RNF-001 no devuelve la contrasena en la respuesta")
    void shouldNotExposePassword() {
        given().contentType("application/json").body(VALID_USER)
                .when().post("/users")
                .then().statusCode(201).body("contrasena", org.hamcrest.Matchers.nullValue());
    }

    @Test
    @DisplayName("RNF-001 persiste la contrasena como hash BCrypt y nunca como texto plano")
    void shouldPersistPasswordAsBcryptHash() {
        int id = createUser();
        User user = userRepository.findById((long) id);

        org.junit.jupiter.api.Assertions.assertNotEquals("secret-password", user.getContrasena());
        org.junit.jupiter.api.Assertions.assertTrue(BcryptUtil.matches("secret-password", user.getContrasena()));
    }

    private int createUser() {
        return given().contentType("application/json").body(VALID_USER)
                .when().post("/users")
                .then().statusCode(201).extract().path("id");
    }

    private void assertBadRequestWithout(String field, String reason) {
        Map<String, Object> invalid = new java.util.HashMap<>(VALID_USER);
        invalid.remove(field);
        given().contentType("application/json").body(invalid)
                .when().post("/users")
                .then().statusCode(400).body("errors." + field, equalTo(reason));
    }

    private void assertInvalidNameOrSurnameOnCreate(String field, String value) {
        Map<String, Object> invalid = new java.util.HashMap<>(VALID_USER);
        invalid.put(field, value);
        given().contentType("application/json").body(invalid)
                .when().post("/users")
                .then().statusCode(400).body("errors." + field, value.isBlank()
                        ? anyOf(equalTo("es obligatorio y no puede estar vacio"), equalTo("solo puede contener letras"))
                        : equalTo("solo puede contener letras"));
    }

    private void assertInvalidNameOrSurnameOnUpdate(int id, String field, String value) {
        given().contentType("application/json").body(Map.of(field, value))
                .when().put("/users/{id}", id)
                .then().statusCode(400).body("errors." + field, value.isBlank()
                        ? anyOf(equalTo("no puede estar vacio"), equalTo("solo puede contener letras"))
                        : equalTo("solo puede contener letras"));
    }

    @Transactional
    void deleteAllUsers() {
        entityManager.createQuery("delete from UserReference").executeUpdate();
        entityManager.createQuery("delete from User").executeUpdate();
    }

    @Transactional
    void createUserReference(int id) {
        entityManager.persist(new UserReference(entityManager.getReference(User.class, (long) id)));
    }

    private Map<String, Object> randomUser() {
        String suffix = ThreadLocalRandom.current().ints('a', 'z' + 1)
                .limit(12)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return Map.of(
                "nombre", "Nombre" + suffix,
                "apellidos", "Apellidos" + suffix,
                "fechaNacimiento", LocalDate.of(1980, 1, 1).toString(),
                "contrasena", "password-" + suffix);
    }
}
