CREATE TABLE public.tenant_profile (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    email varchar(255) NOT NULL,
    phone_no varchar(50),
    notes text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by character varying(255),
    updated_by character varying(255),
    keycloak_user_id character varying(255)
);
