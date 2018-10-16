package jProject.controllers;

import java.math.BigDecimal;
import java.util.Arrays;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.Transaction.Status;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.ValidationError;

import jProject.jProjectApplication;
import jProject.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CheckoutController {

    private BraintreeGateway gateway = jProjectApplication.gateway;

    private Status[] TRANSACTION_SUCCESS_STATUSES = new Status[] {
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };

    @Autowired
    ReservationService reservationService;


    @RequestMapping(value = "/checkouts", method = RequestMethod.GET)
    public String checkout(@ModelAttribute("amount") String amount, Model model) {

        String clientToken = gateway.clientToken().generate();
        model.addAttribute("clientToken", clientToken);
        model.addAttribute("amount",amount);

        return "checkouts/new";
    }

    @RequestMapping(value = "/checkouts", method = RequestMethod.POST)
    public String postForm( @RequestParam("payment_method_nonce") String nonce, final RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();

        BigDecimal decimalAmount;
        try {
            decimalAmount = new BigDecimal((String) session.getAttribute("amount"));
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorDetails", "Error: 81503: Amount is an invalid format.");
            return "redirect:checkouts";
        }

        TransactionRequest request = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            return "redirect:checkouts/" + transaction.getId();
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTransaction();
            return "redirect:checkouts/" + transaction.getId();
        } else {
            String errorString = "";
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
            }
            redirectAttributes.addFlashAttribute("errorDetails", errorString);
            return "redirect:checkouts";
        }
    }


    @RequestMapping(value = "/checkouts/{transactionId}")
    public String getTransaction(@PathVariable String transactionId, Model model, HttpServletRequest httpServletRequest) {
        Transaction transaction;
        CreditCard creditCard;
        Customer customer;

        try {
            transaction = gateway.transaction().find(transactionId);
            creditCard = transaction.getCreditCard();
            customer = transaction.getCustomer();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return "redirect:/checkouts";
        }

        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
        model.addAttribute("transaction", transaction);
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("customer", customer);


        HttpSession session = httpServletRequest.getSession();

        reservationService.paymentIsComplete((int)session.getAttribute("reservationId"));

        return "checkouts/show";
    }
}