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

### How I did spring batch optimizations
50k record processing. 
* for 50k rent record processing, with chunk size 50, simple thread executor with concurrency limit=2, i was getting 6s 771ms.
* For optimizing I increased the concurrency levl of simple task exectuor to 10, with chunk =50, time taken was 5s 833ms.  even with concurrency=20, chunk=50, it was 5s 400ms. so chunk size is really improtant.
* So i increased the chunk size to 100, concruccy=4, still 5s 896ms. chunk=100, concurrcy=8, still 5s 500ms
* ok then i used master salve paritioning, number of parition=8, core thread pool=8,max16, queue=20, chunk=250. i saw it was reduced to 4s 100s. increasing core pool size anymore does not help. ttied with 12, its the same
* ok then i increased the chunk size=400, parition number =corepool size = 8, max 16, queue=20. i saw dramatic improvement, 3s 787ms. I tried increasing to 800, does not increase anymore.
so overall i gained 40-50% efficiency. although ihave to check without parition manager, chunk=400, simple async core=8 do a similar result. i mean if the paralel thread configuration do any difference.

Key differences:

With custom partitioning (Approach 1):

* Each partition reads a different section of the file independently
* True parallel processing of the file
* Memory efficient as each thread handles only its chunk


With single reader + TaskExecutor (Approach 2):

* Single reader reads records sequentially
* Multiple threads process the CHUNKS after reading
* **I/O bottleneck at the reader level**



So yes, using just an ItemReader with TaskExecutor will likely be slower because:

- The file reading is still sequential
- Only the processing/writing of chunks happens in parallel
- **All threads compete for the same reader resource**