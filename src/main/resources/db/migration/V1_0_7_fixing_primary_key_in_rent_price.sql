-- 1. Add a new 'id' column with BIGSERIAL (it automatically creates a sequence)
ALTER TABLE public.rent_price ADD COLUMN new_id BIGSERIAL PRIMARY KEY;

-- 2. Drop the old 'id' column if necessary (be cautious! Make sure you don't have any data loss)
-- Only drop the old 'id' column if you don't need it anymore
ALTER TABLE public.rent_price DROP COLUMN id;

-- 3. Rename the new column to 'id' (if it wasn't named 'id' initially)
ALTER TABLE public.rent_price RENAME COLUMN new_id TO id;

-- 4. If you need to link this to the existing sequence (if necessary):
-- ALTER SEQUENCE public.rent_price_id_seq OWNED BY public.rent_price.id;
-- ALTER TABLE ONLY public.rent_price ALTER COLUMN id SET DEFAULT nextval('public.rent_price_id_seq'::regclass);
