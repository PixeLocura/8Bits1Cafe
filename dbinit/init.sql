-- Initialize roles if they don't exist
INSERT INTO roles (name)
VALUES ('ADMIN'), ('DEVELOPER')
ON CONFLICT (name) DO NOTHING;
