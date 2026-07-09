# ADR Template Prompt

Use this prompt to create an Architecture Decision Record following Avanade Method standards.

## Quick Start

```
Create an ADR for [decision title]:

**Context**: [What is the issue we're facing?]

**Decision**: [What is the change we're proposing/making?]

**Status**: [Proposed | Accepted | Deprecated | Superseded]

**Consequences**:
- Positive: [What becomes easier?]
- Negative: [What becomes harder?]
- Neutral: [What stays the same?]

**Alternatives Considered**:
1. [Alternative 1] - Rejected because [reason]
2. [Alternative 2] - Rejected because [reason]
```

## Standard Sections

1. **Title**: ADR-XXX: [Short descriptive title]
2. **Date**: YYYY-MM-DD
3. **Status**: Proposed | Accepted | Deprecated | Superseded
4. **Context**: Technical/business context and problem
5. **Decision**: Chosen solution and approach
6. **Rationale**: Why this decision was made
7. **Consequences**: Positive, negative, and neutral impacts
8. **Alternatives**: Other options considered and why rejected

## Best Practices

✅ One ADR per decision
✅ Immutable once accepted (create new ADR to supersede)
✅ Include trade-offs explicitly
✅ Link to related ADRs
✅ Keep it concise (1-2 pages max)

## Examples

- **ADR-001**: Choice of Azure SQL vs Cosmos DB
- **ADR-002**: Authentication via Azure AD B2C
- **ADR-003**: Microservices vs Monolith architecture

---

**Reference**: `.avanade-method/_base/artifacts/AVANADE_ADR_TEMPLATE.md`
**Owner**: Wilson Architect
**Agent**: Use `@wilson-architect` for assisted ADR creation
