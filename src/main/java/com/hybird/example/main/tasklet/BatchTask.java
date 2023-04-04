package com.hybird.example.main.tasklet;

import com.hybird.example.main.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class BatchTask {

	@Resource
	MainService mainService;

	/*-- 1: 오픈준비중, 2: 운행중, 3: 운행종료, 4: 정기정검, 5: 정기운휴*/
	
	// 오픈준비중 - 오픈 2시간 전
	@Scheduled(cron = "1 0 7-9 * * *")
	private void beforeOpeningBatch() throws Exception {
		log.info(">>> Batch Scheduler = {}", "beforeOpeningBatch");
		this.mainService.beforeOpeningBatch();
	}

	// 운영중
	@Scheduled(cron = "1 0 9-11 * * *")
	private void OpenBatch() throws Exception {
		log.info(">>> Batch Scheduler = {}", "OpenBatch");
		this.mainService.openBatch();
	}

	//운행종료
	@Scheduled(cron = "1 0 17-19 * * *")
	private void ClosedBatch() throws Exception {
		log.info(">>> Batch Scheduler = {}", "closedBatch");
		this.mainService.closedBatch();
	}
}
