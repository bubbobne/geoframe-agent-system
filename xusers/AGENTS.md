# xusers

## Scope

This directory contains the user-facing GEOframe agent prompts. These agents support hydrological modelling workflows, data preparation, simulation setup, evaluation, and scientific documentation.

Use them for:
- modelling objectives and workflow planning
- meteorological forcing preparation
- simulation, calibration, and validation setup
- hydrological evaluation and diagnostics
- documentation of assumptions and results

## Conventions

- Keep prompts in English.
- Keep the `xusers` name consistent across documentation.
- Do not silently fill missing data or hide assumptions.
- Separate facts, assumptions, and interpretation.
- Prefer diagnostic hydrology over single-metric optimisation.

## Agent Set

- `geoframe-orchestrator`: plan the workflow and delegate tasks
- `geoframe-meteo-data`: prepare and check meteorological forcing data
- `geoframe-simulation-builder`: define simulation, calibration, and validation setups
- `geoframe-evaluator`: compare model output with observations and diagnostics
- `geoframe-documenter`: write reproducible scientific documentation

## Related Documentation

- Root overview: `../Readme.md`
- Developer agents: `../xdevelopers/Readme.md`
