package ru.levin.tm.command.task;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Task;
import ru.levin.tm.util.CommandUtil;

import java.text.ParseException;
import java.util.Date;

public final class TaskChangeSelectedCommand extends AbstractCommand {
    protected static final String NAME_PROMPT = "ENTER NAME:";
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final ITaskService taskService;
    private final ITerminalService terminalService;

    public TaskChangeSelectedCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.taskService = bootstrap.getTaskService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "task-change";
    }

    @Override
    public String getTitle() {
        return "[CHANGE TASK]";
    }

    @Override
    public String getDescription() {
        return "Change selected task";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedTask == null) return;

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        selectedTask.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        selectedTask.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        selectedTask.setStartDate(parseDate(true));
        terminalService.println(END_DATE_PROMPT);
        selectedTask.setEndDate(parseDate(false));

        try {
            taskService.update(selectedTask);
        } catch (Exception e) {
            terminalService.println(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }

    private Date parseDate(final boolean isStartDate) {
        final String date = terminalService.getLine();

        if ("".equals(date)) {
            return isStartDate ? selectedTask.getStartDate() : selectedTask.getEndDate();
        }

        try {
            return CommandUtil.DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            terminalService.println(ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
