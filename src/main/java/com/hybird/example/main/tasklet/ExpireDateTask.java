package com.hybird.example.main.tasklet;

import com.hybird.example.user.payMent.service.OrderInvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ExpireDateTask {
    @Resource(name = "OrderInvoiceService")
    private OrderInvoiceService orderInvoiceService;

    @Scheduled(cron = "0 30 3 * * *")
    private void runExpireDate() throws Exception {
        log.info("runExpireDate");
        orderInvoiceService.runExpireDate();
    }
}
