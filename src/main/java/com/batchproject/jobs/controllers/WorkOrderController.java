package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.maintenance.WorkOrder;
import com.batchproject.jobs.models.maintenance.WorkOrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<WorkOrder>>> getAllWorkOrders() {
        return workOrderService.getAllWorkOrders()
                .thenApply(ResponseEntity::ok);
    }
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<WorkOrder>> getWorkOrderDetail(@PathVariable Long id) {
        return workOrderService.getWorkOrderDetail(id)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<WorkOrderDTO>> updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrderDTO updatedDto) {
        return workOrderService.updateWorkOrder(id, updatedDto)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteWorkOrder(@PathVariable Long id) {
        return workOrderService.deleteWorkOrder(id)
                .thenApply(unused -> ResponseEntity.noContent().<Void>build());
    }


}
