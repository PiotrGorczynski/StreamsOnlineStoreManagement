# <p align="center"> StreamsOnlineStoreManagement </p>
<p align="center"> <b>Program solves following problems:</b> </p>


<p align="justify"> Let's imagine we have data from an online store called... StreamsOnlineStoreManagement.
This data is provided in the form of a Data Factory class object. <p align="center"><b>There are three levels of tasks:</b> </p>
<p align="center"><b>Level one tasks:</p></b>
<p align="center">1. Calculates how many customers have made a purchase in our store.</p>
<p align="center">2. Calculates how many customers paid with Blik.</p>
<p align="center">3. Calculates how many customers paid by credit card.</p>
<p align="center">4. Calculates how many purchases were made in EUR.</p>
<p align="center">5. Calculates how many unique purchased products were purchased in EUR.</p>
<p align="center"><b>Level two tasks:</p></b>
<p align="center">1. Calculates how much PLN each person who made a purchase with us spent in the store. Include purchases only
made in PLN. </p>
<p align="center">2. Prepares a method that will accept a specific category and return a map for this category, where the key
will be the customer's ID, and the value will be the number of products purchased by him from the given category (take under
only those transactions where the quantity of products purchased is greater than 1). </p>
<p align="center">3. Each order is initially PAID. Updates the status of all orders,
using checking the status of each specific order through the class code
OrderService given below. Uses the class code to check the status of each order
OrderService given below. Finally, calculates how many orders have been processed, i.e. they have
DONE status. </p>
<p align="center">4. Calculates how many unique customers bought a product priced in EUR (customers cannot be repeated,
remember that one could buy several products). Additionally, creates a map where the key is id
customer, and the value of the purchase list of this customer's products in EUR. </p>
<p align="center">5. Prepares a map, where the key will be the customer's year, and the values ​​will be a list of all products
which a customer from a given year bought. Takes the year from the PESEL number, it doesn't have to be full 1987, maybe
be, for example, 87. Sorts the map by key in ascending order. </p>
<p align="center">6. Creates a map, where vintages will be the key, and a unique set of product categories will be the value
purchased by the year. </p>
<p align="center">7. What is the second most purchased product? If several products are purchased in the same
quantity, sorts them alphabetically by id, and still get the second one. So it sorts by largest first
the number of occurrences of a given product, and then after the id. </p>

<p align="center"><b>Level three tasks:</p></b>
<p align="center">1. For people older than 50, it creates a structure in which it contains information: year, least
popular category for a given year, number of transactions for a given year within a given year
category. By least popular, we mean the least amount of purchases made in
within a given category. For example: "year: 62, least popular category: GARDEN, transactions: 5". </p>
<p align="center">2. Which year bought the most products? </p>
