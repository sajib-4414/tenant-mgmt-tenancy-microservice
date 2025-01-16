package com.batchproject.jobs.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

//for keeping things like
/*
wash price, dry price
has basement
 */
@Table(name = "sysdata", indexes = @Index(columnList = "table_name"))
@Entity
public class SysData extends BaseEntity{
    @Column(name = "table_name")
    String tableName;

    @Column(name = "reference_id")
    Long referenceId;

    @Column(name = "key")
    String key;

    @Column(name = "value")
    String value;
}
