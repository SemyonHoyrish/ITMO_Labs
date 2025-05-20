package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.types.FormOfEducation;
import itmo.semyonh.lab8.types.StudyGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * PrintFieldDescFormOfEducationCommand (print_field_descending_form_of_education)
 * prints formOfEducation of all collection entries in descending order.
 */
public class PrintFieldDescFormOfEducationCommand implements Command {
    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        List<FormOfEducation> l = new ArrayList<FormOfEducation>();

        for (StudyGroup studyGroup : collection) {
            l.add(studyGroup.getFormOfEducation());
        }

        l.sort((a, b) -> -a.compareTo(b));

        StringBuilder result = new StringBuilder();
        for (var f : l) {
            result.append(f);
            result.append("\n");
        }

        return new CommandResult(CommandResultType.PlainText, result.toString());
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "print_field_descending_form_of_education";
    }

    @Override
    public String getDescription() {
        return "print_field_descending_form_of_education : вывести значения поля formOfEducation всех элементов в порядке убывания";
    }
}
