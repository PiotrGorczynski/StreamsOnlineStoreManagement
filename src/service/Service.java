package service;

import model.Money;
import model.OrderService;
import model.Product;
import model.Purchase;
import utils.Pair;
import utils.PrintingUtility;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class Service
{

    static final Integer CURRENT_YEAR = 2022;

    public static void howManyClientsDoTheShopping(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .map(Purchase::getBuyer)
                .distinct()
                .count();
        System.out.println(peopleCount);
    }

    public static void howManyClientsPayByBlik(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d -> d.getPayment().equals(Purchase.Payment.BLIK))
                .map(Purchase::getBuyer)
                .distinct()
                .count();
        System.out.println(peopleCount);
    }

    public static void howManyClientsPayByCreditCard(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d -> d.getPayment().equals(Purchase.Payment.CREDIT_CARD))
                .map(Purchase::getBuyer)
                .distinct()
                .count();
        System.out.println(peopleCount);
    }

    public static void howManyClientsBuyByEur(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.EUR))
                .map(Purchase::getProduct)
                .count();
        System.out.println(peopleCount);
    }

    public static void howManyClientsBuyByEurUniqueItems(List<Purchase> purchaseList)
    {
        long peopleCount = purchaseList.stream()
                .filter(d->d.getProduct().getPrice().getCurrency().equals(Money.Currency.EUR))
                .map(Purchase::getProduct)
                .distinct()
                .count();
        System.out.println(peopleCount);
    }

    public static void howMuchMoneyDoesClientSpendInPln(List<Purchase> purchaseList)
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


    public static void howMuchMoneyClientSpendInParticularCategory(List<Purchase> purchaseList, Product.Category category)
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

    public static void statusCheck(List<Purchase> purchaseList)
    {
        long count = purchaseList.stream()
                .map(p -> new Purchase(p, OrderService.checkOrderStatus(p)))
                .filter(p -> Purchase.Status.DONE.equals(p.getStatus()))
                .count();

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

    public static void howManyUniqueClientsBuyItemsByEuro(List<Purchase> purchaseList)
    {
        List<Purchase> euroPurchases = purchaseList.stream()
                .filter(p->p.getProduct().getPrice().getCurrency().equals(Money.Currency.EUR))
                .collect(Collectors.toList());

        long count = euroPurchases.stream()
                .map(p->p.getBuyer())
                .distinct()
                .count();

        System.out.println("Number of unique clients buying products in EUR: " + count);
    }



    public static void mapWithYearOfClientAndBoughtProductsByYear(List<Purchase> purchaseList)
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

    public static void mapWithYearOfClientAndCategoryOfBoughtProducts(List<Purchase> purchaseList)
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

    public static void secondMostBoughtProduct(List<Purchase> purchaseList)
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


    public static void structureWithAgeWorstCategoryAndTransaction(List<Purchase> purchaseList)
    {
        Map<String, Map<Product.Category, Long>> yearWithCategoriesWithoutZeros = purchaseList.stream()
                .filter(p -> CURRENT_YEAR - (1900 + Integer.parseInt(p.getBuyer().getYearOfBirth())) > 50)
                .collect(groupingBy(
                        p -> p.getBuyer().getYearOfBirth(),
                        groupingBy(
                                p -> p.getProduct().getCategoryOfProduct(),
                                Collectors.counting()
                        )
                ));

        Map<String, TreeMap<Long, List<Product.Category>>> yearWithCategoriesWithZeros = yearWithCategoriesWithoutZeros.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Arrays.stream(Product.Category.values())
                                .collect(Collectors.toMap(
                                        categoryKey -> e.getValue().getOrDefault(categoryKey, 0L),
                                        List::of,
                                        (currentList, nextList) -> Stream.concat(currentList.stream(), nextList.stream())
                                                .collect(Collectors.toList()),
                                        TreeMap::new))));

        Map<String, Map.Entry<Long,List<Product.Category>>> yearWithMinimumEntry = yearWithCategoriesWithZeros.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e->e.getValue().entrySet().stream()
                                .min(Map.Entry.comparingByKey())
                                .get(),
                        (e1,e2)->e2,
                        TreeMap::new
                ));

        PrintingUtility.printingMap(yearWithCategoriesWithoutZeros);
        PrintingUtility.printingMap(yearWithCategoriesWithZeros);
        PrintingUtility.printingMap(yearWithMinimumEntry);


    }

    public static void whichYearBoughtMostQuantityOfProducts(List<Purchase> purchaseList)
    {
        TreeMap<String, Long> quantityByYear = purchaseList.stream()
                .collect(groupingBy(
                        (Purchase p) -> p.getBuyer().getYearOfBirth(),
                        TreeMap::new,
                        Collectors.mapping(p -> p.getQuantity(),
                                Collectors.reducing(0L, Long::sum))
                ));
        System.out.println(quantityByYear);

        TreeMap<Long, Set<String>> yearByQuantity = quantityByYear.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        e -> Set.of(e.getKey()),
                        (currentList, nextList) -> Stream.concat(currentList.stream(), nextList.stream())
                                .collect(Collectors.toSet()),
                        TreeMap::new
                ));

        Map.Entry<Long, Set<String>> mostQuantityByYear = yearByQuantity.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .orElseThrow(() -> new RuntimeException("No purchases!"));


        System.out.println(yearByQuantity);
        System.out.println(mostQuantityByYear);
    }
}
