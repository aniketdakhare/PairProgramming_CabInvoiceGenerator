package com.bridgelabz.cabinvoicegenerator.service;

import com.bridgelabz.cabinvoicegenerator.exception.InvoiceGeneratorException;
import com.bridgelabz.cabinvoicegenerator.model.InvoiceSummary;
import com.bridgelabz.cabinvoicegenerator.model.Ride;
import com.bridgelabz.cabinvoicegenerator.utility.RideRepository;

import java.util.stream.IntStream;

public class InvoiceGenerator
{

    RideRepository rideRepository;

    public InvoiceGenerator()
    {
       rideRepository = new RideRepository();
    }

    /**
     * METHOD TO CALCULATE TOTAL FARE
     * @param rides provides ride details
     * @return Invoice summary
     */
    public InvoiceSummary calculateFare(Ride... rides)
    {
        double rideFare;
        double totalFare = 0;

        for (Ride ride : rides)
        {
            rideFare = ride.distance * ride.rideType.COST_PER_KM + ride.time * ride.rideType.COST_PER_MINUTE;
            if (rideFare < ride.rideType.MINIMUM_FARE)
                rideFare = ride.rideType.MINIMUM_FARE;
            totalFare += rideFare;
        }
        return new InvoiceSummary(rides.length, totalFare);
    }

    /**
     * METHOD TO GENERATE INVOICE FOR USER
     * @param userId provides user id to get particular invoice
     * @return Invoice summary
     */
    public InvoiceSummary invoiceForUser(String userId)
    {
        return calculateFare(rideRepository.getRidesForUser(userId));
    }

    /**
     * METHOD TO ADD RIDE DETAILS ACCORDING TO USER IN LIST
     * @param userId provides user id to add particular user ride details
     * @param rides provides total rides
     */
    public void addRideToRepository(String[] userId, Ride[][] rides)
    {
        IntStream.range(0, userId.length).forEach(i -> {
            try
            {
                rideRepository.addRideForUser(userId[i], rides[i]);
            }
            catch (InvoiceGeneratorException e)
            {
                e.printStackTrace();
            } });
    }
}