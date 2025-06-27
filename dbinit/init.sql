-- Initialize roles if they don't exist
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO roles (name)
VALUES ('ADMIN'), ('DEVELOPER')
ON CONFLICT (name) DO NOTHING;
