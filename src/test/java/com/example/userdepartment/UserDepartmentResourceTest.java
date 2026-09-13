package com.example.userdepartment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.*;

import com.example.department.entity.Department;
import com.example.department.repository.DepartmentRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.userdepartment.entity.UserDepartment;
import com.example.userdepartment.repository.UserDepartmentRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
class UserDepartmentResourceTest {
    @Inject UserDepartmentRepository associations;
    @Inject UserRepository users;
    @Inject DepartmentRepository departments;
    private Long[] userIds;
    private Long[] departmentIds;

    @BeforeAll static void enableHttpLogging() { RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); }
    @AfterAll static void resetRestAssuredConfiguration() { RestAssured.reset(); }

    @BeforeEach
    @Transactional
    void clearAndCreateRandomData() {
        associations.deleteAll();
        users.getEntityManager().flush();
        users.deleteAll();
        departments.deleteAll();
        userIds = new Long[5]; departmentIds = new Long[5];
        for (int i = 0; i < 5; i++) {
            String suffix = randomLetters(8);
            User user = new User("User" + suffix, "Surname" + suffix, LocalDate.of(1980, 1, 1), BcryptUtil.bcryptHash("password" + suffix));
            users.persist(user); userIds[i] = user.getId();
            Department department = new Department("Department" + suffix, "Description " + suffix, LocalDate.now());
            departments.persist(department); departmentIds[i] = department.getId();
        }
    }

    @Test @DisplayName("RF-001 crea una asociacion con ID y fecha automaticos")
    void shouldCreateAssociation() {
        given().contentType("application/json").body(Map.of("userId", userIds[0], "departmentId", departmentIds[0]))
                .when().post("/user-departments").then().statusCode(201).body("id", notNullValue()).body("fechaCreacion", equalTo(LocalDate.now().toString()));
    }

    @Test @DisplayName("RF-002 consulta una asociacion existente y rechaza una inexistente")
    void shouldGetAssociation() {
        int id = createAssociation(0, 0);
        given().when().get("/user-departments/{id}", id).then().statusCode(200).body("userId", equalTo(userIds[0].intValue()));
        given().when().get("/user-departments/999999999").then().statusCode(404);
    }

    @Test @DisplayName("RF-003 modifica una asociacion conservando su fecha")
    void shouldUpdateAssociation() {
        int id = createAssociation(0, 0);
        String date = given().when().get("/user-departments/{id}", id).then().extract().path("fechaCreacion");
        given().contentType("application/json").body(Map.of("departmentId", departmentIds[1]))
                .when().put("/user-departments/{id}", id).then().statusCode(200).body("departmentId", equalTo(departmentIds[1].intValue())).body("fechaCreacion", equalTo(date));
    }

    @Test @DisplayName("RF-004 elimina una asociacion")
    void shouldDeleteAssociation() { int id = createAssociation(0, 0); given().when().delete("/user-departments/{id}", id).then().statusCode(204); }

    @Test @DisplayName("RF-005 lista asociaciones")
    void shouldListAssociations() { createAssociation(0, 0); createAssociation(0, 1); given().when().get("/user-departments").then().statusCode(200).body("$", hasSize(2)); }

    @Test @DisplayName("RF-010 permite cero, una o multiples asociaciones por usuario")
    void shouldAllowZeroOneOrManyDepartmentsPerUser() {
        createAssociation(0, 0); createAssociation(0, 1); createAssociation(0, 2);
        given().when().get("/user-departments").then().statusCode(200).body("$", hasSize(3)).body("userId", everyItem(equalTo(userIds[0].intValue())));
    }

    @Test @DisplayName("RF-006 rechaza IDs ausentes o inexistentes identificando el campo")
    void shouldValidateReferences() {
        given().contentType("application/json").body(Map.of("userId", userIds[0])).when().post("/user-departments").then().statusCode(400).body("errors.departmentId", notNullValue());
        given().contentType("application/json").body(Map.of("userId", 999999999L, "departmentId", departmentIds[0])).when().post("/user-departments").then().statusCode(404).body("error", containsString("userId"));
    }

    private int createAssociation(int user, int department) { return given().contentType("application/json").body(Map.of("userId", userIds[user], "departmentId", departmentIds[department])).when().post("/user-departments").then().statusCode(201).extract().path("id"); }
    private String randomLetters(int length) { return ThreadLocalRandom.current().ints('a', 'z' + 1).limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString(); }
}
