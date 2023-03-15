package model;

import java.math.BigInteger;

public class Client implements Comparable<Client>
{
    private final String id;
    private final String name;
    private final String surname;
    private final BigInteger pesel;
    private final String city;

    public Client(String id, String name, String surname, BigInteger pesel, String city)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.city = city;
    }


    @Override
    public int compareTo(Client o)
    {
        return this.id.compareTo(o.id);
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public BigInteger getPesel()
    {
        return pesel;
    }

    public String getYearOfBirth()
    {
        return pesel.toString().substring(0,2);
    }

    public String getCity()
    {
        return city;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Client client = (Client) o;

        return pesel != null ? pesel.equals(client.pesel) : client.pesel == null;
    }

    @Override
    public int hashCode()
    {
        return pesel != null ? pesel.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "Client{" +
                "id='" + id +
                '}';
    }
}
