# Cheat Sheets Spring Boot Complètes

## Table des Matières

1. [Démarrage & Structure de Projet](#1-démarrage--structure-de-projet)
2. [Annotations Principales](#2-annotations-principales)
3. [Configuration & Properties](#3-configuration--properties)
4. [Contrôleurs REST & API](#4-contrôleurs-rest--api)
5. [Services & Injection des Dépendances](#5-services--injection-des-dépendances)
6. [Data Access: JPA & Repositories](#6-data-access-jpa--repositories)
7. [Validation Bean & DTOs](#7-validation-bean--dtos)
8. [Sécurité avec Spring Security & JWT](#8-sécurité-avec-spring-security--jwt)
9. [Gestion des Exceptions & Logging](#9-gestion-des-exceptions--logging)
10. [Tests & Bonnes Pratiques](#10-tests--bonnes-pratiques)
11. [Microservices & Spring Cloud](#11-microservices--spring-cloud)

---

## 1. Démarrage & Structure de Projet

### Initialisation rapide (Spring Initializr)
- Utiliser https://start.spring.io/
- Sélectionner les starters nécessaires : `web`, `jpa`, `security`, `devtools`, `lombok`, `validation`, `actuator`, etc.

### Structure de projet standard
```
src/
├── main/
│   ├── java/com/example/project/
│   │   ├── config
│   │   ├── controller
│   │   ├── dto
│   │   ├── exception
│   │   ├── model
│   │   ├── repository
│   │   ├── security
│   │   ├── service
│   │   └── utils
│   └── resources/
│       ├── application.properties
│       └── application.yml
└── test/
```

---

## 2. Annotations Principales

### Annotations de base
```java
@SpringBootApplication      // Point d'entrée, combine @Configuration, @EnableAutoConfiguration, @ComponentScan
@Component                 // Composant général Spring
@Service                   // Logique métier
@Repository                // Accès données (DAO)
@Controller                // Contrôleur MVC
@RestController            // Contrôleur REST (renvoie le corps directement)
@Configuration             // Classe de configuration
@Bean                      // Déclare un bean Spring dans une classe @Configuration
@Primary                   // Bean par défaut s'il y a plusieurs implémentations
@Qualifier                 // Qualifie le bean à injecter
@Autowired                 // Injection automatique de dépendance
@Value("${property}")        // Injection de propriété externe
@Profile("dev")             // Active le bean pour un profil donné
```

### Annotations Web
```java
@RequestMapping("/api")      // Mapping général sur une classe contrôleur
@GetMapping("/users")        // Mapping GET
@PostMapping("/users")       // Mapping POST
@PutMapping("/users/{id}")  // Mapping PUT
@DeleteMapping("/users/{id}") // Mapping DELETE
@RequestParam                // Lire un paramètre de requête ?id=3
@PathVariable                // Lire une variable d'URL /user/{id}
@RequestBody                 // Lier le corps d'une requête JSON à un objet
@ResponseBody                // Retourne la valeur du corps directement
```

### JPA & Data
```java
@Entity                     // Classe persistée en base de données
@Table                      // Mapping table
@Id                         // Clé primaire
@GeneratedValue             // Génération de clé
@Column                     // Mapping colonne
@OneToMany, @ManyToOne ...  // Relations entités
@Query                      // Requête JPQL personnalisée
```

### Sécurité
```java
@EnableWebSecurity          // Active la sécurité Web
@Secured("ROLE_ADMIN")      // Autorisation par rôle
@PreAuthorize               // SpEL pour sécuriser méthodes
@WithMockUser               // Test avec utilisateur simulé
```

### Validation
```java
@Valid
@NotBlank
@Email
@Size(min=, max=)
@Pattern
@Min / @Max
```

---

## 3. Configuration & Properties

### application.properties / application.yml
- Définir les propriétés personnalisées, JDBC, JPA, serveur, etc.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=postgres
spring.datasource.password=secret
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
logging.level.org.springframework=INFO
server.port=8081
my.custom.prop=valeur
```

### Profiles
```java
@Profile("dev")
public class DevConfig { ... }
```
- Profiles dans application-dev.properties, etc.

---

## 4. Contrôleurs REST & API

### Exemples de contrôleur
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
      this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
      return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
      return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto) {
      return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }
}
```

### Bonnes pratiques API REST
- Toujours utiliser DTOs, jamais exposer d'entités en API
- Statuts HTTP corrects :
  - 200 (OK), 201 (CREATED), 204 (NO CONTENT), 400 (BAD REQUEST), 401 (UNAUTHORIZED), 404 (NOT FOUND), 500 (ERROR)
- Pagination :
```java
@GetMapping
public Page<UserDto> getUsers(Pageable pageable) { ... }
```
- Versionning d'API
```java
@RequestMapping("/api/v1/users")
```
- Swagger/OpenAPI via springdoc-openapi

---

## 5. Services & Injection des Dépendances

### Principes
```java
@Service
public class UserService {
  private final UserRepository userRepository;
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
```
- **Toujours préférer injection via constructeur** pour testabilité.
- **@Autowired** est implicite à partir de Spring Boot 4.
- Utilisation de **@Qualifier** si plusieurs beans.

---

## 6. Data Access: JPA & Repositories

### Repository
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  @Query("SELECT u FROM User u WHERE u.active = true")
  List<User> findActiveUsers();
}
```

### Configuration JPA/Hibernate
- Entités cartographiées via annotations (@Entity, @Id, @Column, @Table)
- Chargement Lazy/Eager stratégique
- Les Repositories héritent de JpaRepository

### Optimisation
- Utilisation des index, projections, pagination
- @BatchSize pour le N+1, second level cache
- Critère/JPQL pour requêtes dynamiques
- DTO projections pour éviter LazyInitializationException

---

## 7. Validation Bean & DTOs

### Exemple de DTO validé
```java
public class UserDto {
  @NotNull @Size(min=2, max=50)
  private String name;
  @Email
  private String email;
}
```

### Gestion des erreurs de validation
```java
@Validated
@PostMapping("/api/users")
public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto) {...}
```

---

## 8. Sécurité avec Spring Security & JWT

### Dépendances (build.gradle ou pom.xml)
- spring-boot-starter-security
- spring-boot-starter-oauth2-resource-server (pour JWT)

### Configuration minimale
```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://idp.example.com/issuer
```

### Sécuriser les endpoints
```java
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .csrf().disable()
      .authorizeHttpRequests(auth -> auth
        .antMatchers("/api/public/**").permitAll()
        .antMatchers("/api/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
      )
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .oauth2ResourceServer().jwt()
      .and().and()
      .build();
  }
}
```

### Contrôle d'accès rôle
```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) { ... }
```

### Authentification JWT
- Génération de token à la connexion
```java
@PostMapping("/api/auth/login")
public String login(@RequestBody LoginRequest request) { ... // générer JWT }
```
- Vérification des tokens sur les appels suivants

### Centralisation gestion des exceptions
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
    // Format uniforme d'erreur
    return ResponseEntity.badRequest().body(map);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneral(Exception ex) { ... }
}
```

---

## 9. Gestion des Exceptions & Logging

### Logging
- Utiliser SLF4J ou Logback (`@Slf4j` de Lombok)
- logs dans `logs/` avec rotation si souhaité
- logs d’erreur, audit, accès (Spring Boot Actuator)

---

## 10. Tests & Bonnes Pratiques

### Types de tests
- Unitaires (JUnit 5, Mockito) : logique métier
- Intégration (@DataJpaTest, @SpringBootTest, MockMvc) : couches liées
- E2E : API/flow utilisateur complet

### Exemple test service
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock UserRepository repo;
  @InjectMocks UserService service;
  @Test
  void should_create_user() {
    ...
  }
}
```

### Exemple test controleur
```java
@WebMvcTest(UserController.class)
class UserControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean UserService userService;
  @Test
  void should_return_user() throws Exception {
    ...
  }
}
```

### Tactiques
- 70% unitaires, 20% intégration, 10% E2E (pyramide 70-20-10)
- branches logiques plutôt que lignes
- outils : Jacoco, SonarQube, testcontainers

---

## 11. Microservices & Spring Cloud

### Bonnes pratiques microservices
- Déploiement indépendant de chaque microservice
- Service discovery avec Spring Cloud/Eureka
- API Gateway (Spring Cloud Gateway)
- Configuration centralisée (Spring Cloud Config)
- Communication par Messaging/RabbitMQ/Kafka
- Sécurité distribuée JWT/Resource Server + OAuth2

### Optimisation & performance
- Utilisation des virtual threads (Java 21),
- GraalVM, AOT compilation,
- Monitoring : Spring Boot Actuator, Prometheus
- Circuit Breaker (Resilience4j)
- Tracing/Logging distribué (Sleuth, Zipkin)

---

## Références & Ressources
- [docs.spring.io](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Guides](https://spring.io/guides)
- [Baeldung Spring Boot](https://www.baeldung.com/spring-boot)
- [Dev.to Spring Boot](https://dev.to/t/springboot)

---

*Dernière mise à jour : Septembre 2025*