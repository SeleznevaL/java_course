package ru.selezneva.L21_SpringBoot_adv.atm.dto;

import lombok.Data;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;


@Data
public class CashPair {
    private final Nominal nominal;
    private final int count;
}
