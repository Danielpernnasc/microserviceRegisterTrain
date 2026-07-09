# PRD Creation Prompt

Use this prompt to create a Product Requirements Document following Avanade Method standards.

## Quick Start

```
Create a PRD for [feature/product name] that includes:

1. **Executive Summary**
   - Problem statement
   - Proposed solution
   - Expected impact

2. **Goals & Success Metrics**
   - Primary goal (measurable)
   - KPIs with baselines and targets

3. **User Stories**
   - Epic and user stories
   - Acceptance criteria

4. **Technical Requirements**
   - Functional requirements
   - Non-functional requirements

5. **Roadmap**
   - Phases and milestones
   - Timeline
```

## Full Template Reference

For the complete PRD template, see: `.avanade-method/templates/prd-template.yaml`

## Tri-Modal Workflow

- **Create**: Generate new PRD from scratch
- **Validate**: Check PRD against PM checklist
- **Edit**: Update existing PRD with change tracking

## Validation Checklist

✅ Problem clearly defined?
✅ At least 3 measurable KPIs?
✅ User stories have acceptance criteria?
✅ Risks identified with mitigation?
✅ Timeline realistic?

---

**Reference**: `.avanade-method/_base/artifacts/AVANADE_PRD_TEMPLATE_YAML.yaml`
**Owner**: João PM
**Agent**: Use `@joao-pm` for assisted PRD creation
