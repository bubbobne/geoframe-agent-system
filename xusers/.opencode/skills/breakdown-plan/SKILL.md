---
name: breakdown-plan
description: 'Generate a structured, verifiable execution plan for a technical task (inputs, steps, constraints, risks, verification).'
---

# Breakdown Plan (execution-oriented)

Use this skill to turn an ambiguous request into a concrete, verifiable plan.

## Output contract (required)

Produce a plan that contains, in this order:

1. **Task type** (bugfix / feature / refactor / docs / build-release / debugging / evaluation)
2. **Relevant repository area(s)** (directories/files *to be confirmed* via inspection)
3. **Likely specialist agent** (if delegation is supported/allowed)
4. **Required inputs** (files, logs, versions, commands, example data)
5. **Constraints that must not change** (APIs, units, scientific meaning, output formats)
6. **Step-by-step plan** (small steps; each step states what evidence will confirm success)
7. **Verification steps** (commands or checks; do not invent build/test commands)
8. **Risks & rollback** (what could go wrong; how to revert safely)
9. **Open questions** (explicit `[ASK USER]` items)

## Rules

- Do not assume paths, module names, versions, or tooling: **inspect first** (or ask).
- Prefer minimal, reversible changes.
- Separate *scientific/model* decisions from *technical/implementation* changes.
- If the host environment does not provide skill injection, treat this document as a reference checklist.
