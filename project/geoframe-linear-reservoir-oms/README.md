# GEOframe Linear Reservoir OMS

## Abstract

This module implements a linear reservoir component for OMS-based GEOframe workflows. The reservoir is updated with an analytic time-step solution under a piecewise-constant inflow assumption.

## Purpose

The component is intended for small hydrological workflows where a single storage reservoir is sufficient to represent delayed drainage with a constant residence time.

## Formulation

The implementation uses the following update:

```text
decay = exp(-dt / k)
S(t+dt) = S(t) * decay + inflow * k * (1 - decay)
Q(t+dt) = S(t+dt) / k
```

where:

- `S` is reservoir storage `[mm]`
- `k` is the residence time `[d]`
- `dt` is the timestep `[d]`
- `inflow` is the non-negative input applied during the step `[mm/d]`

## Numerical Behaviour

- Negative initial storage is clamped to zero during initialization.
- Negative inflow is clamped to zero during execution.
- Residence time and timestep are bounded below by a small epsilon to avoid division by zero.
- `outflow` is reported as storage divided by residence time at the end of the step.

## Project Structure

- `pom.xml` - Maven build descriptor
- `src/main/java` - OMS component implementation
- `src/test/java` - regression tests

## Validation

The test suite checks:

- analytic storage and outflow update
- clamping of negative initial storage
- zero-timestep behaviour

Run:

```bash
mvn test
```

## Implementation Notes

- The module depends on `hm-oms3` and JUnit 5.
- The project targets Java 8 through Maven compiler release settings.
- The module lives under `xdevelopers/project/`, the local working area for code development.
