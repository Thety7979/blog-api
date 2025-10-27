-- Migration file created at 20251027201555 
create table invalidated_token(
    id varchar primary key,
    expirytime timestamp with time zone
)