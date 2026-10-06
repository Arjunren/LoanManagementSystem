# Loan Management System

## Overview

A secure Spring Boot REST API for customer loan applications, staff decisions, disbursement, repayments, balances, and portfolio reporting.

## Features

- Registration, revocable bearer login/logout, admin/officer/customer roles
- Owner-scoped applications with status filtering and pagination
- Approval with deterministic interest calculation, rejection reasons, and disbursement workflow
- Row-locked repayments, overpayment prevention, automatic paid status, repayment history
- Dashboard counts, audit timestamps, Flyway, PostgreSQL, H2 tests, Docker, CI, Dependabot

## Technology Stack

Java 25, Spring Boot 4.1.1, Spring Web MVC, Security, Data JPA, Bean Validation, Flyway, PostgreSQL 17, H2 tests, Maven Wrapper.

## Requirements

Java 25 and PostgreSQL 15+ or Docker. Maven is optional because the wrapper is included.

## Installation

Run `Copy-Item .env.example .env` and `.\mvnw.cmd clean package`.

## Environment Variables

Required: `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`. Optional: `TOKEN_TTL_MINUTES`, `ALLOWED_ORIGINS`, `SPRING_PROFILES_ACTIVE`. Example values are development-only.

## Database Setup

Create the configured PostgreSQL database. Flyway runs `V1__init.sql` at startup. Docker users can run `docker compose up --build`.

## Running the Application

```powershell
$env:SPRING_PROFILES_ACTIVE='dev'
.\mvnw.cmd spring-boot:run
```

The API listens on port 8080; health is `/actuator/health`.

## Running Tests

Run `.\mvnw.cmd verify`.

## Default Development Accounts

With the `dev` profile: admin `admin@example.com` / `AdminPassword123!`; officer `officer@example.com` / `OfficerPassword123!`; customer `customer@example.com` / `CustomerPassword123!`. Never use these outside disposable development.

## API Endpoints

Auth: `POST /api/auth/register|login|logout`. Loans: `GET/POST /api/loans`, `GET /api/loans/{id}`, `PATCH /api/loans/{id}/approve|reject|disburse`. Repayments: `GET/POST /api/loans/{id}/repayments`. Reporting: `GET /api/dashboard`.

## Folder Structure

`controller/`, `service/`, `repository/`, `entity/`, `dto/`, `security/`, `config/`, `exception/`, and `resources/db/migration/` provide explicit application layers.

## Security Notes

BCrypt cost 12, hashed opaque tokens, revocation, login rate limiting/timing equalization, server-side RBAC and ownership, explicit DTOs, bounded input/pages, parameterized JPA, allow-listed CORS, hidden stack traces, row locking, versioning, and database constraints protect the workflow.

## Known Limitations

Simple interest is used. Credit scoring, document uploads, amortization schedules, late fees, payment gateways, notifications, MFA, password recovery, and regulatory-grade auditing are outside scope. Rate limiting is per process.
