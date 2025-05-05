package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.helpers.Database;
import itmo.semyonh.lab7.types.StudyGroup;

import java.sql.SQLException;
import java.util.LinkedHashSet;

public class RegisterAccountCommand implements Command {
    private String login;
    private String password;

    @Override
    public void prepare() {
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        if (login == null || password == null) {
            return new CommandResult(CommandResultType.Failed, "login or password is null!");
        }
        try {
            var res = Database.getInstance().registerAccount(login, password);
            if (!res) {
                return new CommandResult(CommandResultType.Failed, "Register method return false value");
            }
            return new CommandResult(CommandResultType.None, null);
        } catch (SQLException e) {
            return new CommandResult(CommandResultType.Failed, "Could not create user in the DB");
        }
    }

    @Override
    public Command with(String[] args) {
        RegisterAccountCommand n = new RegisterAccountCommand();

        n.login = args[0];
        n.password = args[1];

        return n;
    }

    @Override
    public String getName() {
        return "register-account";
    }

    @Override
    public String getDescription() {
        return "Registers an account on the server for an access to the collection";
    }
}
