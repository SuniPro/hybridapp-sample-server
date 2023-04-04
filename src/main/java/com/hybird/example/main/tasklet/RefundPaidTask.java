package com.hybird.example.main.tasklet;

import com.hybird.example.user.payMent.service.OrderInvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RefundPaidTask {
    @Resource(name = "OrderInvoiceService")
    private OrderInvoiceService orderInvoiceService;

//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 3 * * *")
    private void runRefundPaid() throws Exception {
        log.info("runRefundPaid");
        orderInvoiceService.doRefundPaid();
    }
}
