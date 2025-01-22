--
-- Name: rent; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rent (
    id bigint NOT NULL,
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


-- --
-- -- Name: rent_id_seq; Type: SEQUENCE; Schema: public; Owner: -
-- --

CREATE SEQUENCE public.rent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



-- Name: rent_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -


ALTER SEQUENCE public.rent_id_seq OWNED BY public.rent.id;