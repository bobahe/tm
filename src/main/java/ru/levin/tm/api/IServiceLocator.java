package ru.levin.tm.api;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;

public interface IServiceLocator {
    @NotNull IProjectService getProjectService();
    @NotNull ITaskService getTaskService();
    @NotNull IUserService getUserService();
    @NotNull ITerminalService getTerminalService();
}
