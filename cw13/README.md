# Ćwiczenie 13

1. Pobrać zależność
   ```
   <dependency>
        <groupId>io.github.resilience4j</groupId>
        <artifactId>resilience4j-spring-boot2</artifactId>
        <version>1.1.0</version>
    </dependency>
   ```
2. Stworzyć klasę `TaskClient`, pobierającą `RestTemplate` i `ConfigProps`. Przenieść do niej wysyłanie zapytania do mikroserwisu tasków. Użyć tej klasy w klasie `TaskController`
3. W oparciu o [przykład](https://resilience4j.readme.io/docs/getting-started-3), dodać adnotację `@CircuitBreaker` i wartości konfiguracyjne, m.in. `waitDurationInOpenState: 60s` i w razie niepowodzenia, logować informację w konsoli i zwracać przekazany task
   * Można stworzyć metodę np. `private TaskDto fallback(TaskDto toSend, IllegalStateException e)`
