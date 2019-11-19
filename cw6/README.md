# Ćwiczenie 6
1. Utworzyć nowy moduł (start.spring.io): wybrać Mongo DB i Web, całość nazwać "reports"
2. Dodać klasę, np. `ReportedTaskEventListener` i dać jej adnotację `@Service`
3. Na podstawie [artykułu](https://www.baeldung.com/spring-events#annotation-driven) i przykładu (folder cw6/example) dodać obsługę zdarzeń z poprzedniego ćwiczenia*
3. Utworzyć klasę reprezentującą "zaraportowany task" - odpowiednik klasy `Task`, który będzie powstawał ze zdarzeń (stworzyć odpowiednie konstruktory)
4. Na każde odebrane zdarzenie zapisywać "zaraportowany task" w bazie
5. Stworzyć kontroler, który umożliwi nową reprezentację klasy `Task` na różne sposoby, na podstawie zaraportowanych tasków: najszybciej zmieniony task, najczęściej zmieniane taski, zrobione taski, najwięcej czasu PRZED deadline, najwięcej po. Plan minimum:
   * Zwrócenie akaualnych stanów + informacji ile było zmian stanu
   * Raport zrobionych tasków
___
*Potrzebny będzie dostęp do zdarzeń, więc nowy moduł musi mieć "tasks" jako swoją zależność