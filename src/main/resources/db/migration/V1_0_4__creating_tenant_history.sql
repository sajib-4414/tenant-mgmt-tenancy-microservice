--
-- Name: tenant_history; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tenant_history (
    id bigint NOT NULL,
    tenant_profile_id bigint NOT NULL,
    start_date date NOT NULL,
    end_date date,
    is_current_tenant boolean NOT NULL,
    suite_stayed bigint,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by character varying(255),
    updated_by character varying(255)
);


--
-- Name: tenant_history_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tenant_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tenant_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tenant_history_id_seq OWNED BY public.tenant_history.id;
