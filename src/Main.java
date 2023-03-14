import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

public class Main
{
    public static void main(String[] args)
    {
        List<Purchase> purchaseList = DataFactory.produce();
        //1)How many clients do the shopping?
        howManyClientsDoTheShopping(purchaseList);
        //2)How many clients pay by blik?
        howManyClientsPayByBlik(purchaseList);
        //3)How many clients pay by credit card?
        howManyClientsPayByCreditCard(purchaseList);
        //4)How many clients pay by eur?
        howManyClientsBuyByEur(purchaseList);
        //5)How many unique items were bought by euro?
        howManyClientsBuyByEurUniqueItems(purchaseList);
        //6)How much PLNs does every client spend?
        howMuchMoneyDoesClientSpendInPln(purchaseList);
        //7)How much money does client spend in particular category of product?
        howMuchMoneyClientSpendInParticularCategory(purchaseList, Product.Category.GARDEN);
        statusCheck(purchaseList);
        howManyUniqueClientsBuyItemsByEuro(purchaseList);
        mapWithYearOfClientAndBoughtProductsByYear(purchaseList);
        mapWithYearOfClientAndCategoryOfBoughtProducts(purchaseList);


        secondMostBoughtProduct(purchaseList);


    }


    private static void howManyClientsDoTheShopping(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .map(Purchase::getBuyer)
                .distinct()
                .count();
        System.out.println(peopleCount);
        PrintingUtility.printingList(purchaseList);
    }

    private static void howManyClientsPayByBlik(List<Purchase> purchaseList)
    {
         long peopleCount = purchaseList.stream()
                 .filter(d -> d.getPayment().equals(Purchase.Payment.BLIK))
                 .map(Purchase::getBuyer)
                 .distinct()
                 .count();
        System.out.println(peopleCount);
        System.out.println("----------");
    }

