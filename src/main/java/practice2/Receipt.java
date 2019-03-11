package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    private BigDecimal tax;

    public Receipt() {
        tax = new BigDecimal(0.1).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {

        BigDecimal subTotal = calculateSubtotal(products, items);

        BigDecimal reduceTotal = calulateReduceTotal(products, items);

        BigDecimal reducedSubTotal = subTotal.subtract(reduceTotal);

        BigDecimal grandTotal = getGrandTotal(reducedSubTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal getGrandTotal(BigDecimal subTotal) {

        BigDecimal taxTotal = subTotal.multiply(tax);

        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal;
    }

    private BigDecimal calulateReduceTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal reduceTotal = new BigDecimal( 0);

        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);

            BigDecimal ReducedPrice = product.getPrice().multiply(new BigDecimal(item.getCount()))
                    .multiply(product.getDiscountRate());

            reduceTotal = reduceTotal.add(ReducedPrice);
        }
        return reduceTotal;
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;
        for (OrderItem item : items) {
            if (item.getCode() == product.getCode()) {
                curItem = item;
                break;
            }
        }
        return curItem;
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getCount()));
            subTotal = subTotal.add(itemTotal);
        }
        return subTotal;
    }
}
