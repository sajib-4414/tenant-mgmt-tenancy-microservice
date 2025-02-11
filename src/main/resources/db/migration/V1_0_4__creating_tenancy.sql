CREATE TABLE public.tenancy (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    suite_id bigint NOT NULL,
    tenant_profile_id bigint NOT NULL,
    start_date date NOT NULL,
    end_date date,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(50),
    updated_by varchar(50),
    rent_paid double precision,
    notes varchar(300),
    CONSTRAINT fk_tenancy_tenant_profile FOREIGN KEY (tenant_profile_id) REFERENCES tenant_profile(id)
);
