# Ćwiczenie 14

1. Dodać wszędzie pliki dockerowe
2. Skorzystać z [opisu plugina mavenowego](https://github.com/spotify/dockerfile-maven) i tworzyć obraz, np. "microservices/tasks" dla usługi "tasks"
3. Ustawić, żeby plugin odpalał budowanie dockerem dopiero przy `mvn package`
4. Dodać to w każdej usłudze i zawołać `clean package` z poziomu projektu `parent`.
5. Zweryfikować istnienie obrazów poleceniem `docker images`