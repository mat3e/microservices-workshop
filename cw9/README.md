# Ćwiczenie 9

1. Pobrać projekt z folderu "app" - są tam wydzielone aplikacje, nie ma już monolitu
2. W module-źródle zdarzeń (tasks)
   1. Dodać adnotację `@EnableBinding(Source.class)` - możliwość wysyłania zdarzeń na jakiś konfigurowalny kanał
   2. Wstrzyknąć klasę `Source` do `TaskService`, dodać pole `private Source source` i skorzystać z niego do publikacji zdarzenia
      ```
      source.output().send(MessageBuilder
                .withPayload(event)
                .setHeader(EVENT_TYPE_HEADER, event.getType())
                .build());
      ```
    1. Wyrzucić `ApplicationEventPublisher` z `TaskService`
    2. Ustawić `spring.cloud.stream.bindings.output` - `destination` oraz `contentType` w application.yml

3. W module-odbiorcy zdarzeń (reports)
   1. Dodać `@EnableBinding(Sink.class)`
   2. Podmienić adnotacje `Listenera` - metody mają mieć TYLKO `@StreamListener(target = Sink.INPUT, condition = "headers['type'] == 'done'")` i kolejne conditiony - `created`, `undone`
   3. Skonfigurować analogicznie jak poprzednio, ale **`input`** `spring.cloud.stream.bindings.input` - `destination` oraz `contentType`