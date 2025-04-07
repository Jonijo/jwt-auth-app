CREATE TABLE IF NOT EXISTS users
(
    id serial,
    username  character varying(255),
    first_name  character varying(255),
    last_name  character varying(255),
    password  character varying(255),
    created_at date default now(),
    updated_at date  default now(),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);