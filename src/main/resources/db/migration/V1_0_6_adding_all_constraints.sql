ALTER TABLE ONLY public.rent ALTER COLUMN id SET DEFAULT nextval('public.rent_id_seq'::regclass);

--
-- Name: rent_price id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rent_price ALTER COLUMN id SET DEFAULT nextval('public.rent_price_id_seq'::regclass);

--
-- Name: sysdata id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sysdata ALTER COLUMN id SET DEFAULT nextval('public.metadata_id_seq'::regclass);
--
-- Name: tenancy id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenancy ALTER COLUMN id SET DEFAULT nextval('public.tenancy_id_seq'::regclass);
--
-- Name: tenant_history id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenant_history ALTER COLUMN id SET DEFAULT nextval('public.tenant_history_id_seq'::regclass);


--
-- Name: tenant_profile id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenant_profile ALTER COLUMN id SET DEFAULT nextval('public.tenant_profile_id_seq'::regclass);
--
-- Name: sysdata metadata_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.sysdata
    ADD CONSTRAINT metadata_pkey PRIMARY KEY (id);

--
-- Name: rent rent_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rent
    ADD CONSTRAINT rent_pkey PRIMARY KEY (id);
--
-- Name: rent_price rent_price_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rent_price
    ADD CONSTRAINT rent_price_pkey PRIMARY KEY (id);

--
-- Name: tenancy tenancy_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenancy
    ADD CONSTRAINT tenancy_pkey PRIMARY KEY (id);

--
-- Name: tenant_history tenant_history_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenant_history
    ADD CONSTRAINT tenant_history_pkey PRIMARY KEY (id);


--
-- Name: tenant_profile tenant_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenant_profile
    ADD CONSTRAINT tenant_profile_pkey PRIMARY KEY (id);


--
-- Name: idx_keycloak_user_id_tenant_profile; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_keycloak_user_id_tenant_profile ON public.tenant_profile USING btree (keycloak_user_id);

--
-- Name: idx_table_name; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_table_name ON public.sysdata USING btree (table_name);
--
-- Name: tenant_history fk_tenant_profile; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenant_history
    ADD CONSTRAINT fk_tenant_profile FOREIGN KEY (tenant_profile_id) REFERENCES public.tenant_profile(id);

--
-- Name: tenancy fk_tenant_profile; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tenancy
    ADD CONSTRAINT fk_tenant_profile FOREIGN KEY (tenant_profile_id) REFERENCES public.tenant_profile(id);

