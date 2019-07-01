package ru.levin.tm.command.project;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;

public final class ProjectRemoveAllCommand extends AbstractCommand {
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";
    private final ProjectService projectService;
    private final TaskService taskService;
    private final IUserHandlerServiceLocator bootstrap;

    public ProjectRemoveAllCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "project-clear";
        this.description = "Remove all projects";
        this.projectService = bootstrap.getProjectService();
        this.taskService = bootstrap.getTaskService();
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

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        projectService.removeByUserId(bootstrap.getUserService().getCurrentUser().getId());

        taskService.findAll().forEach(task -> {
            if (task.getProjectId() != null) {
                taskService.remove(task);
            }
        });

        selectedProject = null;
        System.out.println(ALL_PROJECTS_REMOVED_MESSAGE);
    }
}
