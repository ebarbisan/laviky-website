---
name: deploy-check
description: Validates the application is ready for production deployment to Railway/Render.
---

# Deploy Readiness Check
Ensures that all production requirements are met before pushing to the main branch.

## Steps:
1. **Dockerfile Verification:** Confirm `Dockerfile` exists and is updated.
2. **Environment Variable Audit:** Check `application.yml` for any hardcoded secrets (must use ${VAR} syntax).
3. **Migration Check:** Ensure all SQL scripts in `src/main/resources/db/migration` are correctly numbered (V1, V2...).
4. **Build Validation:** Execute a production build: `mvn clean package -DskipTests` to ensure the JAR is generated.

## Output:
A checklist confirming the application is "Production-Ready" or a list of blocking issues.
