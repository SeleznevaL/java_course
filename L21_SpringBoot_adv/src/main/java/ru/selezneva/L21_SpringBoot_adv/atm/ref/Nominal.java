package ru.selezneva.L21_SpringBoot_adv.atm.ref;

public enum Nominal {
    ONE_HUND(100)
    ,TWO_HUND(200)
    ,FIVE_HUND(500)
    ,ONE_THOUS(1000)
    ,TWO_THOUS(2000)
    ,FIVE_THOUS(5000)
    ;

    private int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Nominal getByValue(int value){
        for (Nominal nominal: values()){
            if (nominal.value == value) return nominal;
        }
        return null;
    }
}
