package ru.selezneva.atm.dto;


import lombok.Data;
import ru.selezneva.atm.ref.Nominal;

@Data
public class CashPair {
    private final Nominal nominal;
    private final int count;
}
