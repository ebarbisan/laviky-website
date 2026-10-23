---
name: project-status
description: Verifies the current state of the Laviky project, checking git status, build health, and environment variables.
---

# Project Status Check
This skill provides a comprehensive overview of the project's current state.

## Steps:
1. **Git Check:** Run `git status` to see pending changes.
2. **Build Check:** Run `./mvnw clean compile` to verify there are no compilation errors.
3. **Config Check:** Verify if `.env` or environment variables are properly set for the current profile.
4. **Dependency Check:** Run `mvn dependency:analyze` to check for unused or missing dependencies.

## Output:
A summary report indicating if the project is "Ready for Deploy", "Needs Fixes", or "In Development".
