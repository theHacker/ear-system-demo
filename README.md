# `ear-system-demo`

Demo application and prototype for the [earsystem](https://www.ear-system.de/ear-portal/#no-back).

Used documentation:

- https://test.ear-system.de/ear-soap/v2/doc/
- https://www.stiftung-ear.de/technische-rahmenbedingungen/
- https://test.ear-system.de/ear-soap/v2/MitteilungsService?wsdl

## Installation

Ensure you have the required dependencies ready (JVM and Node environment).

Java:
- Install JDK 21, e&nbsp;g. Amazon Corretto, and select in IntelliJ (Gradle settings -> Gradle JVM)

Node:
- Install `nvm`: See https://github.com/nvm-sh/nvm#installing-and-updating
- Install Node 24: `nvm install 24 && nvm use 24`

Bun:
- Install `bun`: See https://bun.com/docs/installation
- Install dependencies: `bun i`

## Running

Backend and frontend run with a single command.

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

### Backend: Import/Update WSDL

```shell
wget -O backend/src/main/resources/wsdl/MitteilungsService.wsdl "https://test.ear-system.de/ear-soap/v2/MitteilungsService?wsdl"
```

Since the initial file has a horrible formatting,
you should auto-format the file by IntelliJ before committing the WSDL into VCS.

## Found Issues with the WSDL

The WSDL definition is faulty/incomplete.

For example, `submitIstInput`'s `registrierungsnummer` is of type `xs:int`,
but should rather be `xs:string` with a `xs:restriction` `xs:pattern value="[0-9]{8}"`. We get this information
by querying `checkRegistrierungsnummer(4)` which gives a German error message saying it wants 8 digits.
This conforms our suspicion that `int` is not the correct type for such a registration number.

The same error message we get for using negative numbers (e.&nbsp;g. `checkRegistrierungsnummer(-4)`).
`-4` is a fine integer, but it sounded strange as a registration number from the beginning. 
