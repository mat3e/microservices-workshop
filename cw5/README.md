# Ćwiczenie 5
1. Pobrać testy i zobaczyć, czego brakuje
2. Dodać proste klasy `TaskCreated`, `TaskDone` i `TaskUndone`. Mogą mieć klasę bazową `TaskEvent`
3. Zmienić `Task`, żeby oprócz zmiany pola `done`, zwracał powyższe klasy
4. Dodać klasę `TaskService`, zawierającą w sobie repozytorium, przekazywaną do kontrolera
5. W serwisie dodać jeszcze `ApplicationEventPublisher` i w istotnych metodach dodać adnotację `@Transactional`, a także wołać `publisher.publishEvent(event)`
6. Zmienić kontroler, żeby korzystał z metody PATCH i pozwalał wyłącznie na zmianę statusu taska