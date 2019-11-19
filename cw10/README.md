# Ćwiczenie 10

1. Pobrać projekt i zobaczyć, co się zmieniło (nazwy projektów, zależności)
2. `docker run -d --name=dev-consul -p 8400:8400 -p 8500:8500 -e CONSUL_BIND_INTERFACE=eth0 consul`
3. Czy wszystkie usługi poprawnie zarejestrowały się w Consulu? Co jest problemem? (szukać po plikach pom.xml)
4. Poprawić