    private static void howManyClientsPayByCreditCard(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d -> d.getPayment().equals(Purchase.Payment.CREDIT_CARD))
                .map(Purchase::getBuyer)
                .distinct()
                .count();
        System.out.println(peopleCount);
    }

    private static void howManyClientsBuyByEur(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.EUR))
                .map(Purchase::getProduct)
                .count();
        System.out.println(peopleCount);
    }

    private static void howManyClientsBuyByEurUniqueItems(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.EUR))
                .map(Purchase::getProduct)
                .distinct()
                .count();
        System.out.println(peopleCount);
    }

    private static void howMuchMoneyDoesClientSpendInPln(List<Purchase> purchaseList)
    {
        var clients = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.PLN))
                .collect(groupingBy(
                        p->p.getBuyer().getId(),
                        TreeMap::new,
                        Collectors.mapping(
                                p->p.getProduct().getPrice().getValue().multiply(BigDecimal.valueOf(p.getQuantity())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )));
        PrintingUtility.printingMap(clients);

    }


    private static void howMuchMoneyClientSpendInParticularCategory(List<Purchase> purchaseList, Product.Category category)
    {
        var clients = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.PLN))
                .filter(d->d.getProduct().getCategoryOfProduct().equals(category))
                .filter(d->d.getQuantity()>1)
                .collect(groupingBy(
                        p->p.getBuyer().getId(),
                        TreeMap::new,
                        Collectors.mapping(
                                p->p.getProduct().getPrice().getValue().multiply(BigDecimal.valueOf(p.getQuantity())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )));
        PrintingUtility.printingMap(clients);

    }


    private static void updateOrderStatus(List<Purchase> purchaseList, Product.Category category)
    {
        var clients = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.PLN))
                .filter(d->d.getProduct().getCategoryOfProduct().equals(category))
                .filter(d->d.getQuantity()>1)
                .collect(groupingBy(
                        p->p.getBuyer().getId(),
                        TreeMap::new,
                        Collectors.mapping(
                                p->p.getProduct().getPrice().getValue().multiply(BigDecimal.valueOf(p.getQuantity())),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )));
        PrintingUtility.printingMap(clients);

    }

    private static void statusCheck(List<Purchase> purchaseList)
    {
        long count = purchaseList.stream()
                .map(p -> new Purchase(p, OrderService.checkOrderStatus(p)))
                .filter(p -> Purchase.Status.DONE.equals(p.getStatus()))
                .count();


//        Map<Purchase.Status, Integer> collect = purchaseList.stream()
//                .map(p -> new Purchase(p, OrderService.checkOrderStatus(p)))
//                .collect(Collectors.toMap(
//                        p -> p.getStatus(),
//                        p -> 1,
//                        Integer::sum));

        Map<Purchase.Status, List<Purchase>> collect = purchaseList.stream()
                        .map(p->new Purchase(p, OrderService.checkOrderStatus(p)))
                        .collect(Collectors.toMap(
                                Purchase::getStatus,
                                List::of,
                                (List<Purchase> cL, List<Purchase>nL) -> Stream.concat(cL.stream(), nL.stream())
                        .collect(Collectors.toList())));


        System.out.println("Total number of Purchases: " + purchaseList.size());
        System.out.println("Number of purchases with DONE status: " + count);
        PrintingUtility.printingMap(collect);
    }

    private static void howManyUniqueClientsBuyItemsByEuro(List<Purchase> purchaseList)
    {
        List<Purchase> euroPurchases = purchaseList.stream()
                        .filter(p->p.getProduct().getPrice().getCurrency().equals(Money.Currency.EUR))
                        .collect(Collectors.toList());

        long count = euroPurchases.stream()
                        .map(p->p.getBuyer())
                        .distinct()
                        .count();

        System.out.println("Number of unique clients buying products in EUR: " + count);

        //Not necessary.
//        Map<String,List<Purchase>> purchasesInEurByClient = euroPurchases.stream()
//                .collect(Collectors.groupingBy(p->p.getBuyer().getId()));
//        System.out.println(purchasesInEurByClient);
    }



    private static void mapWithYearOfClientAndBoughtProductsByYear(List<Purchase> purchaseList)
    {
        TreeMap<String, List<Product>> productsPerClientYear = purchaseList.stream()
                .sorted(Comparator.comparing(Purchase::getBuyer))
                .collect(groupingBy(
                        p -> p.getBuyer().getYearOfBirth(),
                        TreeMap::new,
                        Collectors.mapping(p -> p.getProduct(), Collectors.toList())
                ));

        PrintingUtility.printingMap(productsPerClientYear);
    }
    
    private static void mapWithYearOfClientAndCategoryOfBoughtProducts(List<Purchase> purchaseList)
    {
        Map<String, HashSet<Product.Category>> uniqueCategoriesByClientYear = purchaseList.stream()
                .collect(Collectors.toMap(
                        (Purchase p) -> p.getBuyer().getYearOfBirth(),
                        p -> new HashSet<>(List.of(p.getProduct().getCategoryOfProduct())),
                        (currentSet, nextCategory) ->
                        {
                            currentSet.addAll(nextCategory);
                            return currentSet;
                        }
                ));
        PrintingUtility.printingMap(uniqueCategoriesByClientYear);
    }

    private static void secondMostBoughtProduct(List<Purchase> purchaseList)
    {
        Map<String, Long> quantityPerProductId = purchaseList.stream()
                .collect(groupingBy(
                        p -> p.getProduct().getId(),
                        TreeMap::new,
                        Collectors.mapping(p -> p.getQuantity(),
                                Collectors.reducing(0L, Long::sum)
                        )));

        PrintingUtility.printingMap(quantityPerProductId);

        Comparator<? super Pair<String, Long>> pairComparator = Comparator.comparing((Pair<String, Long> p)->p.getV())
                .reversed()
                .thenComparing((Pair<String, Long> p) ->p.getU());

        Pair<String, Long> secondMostBoughtProduct = quantityPerProductId.entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .sorted(pairComparator)
                .skip(1)
                .findFirst()
                .orElse(new Pair<>("default", 0L));

        System.out.println(secondMostBoughtProduct);
    }


}






