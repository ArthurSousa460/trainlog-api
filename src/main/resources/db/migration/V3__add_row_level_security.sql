-- Restrict user-owned data to the user identified by app.current_user_id.
-- The setting is intentionally nullable when it is not configured so that
-- requests without a user context do not see or write any rows.

-- Flyway runs as the schema owner/admin. The application role only needs
-- runtime access to the application tables.
GRANT USAGE ON SCHEMA public TO trainlog_app;

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE
    tb_users,
    tb_exercise_types,
    tb_exercises,
    tb_routines,
    tb_routine_exercises,
    tb_series,
    tb_sessions,
    tb_series_session
TO trainlog_app;

-- Foreign-key checks performed by the application role also need REFERENCES.
GRANT REFERENCES ON TABLE
    tb_users,
    tb_exercise_types,
    tb_exercises,
    tb_routines,
    tb_routine_exercises,
    tb_series,
    tb_sessions,
    tb_series_session
TO trainlog_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO trainlog_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT REFERENCES ON TABLES TO trainlog_app;

ALTER TABLE tb_exercises ENABLE ROW LEVEL SECURITY;
ALTER TABLE tb_exercises FORCE ROW LEVEL SECURITY;

CREATE POLICY tb_exercises_user_isolation
    ON tb_exercises
    FOR ALL
    TO trainlog_app
    USING (user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid)
    WITH CHECK (user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid);

ALTER TABLE tb_routines ENABLE ROW LEVEL SECURITY;
ALTER TABLE tb_routines FORCE ROW LEVEL SECURITY;

CREATE POLICY tb_routines_user_isolation
    ON tb_routines
    FOR ALL
    TO trainlog_app
    USING (user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid)
    WITH CHECK (user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid);

-- The join table has no user_id of its own. Both sides must belong to the
-- current tenant; otherwise a tenant could link its data to another tenant's
-- routine or exercise by guessing an id.
ALTER TABLE tb_routine_exercises ENABLE ROW LEVEL SECURITY;
ALTER TABLE tb_routine_exercises FORCE ROW LEVEL SECURITY;

CREATE POLICY tb_routine_exercises_user_isolation
    ON tb_routine_exercises
    FOR ALL
    TO trainlog_app
    USING (
        EXISTS (
            SELECT 1
            FROM tb_routines r
            WHERE r.id = routine_id
              AND r.user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        )
        AND EXISTS (
            SELECT 1
            FROM tb_exercises e
            WHERE e.id = exercise_id
              AND e.user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        )
    )
    WITH CHECK (
        EXISTS (
            SELECT 1
            FROM tb_routines r
            WHERE r.id = routine_id
              AND r.user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        )
        AND EXISTS (
            SELECT 1
            FROM tb_exercises e
            WHERE e.id = exercise_id
              AND e.user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        )
    );

ALTER TABLE tb_series ENABLE ROW LEVEL SECURITY;
ALTER TABLE tb_series FORCE ROW LEVEL SECURITY;

CREATE POLICY tb_series_user_isolation
    ON tb_series
    FOR ALL
    TO trainlog_app
    USING (
        user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        AND EXISTS (
            SELECT 1
            FROM tb_routine_exercises re
            WHERE re.id = routine_exercise_id
        )
    )
    WITH CHECK (
        user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        AND EXISTS (
            SELECT 1
            FROM tb_routine_exercises re
            WHERE re.id = routine_exercise_id
        )
    );

ALTER TABLE tb_sessions ENABLE ROW LEVEL SECURITY;
ALTER TABLE tb_sessions FORCE ROW LEVEL SECURITY;

CREATE POLICY tb_sessions_user_isolation
    ON tb_sessions
    FOR ALL
    TO trainlog_app
    USING (
        user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        AND EXISTS (
            SELECT 1
            FROM tb_routines r
            WHERE r.id = routine_id
        )
    )
    WITH CHECK (
        user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        AND EXISTS (
            SELECT 1
            FROM tb_routines r
            WHERE r.id = routine_id
        )
    );

ALTER TABLE tb_series_session ENABLE ROW LEVEL SECURITY;
ALTER TABLE tb_series_session FORCE ROW LEVEL SECURITY;

CREATE POLICY tb_series_session_user_isolation
    ON tb_series_session
    FOR ALL
    TO trainlog_app
    USING (
        user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        AND EXISTS (
            SELECT 1
            FROM tb_sessions s
            WHERE s.id = session_id
        )
        AND EXISTS (
            SELECT 1
            FROM tb_series s
            WHERE s.id = serie_id
        )
    )
    WITH CHECK (
        user_id = NULLIF(current_setting('app.current_user_id', true), '')::uuid
        AND EXISTS (
            SELECT 1
            FROM tb_sessions s
            WHERE s.id = session_id
        )
        AND EXISTS (
            SELECT 1
            FROM tb_series s
            WHERE s.id = serie_id
        )
    );
