public class Product implements Comparable<Product> {
    private final String id;
    private final String name;
    private final CategoryOfProduct category;
    private final Money price;

    public Product(String id, String name, CategoryOfProduct category, Money price)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public CategoryOfProduct getCategoryOfProduct()
    {
        return category;
    }

    public Money getPrice()
    {
        return price;
    }

    @Override
    public int compareTo(final Product o) {
        int thisNumber = Integer.parseInt(id.substring(7));
        int otherNumber = Integer.parseInt(o.id.substring(7));
        return thisNumber - otherNumber;
    }
}
