# AGENTS.md

- This repo is an OpenCode agent pack for GEOframe, not an application source tree.
- Top-level guidance lives in `Readme.md`; agent prompts live in `xusers/.opencode/agents/` and `xdevelopers/.opencode/agents/`.
- `xusers` holds primary workflow agents; `xdevelopers` holds developer subagents.
- `geoframe-git-maven-agent` is optional and should be used only when explicitly requested.
- `geoframe-xdevelopers-orchestrator` is optional and coordinates technical developer workflows only.
- `geoframe-orchestrator` is the primary agent and only delegates to `geoframe-*` tasks.
- All currently checked prompts except `geoframe-documenter` use `edit: ask` and `bash: ask`.
- `geoframe-orchestrator` denies all non-`geoframe-*` task delegation.
- `geoframe-simulation-builder` never recalibrates during validation and must state whether validation is temporal, spatial, or both.
- `geoframe-meteo-data` must not silently fill missing data or mix daily and hourly data without documenting aggregation rules.
- Preserve the hydrology-specific constraints in the prompts: do not optimize or report only KGE; check water balance, snow, ET, and internal consistency.
- Do not assume file paths; inspect the repo or ask.
- There are no repo build, test, or lint scripts in `.opencode/package.json`; do not invent commands.
- Keep YAML front matter and permissions exact when editing agent prompts.
