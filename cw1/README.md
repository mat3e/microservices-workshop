# Ćwiczenie 1
1. Pobrać zaczętą i omówioną aplikację
2. Odpalić testy. Co przechodzi, a co wymaga poprawek?
3. Dopisać brakujące rzeczy w testach `Controllera`
4. Zaimplementować `Controller`, żeby testy przechodziły
5. Uruchomić Mongo*, uruchomić aplikację (moduł `monolith`)
6. Przeklikać REST API**: 
   1. Stworzyć przykadowy task
   2. Przeczytać taski w przeglądarce, przeczytać konkretny task
   3. Zaktualizować opis taska
   4. Dodać kolejny task
   5. Usunąć jeden z tasków

___
\* `docker run -d -p 27017-27019:27017-27019 --name microservices mongo`

\** curl, np. `curl -i -X POST -H 'Content-Type:application/json' -d '{  "done" : true,  "text" : "Start example app" }" http://localhost:8080/api/tasks` albo [Postman](https://www.getpostman.com/)