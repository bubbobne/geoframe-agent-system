# xusers

## Scope

This directory contains the user-facing GEOframe agent prompts. These agents support hydrological modelling workflows, data preparation, simulation setup, execution through Python/console/Java OMS entry points, evaluation, and scientific documentation.

Use them for:
- modelling objectives and workflow planning
- meteorological forcing preparation
- simulation, calibration, and validation setup
- execution of prepared `.sim` workflows from Python scripts, shell commands, or direct Java OMS launches
- hydrological evaluation and diagnostics
- documentation of assumptions and results

## Conventions

- Keep prompts in English.
- Keep the `xusers` name consistent across documentation.
- Do not silently fill missing data or hide assumptions.
- Separate facts, assumptions, and interpretation.
- Prefer diagnostic hydrology over single-metric optimisation.
- Keep user-layer execution operational: run documented workflows, but do not patch Java components or alter model physics.
- Route Java/OMS code-level failures to the developer layer with logs and reproduction commands.

## Agent Set

- `geoframe-orchestrator`: plan the workflow and delegate tasks
- `geoframe-meteo-data`: prepare and check meteorological forcing data
- `geoframe-simulation-builder`: define simulation, calibration, and validation setups
- `geoframe-oms-runner`: execute prepared `.sim` workflows and preserve run evidence
- `geoframe-evaluator`: compare model output with observations and diagnostics
- `geoframe-documenter`: write reproducible scientific documentation

## Related Documentation

- Root overview: `../Readme.md`
- Developer agents: `../xdevelopers/Readme.md`
