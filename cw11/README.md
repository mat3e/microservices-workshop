# Ćwiczenie 11

1. Podpiąć wszędzie zależność `spring-cloud-starter-consul-config`
2. Przenieść ważne dla Consula ustawienia do plików bootstrap.yml (nazwa projektu, rzeczy ze `spring.cloud.consul`)
3. W oparciu o [artykuł](https://cloud.spring.io/spring-cloud-consul/reference/html/#spring-cloud-consul-config) zdefiniować konfigurację w pliku YAML w Consulu
   1. W konfiguracji udostępnić wszyskie endpoiny Actuatora wszystkim serwisom:
      ```
       management:
       endpoints:
           web:
           exposure:
               include: "*"
       ```
4. W niedziałających testach Groovy dodać `@TestPropertySource(properties = ['spring.cloud.consul.config.enabled = false'])` (brak konfiguracji z Consula przy odpalaniu testu)