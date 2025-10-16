-- Migration file created at 20251012211942 


alter table users
add column created_at timestamp with time zone,
add column updated_at timestamp with time zone;