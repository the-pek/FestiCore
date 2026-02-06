package org.festicore.festicore;
import org.festicore.festicore.Enumeration.PassType;
import org.festicore.festicore.service.LogManager;

import java.time.LocalDate;

public class Pass extends Reservation{
    private PassType passType;
    private static final java.util.logging.Logger logger = LogManager.getLogger();
    public Pass(String id, float price, LocalDate validityDate, int quota, PassType passType) {
        super(id, price, validityDate, quota);
        this.passType = passType;
        logger.info("Pass created: " + id + " type=" + passType);
    }

    public Pass() {

    }

    public PassType getPassType() {
        return passType;
    }
    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    @Override
    public double  calculatePrice() {
        logger.fine("Calculating pass price for: " + getId());
        return getPrice();
    }
    @Override
    public Reservation copyForHistory() {
        return new Pass(getId(), getPrice(), getValidityDate(), 1, getPassType());
    }


}
