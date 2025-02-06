## Adding spring scheduler
fixedRate: Executes at a Fixed Interval
Runs the task at a fixed interval, regardless of the previous execution's completion.
If a task takes longer than the interval, executions can overlap.
Ideal when you want to ensure the task runs every X milliseconds, even if the previous one is still running.

fixedDelay: Executes After Completion
Waits for the previous execution to complete before waiting the delay period.
Prevents overlapping executions.
Ensures a gap between task executions.

initialDelay: Delays First Execution
Both fixedRate and fixedDelay support initialDelay, which adds a delay before the first execution.

TaskScheduler Interface (More Flexible, Supports Future Execution)
```java
public void scheduleTask() {
        taskScheduler.schedule(() -> System.out.println("Task running at: " + new Date()), 
            new Date(System.currentTimeMillis() + 5000)); // Runs 5 sec later
    }
```
* I think I will experiement running with fixed rate.
* I will experiment with provided time.
* then i will experiement with scheduled cron