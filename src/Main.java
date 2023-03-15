import model.*;
import utils.PrintingUtility;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static service.Service.*;

public class Main
{
    public static void main(String[] args)
    {
        List<Purchase> purchaseList = DataFactory.produce();
        PrintingUtility.print("------------------");
        //1)How many clients do the shopping?
        howManyClientsDoTheShopping(purchaseList);
        //2)How many clients pay by blik?
        PrintingUtility.print("------------------");
        howManyClientsPayByBlik(purchaseList);
        PrintingUtility.print("------------------");
        //3)How many clients pay by credit card?
        howManyClientsPayByCreditCard(purchaseList);
        PrintingUtility.print("------------------");
        //4)How many clients pay by eur?
        howManyClientsBuyByEur(purchaseList);
        PrintingUtility.print("------------------");
        //5)How many unique items were bought by euro?
        howManyClientsBuyByEurUniqueItems(purchaseList);
        PrintingUtility.print("------------------");
        //6)How much PLNs does every client spend?
        howMuchMoneyDoesClientSpendInPln(purchaseList);
        PrintingUtility.print("------------------");
        //7)How much money does client spend in particular category of product?
        howMuchMoneyClientSpendInParticularCategory(purchaseList, Product.Category.GARDEN);
        PrintingUtility.print("------------------");
        //8)model.Client's status:
        statusCheck(purchaseList);
        PrintingUtility.print("------------------");
        //9)How many distinct clients buy items by euro?
        howManyUniqueClientsBuyItemsByEuro(purchaseList);
        PrintingUtility.print("------------------");
        //10) Map with age of model.Client and bought products by year:
        mapWithYearOfClientAndBoughtProductsByYear(purchaseList);
        PrintingUtility.print("------------------");
        //11)Map with age of model.Client and category of products:
        mapWithYearOfClientAndCategoryOfBoughtProducts(purchaseList);
        PrintingUtility.print("------------------");
        //12)Which product is the second most bought?
        secondMostBoughtProduct(purchaseList);
        PrintingUtility.print("------------------");
        //13)The most unpopular category among clients:
        structureWithAgeWorstCategoryAndTransaction(purchaseList);
        PrintingUtility.print("------------------");
        //14)Which year do the shopping the most?
        whichYearBoughtMostQuantityOfProducts(purchaseList);
        PrintingUtility.print("------------------");
    }

}






