-- V1: initial schema for ApplyPilot

CREATE TABLE users (
                       id            UUID PRIMARY KEY,
                       email         VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(60)  NOT NULL,
                       role          VARCHAR(20)  NOT NULL,
                       created_at    TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE profiles (
                          id        UUID PRIMARY KEY,
                          user_id   UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                          full_name VARCHAR(255) NOT NULL,
                          phone     VARCHAR(30),
                          summary   TEXT,
                          links     JSONB
);

CREATE TABLE experiences (
                             id         UUID PRIMARY KEY,
                             profile_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
                             title      VARCHAR(255) NOT NULL,
                             org        VARCHAR(255) NOT NULL,
                             start_date DATE,
                             end_date   DATE,              -- NULL means current
                             bullets    JSONB
);

CREATE TABLE projects (
                          id          UUID PRIMARY KEY,
                          profile_id  UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
                          title       VARCHAR(255) NOT NULL,
                          description TEXT,
                          url         VARCHAR(1000),
                          bullets     JSONB
);

CREATE TABLE jobs (
                      id            UUID PRIMARY KEY,
                      user_id       UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                      company       VARCHAR(255) NOT NULL,
                      role          VARCHAR(255) NOT NULL,
                      url           VARCHAR(1000),
                      description   TEXT NOT NULL,
                      analysis_json JSONB,
                      created_at    TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE applications (
                              id                  UUID PRIMARY KEY,
                              user_id             UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              job_id              UUID NOT NULL REFERENCES jobs(id) ON DELETE CASCADE,
                              status              VARCHAR(30) NOT NULL,
                              notes               TEXT,
                              tailored_resume_url VARCHAR(1000),
                              applied_at          TIMESTAMPTZ,
                              created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
                              updated_at          TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE application_events (
                                    id             UUID PRIMARY KEY,
                                    application_id UUID NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
                                    type           VARCHAR(40) NOT NULL,
                                    message        VARCHAR(500),
                                    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE generated_answers (
                                   id             UUID PRIMARY KEY,
                                   application_id UUID NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
                                   question       VARCHAR(1000) NOT NULL,
                                   answer         TEXT NOT NULL,
                                   approved       BOOLEAN NOT NULL DEFAULT false,
                                   created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_jobs_user_id             ON jobs(user_id);
CREATE INDEX idx_applications_user_status ON applications(user_id, status);
CREATE INDEX idx_applications_job_id      ON applications(job_id);
CREATE INDEX idx_events_application_id    ON application_events(application_id);
CREATE INDEX idx_answers_application_id   ON generated_answers(application_id);