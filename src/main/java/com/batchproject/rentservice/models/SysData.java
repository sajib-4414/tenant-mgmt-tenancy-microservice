package com.batchproject.rentservice.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

//for keeping things like
/*
wash price, dry price
has basement
 */
@Table(name = "sysdata", indexes = @Index(name = "sys_data_index", columnList = "table_name"))
@Entity
public class SysData extends BaseEntity{
    @Column(name = "table_name")
    String tableName;

    @Column(name = "reference_id")
    Long referenceId;

    @Column(name = "t_key")
    String key;

    @Column(name = "t_value")
    String value;
}
