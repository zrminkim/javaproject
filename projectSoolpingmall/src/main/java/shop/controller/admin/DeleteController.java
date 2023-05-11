package shop.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import shop.model.admin.DataDao;
import shop.common.model.ProductDto;

@Controller
public class DeleteController {
	@Autowired
	private DataDao dataDao;
	
	
	@GetMapping("delete")
	public String deleteProduct(@RequestParam("product_no")int no) {
		boolean b = dataDao.deleteProductData(no);
		if(b) {
			return "redirect:http://localhost/adminManager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
}

