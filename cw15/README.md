# Ćwiczenie 15

1. Otworzyć kod z folderu "app"
2. W obrazach dockerowych dodać opóźnienie startu aplikacji
   * Skorzystać ze zmiennej środowiskowej `ENV DELAY_START=0`
   * Zmienić komendę startującą: `CMD echo "The application will start in ${DELAY_START}s..." && sleep ${DELAY_START} && java -jar /app.jar`
3. Przeanalizować plik `docker-compose-tools.yml` - uruchomienie zewnętrznych narzędzi
4. Stworzyć `docker-compose-apps.yml`, który skorzysta z naszych obrazów, np. "microservices/tasks". Ustawić kontenerowi zmienne, które nadpiszą wartości np. z bootstrap.yml albo application.yml
   - `SPRING_CLOUD_CONSUL_HOST=consul`
   - `SPRING_CLOUD_CONSUL_PORT=8500`
   - `SPRING_DATA_MONGODB_URI=mongodb://mongo:27017`
   - `SPRING_CLOUD_STREAM_KAFKA_BINDER_BROKERS=kafka`
   - `SPRING_CLOUD_STREAM_KAFKA_BINDER_ZK_NODES=zookeeper`
   - `DELAY_START=10`
5. Ustawić mapowania portów, np. `8082:8080` - to co w kontenerze jako 8080, u nas 8082
6. W przypadku serwisu tasków dodać mapowanie `8083-8085:8080`
7. Zatrzymać wszystkie dockery i uruchomić: `docker-compose -f docker-compose-tools.yml -f docker-compose-apps.yml up -d`. Pod jakim adresem jest usługa tasków?
8. Zawołać `docker-compose -f docker-compose-tools.yml -f docker-compose-apps.yml up --scale tasks=3 -d`
