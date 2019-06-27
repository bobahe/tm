package ru.levin.tm.command;

import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Task;

import java.util.List;
import java.util.Scanner;

public class TaskListCommand extends Command {
    private TaskService taskService = TaskService.getInstance();

    public TaskListCommand(Scanner scanner) {
        super(scanner);
        this.name = "task-list";
        this.title = "[TASK LIST]";
        this.description = "Show all tasks";
    }

    @Override
    public void run() {
        List<Task> taskList = taskService.getAll();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            System.out.println((i + 1) + ". " + task.getName());
            System.out.println("\tDescription: " + task.getDescription());
            if (task.getStartDate() != null) {
                System.out.println("\tStart date: " + DATE_FORMAT.format(task.getStartDate()));
            } else {
                System.out.println("\tStar date: not set");
            }
            if (task.getEndDate() != null) {
                System.out.println("\tEnd date: " + DATE_FORMAT.format(task.getEndDate()));
            } else {
                System.out.println("\tEnd date: not set");
            }

            System.out.println("\tProject: " + task.getProjectId());
        }
    }
}
