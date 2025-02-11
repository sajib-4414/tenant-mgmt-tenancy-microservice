CREATE TABLE public.sysdata (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    table_name varchar(100),
    t_key varchar(100),
    t_value varchar(200),
    reference_id bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by varchar(255),
    updated_by varchar(255)
);
CREATE INDEX sys_data_index ON public.sysdata (table_name);

