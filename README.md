# Airline Fleet Manager App #

## Background ##
This is a Kotlin console application developed as part of the Software Development Tools module assignment. The app is menu-driven and designed to manage airlines and their fleets of aircraft. It demonstrates best practices in Kotlin development, including documentation, testing, and build automation.

## Features ##
- **Aircraft Management**
  - Add, update, and delete aircraft in the global Aircraft collection.
  - Conditional listing (e.g., aircraft no longer in production).
- **Airline Management**
  - Add, update, and delete airlines in the Airline collection.
  - List active airlines.
- **Fleet Management**
  - Add, update, and delete aircraft in an airline’s fleet.
  - Conditional listing (e.g., aircraft in a fleet with the highest revenue).
- **Persistence**
  - Save and load aircraft, airlines, and fleets.
- **Advanced Listings**
  - List aircraft by production status.
  - List fleets by revenue performance.

## Development Tools & Enhancements ##
- **Documentation**
  - KDoc comments throughout the codebase.
  - Dokka-generated HTML documentation.
- **Testing**
  - JUnit 5 tests for all core features.
  - JaCoCo integrated for code coverage reporting.
- **Code Quality**
  - Ktlint applied for consistent Kotlin style.
- **Build**
  - Gradle build automation.
  - Fat JAR packaging for easy distribution.

## How to Run ##
- Clone the repository in IntellJ
- Build with Grade and run the airfleet-manager-1.0.jar

## Reports ##
- JaCoCo Coverage Report: build/reports/jacoco/test/html/index.html
- Ktlint Report: build/reports/ktlint/

##License##

This project is for educational purposes only.
