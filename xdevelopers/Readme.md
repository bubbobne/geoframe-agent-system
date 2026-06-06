# GEOframe Developer Agent Layer (xdevelopers)

## 1. Scope Alignment

This directory defines the **developer / method-modeller agent layer** of the GEOframe Agent Pack.

It complements the `xusers` layer (case-study modelling) described in the root `README.md`.

This layer focuses on:
- implementation of numerical and computational methods
- development and maintenance of GEOframe/OMS software components
- debugging and improving simulation execution
- testing, refactoring, and release engineering
- supporting scientific modelling tools used by xusers

It does NOT define case-specific hydrological applications.

---

## 2. Role in the GEOframe Ecosystem

The GEOframe system is structured in two complementary layers:

### xusers (case-study modeller)
Responsible for:
- defining hydrological case studies
- selecting and configuring model structures for specific basins or experiments
- interpreting simulation results in a hydrological context
- designing modelling workflows

### xdevelopers (method modeller)
Responsible for:
- implementing numerical methods and algorithms
- maintaining simulation infrastructure (OMS / GEOframe execution layer)
- ensuring correctness, stability, and performance of computational models
- developing reusable scientific components used by xusers workflows

---

## 3. Core Purpose

The `xdevelopers` layer ensures that GEOframe components are:

- numerically correct
- computationally stable
- reproducible
- maintainable
- compatible with OMS execution environments

It provides the **computational foundation** for hydrological modelling workflows.

---

## 4. Scientific Boundary

Developer agents operate at the level of **method implementation**, not case-specific modelling.

### They are responsible for:
- numerical solvers
- probabilistic methods
- optimization algorithms
- data assimilation methods (computational side)
- simulation infrastructure and execution logic

### They are NOT responsible for:
- defining hydrological case studies
- selecting which physical processes apply to a basin
- interpreting hydrological results in a real-world context
- calibrating models for specific catchments

---

## 5. Agent Set (Developer Layer)

### 5.1 `geoframe-xdevelopers-orchestrator`
Coordinates technical development workflows.

Responsibilities:
- manage implementation tasks
- coordinate developer agents
- ensure consistency across software components
- handle debugging and integration workflows

---

### 5.2 `geoframe-java-developer`
Implements and maintains Java-based GEOframe components.

Responsibilities:
- develop numerical and simulation modules
- implement algorithms in Java
- ensure correctness and readability
- follow architecture and design standards

---

### 5.3 `geoframe-oms-debugger`
Diagnoses runtime issues in OMS execution.

Responsibilities:
- analyze simulation crashes and stack traces
- debug execution graphs
- identify infrastructure or implementation bugs
- isolate computational failures

---

### 5.4 `geoframe-refactor-agent`
Improves code structure without changing behavior.

Responsibilities:
- refactor code for readability and maintainability
- reduce complexity
- improve modularity

Constraint:
- MUST NOT change scientific or numerical behavior

---

### 5.5 `geoframe-code-reviewer`
Ensures software quality.

Responsibilities:
- review code correctness
- detect bugs and anti-patterns
- validate architectural consistency

---

### 5.6 `geoframe-test-agent`
Designs and maintains test suites.

Responsibilities:
- unit tests for numerical methods
- integration tests for simulation workflows
- regression tests for model stability

---

### 5.7 `geoframe-build-release-agent`
Manages build and release pipelines.

Responsibilities:
- ensure reproducible builds
- manage versioning and releases
- coordinate packaging and deployment

---

### 5.8 `geoframe-git-maven-agent`
Handles Git and Maven workflows.

Constraint:
- must be explicitly invoked

Responsibilities:
- branching strategies
- dependency management
- release tagging

---

## 6. Workflow Principles

Developer workflows follow a structured pipeline:

1. Identify technical or computational problem
2. Select appropriate developer agent(s)
3. Implement or fix numerical / software components
4. Validate correctness via tests or runtime execution
5. Review changes
6. Integrate into GEOframe ecosystem

---

## 7. Execution Constraints

Developer agents must:

- ensure numerical correctness and stability
- maintain compatibility with OMS/GEOframe execution system
- support reproducibility of simulations

Developer agents must NOT:

- define hydrological case studies
- decide model structure for specific applications
- interpret simulation results in hydrological terms
- bypass testing or review steps for critical changes

---

## 8. Interaction with xusers Layer

The xdevelopers layer supports xusers by providing:

- reliable and tested numerical methods
- stable simulation infrastructure
- correct and efficient implementation of models

The xdevelopers layer does NOT participate in:
- case-specific modelling decisions
- hydrological interpretation of results
- calibration strategy design

---

## 9. Design Philosophy

The xdevelopers layer is a **method modelling and computational engineering system**.

It ensures that GEOframe:

- executes correctly
- remains numerically stable
- produces reproducible results
- provides reliable tools for case-study modelling

It acts as the **computational backbone** of the GEOframe Agent Pack.
