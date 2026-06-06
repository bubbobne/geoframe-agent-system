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

- `xusers` contains hydrological workflow agents for researchers who configure and run GEOframe/OMS models, commonly through Python helper scripts, console commands, or direct Java OMS launches.
- `xdevelopers` contains Java, OMS, testing, refactoring, build/release, and review agents for component developers.

Keep the two folders as separate OpenCode project roots when you want strict user/developer separation. If both agent sets are copied into a single OpenCode configuration, preserve the exact `permission.task` allow-lists in the orchestrators so that user workflows do not accidentally delegate to developer agents and developer workflows do not make case-study hydrological decisions.

Agent prompts do not pin a model by default. OpenCode will use the globally configured model for primary agents, and subagents inherit the invoking primary agent model unless a specific agent later defines a model override.

## Imported Skills

The following MIT-licensed skill prompts from `github.com/github/awesome-copilot/tree/main/skills` are useful for this pack. Keep the source reference visible when reusing or adapting them.

- `acquire-codebase-knowledge` - repo discovery and onboarding. Source: `github.com/github/awesome-copilot/tree/main/skills/acquire-codebase-knowledge`
- `breakdown-plan` - task and workflow planning. Source: `github.com/github/awesome-copilot/tree/main/skills/breakdown-plan`
- `create-readme` - documentation drafting. Source: `github.com/github/awesome-copilot/tree/main/skills/create-readme`
- `create-agentsmd` - AGENTS.md authoring. Source: `github.com/github/awesome-copilot/tree/main/skills/create-agentsmd`

Note: skill availability depends on the host tool/runtime. The developer layer carries the full imported skill set. The user layer carries the shared workflow/documentation skills it directly references, so `xusers` can operate as a self-contained OpenCode root. If skills are not discoverable via the `skill` tool, consult the corresponding `.opencode/skills/<skill-name>/SKILL.md` as reference text.

Java-focused skills that fit the developer agents:

- `java-docs` - Javadoc and API documentation. Source: `github.com/github/awesome-copilot/tree/main/skills/java-docs`
- `java-junit` - JUnit 5 test design. Source: `github.com/github/awesome-copilot/tree/main/skills/java-junit`
- `java-refactoring-extract-method` - method extraction refactoring. Source: `github.com/github/awesome-copilot/tree/main/skills/java-refactoring-extract-method`
- `java-refactoring-remove-parameter` - parameter cleanup refactoring. Source: `github.com/github/awesome-copilot/tree/main/skills/java-refactoring-remove-parameter`

Primary agents should prefer these skills when the task matches their scope.

## Agent Selection

Use the following agents according to task type:

- `geoframe-orchestrator`: plan the workflow and delegate tasks
- `geoframe-meteo-data`: prepare meteorological forcing data
- `geoframe-simulation-builder`: define simulation, calibration, and validation setups
- `geoframe-oms-runner`: execute `.sim` workflows through Python, console, or Java OMS commands
- `geoframe-evaluator`: assess model behaviour and diagnostics
- `geoframe-documenter`: write reproducible scientific documentation

- `geoframe-xdevelopers-orchestrator`: primary coordinator for technical developer workflows
- `geoframe-java-developer`: implement and maintain Java components
- `geoframe-refactor-agent`: improve structure without changing scientific meaning
- `geoframe-code-reviewer`: review code for correctness and maintainability
- `geoframe-test-agent`: design and write tests
- `geoframe-build-release-agent`: support builds and releases
- `geoframe-git-maven-agent`: handle Git and Maven release workflows when explicitly requested
- `geoframe-oms-debugger`: diagnose OMS runtime issues


## Visual Overview

```mermaid
flowchart TD
    A[GEOframe Agent Pack Repo] --> B[xusers/.opencode/agents]
    A --> C[xdevelopers/.opencode/agents]

    B --> B1[geoframe-orchestrator
(primary workflow planner)]
    B1 --> B2[geoframe-meteo-data]
    B1 --> B3[geoframe-simulation-builder]
    B1 --> B6[geoframe-oms-runner]
    B1 --> B4[geoframe-evaluator]
    B1 --> B5[geoframe-documenter]

    C --> C1[geoframe-xdevelopers-orchestrator
(primary technical coordinator)]
    C1 --> C2[geoframe-java-developer]
    C1 --> C3[geoframe-refactor-agent]
    C1 --> C4[geoframe-code-reviewer]
    C1 --> C5[geoframe-test-agent]
    C1 --> C6[geoframe-build-release-agent]
    C1 --> C7[geoframe-oms-debugger]
    C1 --> C8[geoframe-git-maven-agent
(optional, explicit request only)]
```

## Workflow Model

The primary agent should define the task, then delegate to specialist agents. Each agent has a limited scope and must not infer paths, inputs, or scientific choices without evidence.

Typical sequence:

```text
@geoframe-orchestrator plan the workflow
@geoframe-meteo-data prepare forcing data
@geoframe-simulation-builder define the setup
@geoframe-oms-runner execute the .sim through Python, console, or Java OMS
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
