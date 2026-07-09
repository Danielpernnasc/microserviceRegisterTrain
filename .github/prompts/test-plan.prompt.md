# Test Plan Prompt

Use this prompt to create a comprehensive Test Plan following Avanade Method standards.

## Quick Start

```
Create a test plan for [feature/story name]:

**Scope**:
- In Scope: [What will be tested]
- Out of Scope: [What won't be tested]

**Test Strategy**:
- Unit Tests: [Coverage target %]
- Integration Tests: [Key integrations]
- E2E Tests: [Critical user flows]

**Test Cases**:
1. [Scenario] - [Expected Result] - [Priority: P0/P1/P2]
2. [Scenario] - [Expected Result] - [Priority: P0/P1/P2]

**Entry/Exit Criteria**:
- Entry: [When testing can start]
- Exit: [When testing is complete]
```

## Test Levels

1. **Unit Tests**
   - Function/method level
   - Mocked dependencies
   - Target: >80% coverage

2. **Integration Tests**
   - API endpoints
   - Database interactions
   - External service integrations

3. **E2E Tests**
   - Critical user flows
   - Cross-browser testing
   - Performance validation

4. **Non-Functional Tests**
   - Performance (load, stress)
   - Security (OWASP Top 10)
   - Accessibility (WCAG AA)

## Test Case Template

```
TC-001: [Test Case Title]
Priority: P0 | P1 | P2
Type: Functional | Non-Functional

Preconditions:
- [Setup required]

Steps:
1. [Action]
2. [Action]
3. [Action]

Expected Result:
- [What should happen]

Actual Result:
- [What actually happened]

Status: Pass | Fail | Blocked
```

## Validation Checklist

✅ All acceptance criteria covered?
✅ Edge cases identified?
✅ Security test cases included?
✅ Performance baselines defined?
✅ Regression test suite updated?

---

**Reference**: `.avanade-method/_base/artifacts/AVANADE_TEST_PLAN_TEMPLATE.md`
**Owner**: Carla QA
**Agent**: Use `@carla-qa` for assisted test planning
