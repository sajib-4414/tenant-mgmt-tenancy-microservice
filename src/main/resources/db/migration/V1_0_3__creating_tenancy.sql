--
-- Name: tenancy; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tenancy (
    id bigint NOT NULL,
    suite_id bigint NOT NULL,
    tenant_profile_id bigint NOT NULL,
    start_date date NOT NULL,
    end_date date,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by character varying(50),
    updated_by character varying(50),
    rent_paid double precision,
    notes character varying(300)
);


--
-- Name: tenancy_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tenancy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tenancy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tenancy_id_seq OWNED BY public.tenancy.id;