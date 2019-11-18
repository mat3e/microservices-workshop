# Ćwiczenie 6
1. Utworzyć nowy moduł - `mongodb` i `web`, nazwać go `reports`
2. Dodać i zarejestrować klasę, np. `ReportedTaskEventListener`, która będzie nasłuchiwać na zdarzenia Springa (można się posłużyć przykładem z folderu `example`)
3. Utworzyć klasę reprezentującą "zaraportowany task" - odpowiednik klasy `Task`, który będzie powstawał ze zdarzeń (stworzyć odpowiednie konstruktory)
4. Na każde odebrane zdarzenie zapisywać "zaraportowany task" w bazie
5. Stworzyć kontroler, który umożliwi nową reprezentację klasy `Task` na różne sposoby, na podstawie zaraportowanych tasków: najszybciej zmieniony task, najczęściej zmieniane taski, zrobione taski, najwięcej czasu PRZED deadline, najwięcej po
   * minimum: task w aktualnym stanie + ile razy był zmieniany i raport zrobionych tasków