package shop.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrController {

	@GetMapping("err")
	public String getErr() {
		return "err";
	}
}
