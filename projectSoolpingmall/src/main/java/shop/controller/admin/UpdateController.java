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
public class UpdateController {
	@Autowired
	private DataDao dataDao;
	@Autowired
	private FileValidator fileValidator;
	
	@GetMapping("update")
	public String updateProduct(@RequestParam("product_no")String product_no, Model model) {
		int no = Integer.parseInt(product_no);
		System.out.println(no);
		ProductDto dto = dataDao.searchProduct(no);
		model.addAttribute("product",dto);
		return "admin/update";
	}
	
	@PostMapping("update")
	public String updateProcess(ProductBean bean, BindingResult result) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		// 업로드 된 파일 검사
		MultipartFile file = bean.getUploadfile();
		fileValidator.validate(file, result);
		String filename = file.getOriginalFilename();
		bean.setProduct_img(filename);
		String filepath = "C:/acorn3project/acorn3project/projectSoolpingmall/src/main/resources/static/images/product/";
		if(result.hasErrors()) {
			return "admin/update"; //에러 메세지가 넘어간다. -> 에러 발생(파일을 선택하지 않음) 시 수행
		}
		try {
			inputStream = file.getInputStream();
			File newFile = new File(filepath + filename);
			if(newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes,0,read);
			}

		} catch (Exception e) {
			System.out.println("file submit err :" + e);
		}finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		boolean b = dataDao.updateProductData(bean);
		if(b) {
			return "redirect:http://localhost/adminManager";
		}else {
			return "redirect:http://localhost/error";
		}
	}
}
