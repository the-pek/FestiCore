package org.esiea;
import org.esiea.Enumeration.PassType;

import java.time.LocalDate;

public class Pass extends Reservation{
    private PassType passType;
    public Pass(String id, float price, LocalDate validityDate, int quota, PassType passType) {
        super(id, price, validityDate, quota);
        this.passType = passType;
    }

    public PassType getPassType() {
        return passType;
    }

    @Override
    public double  calculatePrice() {
        return getPrice();
    }
}
