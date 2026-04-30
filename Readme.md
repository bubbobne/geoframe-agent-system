# GEOframe Multi-Agent Setup (OpenCode)

This repository contains a structured **multi-agent system for hydrological modelling** using GEOframe, built on top of OpenCode.

The goal is to support:
- reproducible modelling workflows
- separation of concerns
- automated reasoning over modelling steps
- better scientific transparency

---

## Project Structure
.opencode/
└── agents/
├── xusers/
│ ├── geoframe-orchestrator.md
│ ├── geoframe-meteo-data.md
│ ├── geoframe-simulation-builder.md
│ ├── geoframe-evaluator.md
│ └── geoframe-documenter.md
│
└── xdeveloper/
├── code-reviewer.md
├── java-developer.md
├── refactor-agent.md
└── testing-agent.md


---

## Agent Categories

### `xusers/` — Hydrological Workflow Agents

These agents are designed for **end-users and hydrologists** working with GEOframe.

They focus on:
- modelling workflows
- data preparation
- calibration and validation
- scientific evaluation

#### Main agents:

- **geoframe-orchestrator**
  - Plans the modelling workflow
  - Chooses components and strategy

- **geoframe-meteo-data**
  - Prepares and validates meteorological forcing

- **geoframe-simulation-builder**
  - Builds simulation, calibration, and validation setups

- **geoframe-evaluator**
  - Evaluates model performance (KGE, NSE, etc.)

- **geoframe-documenter**
  - Produces reproducible scientific documentation

---

### `xdeveloper/` — Development Agents

These agents are intended for **developers working on GEOframe or related tools**.

They focus on:
- code quality
- architecture
- testing
- refactoring

Typical roles include:
- reviewing Java code
- improving OMS/GEOframe components
- ensuring reproducibility and maintainability

---

##  How It Works

This project follows a **multi-agent architecture**:

- A primary agent (orchestrator) plans the workflow
- Specialized subagents execute specific tasks
- Each agent has:
  - a defined role
  - limited permissions
  - a specific scope

This design improves:
- modularity
- traceability of decisions
- robustness of modelling workflows

---

## Usage Example

Example workflow:

```text
@geoframe-orchestrator plan a calibration and validation workflow for the basin
```
Then:

```
@geoframe-meteo-data prepare forcing data
@geoframe-simulation-builder create calibration setup
@geoframe-evaluator evaluate results
```
---
## Scientific Approach

This setup follows a diagnostic hydrological modelling philosophy:

- Do not rely on a single metric (e.g., KGE)
- Always evaluate:
  - discharge
  - snow processes
  - water balance
  - internal states when available

A good KGE does not guarantee a correct hydrological model.

---

## Design Principles

- Separation between modelling and development
- Explicit workflows (no hidden steps)
- Reproducibility first
- Minimal assumptions
- Critical evaluation of results

---

## Limitations

- Agents do not replace hydrological expertise
- Results must always be critically interpreted
- Data quality strongly affects outcomes

---

## Future Work

- Multi-objective calibration support
- Integration with remote sensing data (e.g. snow cover)
- Automated diagnostic evaluation
- Hybrid spatial discretization workflows

---

## License

This project is licensed under the GNU General Public License v3.0 (GPL-3.0).

You are free to use, modify, and distribute this software under the terms of the GPL-3.0 license.

See the LICENSE file for the full text.

---

## Contributing

Contributions are welcome, especially for:

- new agents
- improved workflows
- better evaluation metrics
- integration with GEOframe tools

---


