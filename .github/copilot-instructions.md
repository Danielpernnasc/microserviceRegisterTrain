# GitHub Copilot - Avanade Method Instructions

This workspace uses **Avanade Method v2** - a comprehensive software development methodology with specialized AI agents for each role in the SDLC.

## 🎯 Workspace Context

You are working in an **Avanade Method environment** where development follows structured workflows with quality gates at each phase.

## 🎭 Available Agents

When users mention these agents, understand their roles:

- **@avanade-supervisor**: Orchestrator & methodology guide
- **@maria-analyst**: Discovery & business analysis
- **@joao-pm**: Product management & PRDs
- **@wilson-architect**: Architecture & technical decisions
- **@paula-po**: Product ownership & backlog
- **@roberto-sm**: Scrum Master & sprint facilitation
- **@carla-qa**: Quality assurance & testing
- **@tiago-dev**: Full-stack development
- **@sofia-ux**: UX design & accessibility
- **@paige-tech-writer**: Technical documentation

## 📋 Methodology Principles

When assisting users, follow these principles:

1. **Quality First**: Always suggest tests, validation, and documentation
2. **Azure-Native**: Prefer Microsoft technologies (.NET, Azure, TypeScript, React)
3. **Security by Design**: Include authentication, authorization, input validation
4. **Accessibility**: WCAG AA compliance for all UI implementations
5. **Documentation**: CommonMark standards, clear and structured

## 🏗️ Code Standards

### .NET / C#

- Use .NET 8+ features
- Follow SOLID principles
- Async/await for I/O operations
- Dependency injection
- Entity Framework Core for data access

### TypeScript / React

- Functional components with hooks
- TypeScript strict mode
- Fluent UI React components
- Proper error boundaries
- Accessibility (ARIA labels)

### Azure Services

- Prefer managed services (App Service, SQL Database, Functions)
- Use Managed Identity for service-to-service auth
- Key Vault for secrets
- Application Insights for monitoring

## 📁 Project Structure

Standard Avanade Method workspace structure:

```
.avanade-method/
├── _base/              # Base agent templates
├── agents/             # Agent customizations
├── workflows/          # Workflow guides
├── tasks/              # Task definitions
├── templates/          # Document templates
├── checklists/         # Validation checklists
├── standards/          # Coding & doc standards
├── memory/             # Agent memory files
└── guides/             # Special guides

.github/
├── agents/             # Copilot agent chatmodes
└── prompts/            # Quick prompt templates
```

## 🚀 Workflow Phases

Understand the standard development flow:

1. **Discovery** → Maria Analyst creates product brief
2. **Planning** → João PM creates PRD
3. **Solutioning** → Wilson designs architecture, Paula creates stories
4. **Implementation** → Roberto plans sprint, Tiago develops, Carla tests
5. **Documentation** → Paige creates technical docs

## ✅ Quality Gates

Before suggesting code is "complete":

- ✅ Unit tests written and passing (>80% coverage)
- ✅ Integration tests for APIs
- ✅ Error handling implemented
- ✅ Logging added (structured logging)
- ✅ Security validated (no exposed secrets, input validation)
- ✅ Accessibility checked (WCAG AA)
- ✅ Documentation updated

## 🔒 Security Checklist

Always validate:

- ❌ No hardcoded secrets or API keys
- ✅ Environment variables for config
- ✅ Input validation and sanitization
- ✅ Parameterized SQL queries (prevent injection)
- ✅ HTTPS/TLS for all communications
- ✅ Authentication & authorization on endpoints

## 📝 Documentation Standards

When generating documentation:

- Use CommonMark-compliant Markdown
- Include: Overview, Usage, API Reference, Examples
- Code samples should be complete and runnable
- Mermaid diagrams for flows and architecture
- Accessibility notes for UI components

## 🎨 UI/UX Guidelines

When suggesting UI:

- Use Fluent UI React components
- Follow Microsoft Fluent Design principles
- Ensure keyboard navigation
- Provide ARIA labels for screen readers
- Minimum contrast ratio 4.5:1 (WCAG AA)
- Responsive design (mobile-first)

## 🧪 Testing Philosophy

Suggest comprehensive testing:

- **Unit Tests**: Every function/method
- **Integration Tests**: API endpoints, database queries
- **E2E Tests**: Critical user flows
- **Accessibility Tests**: Automated WCAG scans
- **Performance Tests**: Load baselines for critical paths

## 🔄 Git Workflow

Standard practices:

- Feature branches: `feature/[story-id]-[description]`
- Commit messages: `[TYPE] Story-123: Description`
  - Types: feat, fix, docs, style, refactor, test, chore
- PRs require: tests passing, code review, no merge conflicts

## 💡 Helpful Suggestions

When user asks for help:

1. Understand context (which phase? which agent role?)
2. Suggest appropriate workflow from `.avanade-method/workflows/`
3. Reference relevant tasks from `.avanade-method/tasks/`
4. Validate against checklists in `.avanade-method/checklists/`
5. Follow standards in `.avanade-method/standards/`

## 🚨 Anti-Patterns to Avoid

Never suggest:

- ❌ Skipping tests ("add tests later")
- ❌ Hardcoded credentials
- ❌ Console.WriteLine for logging (use ILogger)
- ❌ Synchronous I/O (use async/await)
- ❌ Ignoring accessibility
- ❌ Incomplete error handling (catch without handling)

## 🔗 Quick References

- **PRD Template**: `.avanade-method/templates/prd-template.yaml`
- **ADR Template**: `.avanade-method/templates/adr-template.md`
- **Story Template**: `.avanade-method/templates/story-template.yaml`
- **Test Plan**: `.avanade-method/templates/test-plan-template.md`
- **Clean Code Guide**: `.avanade-method/tasks/clean-code.task.md`
- **Code Review**: `.avanade-method/tasks/code-review.task.md`

---

**Version**: Avanade Method v2.1
**Updated**: 2026-02-25
**Framework Owner**: Avanade Supervisor
