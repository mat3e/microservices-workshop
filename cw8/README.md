# Ćwiczenie 8

1. Utworzyć publiczną klasę `ConfigProps` w "plans". Dać jej adnotacje `@Configuration` i `@ConfigurationProperties("plans")`*
2. Utworzyć wewnętrzną klasę `public static class ExternalServiceUrls`
3. `ConfigProps` powinno mieć pole `private ExternalServiceUrls services`, a `ExternalServiceUrls` - `private String tasksUrl`. Dodać polom gettery i settery
4. Dodać właściwość* do "application-local.properties":
   ```
   plans.services.tasksUrl=http://localhost:8080/api/tasks
   ```
5. Dodać nowy `@Bean` - `RestTemplate` (`return new RestTemplate()`)
6. Stworzyć klasę `TaskDto` z konstruktorem ustawiającym pola:
   ```
   @JsonCreator
   TaskDto(
           @JsonProperty("id") String id,
           @JsonProperty("text") String text,
           @JsonProperty("deadline") ZonedDateTime deadline
   )
   ```
   Dodać też `equals`, `hashCode`, gettery i konstruktor pobierający tylko `text` i `deadline`.
7. Stworzyć kontroler, np. dla adresu "/api/plans/{planId}/tasks", który zamieni plan i wszystkie jego krkoki na taska z deadline'ami. Przykładowe rozwiązanie -> "cw8/example"
8. Przetestować - dodać plan i utworzyć dla niego taski
   ___
   *W razie problemów IntelliJ dodać zależność do "pom.xml"
   ```
   <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    ```