--
-- Name: rent_price; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rent_price (
    id bigint NOT NULL,
    suite_id bigint,
    effective_start_date date,
    effective_end_date date,
    rent_amount double precision,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    created_by character varying(50),
    updated_by character varying(50)
);


--
-- Name: rent_price_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.rent_price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: rent_price_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.rent_price_id_seq OWNED BY public.rent_price.id;