package ru.levin.tm.command.user;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class UserShowProfileCommand extends AbstractCommand {
    private final ITerminalService terminalService;

    public UserShowProfileCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "show-profile";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Shows user profile";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) {
            return;
        }

        terminalService.println("Id: " + bootstrap.getUserService().getCurrentUser().getId());
        terminalService.println("Login: " + bootstrap.getUserService().getCurrentUser().getLogin());
        terminalService.println("Password: " + bootstrap.getUserService().getCurrentUser().getPassword());
        terminalService.println("Role: " + bootstrap.getUserService().getCurrentUser().getRoleType().name());
    }
}
