# Cheat Sheets Java Complètes

## Table des Matières

1. [Bases de Java](#1-bases-de-java)
2. [Collections Framework](#2-collections-framework)
3. [Streams API](#3-streams-api)
4. [Lambda Expressions](#4-lambda-expressions)
5. [Concurrence et Multithreading](#5-concurrence-et-multithreading)
6. [Gestion Mémoire JVM](#6-gestion-mémoire-jvm)
7. [Annotations Spring Boot](#7-annotations-spring-boot)
8. [Design Patterns](#8-design-patterns)
9. [Syntaxe Avancée](#9-syntaxe-avancée)

---

## 1. Bases de Java

### Types de Données Primitifs

| Type | Taille | Valeur par défaut | Plage |
|------|--------|------------------|-------|
| `byte` | 1 byte | 0 | -128 à 127 |
| `short` | 2 bytes | 0 | -2^15 à 2^15-1 |
| `int` | 4 bytes | 0 | -2^31 à 2^31-1 |
| `long` | 8 bytes | 0L | -2^63 à 2^63-1 |
| `float` | 4 bytes | 0.0f | N/A |
| `double` | 8 bytes | 0.0d | N/A |
| `char` | 2 bytes | '\u0000' | 0 à 65535 |
| `boolean` | N/A | false | true / false |

### Déclaration et Initialisation

```java
// Types primitifs
int num = 10;
double pi = 3.1415;
boolean flag = true;
char letter = 'A';

// Objets
String name = "Java";
Integer boxedInt = Integer.valueOf(42);

// Tableaux
int[] numbers = {1, 2, 3, 4, 5};
String[] words = new String[10];
```

### Structures de Contrôle

```java
// Conditionnelles
if (condition) {
    // code
} else if (condition2) {
    // code
} else {
    // code
}

switch (variable) {
    case VALUE1:
        // code
        break;
    case VALUE2:
        // code
        break;
    default:
        // code
}

// Boucles
for (int i = 0; i < 10; i++) {
    // code
}

for (String item : collection) {
    // code
}

while (condition) {
    // code
}

do {
    // code
} while (condition);
```

### Méthodes

```java
// Méthode basique
public static returnType methodName(parameters) {
    // corps de la méthode
    return value; // si nécessaire
}

// Méthode avec varargs
public void method(String... args) {
    for (String arg : args) {
        System.out.println(arg);
    }
}

// Méthode générique
public <T> T genericMethod(T param) {
    return param;
}
```

---

## 2. Collections Framework

### Hiérarchie des Collections

```
Collection
├── List (ArrayList, LinkedList, Vector)
├── Set (HashSet, LinkedHashSet, TreeSet)
└── Queue (LinkedList, PriorityQueue, ArrayDeque)

Map (HashMap, LinkedHashMap, TreeMap, Hashtable)
```

### Constructeurs Courants

```java
// Lists
List<Integer> list = new ArrayList<>();
List<String> linkedList = new LinkedList<>();
List<Double> vector = new Vector<>();

// Sets
Set<String> hashSet = new HashSet<>();
Set<Integer> treeSet = new TreeSet<>();
Set<String> linkedHashSet = new LinkedHashSet<>();

// Maps
Map<String, Integer> hashMap = new HashMap<>();
Map<String, Integer> treeMap = new TreeMap<>();
Map<String, Integer> linkedHashMap = new LinkedHashMap<>();

// Queues
Queue<Double> queue = new LinkedList<>();
Deque<String> deque = new ArrayDeque<>();
Queue<Integer> priorityQueue = new PriorityQueue<>();
```

### Méthodes Essentielles

#### Collection Methods (Tous)
```java
boolean add(E element)
boolean remove(Object o)
int size()
boolean isEmpty()
boolean contains(Object o)
void clear()
Object[] toArray()
Iterator<E> iterator()
```

#### List Methods
```java
void add(int index, E element)
E get(int index)
E set(int index, E element)
E remove(int index)
int indexOf(Object o)
int lastIndexOf(Object o)
List<E> subList(int from, int to)
```

#### Set Methods
```java
// Hérite de Collection - pas de méthodes supplémentaires uniques
// TreeSet ajoute des méthodes SortedSet:
E first()
E last()
SortedSet<E> headSet(E toElement)
SortedSet<E> tailSet(E fromElement)
```

#### Map Methods
```java
V put(K key, V value)
V get(Object key)
V remove(Object key)
boolean containsKey(Object key)
boolean containsValue(Object value)
Set<K> keySet()
Collection<V> values()
Set<Map.Entry<K,V>> entrySet()
```

### Caractéristiques des Collections

| Collection | Interface | Ordonné | Trié | Thread-safe | Doublons | Null |
|------------|-----------|---------|------|-------------|----------|------|
| ArrayList | List | Oui | Non | Non | Oui | Oui |
| LinkedList | List, Deque | Oui | Non | Non | Oui | Oui |
| Vector | List | Oui | Non | Oui | Oui | Oui |
| HashSet | Set | Non | Non | Non | Non | Un null |
| LinkedHashSet | Set | Oui | Non | Non | Non | Un null |
| TreeSet | Set | Oui | Oui | Non | Non | Non |
| HashMap | Map | Non | Non | Non | Non (key) | Un null (key) |
| LinkedHashMap | Map | Oui | Non | Non | Non (key) | Un null (key) |
| TreeMap | Map | Oui | Oui | Non | Non (key) | Non (key) |
| Hashtable | Map | Non | Non | Oui | Non (key) | Non |

---

## 3. Streams API

### Création de Streams

```java
// À partir d'une collection
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();

// Stream parallèle
Stream<String> parallelStream = list.parallelStream();

// À partir de valeurs
Stream<String> streamOf = Stream.of("a", "b", "c");

// Stream infini
Stream<Integer> infinite = Stream.iterate(0, n -> n + 1);

// Stream avec limite
Stream<Double> randoms = Stream.generate(Math::random).limit(10);

// À partir d'un tableau
String[] array = {"x", "y", "z"};
Stream<String> arrayStream = Arrays.stream(array);
```

### Opérations Intermédiaires

| Opération | Description | Exemple |
|-----------|-------------|---------|
| `filter(Predicate)` | Filtre les éléments | `stream.filter(s -> s.length() > 3)` |
| `map(Function)` | Transforme les éléments | `stream.map(String::toUpperCase)` |
| `flatMap(Function)` | Aplatit les streams imbriqués | `stream.flatMap(s -> Arrays.stream(s.split("")))` |
| `distinct()` | Supprime les doublons | `stream.distinct()` |
| `sorted()` | Trie les éléments | `stream.sorted()` |
| `sorted(Comparator)` | Trie avec comparateur | `stream.sorted(Comparator.reverseOrder())` |
| `peek(Consumer)` | Observe les éléments | `stream.peek(System.out::println)` |
| `limit(long)` | Limite le nombre d'éléments | `stream.limit(5)` |
| `skip(long)` | Saute les premiers éléments | `stream.skip(3)` |

### Opérations Terminales

| Opération | Description | Exemple |
|-----------|-------------|---------|
| `forEach(Consumer)` | Action sur chaque élément | `stream.forEach(System.out::println)` |
| `collect(Collector)` | Collecte en collection | `stream.collect(Collectors.toList())` |
| `reduce(BinaryOperator)` | Réduit à une valeur | `stream.reduce(Integer::sum)` |
| `count()` | Compte les éléments | `long count = stream.count()` |
| `anyMatch(Predicate)` | Vérifie si au moins un correspond | `boolean any = stream.anyMatch(s -> s.contains("a"))` |
| `allMatch(Predicate)` | Vérifie si tous correspondent | `boolean all = stream.allMatch(s -> s.length() > 0)` |
| `noneMatch(Predicate)` | Vérifie si aucun ne correspond | `boolean none = stream.noneMatch(String::isEmpty)` |
| `findFirst()` | Premier élément | `Optional<String> first = stream.findFirst()` |
| `findAny()` | N'importe quel élément | `Optional<String> any = stream.findAny()` |
| `min(Comparator)` | Élément minimum | `Optional<String> min = stream.min(String::compareTo)` |
| `max(Comparator)` | Élément maximum | `Optional<String> max = stream.max(String::compareTo)` |

### Collectors Utiles

```java
// Basiques
Collectors.toList()
Collectors.toSet()
Collectors.toMap(keyMapper, valueMapper)

// Jointure
Collectors.joining() // "abc"
Collectors.joining(", ") // "a, b, c"
Collectors.joining(", ", "[", "]") // "[a, b, c]"

// Groupement
Collectors.groupingBy(classifier)
Collectors.groupingBy(classifier, downstream)

// Partitionnement
Collectors.partitioningBy(predicate)

// Statistiques
Collectors.counting()
Collectors.summingInt(mapper)
Collectors.averagingDouble(mapper)
Collectors.summarizingInt(mapper)
```

### Exemples Complets

```java
// Exemple 1: Noms uniques en majuscules, limités à 15
List<String> result = authors.stream()
    .filter(author -> author.getAge() >= 50)
    .map(Author::getName)
    .map(String::toUpperCase)
    .distinct()
    .limit(15)
    .collect(Collectors.toList());

// Exemple 2: Somme des âges des femmes < 25 ans
int sum = authors.stream()
    .filter(author -> author.getGender() == Gender.FEMALE)
    .mapToInt(Author::getAge)
    .filter(age -> age < 25)
    .sum();

// Exemple 3: Groupement par âge
Map<Integer, List<Author>> byAge = authors.stream()
    .collect(Collectors.groupingBy(Author::getAge));
```

---

## 4. Lambda Expressions

### Syntaxe de Base

```java
// Syntaxe générale
(parameters) -> expression
(parameters) -> { statements; }

// Sans paramètres
() -> System.out.println("Hello");

// Un paramètre (parenthèses optionnelles)
param -> param * 2
(param) -> param * 2

// Plusieurs paramètres
(a, b) -> a + b
(String s, int i) -> s.length() + i

// Corps de méthode
(a, b) -> {
    int sum = a + b;
    return sum;
}
```

### Interfaces Fonctionnelles Courantes

```java
// Consumer<T> - consomme un argument, ne retourne rien
Consumer<String> printer = s -> System.out.println(s);
Consumer<String> printer2 = System.out::println; // method reference

// Function<T, R> - prend T, retourne R
Function<String, Integer> length = s -> s.length();
Function<String, Integer> length2 = String::length;

// Predicate<T> - prend T, retourne boolean
Predicate<String> isEmpty = s -> s.isEmpty();
Predicate<String> isEmpty2 = String::isEmpty;

// Supplier<T> - ne prend rien, retourne T
Supplier<Double> random = () -> Math.random();
Supplier<Double> random2 = Math::random;

// UnaryOperator<T> - prend T, retourne T
UnaryOperator<String> toUpper = s -> s.toUpperCase();
UnaryOperator<String> toUpper2 = String::toUpperCase;

// BinaryOperator<T> - prend deux T, retourne T
BinaryOperator<Integer> add = (a, b) -> a + b;
BinaryOperator<Integer> add2 = Integer::sum;
```

### Method References

```java
// Référence à une méthode statique
Function<String, Integer> parser = Integer::parseInt;

// Référence à une méthode d'instance d'un objet particulier
String str = "Hello";
Supplier<Integer> lengthSupplier = str::length;

// Référence à une méthode d'instance d'un type arbitraire
Function<String, Integer> lengthFunction = String::length;

// Référence à un constructeur
Supplier<ArrayList> listSupplier = ArrayList::new;
Function<Integer, ArrayList> listFunction = ArrayList::new;
```

### Variables dans les Lambdas

```java
// Variables effectively final
final int multiplier = 10;
Function<Integer, Integer> multiply = x -> x * multiplier;

// Variable effectively final (pas de reassignment)
int base = 100;
Function<Integer, Integer> add = x -> x + base;
// base = 200; // Erreur de compilation
```

---

## 5. Concurrence et Multithreading

### Thread Basics

```java
// Création de threads
class MyThread extends Thread {
    public void run() {
        // Code à exécuter
    }
}

class MyRunnable implements Runnable {
    public void run() {
        // Code à exécuter
    }
}

// Utilisation
Thread thread1 = new MyThread();
Thread thread2 = new Thread(new MyRunnable());
Thread thread3 = new Thread(() -> System.out.println("Lambda"));

thread1.start(); // Lance l'exécution
thread2.join();  // Attend la fin
```

### Synchronisation

```java
// Méthode synchronisée
public synchronized void synchronizedMethod() {
    // Code thread-safe
}

// Bloc synchronisé
public void method() {
    synchronized(this) {
        // Code thread-safe
    }
}

// Lock explicite
private final ReentrantLock lock = new ReentrantLock();

public void methodWithLock() {
    lock.lock();
    try {
        // Code thread-safe
    } finally {
        lock.unlock();
    }
}
```

### Classes Atomic

```java
// Variables atomiques
AtomicInteger atomicInt = new AtomicInteger(0);
atomicInt.incrementAndGet(); // Thread-safe increment
atomicInt.compareAndSet(expected, update);

AtomicReference<String> atomicRef = new AtomicReference<>();
atomicRef.set("value");
String value = atomicRef.get();
```

### Executor Framework

```java
// Types d'executors
ExecutorService singleThread = Executors.newSingleThreadExecutor();
ExecutorService fixedPool = Executors.newFixedThreadPool(5);
ExecutorService cachedPool = Executors.newCachedThreadPool();
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// Soumission de tâches
executor.execute(() -> System.out.println("Task"));
Future<String> future = executor.submit(() -> "Result");

// Récupération de résultats
try {
    String result = future.get(); // Bloque jusqu'au résultat
    String result2 = future.get(1, TimeUnit.SECONDS); // Timeout
} catch (InterruptedException | ExecutionException | TimeoutException e) {
    // Gestion d'erreur
}

// Fermeture
executor.shutdown();
executor.awaitTermination(60, TimeUnit.SECONDS);
```

### Collections Concurrentes

```java
// Maps thread-safe
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.putIfAbsent("key", 1);
concurrentMap.compute("key", (k, v) -> v == null ? 1 : v + 1);

// Listes thread-safe
CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();

// Queues bloquantes
BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
queue.put(1); // Bloque si pleine
Integer item = queue.take(); // Bloque si vide
```

### Classes de Coordination

```java
// CountDownLatch - compte à rebours
CountDownLatch latch = new CountDownLatch(3);
latch.countDown(); // Décrémente
latch.await(); // Attend que le compte atteigne 0

// CyclicBarrier - barrière cyclique
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All ready"));
barrier.await(); // Attend que tous les threads atteignent la barrière

// Semaphore - contrôle l'accès
Semaphore semaphore = new Semaphore(2); // 2 permits
semaphore.acquire(); // Acquiert un permit
semaphore.release(); // Libère un permit
```

---

## 6. Gestion Mémoire JVM

### Structure Mémoire JVM

```
JVM Memory
├── Heap Memory
│   ├── Young Generation
│   │   ├── Eden Space
│   │   ├── Survivor S0
│   │   └── Survivor S1
│   └── Old Generation (Tenured)
├── Non-Heap Memory
│   ├── Method Area (Metaspace)
│   ├── Code Cache
│   └── Direct Memory
└── Stack Memory (per thread)
    ├── Stack Frames
    ├── Local Variables
    └── Operand Stack
```

### Options JVM Mémoire

#### Taille du Heap
```bash
-Xms<size>    # Taille initiale du heap
-Xmx<size>    # Taille maximale du heap
-Xmn<size>    # Taille de la Young Generation

# Exemples
-Xms512m -Xmx2g -Xmn512m
```

#### Contrôle des Générations
```bash
-XX:NewRatio=<ratio>              # Ratio Old/Young
-XX:SurvivorRatio=<ratio>         # Ratio Eden/Survivor
-XX:MaxTenuringThreshold=<age>    # Âge max avant promotion
```

#### Mémoire Directe
```bash
-XX:MaxDirectMemorySize=<size>    # Limite mémoire directe
```

#### Stack
```bash
-Xss<size>    # Taille de la stack par thread
```

### Garbage Collectors

#### Serial GC (applications mono-thread)
```bash
-XX:+UseSerialGC
```

#### Parallel GC (par défaut)
```bash
-XX:+UseParallelGC
-XX:ParallelGCThreads=<n>
```

#### G1 GC (recommandé pour gros heaps)
```bash
-XX:+UseG1GC
-XX:MaxGCPauseMillis=<ms>
-XX:G1HeapRegionSize=<size>
```

#### ZGC (très faible latence)
```bash
-XX:+UseZGC
-XX:+UnlockExperimentalVMOptions  # Java 11-14
```

### Monitoring et Debugging

```bash
# Logs GC
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-Xloggc:gc.log

# Dump heap en cas d'OutOfMemoryError
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/path/to/dumps

# JFR (Java Flight Recorder)
-XX:+FlightRecorder
-XX:StartFlightRecording=duration=60s,filename=profile.jfr
```

### Outils de Diagnostic

```bash
# jstat - statistiques GC
jstat -gc <pid> 5s      # Stats GC toutes les 5 secondes
jstat -gccapacity <pid> # Capacités des générations

# jmap - dump mémoire
jmap -dump:live,format=b,file=heap.hprof <pid>
jmap -histo <pid>       # Histogramme des objets

# jcmd - commandes diverses
jcmd <pid> GC.run_finalization
jcmd <pid> VM.gc
```

---

## 7. Annotations Spring Boot

### Annotations Core

```java
@SpringBootApplication  // @Configuration + @EnableAutoConfiguration + @ComponentScan
@EnableAutoConfiguration
@Configuration
@ComponentScan
@Bean
@Value("${property.name}")
@Profile("dev")
```

### Annotations Stéréotypes

```java
@Component       // Composant générique
@Service        // Couche service
@Repository     // Couche d'accès aux données
@Controller     // Contrôleur web
@RestController // @Controller + @ResponseBody
```

### Annotations Web

```java
@RequestMapping("/path")
@GetMapping("/path")
@PostMapping("/path")
@PutMapping("/path")
@DeleteMapping("/path")
@PatchMapping("/path")

@RequestParam("param")
@PathVariable("id")
@RequestBody
@ResponseBody
@RequestHeader("header")
@CookieValue("cookie")
```

### Annotations Injection de Dépendances

```java
@Autowired              // Injection automatique
@Qualifier("beanName")  // Spécifie quel bean injecter
@Primary               // Bean prioritaire
@Lazy                  // Initialisation paresseuse
```

### Annotations JPA/Hibernate

```java
@Entity
@Table(name = "users")
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "user_name")
@OneToMany
@ManyToOne
@JoinColumn(name = "user_id")
@Transactional
```

### Annotations Validation

```java
@Valid
@NotNull
@NotEmpty
@NotBlank
@Size(min = 1, max = 50)
@Min(1)
@Max(100)
@Email
@Pattern(regexp = "...")
```

### Annotations Test

```java
@SpringBootTest
@WebMvcTest
@DataJpaTest
@MockBean
@Test
@BeforeEach
@AfterEach
```

### Annotations Sécurité

```java
@EnableWebSecurity
@PreAuthorize("hasRole('USER')")
@PostAuthorize("returnObject.owner == authentication.name")
@Secured("ROLE_USER")
@RolesAllowed("USER")
```

### Annotations Cache

```java
@EnableCaching
@Cacheable("users")
@CacheEvict(value = "users", allEntries = true)
@CachePut("users")
```

### Annotations Scheduling

```java
@EnableScheduling
@Scheduled(fixedRate = 5000)
@Scheduled(cron = "0 0 * * * *")
@Async
@EnableAsync
```

---

## 8. Design Patterns

### Patterns Créationnels

#### Singleton
```java
// Thread-safe lazy initialization
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

// Enum singleton (recommandé)
public enum Singleton {
    INSTANCE;
    
    public void doSomething() {
        // ...
    }
}
```

#### Factory Method
```java
abstract class Creator {
    abstract Product createProduct();
    
    public void someOperation() {
        Product product = createProduct();
        // utiliser le produit
    }
}

class ConcreteCreator extends Creator {
    @Override
    Product createProduct() {
        return new ConcreteProduct();
    }
}
```

#### Builder
```java
public class Person {
    private String name;
    private int age;
    private String email;
    
    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }
    
    public static class Builder {
        private String name;
        private int age;
        private String email;
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Person build() {
            return new Person(this);
        }
    }
}

// Utilisation
Person person = new Person.Builder()
    .name("John")
    .age(30)
    .email("john@example.com")
    .build();
```

### Patterns Structurels

#### Adapter
```java
// Interface cible
interface Target {
    void request();
}

// Classe à adapter
class Adaptee {
    public void specificRequest() {
        // logique existante
    }
}

// Adaptateur
class Adapter implements Target {
    private Adaptee adaptee;
    
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public void request() {
        adaptee.specificRequest();
    }
}
```

#### Decorator
```java
interface Component {
    void operation();
}

class ConcreteComponent implements Component {
    @Override
    public void operation() {
        // opération de base
    }
}

abstract class Decorator implements Component {
    protected Component component;
    
    public Decorator(Component component) {
        this.component = component;
    }
    
    @Override
    public void operation() {
        component.operation();
    }
}

class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }
    
    @Override
    public void operation() {
        super.operation();
        addedBehavior();
    }
    
    private void addedBehavior() {
        // comportement supplémentaire
    }
}
```

### Patterns Comportementaux

#### Observer
```java
interface Observer {
    void update(String message);
}

class Subject {
    private List<Observer> observers = new ArrayList<>();
    
    public void attach(Observer observer) {
        observers.add(observer);
    }
    
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }
}

class ConcreteObserver implements Observer {
    private String name;
    
    public ConcreteObserver(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String message) {
        System.out.println(name + " received: " + message);
    }
}
```

#### Strategy
```java
interface Strategy {
    void execute();
}

class ConcreteStrategyA implements Strategy {
    @Override
    public void execute() {
        // algorithme A
    }
}

class ConcreteStrategyB implements Strategy {
    @Override
    public void execute() {
        // algorithme B
    }
}

class Context {
    private Strategy strategy;
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public void executeStrategy() {
        strategy.execute();
    }
}
```

---

## 9. Syntaxe Avancée

### Generics

```java
// Classe générique
public class Box<T> {
    private T content;
    
    public void set(T content) {
        this.content = content;
    }
    
    public T get() {
        return content;
    }
}

// Méthode générique
public static <T> void swap(T[] array, int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}

// Wildcards
List<? extends Number> numbers;    // Upper bound
List<? super Integer> integers;    // Lower bound
List<?> unknown;                   // Unbounded
```

### Exceptions

```java
// Try-with-resources
try (FileInputStream fis = new FileInputStream("file.txt");
     BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
    // utiliser les ressources
} catch (IOException e) {
    // gestion d'erreur
}

// Multi-catch
try {
    // code risqué
} catch (IOException | SQLException e) {
    // gestion commune
}

// Exception personnalisée
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
    
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Annotations Personnalisées

```java
// Définition
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {
    String value() default "";
    int priority() default 1;
}

// Utilisation
@MyAnnotation(value = "test", priority = 5)
public void annotatedMethod() {
    // ...
}

// Lecture par réflexion
Method method = obj.getClass().getMethod("annotatedMethod");
if (method.isAnnotationPresent(MyAnnotation.class)) {
    MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
    String value = annotation.value();
    int priority = annotation.priority();
}
```

### Optional

```java
// Création
Optional<String> optional = Optional.of("value");
Optional<String> nullable = Optional.ofNullable(getString());
Optional<String> empty = Optional.empty();

// Vérification
if (optional.isPresent()) {
    String value = optional.get();
}

// Méthodes fonctionnelles
optional.ifPresent(System.out::println);
String result = optional.orElse("default");
String result2 = optional.orElseGet(() -> computeDefault());
String result3 = optional.orElseThrow(() -> new RuntimeException("No value"));

// Transformation
optional.map(String::toUpperCase)
       .filter(s -> s.length() > 5)
       .ifPresent(System.out::println);
```

### Réflexion

```java
// Obtenir la classe
Class<?> clazz = obj.getClass();
Class<?> clazz2 = String.class;
Class<?> clazz3 = Class.forName("java.lang.String");

// Informations sur la classe
String name = clazz.getName();
Package pkg = clazz.getPackage();
Class<?> superClass = clazz.getSuperclass();
Class<?>[] interfaces = clazz.getInterfaces();

// Champs
Field[] fields = clazz.getDeclaredFields();
Field field = clazz.getDeclaredField("fieldName");
field.setAccessible(true); // Pour les champs privés
Object value = field.get(obj);
field.set(obj, newValue);

// Méthodes
Method[] methods = clazz.getDeclaredMethods();
Method method = clazz.getMethod("methodName", String.class, int.class);
Object result = method.invoke(obj, "arg1", 42);

// Constructeurs
Constructor<?> constructor = clazz.getConstructor(String.class);
Object newInstance = constructor.newInstance("argument");
```

---

## Références et Ressources

Cette cheat sheet complète couvre les aspects essentiels de Java pour les développeurs de tous niveaux. Pour approfondir :

1. **Documentation officielle Oracle Java** - Référence complète
2. **Effective Java (Joshua Bloch)** - Bonnes pratiques
3. **Java Concurrency in Practice** - Programmation concurrente
4. **Spring Framework Documentation** - Spring Boot et écosystème
5. **Baeldung.com** - Tutoriels détaillés
6. **OpenJDK** - Implémentation open source

---

*Dernière mise à jour : Septembre 2025*