package com.batchproject.rentservice.tasks;

import com.batchproject.rentservice.models.rent.Rent;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class RentProcessor implements ItemProcessor<Rent,Rent> {
    @Override
    public Rent process(Rent rent) throws Exception {
        rent.setId(null);
        return rent;
    }
}
