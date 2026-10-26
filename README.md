# `ear-system-demo`

Demo application and prototype for the [earsystem](https://www.ear-system.de/ear-portal/#no-back).

Used documentation:

- https://test.ear-system.de/ear-soap/v2/doc/
- https://www.stiftung-ear.de/technische-rahmenbedingungen/
- https://test.ear-system.de/ear-soap/v2/MitteilungsService?wsdl

## Running

Backend and frontend run with a single command.
Ensure you have the default dependencies ready (JVM and Node environments).

### Backend

Run with `./gradlew bootRun`.

### Frontend

Run with `bun dev`.

## Testing

### Backend

Run tests with `./gradlew check`.

### Frontend

Run tests with `bun run test`.

> [!NOTE]
> `bun test` also works, but it will use `bun`'s runner, not `vitest`.
> You can use it, too, but source of truth is `bun run test`).

## Special Instructions

### Backend: Generate jOOQ classes

Usually, the IDE or `:bootRun` task will do that for you. If you need to manually trigger that,
execute Gradle task `:jooqCodegen`.
