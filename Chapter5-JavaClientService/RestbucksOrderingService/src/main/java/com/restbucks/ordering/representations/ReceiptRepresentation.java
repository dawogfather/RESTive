package com.restbucks.ordering.representations;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.restbucks.ordering.domain.Payment;

@XmlRootElement(name = "receipt", namespace = Representation.RESTBUCKS_NAMESPACE)
public class ReceiptRepresentation extends Representation {

    @XmlElement(name = "amount", namespace = Representation.RESTBUCKS_NAMESPACE)
    private double amountPaid;
    @XmlElement(name = "paid", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String paymentDate;
    
    ReceiptRepresentation(){} // For JAXB :-(
    
    public ReceiptRepresentation(Payment payment, Link orderLink) {
        this.amountPaid = payment.getAmount();
        this.paymentDate = payment.getPaymentDate().toString();
        this.links = new ArrayList<Link>();
        links.add(orderLink);
    }

    public DateTime getPaidDate() {
        return new DateTime(paymentDate);
    }
    
    public double getAmountPaid() {
        return amountPaid;
    }

    public Link getOrderLink() {
        return getLinkByName(Representation.RELATIONS_URI + "order");
    }
    
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(ReceiptRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
