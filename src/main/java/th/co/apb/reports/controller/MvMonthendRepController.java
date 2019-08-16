package th.co.apb.reports.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monthEndRep")
public class MvMonthendRepController {

	
	@GetMapping("/")
	public String view() {
		return "Hello World";
	}
}
