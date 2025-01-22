--
-- Name: tenant_profile; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tenant_profile (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    phone_no character varying(50),
    notes text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by character varying(255),
    updated_by character varying(255),
    keycloak_user_id character varying(255)
);


--
-- Name: tenant_profile_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tenant_profile_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tenant_profile_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tenant_profile_id_seq OWNED BY public.tenant_profile.id;
