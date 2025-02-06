package com.batchproject.rentservice.models;

import lombok.Data;

import java.util.List;

@Data
public class BulkIdPayload {
    List<Long> ids;
}
