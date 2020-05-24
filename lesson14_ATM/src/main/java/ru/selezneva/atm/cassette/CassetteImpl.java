package ru.selezneva.atm.cassette;

import lombok.Getter;
import ru.selezneva.atm.exceptions.IncorectValue;
import ru.selezneva.atm.exceptions.NoAvailableRequestCount;
import ru.selezneva.atm.ref.Nominal;

@Getter
public class CassetteImpl implements Cassette {
    private final Nominal nominal;
    private Integer count;
    private final Integer capacity = 1000;

    public CassetteImpl( Nominal nominal ) {
        this.nominal = nominal;
        this.count = 0;
    }

    @Override
    public void add( int count ) {
        if ( count < 0 ) throw new IncorectValue();
        this.count += count;
    }

    @Override
    public void extract( int count ) {
        if ( count < 0 ) throw new IncorectValue();
        this.count -= count;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
