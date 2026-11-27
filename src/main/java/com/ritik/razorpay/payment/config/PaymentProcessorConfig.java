package com.ritik.razorpay.payment.config;

import com.ritik.razorpay.common.enums.PaymentMethod;
import com.ritik.razorpay.payment.processor.PaymentProcessor;
import com.ritik.razorpay.payment.processor.strategy.CardPaymentProcessor;
import com.ritik.razorpay.payment.processor.strategy.NetBankingPaymentProcessor;
import com.ritik.razorpay.payment.processor.strategy.UpiPaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap(){
        return Map.of(
                PaymentMethod.CARD,new CardPaymentProcessor(),
                PaymentMethod.NETBANKING,new NetBankingPaymentProcessor(),
                PaymentMethod.UPI,new UpiPaymentProcessor()
        );
    }
}
