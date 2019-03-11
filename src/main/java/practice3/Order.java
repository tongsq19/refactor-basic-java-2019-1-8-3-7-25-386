package practice3;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private List<OrderLineItem> orderLineItemList;
    private List<BigDecimal> discounts;
    private BigDecimal tax;

    public Order(List<OrderLineItem> orderLineItemList, List<BigDecimal> discounts) {
        this.orderLineItemList = orderLineItemList;
        this.discounts = discounts;
        this.tax = new BigDecimal(0.1);
    }

    public BigDecimal calculate() {
        calculations calculations = new calculations().invoke();
        return calculations.getGrandTotal();
    }


    private class calculations {

        private BigDecimal taxTotal = new BigDecimal(0);
        private BigDecimal grandTotal = new BigDecimal(0);
        private BigDecimal subTotal = new BigDecimal(0);

        public BigDecimal getGrandTotal() {
            return grandTotal;
        }

        public calculations invoke() {
            // Total up line items
            for (OrderLineItem lineItem : orderLineItemList) {
                subTotal = subTotal.add(lineItem.getPrice());
            }

            // Subtract discounts
            for (BigDecimal discount : discounts) {
                subTotal = subTotal.subtract(discount);
            }

            // calculate TaxTotal
            taxTotal = subTotal.multiply(tax);

            // calculate GrandTotal
            grandTotal = subTotal.add(taxTotal);
            return this;
        }
    }
}
