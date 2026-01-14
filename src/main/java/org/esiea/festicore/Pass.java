package org.esiea.festicore;
import org.esiea.festicore.Enumeration.PassType;

import java.time.LocalDate;

public class Pass extends Reservation{
    private PassType passType;
    public Pass(String id, float price, LocalDate validityDate, int quota, PassType passType) {
        super(getIdType(passType), price, validityDate, quota);
        this.passType = passType;
    }

    public static String getIdType(PassType type) {
        switch (type) {
            case classic:
                return "3";
            case VIP:
                return "4";
            default:
                break;
        }
        return null;
    } 

    public PassType getPassType() {
        return passType;
    }

    @Override
    public double  calculatePrice() {
        return getPrice();
    }
}
