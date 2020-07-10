package com.bridgelabz.cabinvoicegenerator.service;

import com.bridgelabz.cabinvoicegenerator.exception.InvoiceGeneratorException;
import com.bridgelabz.cabinvoicegenerator.utility.InvoiceSummary;
import com.bridgelabz.cabinvoicegenerator.utility.Ride;
import com.bridgelabz.cabinvoicegenerator.utility.RideRepository;

public class InvoiceGenerator
{
    private static double COST_PER_KM;
    private static int COST_PER_MINUTE;
    private static double MINIMUM_FARE;

    RideRepository rideRepository;
    public InvoiceGenerator()
    {
       rideRepository = new RideRepository();
    }

    public InvoiceSummary calculateFare(Ride... rides)
    {
        double totalFare = 0;
        for (Ride ride : rides)
        {
            checkRideType(ride.rideType);
            totalFare += ride.distance * COST_PER_KM + ride.time * COST_PER_MINUTE;
        }
        if (totalFare < MINIMUM_FARE)
            return new InvoiceSummary(rides.length, MINIMUM_FARE);
        return new InvoiceSummary(rides.length, totalFare);
    }

    private void checkRideType(String rideType)
    {
        switch (rideType)
        {
            case "PREMIUM":
                COST_PER_KM = 15;
                COST_PER_MINUTE = 2;
                MINIMUM_FARE = 20;
                break;
            case "NORMAL":
                COST_PER_KM = 10;
                COST_PER_MINUTE = 1;
                MINIMUM_FARE = 5;
                break;
        }
    }

    public InvoiceSummary invoiceForUser(String userId)
    {
        return calculateFare(rideRepository.getRidesForUser(userId));
    }

    public void addRideToRepository(String[] userId, Ride[][] rides) throws InvoiceGeneratorException
    {
        for (int i = 0; i < userId.length; i++)
        {
            rideRepository.addRideForUser(userId[i], rides[i]);
        }
    }
}