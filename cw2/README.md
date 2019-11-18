# Ćwiczenie 2

1. Skorzystać ze [start.spring.io](https://start.spring.io/)
2. Wyklikać:
   * Java 11, Maven
   * Spring Data MongoDB, Embedded MongoDB Database
   * Rest Repositories
   * Nazwa i opis: _plans_, _Management of the project plan_
3. Zmienić projekt w pom.xml na wzór poprzedniego - testy Groovy itd. Dodać zależność do pozostałych plików pom (parent, monolith)
4. Zmienić główną klasę programu na konfigurację, dodać konwertery dla Mongo (tak jak w poprzednim module)
5. Pobrać pliki z folderu "app", dodać je do aplikacji i przeanalizować
6. Wyszukać jak w Springu dodać obsługę dla `javax.validation.constraints.NotBlank`
7. Stworzyć repozytorium, żeby móc odpalić jego test. Skorzystać z adnotacji `@RepositoryRestResource` i wystawić całość jako "plans"
8. Skorzystać z CURL-a albo Postmana, żeby stworzyć kilka przykładowych zapytań. Czy walidacje działają jak należy? Czy status odpowiedzi jest zgodny z oczekiwaniami?
9. Skonfigurować parsowanie daty i walidacje przy pomocy klasy `org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer`
10. Skonfigurować, żeby całość była wystawiona jako `/api/plans`, a nie `/plans`
11. Jak działa HTTP PATCH przy wysłaniu `projectSteps` do obiektu, który już ma jakieś kroki?
12. Przetestować różne opcje `page`, `size`, `sort`
