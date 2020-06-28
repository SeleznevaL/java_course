package ru.selezneva.L21_SpringBoot_adv.atm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import ru.selezneva.L21_SpringBoot_adv.atm.ref.Nominal;

import javax.annotation.Generated;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Data
@Accessors(chain = true)
@Table(name = "cassette")
public class Cassette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    Nominal nominal;

    @Column(name = "cassette_count")
    Integer cassetteCount = 0;

    @Column
    Integer capacity = 1000;

    @Column(name = "atm_id")
    Integer atmId;
}
