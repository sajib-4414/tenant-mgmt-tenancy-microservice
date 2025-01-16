create table metadata(
    table_name varchar(100),
    key varchar(100),
    value varchar(200)
);

CREATE INDEX idx_table_name ON metadata (table_name);
