# Software Design - Access Control System Project

## About This Course
This project-based course immersed me in object-oriented design principles through the development of an Access Control System (ACS) prototype. Over three milestones, I learned to design scalable software architectures while implementing key features of a cloud-based physical security system.

## Practical Work

### Milestone 1: Core Functionality (Weeks 1-4)
We implemented the foundational components:
- Designed a state machine for door operations:
  - Basic states: Locked/Unlocked
  - Advanced states: Unlocked Shortly → Propped
- Built space/partition hierarchies using composite pattern
- Implemented RBAC with:
  - User groups (Admin/Manager/Employee/Blank)
  - Time-based permissions
- Created a clock observer system for automatic state transitions

### Milestone 2: Code Quality (Weeks 5-7)
We refined the system architecture:
- Applied Singleton pattern to core services
- Implemented visitor pattern for tree traversals
- Added comprehensive logging with Logback
- Enforced Google Checkstyle standards
- Wrote maintainer-focused documentation

## Development Environment
Key technologies used:
- IntelliJ IDEA (Java backend)
- PlantUML (Architecture diagrams)
- Logback/SLF4J (Logging)
- JSON/REST API communication

## Core Concepts Mastered
- State design pattern implementation
- Thread synchronization techniques
- Composite pattern for hierarchical structures
- REST API design principles

## Final Reflection
From basic door state management to a full RBAC system with mobile integration, this project taught us how object-oriented principles translate to real-world systems. The milestone-based approach allowed for iterative improvement of both functionality and code quality.
