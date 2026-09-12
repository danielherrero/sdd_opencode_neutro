package com.example.department;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.department.entity.Department;
import com.example.department.repository.DepartmentRepository;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
class DepartmentResourceTest {

    @BeforeAll
    static void enableHttpLogging() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @AfterAll
    static void resetRestAssuredConfiguration() {
        RestAssured.reset();
    }

    @Inject
    DepartmentRepository repository;

    private static final Map<String, Object> VALID_DEPARTMENT = Map.of(
            "nombre", "Finanzas",
            "descripcion", "Gestion 2.0 / Servicios especiales");

    @Test
    @DisplayName("RF-001 crea un departamento y devuelve 201 con identificador")
    void shouldCreateDepartment() {
        given().contentType("application/json").body(VALID_DEPARTMENT)
                .when().post("/departments")
                .then().statusCode(201).body("id", notNullValue());
    }

    @Test
    @DisplayName("RF-001 persiste el departamento creado")
    void shouldPersistDepartment() {
        int id = createDepartment();

        Department department = repository.findById((long) id);

        org.junit.jupiter.api.Assertions.assertNotNull(department);
        org.junit.jupiter.api.Assertions.assertEquals("Finanzas", department.getNombre());
    }

    @Test
    @DisplayName("RF-002 obtiene un departamento existente y rechaza uno inexistente")
    void shouldGetDepartment() {
        int id = createDepartment();

        given().when().get("/departments/{id}", id)
                .then().statusCode(200).body("id", equalTo(id));
        given().when().get("/departments/999999999")
                .then().statusCode(404);
    }

    @Test
    @DisplayName("RF-003 actualiza un departamento de forma parcial")
    void shouldPartiallyUpdateDepartment() {
        int id = createDepartment();

        given().contentType("application/json").body(Map.of("descripcion", "Nueva descripcion 2026!"))
                .when().put("/departments/{id}", id)
                .then().statusCode(200)
                .body("nombre", equalTo("Finanzas"))
                .body("descripcion", equalTo("Nueva descripcion 2026!"));
    }

    @Test
    @DisplayName("RF-003 rechaza actualizar un departamento inexistente")
    void shouldRejectUpdateOfMissingDepartment() {
        given().contentType("application/json").body(Map.of("nombre", "Ventas"))
                .when().put("/departments/999999999")
                .then().statusCode(404);
    }

    @Test
    @DisplayName("RF-004 elimina un departamento y rechaza uno inexistente")
    void shouldDeleteDepartment() {
        int id = createDepartment();

        given().when().delete("/departments/{id}", id)
                .then().statusCode(204);
        given().when().delete("/departments/{id}", id)
                .then().statusCode(404);
    }

    @Test
    @DisplayName("RF-005 identifica el nombre obligatorio ausente")
    void shouldRejectMissingName() {
        given().contentType("application/json").body(Map.of("descripcion", "Descripcion"))
                .when().post("/departments")
                .then().statusCode(400)
                .body("errors.nombre", equalTo("es obligatorio y no puede estar vacio"));
    }

    @Test
    @DisplayName("RF-005 rechaza un nombre vacio en actualizacion")
    void shouldRejectEmptyNameOnUpdate() {
        int id = createDepartment();

        given().contentType("application/json").body(Map.of("nombre", ""))
                .when().put("/departments/{id}", id)
                .then().statusCode(400).body("errors.nombre", equalTo("no puede estar vacio"));
    }

    @Test
    @DisplayName("RF-006 lista todos los departamentos")
    void shouldListDepartments() {
        deleteAllDepartments();
        createDepartment();
        createDepartment();

        given().when().get("/departments")
                .then().statusCode(200).body("$", hasSize(2));
    }

    @Test
    @DisplayName("RF-007 asigna la fecha del sistema si no se recibe")
    void shouldAssignCurrentDateWhenCreationDateIsMissing() {
        given().contentType("application/json").body(Map.of("nombre", "Recursos Humanos"))
                .when().post("/departments")
                .then().statusCode(201).body("fechaCreacion", equalTo(LocalDate.now().toString()));
    }

    @Test
    @DisplayName("RF-008 recorta nombre y descripcion y permite numeros y simbolos")
    void shouldTrimTextAndAllowSpecialCharacters() {
        Map<String, Object> department = Map.of(
                "nombre", "  I+D 2026  ",
                "descripcion", "  Area #1 / Servicios  ");

        given().contentType("application/json").body(department)
                .when().post("/departments")
                .then().statusCode(201)
                .body("nombre", equalTo("I+D 2026"))
                .body("descripcion", equalTo("Area #1 / Servicios"));
    }

    @Test
    @DisplayName("RF-008 recorta nombre y descripcion durante la modificacion")
    void shouldTrimTextOnUpdate() {
        int id = createDepartment();

        given().contentType("application/json").body(Map.of(
                "nombre", "  Ventas  ",
                "descripcion", "  Area comercial  "))
                .when().put("/departments/{id}", id)
                .then().statusCode(200)
                .body("nombre", equalTo("Ventas"))
                .body("descripcion", equalTo("Area comercial"));
    }

    private int createDepartment() {
        return given().contentType("application/json").body(VALID_DEPARTMENT)
                .when().post("/departments")
                .then().statusCode(201).extract().path("id");
    }

    @Transactional
    void deleteAllDepartments() {
        repository.deleteAll();
    }
}
