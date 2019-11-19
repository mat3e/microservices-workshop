# Przed warsztatem

1. Zainstalować [Dockera](https://docs.docker.com/install/)
   * Zweryfikować - uruchomić bazę Mongo, wpisując w terminalu
      ```
      docker run -d -p 27017-27019:27017-27019 --name microservices mongo
      ```
2. Przeczytać o rozwiązaniu [Docker Compose](https://docs.docker.com/compose/install/)
   * Upewnić się, że jest zainstalowane 
   * Np. wykonać w terminalu polecenie `docker-compose up` - powinno zwrócić błąd o braku pliku `docker-compose.yml`
3. Zainstalować Javę, najlepiej z [AdoptOpenJDK](https://adoptopenjdk.net/)
   * Zalecana wersja - 11, minimalna - 8 (będzie wymagała zmian w kodzie na własną rękę)
   * Zweryfikować poleceniem `java -version`
4. Przygotować swoje ulubione IDE
   * Zalecane - IntelliJ Ultimate

TODO: Postman, import multi-module Maven project z testami w Groovy; odpalenie testów i spodziewane błędy - w testach używać metod kontrolera, a nie @Mvc