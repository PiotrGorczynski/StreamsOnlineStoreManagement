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


}
