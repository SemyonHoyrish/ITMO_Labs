package commands;

import types.FormOfEducation;
import types.StudyGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class PrintFieldDescFormOfEducationCommand implements Command {
    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        List< FormOfEducation> l = new ArrayList<FormOfEducation>();

        for (StudyGroup studyGroup : collection) {
            l.add(studyGroup.getFormOfEducation());
        }

        l.sort((a, b) -> -a.compareTo(b));

        for (var f : l) {
            System.out.println(f);
        }
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "print_field_descending_form_of_education";
    }
}
