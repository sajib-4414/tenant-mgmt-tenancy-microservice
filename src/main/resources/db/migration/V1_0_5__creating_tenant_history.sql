CREATE TABLE public.tenant_history (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    tenant_profile_id bigint NOT NULL,
    start_date date NOT NULL,
    end_date date,
    is_current_tenant boolean NOT NULL,
    suite_stayed bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by varchar(255),
    updated_by varchar(255),
    CONSTRAINT fk_tenant_history_tenant_profile FOREIGN KEY (tenant_profile_id) REFERENCES tenant_profile(id)
);
