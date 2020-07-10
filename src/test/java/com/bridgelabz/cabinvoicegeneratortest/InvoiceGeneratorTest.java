package com.bridgelabz.cabinvoicegeneratortest;

import com.bridgelabz.cabinvoicegenerator.service.InvoiceGenerator;
import com.bridgelabz.cabinvoicegenerator.utility.InvoiceSummary;
import com.bridgelabz.cabinvoicegenerator.utility.Ride;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvoiceGeneratorTest
{
    InvoiceGenerator invoiceGenerator;

    @Before
    public void setUp()
    {
        invoiceGenerator = new InvoiceGenerator();
    }

    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare()
    {
        double distance = 3.0;
        int time = 7;
        InvoiceSummary invoiceSummary = invoiceGenerator.calculateFare(new Ride(distance, time));
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(1,37);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare()
    {
        double distance = 0.01;
        int time = 1;
        InvoiceSummary invoiceSummary = invoiceGenerator.calculateFare(new Ride(distance, time));
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(1,5);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenMultipleRides_ShouldReturnTotalFare()
    {
        Ride[] rides = {new Ride(3.0, 7), new Ride(0.01, 1)};
        InvoiceSummary invoiceSummary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(rides.length,38.1);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserId_ShouldReturnInvoiceSummary() {
        String userId = "id2";
        Ride[] rides = {new Ride(3.0, 7), new Ride(0.01, 1)};
        InvoiceSummary summary = invoiceGenerator.invoiceForUser(userId, rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(rides.length, 38.1);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }
}