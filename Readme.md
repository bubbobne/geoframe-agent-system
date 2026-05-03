# GEOframe Agent Pack

## Abstract

This repository contains an OpenCode agent pack for GEOframe and OMS-based hydrological modelling. It provides structured prompts for scientific workflow management, technical implementation, evaluation, and documentation.

## Purpose

The repository is intended to support:
- reproducible modelling workflows
- separation between scientific and technical tasks
- explicit reasoning about modelling choices
- hydrologically defensible results

## Repository Scope

This is not the GEOframe source code. It contains agent prompts for users and developers working with GEOframe projects.

Reference material:
- https://geoframe.blogspot.com/
- https://abouthydrology.blogspot.com/
- https://github.com/geoframecomponents

## Structure

The principal prompt directories are:

```text
xusers/.opencode/agents/
xdevelopers/.opencode/agents/
```

- `xusers` contains hydrological workflow agents.
- `xdevelopers` contains Java, OMS, testing, refactoring, and review agents.

## Agent Selection

Use the following agents according to task type:

- `geoframe-orchestrator`: plan the workflow and delegate tasks
- `geoframe-meteo-data`: prepare meteorological forcing data
- `geoframe-simulation-builder`: define simulation, calibration, and validation setups
- `geoframe-evaluator`: assess model behaviour and diagnostics
- `geoframe-documenter`: write reproducible scientific documentation

- `geoframe-xdevelopers-orchestrator`: coordinate technical developer workflows
- `geoframe-java-developer`: implement and maintain Java components
- `geoframe-refactor-agent`: improve structure without changing scientific meaning
- `geoframe-code-reviewer`: review code for correctness and maintainability
- `geoframe-test-agent`: design and write tests
- `geoframe-build-release-agent`: support builds and releases
- `geoframe-git-maven-agent`: handle Git and Maven release workflows when explicitly requested
- `geoframe-oms-debugger`: diagnose OMS runtime issues

## Workflow Model

The primary agent should define the task, then delegate to specialist agents. Each agent has a limited scope and must not infer paths, inputs, or scientific choices without evidence.

Typical sequence:

```text
@geoframe-orchestrator plan the workflow
@geoframe-meteo-data prepare forcing data
@geoframe-simulation-builder define the setup
@geoframe-evaluator assess the results
@geoframe-documenter record assumptions and decisions
```

## Hydrological Criteria

- do not evaluate a model using a single metric such as KGE
- verify water balance and internal consistency
- inspect snow, ET, storage, and discharge when available
- interpret fit scores as necessary but not sufficient evidence

## Documentation Standard

Documentation should be written in a scientific register:
- define the objective explicitly
- separate facts, assumptions, and interpretation
- state limitations and reproducibility conditions
- explain the rationale for modelling and implementation choices
- include relevant equations, component names, or references when useful

## Contributing

Contributions are welcome, especially for:
- clearer prompts
- new specialist agents
- better workflow guidance
- stronger diagnostics and validation support

## License

GPL-3.0. See `LICENSE` for details.
