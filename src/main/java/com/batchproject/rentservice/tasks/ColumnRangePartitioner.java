package com.batchproject.rentservice.tasks;


import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.core.partition.support.Partitioner;

import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {// grid size= Number of partitions
        int min = 1;
        int max = 50_000;//adjust based on your data file
// size or read file dynamically to put real number

        int targetSize = ((max-min) / gridSize) + 1 ; //targetsize 50,000/8=6250
        System.out.println("targetSize: " + targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;
        int start = min;
        int end = start + targetSize - 1;
        while(start<=max){
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);
            if(end >= max){
                end = max;
            }
            value.putInt("minValue",start);//name of your choice
            value.putInt("maxValue",end);
            start = start + targetSize;
            end = end + targetSize;
            number++;
        }
        return result;
    }
}
