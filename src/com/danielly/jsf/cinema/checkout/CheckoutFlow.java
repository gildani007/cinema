package com.danielly.jsf.cinema.checkout;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import java.io.Serializable;

/**
 * The class CheckoutFlow in charge of the checkout flow process.
 * It navigates the user to the relevant pages for each phase according the checks. 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 */

public class CheckoutFlow implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Defines the checkout flow.
     *
     * @param builder the builder
     * @return the flow
     */
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder builder) {
        builder.id("", "checkout");

        builder.viewNode("order", "/checkout/order-details.xhtml")
                .markAsStartNode();
        
        builder.viewNode("payment", "/checkout/payment-details.xhtml");

        builder.viewNode("review", "/checkout/order-review.xhtml");

        builder.methodCallNode("execute")
                .expression("#{checkout.fulfillOrder}")
                .defaultOutcome("check-result");

        builder.switchNode("check-result")
		        .switchCase()
			        .condition("#{not checkout.ticketSeatCheckSuccess}")
			        .fromOutcome("seating-failure")
                .switchCase()
                    .condition("#{checkout.paymentSuccess}")
                    .fromOutcome("success")
                .switchCase()
                    .condition("#{not checkout.paymentSuccess}")
                    .fromOutcome("failure")
		        .switchCase()
			        .condition("#{checkout.generalOrderIssueFailure}")
			        .fromOutcome("order-failure");

        builder.viewNode("seating-failure", "/checkout/seating-failure.xhtml");
        
        builder.viewNode("success", "/checkout/payment-success.xhtml");

        builder.viewNode("failure", "/checkout/payment-failure.xhtml");
        
        builder.viewNode("order-failure", "/checkout/order-failure.xhtml");

        builder.returnNode("finished")
                .fromOutcome("home");

        return builder.getFlow();
    }
}
