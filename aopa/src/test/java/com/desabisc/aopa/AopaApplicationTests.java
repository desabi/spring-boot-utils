package com.desabisc.aopa;

import com.desabisc.aopa.service.BillingService;
import com.desabisc.aopa.service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopaApplicationTests {

	@Autowired
	ShipmentService shipmentService;

	@Autowired
	BillingService billingService;

	@Test
	void testBeforeLog() {
		shipmentService.shipStuff();
	}

	@Test
	void testBeforeLogWithBill() {
		shipmentService.shipStuffWithBill();
	}

	@Test
	void testWithin() {
		billingService.createBill();
	}
}
