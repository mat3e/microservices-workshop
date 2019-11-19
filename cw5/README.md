# Ćwiczenie 5
1. Pobrać testy i zobaczyć, czego brakuje
2. Dodać proste klasy `TaskCreated`, `TaskDone` i `TaskUndone`. Mogą mieć klasę bazową `TaskEvent`
3. Zmienić `Task`, żeby oprócz zmiany pola `done`, zwracał powyższe klasy
4. Dodać klasę `TaskService`, zawierającą w sobie repozytorium, przekazywaną do kontrolera
5. W serwisie dodać `ApplicationEventPublisher`
6. Dać adnotację `@Transactional` na metodach `create` i `changeState`. Wołać w nich `publisher.publishEvent(event)`
7. Zmienić kontroler, żeby korzystał z metody PATCH i pozwalał wyłącznie na zmianę statusu taska
