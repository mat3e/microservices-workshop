# Ćwiczenie 7

1. Usunąć "plans" z "monolith/pom.xml"
2. Zmienić `PlansConfiguration` na `PlansApplication`, usunąć wszystkie `@ConditionalOnMissingBean`, dać dostęp publiczny, adnotację `@SpringBootApplication` i metodę
   ```
   public static void main(String[] args) {
       SpringApplication.run(PlansApplication.class, args);
   }
   ```
3. Przenieść konfigurację z monolitu ("application.properties") do "plans". Dodać wpisy  `spring.data.mongodb.database=plans` i `spring.profiles.active=local`*
4. Utworzyć plik "application-local.properties" i ustawić w nim `server.port=8081`
5. Usunąć `TestApp` z "plans"

___
*Spring włączając aplikację ustawi profil "local" i będzie korzystał z dostosowanych do tego ustawień