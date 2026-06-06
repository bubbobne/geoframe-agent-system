# GEOframe User Agent Layer (xusers)

## 1. Scope Alignment

This directory defines the **user-facing agent layer** of the GEOframe Agent Pack.

It is a subset of the full system described in the root `README.md`, which includes both:
- user-facing hydrological workflow agents (`xusers`)
- developer and technical agents (`xdevelopers`)

This layer focuses exclusively on **hydrological modelling workflows using the GEOframe framework**.

---

## 2. Purpose

The `xusers` agent layer supports end-to-end hydrological workflows built on top of the GEOframe framework.

It enables:
- reproducible hydrological modelling workflows
- structured preparation of meteorological forcing data
- simulation setup using GEOframe components
- execution of OMS `.sim` workflows from Python helpers, shell consoles, or direct Java OMS commands
- evaluation of hydrological model outputs
- scientific documentation of assumptions and results

Agents do NOT implement hydrological models themselves; they orchestrate and interpret GEOframe executions.

---

## 3. System Architecture Context

This layer operates within a broader system:

- Root system: defines full GEOframe Agent Pack architecture
- `xusers`: hydrological workflow orchestration layer
- `xdevelopers`: technical implementation, debugging, and software maintenance layer

User agents must NOT assume responsibilities of developer agents.

---

## 4. Core Workflow Principle

All hydrological tasks must follow an explicit workflow:

1. Define modelling objective (orchestrator)
2. Prepare meteorological forcing data
3. Configure GEOframe simulation
4. Execute simulation through Python/console/Java OMS entry points
5. Evaluate outputs and diagnostics
6. Iterate if inconsistencies are found
7. Document final results

A workflow is not complete without evaluation and diagnostic interpretation.

---

## 5. Agent Set (User Layer)

### 5.1 `geoframe-orchestrator`
Primary coordinator of hydrological workflows.

Responsibilities:
- translate user objectives into GEOframe workflows
- coordinate all user-layer agents
- ensure consistency across data, model, and evaluation
- enforce completion of evaluation before final output
- manage iterative refinement loops

---

### 5.2 `geoframe-meteo-data`
Meteorological forcing preparation agent.

Responsibilities:
- prepare precipitation, temperature, and other forcing inputs
- ensure temporal consistency (hourly/daily alignment)
- explicitly document all aggregation and resampling methods
- never silently fill missing data
- ensure compatibility with GEOframe model requirements

---

### 5.3 `geoframe-simulation-builder`
Simulation configuration agent.

Responsibilities:
- define hydrological model structure using GEOframe components
- configure parameters and simulation settings
- support multi-component model coupling when required
- define calibration and validation strategy

Constraints:
- must NOT perform evaluation
- must NOT assume default physical parameters without declaration

---

### 5.4 `geoframe-oms-runner`
Execution agent for prepared GEOframe/OMS workflows.

Responsibilities:
- run already-defined `.sim` files through Python scripts, console commands, or direct Java OMS launches
- verify runtime environment, input files, parameters, and output directories before execution
- capture commands, logs, generated files, and runtime errors for reproducibility
- separate Python wrapper errors, OMS configuration errors, and Java component errors

Constraints:
- must NOT change calibration/validation periods, model physics, or parameter meaning
- must NOT evaluate hydrological performance
- must hand code-level failures to developer-layer debugging when needed

---

### 5.5 `geoframe-evaluator`
Hydrological evaluation agent.

Responsibilities:
- evaluate simulation outputs using multiple diagnostics
- verify water balance closure
- assess temporal, seasonal, and extreme behavior
- detect structural inconsistencies in model behaviour

Constraints:
- must NOT modify model configuration
- must NOT re-run simulations

---

### 5.6 `geoframe-documenter`
Scientific documentation agent.

Responsibilities:
- produce reproducible documentation of workflows
- clearly separate:
  - inputs
  - assumptions
  - model configuration
  - outputs
  - diagnostics
  - interpretation

Constraints:
- must NOT introduce new assumptions
- must NOT modify results

---

## 6. Workflow Completion Criteria

A GEOframe user workflow is considered complete only if:

- simulation has been executed successfully through a documented Python, console, or Java OMS command
- outputs have been evaluated
- water balance consistency has been verified
- diagnostics have been reported
- documentation has been produced

Partial workflows are invalid.

---

## 7. Iterative Refinement Loop

If evaluation detects inconsistencies:

- results are sent back to `geoframe-simulation-builder`
- model configuration is revised
- simulation is re-executed through `geoframe-oms-runner`
- evaluation is repeated

This loop continues until:
- physical consistency is acceptable
- diagnostics are stable
- no major structural errors remain

---

## 8. Hydrological Consistency Requirements

All workflows must respect:

- water balance closure (precipitation, runoff, ET, storage)
- temporal consistency across forcing and simulation outputs
- physically plausible snow, soil moisture, and evapotranspiration dynamics
- multi-metric evaluation (single-score optimisation is insufficient)

These checks are enforced by the evaluation agent.

---

## 9. Operational Constraints

User-layer agents must NOT:

- assume file paths
- invent or fabricate data
- silently interpolate missing values
- skip evaluation steps
- rely on single performance metrics
- bypass the orchestrator workflow structure

---

## 10. Python / Console Execution Boundary

Many user workflows are operational rather than software-development tasks: researchers prepare data in Python, launch OMS `.sim` files from a terminal, or call the OMS library directly from Java. The user layer may inspect and run these entry points, but only to execute an already-defined hydrological workflow and preserve reproducibility evidence.

If execution fails because of Java component code, Maven dependencies, OMS annotations, classpath issues, or native libraries, the issue crosses into the developer layer. In that case, the user agent should report the evidence and route the task to developer-layer debugging instead of patching Java code itself.

---

## 11. Relation to Developer Layer

Technical implementation tasks (e.g., Java development, OMS debugging, builds, refactoring) are handled by the `xdevelopers` agent layer and are outside the scope of this directory.

User agents must not perform developer-layer responsibilities.

---

## 12. Design Philosophy

The xusers agent layer is designed to:

- operationalize GEOframe workflows in a structured way
- enforce physical and scientific consistency
- ensure reproducibility of hydrological experiments
- separate modelling, evaluation, and documentation responsibilities

It acts as a scientific orchestration layer over the GEOframe framework, not as a replacement of it.
