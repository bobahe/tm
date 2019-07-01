package ru.levin.tm.command.project;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.command.AbstractCommand;

public final class ProjectSelectCommand extends AbstractCommand {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED PROJECT: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";
    protected static final String ERROR_MESSAGE = "[ERROR]\n";
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";

    private final ProjectService projectService;
    private final IUserHandlerServiceLocator bootstrap;

    public ProjectSelectCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "project-select";
        this.title = "[PROJECT SELECT]";
        this.description = "Select project";
        this.projectService = bootstrap.getProjectService();
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

    public void execute() {
        System.out.println(this.title);
        System.out.println(SERIAL_NUMBER_PROMPT);
        try {
            final int index = Integer.parseInt(scanner.nextLine());
            selectedProject = projectService.findAll().stream()
                    .filter(project -> project.getUserId().equals(bootstrap.getCurrentUser().getId()))
                    .collect(Collectors.toList())
                    .get(index - 1);
            System.out.println(SELECTED_PROJECT_MESSAGE + selectedProject);
        } catch (NumberFormatException nfe) {
            System.out.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(NO_SUCH_ITEM);
        }
    }
}
