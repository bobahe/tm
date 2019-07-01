package ru.levin.tm.command.task;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.command.AbstractCommand;

public final class TaskSelectCommand extends AbstractCommand {
    private static final String SELECTED_TASK_MESSAGE = "SELECTED TASK: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    private final ITaskService taskService;
    private final IUserHandlerServiceLocator bootstrap;

    public TaskSelectCommand(final IUserHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "task-select";
        this.title = "[TASK SELECT]";
        this.description = "Select task";
        this.taskService = bootstrap.getTaskService();
        this.bootstrap = bootstrap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void execute() {
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            final int index = Integer.parseInt(scanner.nextLine());
            selectedTask = taskService.findAll().stream()
                    .filter(task -> task.getUserId().equals(bootstrap.getCurrentUser().getId()))
                    .collect(Collectors.toList())
                    .get(index - 1);
            System.out.println(SELECTED_TASK_MESSAGE + selectedTask);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
