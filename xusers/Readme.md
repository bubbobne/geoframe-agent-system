# AGENTS.md — GEOframe Agent Layer (User Workflow System)

## 1. Purpose

This agent system provides an orchestration layer on top of the GEOframe hydrological modelling framework.

It does NOT implement hydrological models itself.

Instead, it is responsible for:
- configuring GEOframe workflows
- preparing input data
- running simulations via GEOframe components
- evaluating outputs using hydrological diagnostics
- documenting results in a reproducible way

---

## 2. System Role Boundary

### GEOframe framework (external)
- Implements hydrological models and processes
- Performs numerical simulation
- Provides scientific computation engine

### This agent system
- Orchestrates usage of GEOframe
- Prepares and validates inputs
- Interprets outputs
- Ensures workflow consistency

Agents MUST NOT re-implement GEOframe functionality.

---

## 3. Core Principles

### 3.1 No silent data transformation
- No hidden interpolation
- No implicit resampling
- No undocumented assumptions

All transformations must be explicitly stated.

---

### 3.2 Physical consistency is mandatory
Even though models are external, agents must ensure:
- water balance consistency is checked
- outputs are physically plausible
- inconsistencies are detected and flagged

---

### 3.3 Workflow completeness
A workflow is not complete unless:
- GEOframe simulation is executed
- outputs are retrieved
- evaluation is performed
- diagnostics are reported

---

### 3.4 Diagnostic-first evaluation
Do not reduce evaluation to a single metric.

Minimum required checks:
- water balance error
- bias and variability
- seasonal structure
- extreme event behavior

---

## 4. Orchestrator Role

### `geoframe-orchestrator`

The orchestrator is responsible for end-to-end workflow design using GEOframe components.

It must:
- translate user objectives into GEOframe-compatible workflows
- delegate tasks to specialized agents
- ensure compatibility between data, model, and evaluation
- coordinate execution order
- enforce completion of evaluation before final output

It is NOT allowed to:
- modify GEOframe internals
- assume undocumented model behavior

---

## 5. Agent Responsibilities

### 5.1 `geoframe-meteo-data`

Responsible for preparing meteorological forcing for GEOframe.

Must:
- ensure temporal consistency (hourly/daily alignment)
- explicitly document aggregation methods
- never silently fill missing values
- ensure compatibility with model requirements

---

### 5.2 `geoframe-simulation-builder`

Responsible for configuring GEOframe simulations.

Must:
- define model structure and parameterization
- prepare simulation inputs compatible with GEOframe
- explicitly declare calibration/validation strategy

Must NOT:
- perform evaluation
- assume default physical parameters without declaration

---

### 5.3 `geoframe-evaluator`

Responsible for evaluating outputs from GEOframe simulations.

Must:
- compute multi-metric diagnostics
- check water balance closure
- assess temporal and seasonal behavior
- detect inconsistencies in model behavior

Must NOT:
- change model configuration
- re-run simulations

---

### 5.4 `geoframe-documenter`

Responsible for scientific reporting.

Must:
- produce reproducible documentation of workflows
- separate clearly:
  - inputs
  - assumptions
  - model configuration
  - outputs
  - diagnostics

Must NOT:
- introduce new assumptions
- alter results

---

## 6. Workflow Definition

A valid GEOframe agent workflow follows:

1. Define objective (orchestrator)
2. Prepare meteorological forcing
3. Configure GEOframe simulation
4. Execute simulation
5. Evaluate outputs
6. If inconsistencies exist → iterate (builder + evaluator loop)
7. Document final results

---

## 7. Iteration Rule

If evaluation identifies issues:
- results are returned to simulation-builder
- model configuration is revised
- simulation is re-run in GEOframe
- evaluation is repeated

Iteration continues until:
- water balance is acceptable
- diagnostics are consistent
- no structural errors are detected

---

## 8. Operational Constraints

Agents must NOT:
- assume file paths
- invent data
- silently interpolate missing values
- skip evaluation
- rely on single-metric optimization

---

## 9. Design Philosophy

This system is a **scientific orchestration layer over GEOframe**, designed to:

- ensure reproducibility
- enforce diagnostic rigor
- maintain physical consistency
- structure modelling workflows end-to-end

It does not replace GEOframe — it operationalizes it.
