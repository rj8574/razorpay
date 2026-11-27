package com.ritik.razorpay.payment.config;

import com.ritik.razorpay.common.enums.PaymentMethod;
import com.ritik.razorpay.payment.gateway.PaymentAdapter;
import com.ritik.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.ritik.razorpay.payment.gateway.adapter.NetBankingAdpater;
import com.ritik.razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class PaymentAdapterConfig {
    private final NetBankingAdpater netBankingAdpater;
    private final CardPaymentAdapter cardPaymentAdapter;
    private final UpiPaymentAdapter upiPaymentAdapter;

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap(){
        return Map.of(
                PaymentMethod.CARD,new CardPaymentAdapter(),
                PaymentMethod.NETBANKING,netBankingAdpater,
                PaymentMethod.UPI,upiPaymentAdapter
        );
    }
}
