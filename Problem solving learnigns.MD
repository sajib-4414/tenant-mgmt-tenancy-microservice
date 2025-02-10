## Job scheduling
### spring scheduler, using annotation
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

### TaskScheduler Interface (More Flexible, Supports Future Execution)
```java
public void scheduleTask() {
        taskScheduler.schedule(() -> System.out.println("Task running at: " + new Date()), 
            new Date(System.currentTimeMillis() + 5000)); // Runs 5 sec later
    }
```

we can schedule job by 3 ways
* Using Trigger: `ScheduledFuture<?> schedule(Runnable, Trigger)`

```java
// Using CronTrigger
taskScheduler.schedule(
() -> System.out.println("Task running"),
new CronTrigger("0 0 * * * *")  // Run every hour
);

// Using PeriodicTrigger
        taskScheduler.schedule(
    () -> System.out.println("Task running"), 
    new PeriodicTrigger(Duration.ofHours(1))  // Run every hour
        );
```

* Running instant: `ScheduledFuture<?> schedule(Runnable, Instant)`

```java

// Run task at specific instant in time
Instant executeAt = Instant.now().plusSeconds(60);
taskScheduler.schedule(
    () -> System.out.println("Task running"), 
    executeAt  // Run after 60 seconds
);
```

* Run at a date: `ScheduledFuture<?> schedule(Runnable, Date)`

```java
// Run task at specific date
Date executeAt = new Date(System.currentTimeMillis() + 60000);
taskScheduler.schedule(
    () -> System.out.println("Task running"), 
    executeAt  // Run after 60 seconds
);
```