package org.jim.dynproxy;

public class Audi implements Car {

    public void drive(String driverName, String carName) {
        System.err.println("Audi is driving... " + "driverName: " + driverName + ", carName" + carName);
    }

}
