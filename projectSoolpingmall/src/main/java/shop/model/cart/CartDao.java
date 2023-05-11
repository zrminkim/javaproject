package shop.model.cart;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import shop.model.purchase.PurchaseBean;

@Repository
public class CartDao {
	@Autowired 
	CartMappingInterFace cartMappingInterFace;
	
	public List<CartDto> getDataAll(int user_num){
		List<CartDto> list = cartMappingInterFace.selectAll(user_num);
		
		return list;
	}
	
	public List<CartDto> selectCartbyProductNo(@Param("product_num") int product_num, @Param("user_num") int user_num){
		List<CartDto> list = cartMappingInterFace.selectCartByProductNo(product_num, user_num);
		
		return list;
	}
	
	public boolean deleteCartProduct(@Param("product_num") int product_num, @Param("user_num") int user_num) {

		boolean b =false;
		int re = cartMappingInterFace.DeleteProduct(product_num, user_num);
		if(re > 0) b = true;
		
		return b;
	}
	
	public int selectUserCash(int user_num) {
		int user_cash = cartMappingInterFace.selectUserCash(user_num);
		
		return user_cash;
	}
	
	public boolean addCartProcess(@Param("user_num") int user_num, @Param("product_num") int product_num,@Param("cart_cnt") int cart_cnt) {
		
		boolean b =false;
		int re = cartMappingInterFace.addCart(user_num, product_num, cart_cnt);
		if(re > 0) b = true;
		return b;
	}
	
	public boolean UpdateCartCnt(@Param("cart_cnt") int cart_cnt, @Param("product_num") int product_num) {
		
		boolean b =false;
		int re = cartMappingInterFace.UpdateCartCnt(cart_cnt, product_num);
		if(re > 0) b = true;
		return b;
	}
	
}
