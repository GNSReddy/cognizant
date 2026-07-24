# Employee Management System

A production-quality Spring Boot application built using Spring Data JPA, Hibernate, H2 In-Memory Database, Validation, Lombok, and Maven. This project satisfies Exercises 1 to 10 of the JPA and Hibernate curriculum.

---

## Technical Stack & Configuration
*   **Java**: 17+ (JDK 21 Temurin)
*   **Spring Boot**: 3.2.5
*   **ORM**: Hibernate / Spring Data JPA
*   **Database**: Dual H2 In-Memory Databases (Primary and Secondary)
*   **Build Tool**: Apache Maven 3.9.6
*   **Library**: Lombok (for boilerplate reduction)

---

## Folder Structure
```
Spring_Data_JPA_and_Hibernate/
└── EmployeeManagementSystem/
    ├── src/
    │   └── main/
    │       ├── java/
    │       │   └── com/
    │       │       └── ems/
    │       │           ├── config/          # Datasource & Auditing Config
    │       │           ├── controller/      # REST API Controllers
    │       │           ├── dto/             # Class-based Projection DTOs
    │       │           ├── entity/
    │       │           │   ├── primary/     # Employee & Department (Primary DB)
    │       │           │   └── secondary/   # AuditLog (Secondary DB)
    │       │           ├── exception/       # Global Exception Handler
    │       │           ├── projection/      # Interface-based Projections
    │       │           ├── repository/
    │       │           │   ├── primary/     # Primary DB repositories
    │       │           │   └── secondary/   # Secondary DB repositories
    │       │           ├── service/         # Service layer interfaces
    │       │           │   ├── impl/        # Service implementations
    │       │           └── util/            # AuditorAware helper implementations
    │       └── resources/
    │           └── application.properties   # External Configuration
    ├── pom.xml
    └── README.md
```

---

## Exercise Details

### Exercise 1: Setup & Initialization
*   Bootstrap Spring Boot with Web, JPA, Lombok, Validation, and H2 database dependencies.
*   Configure dual data sources and H2 web console access at `/h2-console` in `application.properties`.

### Exercise 2: Entities & Relationships
*   **Department**: Contains `id` and `name`, having a `@OneToMany` relationship with `Employee`.
*   **Employee**: Contains `id`, `name`, `email`, and `department`, having a `@ManyToOne` relationship with `Department`.
*   Uses Lombok `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, and `@Builder` annotations with proper `toString` overrides to avoid circular recursion.

### Exercise 3: Spring Data Repositories
*   Interfaces `EmployeeRepository` and `DepartmentRepository` extending `JpaRepository`.
*   Derived query methods like `findByName`, `findByEmail`, and `findByDepartmentId`.

### Exercise 4: CRUD REST APIs
*   Exposes endpoints to create, read, update, delete, and list Departments and Employees.
*   Maps HTTP response codes (e.g. `201 Created` for creation, `204 No Content` for deletion, `404 Not Found` for missing resources).
*   Configured `@ControllerAdvice` (`GlobalExceptionHandler`) to format validation and resource errors into standardized JSON payloads.

### Exercise 5: Custom Queries
*   **Derived**: `findByNameContaining`.
*   **JPQL Query**: Custom query to retrieve employees by department name (`findEmployeesByDeptName`).
*   **Native SQL Query**: Custom native query (`findEmployeesByEmailDomain`).
*   **Named Queries**: Declared on the `Employee` entity via `@NamedQueries`/`@NamedQuery` and bound inside the repository.

### Exercise 6: Pagination and Sorting
*   Endpoint supporting `Pageable`, returning `Page<Employee>` containing list data and pagination metadata.
*   Allows sorting results dynamically via URL queries (e.g., sorting by name, email, or ID in ascending or descending direction).

### Exercise 7: Entity Auditing
*   Enabled JPA auditing with `@EnableJpaAuditing` and registered a custom `AuditorAware` bean (`AuditorAwareImpl`) supplying `admin_user`.
*   Entities extend `Auditable` which contains `@CreatedBy`, `@CreatedDate`, `@LastModifiedBy`, and `@LastModifiedDate` fields.

### Exercise 8: Projections
*   **Interface-based**: `EmployeeProjection` containing closed fields (`getName()`, `getEmail()`, `getDepartmentName()`) and an open field utilizing SpEL (`@Value("#{target.name + ' (' + target.email + ')'}")`).
*   **Class-based DTO**: `EmployeeDto` utilizing JPQL constructor expressions to select a subset of columns.

### Exercise 9: Multiple Datasources Configuration
*   Configured two in-memory databases to simulate multi-tenant or auditing configurations:
    1.  **Primary Database (`jdbc:h2:mem:primarydb`)**: Stores Employee and Department entities.
    2.  **Secondary Database (`jdbc:h2:mem:secondarydb`)**: Stores `AuditLog` transaction details.
*   Created `PrimaryDbConfig` and `SecondaryDbConfig` to manually structure transaction and entity managers.

### Exercise 10: Hibernate Optimizations & Batching
*   Hibernate properties like `hibernate.jdbc.batch_size=20`, `order_inserts`, and `order_updates` are configured.
*   `@DynamicUpdate` and `@DynamicInsert` annotations on the `Employee` entity to generate minimal SQL.
*   `@BatchSize` on collections to optimize N+1 query problem.
*   Implemented `BatchService` containing a loop to insert records in batches using `EntityManager.flush()` and `clear()`.

---

## API Endpoints List

### Department Endpoints
*   `POST /api/departments` - Create a department
*   `GET /api/departments` - Get all departments
*   `GET /api/departments/{id}` - Get department by ID
*   `PUT /api/departments/{id}` - Update department
*   `DELETE /api/departments/{id}` - Delete department

### Employee Endpoints
*   `POST /api/employees?departmentId={id}` - Create an employee under a department
*   `GET /api/employees` - List all employees
*   `GET /api/employees/{id}` - Get employee by ID
*   `PUT /api/employees/{id}?departmentId={id}` - Update employee and/or department
*   `DELETE /api/employees/{id}` - Delete employee

### Custom Queries, Pagination & Sorting
*   `GET /api/employees/paginated?page=0&size=5&sortBy=name&direction=asc` - Paginated & sorted employees
*   `GET /api/employees/search?name={infix}&page=0&size=5` - Paginated search by name
*   `GET /api/employees/dept/{deptName}` - Find by department name (JPQL)
*   `GET /api/employees/email-domain?domain={suffix}` - Find by email domain (Native SQL)
*   `GET /api/employees/email-named?email={email}` - Find by email (Named Query)
*   `GET /api/employees/dept-named/{deptName}` - Find by department (Named Query)

### Projections
*   `GET /api/employees/projections` - Get interface projections
*   `GET /api/employees/dtos` - Get class-based DTO projections

### Audit Logs (Secondary Database)
*   `GET /api/audit-logs` - Fetch audit logs from secondary database
*   `GET /api/audit-logs/action?action={action}` - Search audit logs by action

### Batch Operations
*   `POST /api/batch/employees?departmentId={id}&count={count}` - Trigger high-performance Hibernate batch insert of `{count}` employees.
