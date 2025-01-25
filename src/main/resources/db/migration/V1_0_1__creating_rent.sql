CREATE TABLE public.rent (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    amount integer NOT NULL,
    due_date date NOT NULL,
    paid_date date,
    status character varying(255),
    tenant_profile_id bigint,
    suite_id bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by character varying(255),
    updated_by character varying(255)
);