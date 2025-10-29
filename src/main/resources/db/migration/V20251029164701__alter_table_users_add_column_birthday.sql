-- Migration file created at 20251029164701 

ALTER TABLE users
ADD COLUMN birthday timestamp with time zone;