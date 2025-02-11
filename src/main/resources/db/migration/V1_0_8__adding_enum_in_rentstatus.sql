-- Ensure that the column only accepts valid job types
ALTER TABLE public.rent
ADD CONSTRAINT rent_status_enum CHECK (status IN ('DUE',  'OVERDUE', 'PAID'));
