package ru.levin.tm.command.user;

import ru.levin.tm.api.IUserHandlerServiceLocator;
import ru.levin.tm.api.service.IUserService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.User;

public final class UserChangePasswordCommand extends AbstractCommand {
    private final IUserService userService;
    private final IUserHandlerServiceLocator bootstrap;

    public UserChangePasswordCommand(final IUserHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.userService = bootstrap.getUserService();
        this.name = "change-password";
        this.description = "Change password";
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

    @Override
    public void execute() {
        if (bootstrap.getCurrentUser() == null) {
            return;
        }

        System.out.println(PASSWORD_PROMPT);
        final String passwordOne = scanner.nextLine();
        System.out.println(PASSWORD_AGAIN_PROMPT);
        final String passwordTwo = scanner.nextLine();

        if (!passwordOne.equals(passwordTwo)) {
            System.out.println("You have entered different passwords. Abort.");
            return;
        }

        final User user = userService.setNewPassword(bootstrap.getCurrentUser(), passwordOne);

        try {
            userService.update(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Your password has changed successfully");
    }
}
