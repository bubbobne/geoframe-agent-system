#!/usr/bin/env python3
"""Validate basic consistency of the GEOframe OpenCode agent pack.

The check intentionally uses only the Python standard library so it can run in a
fresh research or development checkout without installing dependencies.
"""
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LAYERS = {
    "xusers": {
        "primary": "geoframe-orchestrator",
        "agents": {
            "geoframe-orchestrator",
            "geoframe-meteo-data",
            "geoframe-simulation-builder",
            "geoframe-oms-runner",
            "geoframe-evaluator",
            "geoframe-documenter",
        },
        "skills": {
            "acquire-codebase-knowledge",
            "breakdown-plan",
            "create-readme",
        },
    },
    "xdevelopers": {
        "primary": "geoframe-xdevelopers-orchestrator",
        "agents": {
            "geoframe-xdevelopers-orchestrator",
            "geoframe-java-developer",
            "geoframe-refactor-agent",
            "geoframe-code-reviewer",
            "geoframe-test-agent",
            "geoframe-build-release-agent",
            "geoframe-git-maven-agent",
            "geoframe-oms-debugger",
        },
        "skills": {
            "acquire-codebase-knowledge",
            "breakdown-plan",
            "create-agentsmd",
            "create-readme",
            "java-docs",
            "java-junit",
            "java-refactoring-extract-method",
            "java-refactoring-remove-parameter",
        },
    },
}

FRONTMATTER_RE = re.compile(r"\A---\n(?P<body>.*?)\n---\n", re.DOTALL)


def parse_frontmatter(path: Path) -> dict[str, str]:
    text = path.read_text(encoding="utf-8")
    match = FRONTMATTER_RE.match(text)
    if not match:
        raise ValueError(f"{path} is missing YAML front matter")
    values: dict[str, str] = {}
    for line in match.group("body").splitlines():
        if not line or line.startswith(" "):
            continue
        if ":" in line:
            key, value = line.split(":", 1)
            values[key.strip()] = value.strip()
    return values


def collect_task_allows(path: Path) -> set[str]:
    allows: set[str] = set()
    in_task = False
    for line in path.read_text(encoding="utf-8").splitlines():
        if line.strip() == "task:":
            in_task = True
            continue
        if in_task and line.startswith("  ") and not line.startswith("    "):
            break
        if in_task:
            match = re.match(r'\s+"([^"]+)":\s+allow\s*$', line)
            if match:
                allows.add(match.group(1))
    return allows


def validate_layer(name: str, expected: dict[str, object]) -> list[str]:
    errors: list[str] = []
    layer = ROOT / name
    agent_dir = layer / ".opencode" / "agents"
    skill_dir = layer / ".opencode" / "skills"

    agent_files = {path.stem: path for path in agent_dir.glob("*.md")}
    expected_agents = expected["agents"]
    missing = sorted(expected_agents - set(agent_files))
    extra = sorted(set(agent_files) - expected_agents)
    if missing:
        errors.append(f"{name}: missing agent files: {', '.join(missing)}")
    if extra:
        errors.append(f"{name}: undocumented extra agent files: {', '.join(extra)}")

    primary = expected["primary"]
    for agent, path in sorted(agent_files.items()):
        try:
            frontmatter = parse_frontmatter(path)
        except ValueError as exc:
            errors.append(str(exc))
            continue
        if not frontmatter.get("description"):
            errors.append(f"{path}: missing description")
        mode = frontmatter.get("mode")
        expected_mode = "primary" if agent == primary else "subagent"
        if mode != expected_mode:
            errors.append(f"{path}: expected mode {expected_mode!r}, found {mode!r}")

    skill_files = {path.parent.name for path in skill_dir.glob("*/SKILL.md")}
    expected_skills = expected["skills"]
    missing_skills = sorted(expected_skills - skill_files)
    if missing_skills:
        errors.append(f"{name}: missing skill files: {', '.join(missing_skills)}")

    orchestrator_path = agent_files.get(primary)
    if orchestrator_path:
        allowed = collect_task_allows(orchestrator_path)
        expected_allowed = set(expected_agents) - {primary}
        if allowed != expected_allowed:
            errors.append(
                f"{name}: orchestrator task allow-list mismatch; "
                f"expected {sorted(expected_allowed)}, found {sorted(allowed)}"
            )
        if "geoframe-*" in allowed:
            errors.append(f"{name}: wildcard task allow-list would blur user/developer boundaries")

    return errors


def main() -> int:
    errors: list[str] = []
    for name, expected in LAYERS.items():
        errors.extend(validate_layer(name, expected))

    if errors:
        for error in errors:
            print(f"ERROR: {error}", file=sys.stderr)
        return 1
    print("GEOframe agent pack structure is consistent.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
