CREATE TABLE tb_users (
                          id UUID PRIMARY KEY,
                          name VARCHAR(150),
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255),
                          created_at TIMESTAMPTZ,
                          updated_at TIMESTAMPTZ
);

CREATE TABLE tb_exercise_types (
                                   id UUID PRIMARY KEY,
                                   name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tb_exercises (
                              id UUID PRIMARY KEY,
                              name VARCHAR(100),
                              description VARCHAR(255),
                              media_url VARCHAR(255),
                              type_id UUID REFERENCES tb_exercise_types(id),
                              user_id UUID REFERENCES tb_users(id) NOT NULL,

                              UNIQUE (name, user_id)
);

CREATE TABLE tb_routines (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100),
                             user_id UUID REFERENCES tb_users(id) NOT NULL,

                             UNIQUE (user_id, name)
);

CREATE TABLE tb_routine_exercises (
                                      id UUID PRIMARY KEY,
                                      routine_id UUID REFERENCES tb_routines(id),
                                      exercise_id UUID REFERENCES tb_exercises(id),

                                      UNIQUE (routine_id, exercise_id)
);

CREATE TABLE tb_series (
                           id UUID PRIMARY KEY,
                           type_id UUID REFERENCES tb_exercise_types(id),
                           position INTEGER,
                           rest_time INTERVAL,
                           weight NUMERIC(7,2),
                           duration INTERVAL,
                           repetitions INTEGER,
                           distance NUMERIC(7,2),
                           routine_exercise_id UUID NOT NULL
                               REFERENCES tb_routine_exercises(id),
                           user_id UUID REFERENCES tb_users(id) NOT NULL,

                           UNIQUE (routine_exercise_id, position)
);

CREATE TABLE tb_sessions (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100),
                             status VARCHAR(10) DEFAULT 'OPEN',
                             created_at TIMESTAMPTZ NOT NULL,
                             updated_at TIMESTAMPTZ NOT NULL,
                             finalized_at TIMESTAMPTZ,
                             routine_id UUID NOT NULL REFERENCES tb_routines(id),
                             user_id UUID REFERENCES tb_users(id) NOT NULL,

                             CONSTRAINT chk_session_status
                                 CHECK (status IN ('OPEN', 'CLOSED'))
);

CREATE TABLE tb_series_session (
                                   id UUID PRIMARY KEY,
                                   session_id UUID NOT NULL REFERENCES tb_sessions(id),
                                   serie_id UUID NOT NULL REFERENCES tb_series(id),
                                   exercise_type VARCHAR(100),
                                   weight NUMERIC(7,2),
                                   time INTERVAL,
                                   status VARCHAR(50) DEFAULT 'NOT_COMPLETED',
                                   repetitions INTEGER,
                                   distance NUMERIC(7,2),
                                   user_id UUID REFERENCES tb_users(id) NOT NULL,

                                   UNIQUE (session_id, serie_id),

                                   CONSTRAINT chk_serie_session_status
                                       CHECK (status IN ('COMPLETED', 'NOT_COMPLETED'))
);