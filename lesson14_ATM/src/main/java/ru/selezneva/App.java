package ru.selezneva;

import ru.selezneva.atm.ATM;
import ru.selezneva.atm.exceptions.ATMException;
import ru.selezneva.atm.impl.ATMImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ATM atm = new ATMImpl();

        try {
            atm.get( 100 );
        }catch ( ATMException e ){
            e.printStackTrace();
        }catch ( Exception e ){

        }
    }
}
