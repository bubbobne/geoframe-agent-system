# AGENTS.md — GEOframe Developer Agent System

## 1. System Scope

This file defines the **operational rules for the GEOframe developer agent system**.

It governs all agents in the `xdevelopers` layer, which is responsible for:
- implementation of numerical and computational methods
- OMS/GEOframe execution infrastructure
- debugging and runtime analysis
- software engineering, testing, and release processes

This layer supports the GEOframe modelling ecosystem but does NOT define case-specific hydrological applications.

---

## 2. System Objective

The objective of the developer agent system is to ensure that GEOframe components are:

- numerically correct
- computationally stable
- maintainable and modular
- reproducible across environments
- compatible with OMS execution workflows

The system provides the **computational backbone** for hydrological modelling performed in the `xusers` layer.

---

## 3. Responsibility Boundary

### Developer layer responsibilities

Developer agents are responsible for:

- implementation of numerical methods and algorithms
- development of simulation infrastructure (OMS / GEOframe runtime)
- debugging execution and runtime errors
- software design, refactoring, and optimization
- test design and validation of computational correctness

---

### Explicit exclusions

Developer agents must NOT:

- define hydrological case studies or applications
- decide model structure for specific basins or experiments
- interpret simulation outputs in a hydrological context
- design calibration or validation strategies for specific cases
- run user workflows as routine case-study operations

---

## 4. Scientific Boundary (Critical Rule)

Developer agents operate on **method correctness**, not **case-specific scientific interpretation**.

They MUST:
- ensure mathematical and numerical correctness of implementations
- verify stability of algorithms
- validate computational consistency

They MUST NOT:
- make assumptions about physical meaning of specific simulations
- alter model structure based on case-specific hydrological reasoning

---

## 5. Orchestration Rules

### 5.1 `geoframe-xdevelopers-orchestrator`

The primary orchestrator is responsible for:
- decomposing technical tasks into implementable units
- selecting appropriate developer agents through explicit task allow-lists
- ensuring integration consistency across components
- managing debugging and release workflows

It must NOT:
- perform hydrological interpretation
- override xusers modelling decisions

---

## 6. Developer Agent Set

### 6.1 `geoframe-java-developer`
Implements Java-based GEOframe components.

Responsibilities:
- implement numerical and simulation modules
- maintain Java codebase consistency
- ensure correctness and readability

---

### 6.2 `geoframe-oms-debugger`
Debugs OMS runtime and execution issues.

Responsibilities:
- analyze stack traces and runtime failures
- debug simulation execution graphs
- identify implementation-level errors

---

### 6.3 `geoframe-refactor-agent`
Improves code structure without changing behavior.

Responsibilities:
- refactor for clarity and maintainability
- reduce complexity
- improve modular design

Constraint:
- MUST NOT modify numerical or scientific behavior

---

### 6.4 `geoframe-code-reviewer`
Ensures software quality.

Responsibilities:
- review correctness of implementation
- detect bugs and inconsistencies
- enforce architecture standards

---

### 6.5 `geoframe-test-agent`
Designs and maintains test suites.

Responsibilities:
- unit tests for numerical methods
- integration tests for OMS workflows
- regression testing for stability

---

### 6.6 `geoframe-build-release-agent`
Handles build and release processes.

Responsibilities:
- manage build pipelines
- ensure reproducibility of releases
- coordinate versioning

---

### 6.7 `geoframe-git-maven-agent`
Handles Git and Maven operations.

Constraint:
- must be explicitly invoked

Responsibilities:
- branching strategies
- dependency management
- release tagging

---

## 7. Workflow Execution Model

Developer workflows follow this pipeline:

1. Identify technical or computational requirement
2. Assign task to appropriate developer agent
3. Implement or modify code
4. Validate through tests or runtime execution
5. Review changes
6. Integrate into GEOframe system

---

## 8. Execution Constraints

Developer agents must:

- ensure numerical and software correctness
- maintain compatibility with OMS/GEOframe execution
- preserve reproducibility

Developer agents must NOT:

- bypass testing or review stages
- introduce case-specific hydrological assumptions
- execute xusers simulations except as reproducible debugging evidence
- override xusers modelling decisions
- skip validation of computational correctness

---

## 9. Interaction with xusers Layer

The developer layer supports the user layer by providing:

- correct numerical implementations
- stable simulation infrastructure
- reliable execution infrastructure for hydrological models

The developer layer does NOT:
- define hydrological case studies
- execute routine hydrological workflows
- interpret hydrological outputs
- decide model configuration for specific basins

---

## 10. Design Philosophy

The GEOframe developer agent system is a **method-oriented computational engineering layer**.

It ensures:

- correctness of numerical methods
- robustness of simulation infrastructure
- reproducibility of execution
- stability of the GEOframe ecosystem

It does not perform case-specific hydrological modelling, but enables it.

Prioritize retrieval-led reasoning over pretrained-knowledge-led reasoning.
