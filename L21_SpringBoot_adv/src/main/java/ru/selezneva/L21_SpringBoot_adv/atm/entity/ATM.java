package ru.selezneva.L21_SpringBoot_adv.atm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.selezneva.L21_SpringBoot_adv.atm.dto.CashPair;
import ru.selezneva.L21_SpringBoot_adv.atm.exceptions.IncorectValue;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@Table(name = "atm")
@Accessors(chain = true)
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(targetEntity = Cassette.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "atm_id")
    private List<Cassette> cassettes;

}
