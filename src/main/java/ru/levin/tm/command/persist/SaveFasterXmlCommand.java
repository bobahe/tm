package ru.levin.tm.command.persist;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.dto.Domain;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.NoCurrentUserException;
import ru.levin.tm.exception.SerializeException;

import java.io.File;

public final class SaveFasterXmlCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public SaveFasterXmlCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "save-fxml-xml";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Marshal data into xml via FasterXML";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @Nullable final User user = bootstrap.getUserService().getCurrentUser();
        if (user == null) throw new NoCurrentUserException();
        @NotNull final String fileName = user.getLogin() + "fxmldata.xml";
        @NotNull final Domain domain = new Domain();
        domain.initFromInternalStorage(bootstrap);

        try {
            @NotNull final XmlMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(fileName), domain);
        } catch (Exception e) {
            throw new SerializeException();
        }

        terminalService.println("Saved FasterXML XML successfully to " + fileName);
    }

}
