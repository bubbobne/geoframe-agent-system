# GEOframe SALTO Seed OMS

Seed project (Maven + OMS3 annotations) implementing a minimal, runnable, **distributed catchment model** inspired by the SALTO idea (parameter controls + conventional catchment descriptors).

## Build & test

From this module folder:

```bash
mvn test
mvn verify
```

- `mvn test` runs unit tests (Surefire)
- `mvn verify` runs unit + integration tests (Failsafe)

## Run the example runner

```bash
mvn -q -DskipTests exec:java
```

The runner executes a tiny synthetic simulation (2 HRUs, 3 timesteps) and prints a discharge time series.

## Project structure

- `pom.xml` Maven descriptor (Java 8, UTF-8, surefire/failsafe)
- `src/main/java/org/geoframe/salto` OMS3 component(s)
- `src/main/java/org/geoframe/salto/runner` runnable example
- `src/test/java` unit + integration tests
- `docs/salto-model.md` model assumptions, parameters, units

## Inputs/outputs (seed model)

The core component `SaltoSeedModel` consumes:

- forcing: `precipitation [mm/d]`, `potentialET [mm/d]`, `timeStep [d]`
- HRU descriptors: `hruArea [m2]`, `slope [-]`, `soilDepth [mm]`, `drainageDensity [1/m]`
- parameters: see `docs/salto-model.md`

It outputs catchment totals (area-weighted averages):

- `discharge [mm/d]` (quickflow + baseflow)
- `quickflow [mm/d]`, `baseflow [mm/d]`, `aet [mm/d]`
- updated HRU states: `soilWater [mm]`, `groundWater [mm]`
