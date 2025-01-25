CREATE TABLE public.rent_price (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    suite_id bigint,
    effective_start_date date,
    effective_end_date date,
    rent_amount double precision,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by character varying(50),
    updated_by character varying(50)
);
