# Machine Problem: Task Scheduler

## Requirements
1. We need to calculate calendar schedules for project plans
1. Each project plan consists of tasks. Every task has a certain duration
1. A task can depend on zero or more other tasks. If a task depends on some other tasks, it can only be started after these tasks are completed
1. So, for a set of tasks (with durations and dependencies), the solution for the challenge should generate a schedule, i.e. assign Start and End Dates for every task
1. It is ok to have a console app

## Assumptions
1. The program does not store data in a database
1. The program assumes only one project plan
1. Tasks cannot be scheduled in parallel or overlap
1. The program / calculator does not consider non-working days such as weekends or holidays
1. Duration field is in terms of number of days

## Getting the Code and Running the Application
### Pre-requiste
1. JDK 8
1. Maven 3

### Clone repository
```unix
$ git clone https://github.com/albertdy/machineproblems.git
```

### Installing the Application
```unix
$ mvn install
```

### Running the application
```unix
$ java -jar TaskScheduler
```
#### Menu Selection
```unix
        1 - Add new task
        2 - Update existing task
        3 - List all tasks
        4 - Calculate task schedules
        5 - Quit
Enter selected option: _
```
#### 1 - Adding a new task
```unix
Enter task name: Task-1
Enter task duration: 1
Enter task dependencies (comma separated task id):
Create another task? (Y/N) _
```
#### 2 - Updating a task
```unix
Enter task ID to update: 1
Update task name [Task-1]:
Update task duration [1]:
Update task dependencies []: 4,5
Update another task? (Y/N) _
```
#### 3 - List all tasks
```unix
 ID      Name     Dur.    Start           End             Dependencies
 1       Task-1   1
 2       Task-2   2                                       4,5
 3       Task-3   3
 4       Task-4   4
 5       Tas-k5   5                                       1,3,4
```
#### 4 - Calculate Task Schedules
```unix
Enter start date [MM/DD/YYYY]: 06/04/2018
 ID      Name     Dur.    Start           End             Dependencies
 1       Task-1   1       06/04/2018      06/05/2018
 3       Task-3   3       06/06/2018      06/09/2018
 4       Task-4   4       06/10/2018      06/14/2018
 5       Task-5   5       06/15/2018      06/20/2018      1,3,4
 2       Task-2   2       06/21/2018      06/23/2018      4,5
```