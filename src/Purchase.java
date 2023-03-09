import java.time.LocalDate;

public class Purchase
{
    private Client buyer;
    private Product product;
    private long quantity;
    private Delivery delivery;
    private Payment payment;
    private LocalDate when;
    private Status status = Status.PAID;

    public Purchase(Client buyer, Product product, long quantity, Delivery delivery, Payment payment, LocalDate when)
    {
        this.buyer = buyer;
        this.product = product;
        this.quantity = quantity;
        this.delivery = delivery;
        this.payment = payment;
        this.when = when;
        this.status = status;
    }

    public enum Delivery
    {
        IN_POST,
        UPS,
        DHL
    }


    public enum Payment
    {
        CASH,
        BLIK,
        CREDIT_CARD
    }

    public enum Status
    {
        PAID,
        SENT,
        DONE
    }

    @Override
    public String toString()
    {
        return "Purchase{" +
                "buyer=" + buyer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", delivery=" + delivery +
                ", payment=" + payment +
                ", when=" + when +
                ", status=" + status +
                '}';
    }
}
