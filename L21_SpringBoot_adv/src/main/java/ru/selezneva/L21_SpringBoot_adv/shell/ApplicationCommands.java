package ru.selezneva.L21_SpringBoot_adv.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.selezneva.L21_SpringBoot_adv.ATMStart;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {
    private final ATMStart atmStart;

    @ShellMethod(value = "Start ATM", key = {"start"})
    public void start(@ShellOption(defaultValue = "") Integer id) {
        atmStart.start(id);
    }
}
