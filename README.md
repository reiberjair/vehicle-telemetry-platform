\# Vehicle Telemetry \& Diagnostics Platform



An engineering portfolio project combining embedded firmware,

automotive electronics, backend development, and an Android application.



\## Purpose



Design and build a physical system that captures automotive network

data, processes it on an STM32 microcontroller, and delivers telemetry

to a backend and mobile application.



The project emphasizes documented engineering decisions, measurable

behavior, testing, and hardware/software integration.



\## Planned System



Vehicle / Bench Simulator

&#x20;   |

&#x20;   | CAN / CAN-FD / LIN

&#x20;   v

Transceivers + STM32 + FreeRTOS

&#x20;   |

&#x20;   | USB or wireless transport

&#x20;   | Host connectivity to be defined

&#x20;   v

Java Spring Boot Backend

&#x20;   |

&#x20;   v

PostgreSQL

&#x20;   |

&#x20;   | Data exposed through the backend API

&#x20;   v

Kotlin / Jetpack Compose Android App



\## Initial Technical Direction



\- Microcontroller candidate: STM32G474RE

\- Prototyping board candidate: NUCLEO-G474RE

\- Firmware: C/C++, STM32 tools, and FreeRTOS

\- Network target: two CAN/CAN-FD channels and one LIN channel

\- Custom hardware: schematic and PCB design in KiCad

\- Backend: Java and Spring Boot

\- Database: PostgreSQL

\- Android application: Kotlin and Jetpack Compose

\- Version control: Git and GitHub



These are initial design targets, not implemented capabilities.

Pin assignments, peripheral compatibility, transport, throughput,

and power requirements will be validated before hardware design.



\## Planned Capabilities



\- Capture and timestamp automotive network frames.

\- Identify the source channel of each captured frame.

\- Decode selected signals using documented definitions.

\- Buffer, transmit, and store telemetry.

\- Display live data and connection status.

\- Report communication errors and dropped frames.

\- Add diagnostic functions incrementally with explicit access controls.



\## Diagnostic Access



The architecture will distinguish between:



\- Passive monitoring

\- Generic OBD-II requests

\- Permitted diagnostic operations

\- OEM enhanced diagnostics

\- Authenticated diagnostic access



The project will not depend on bypassing security gateways.

OEM authentication, where required, must use authorized mechanisms.



Initial development and verification will use a controlled bench

or simulator before testing on a vehicle.



\## Development Approach



Requirements → Architecture → Repository → Modules → Interfaces

→ Implementation → Testing → Documentation → Releases



Directories and software layers will be introduced when their

responsibilities justify them.



Development will begin with a small end-to-end demonstration and

expand toward the complete system.



\## Current Status



Phase 0 — Project definition and repository setup.



No firmware, backend, mobile application, or custom PCB has been

implemented yet.



The next deliverables are requirements v0.1 and initial milestones.

The custom PCB will require a design review before fabrication.



\## Planned Portfolio Evidence



\- Requirements and architecture decisions

\- Firmware, backend, and Android source code

\- Schematics, PCB files, and bill of materials

\- Engineering calculations

\- Test procedures and results

\- Oscilloscope and network captures

\- Hardware photographs

\- Demonstration video



\## Author



Reiber Arias — Electronic Engineer

