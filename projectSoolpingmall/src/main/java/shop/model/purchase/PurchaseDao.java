package shop.model.purchase;

import java.util.List;
import java.util.Random;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.common.model.UserDto;
import shop.controller.admin.UserBean;
import shop.model.cart.CartDto;

@Repository
public class PurchaseDao {
	@Autowired
	PurchaseMappingInterface purchaseMappingInterface;

	public List<UserDto> getUserData(int user_no) {
		List<UserDto> ulist = purchaseMappingInterface.selectUserData(user_no);

		return ulist;
	}

	public List<CartDto> getCartData(int user_num) {
		List<CartDto> clist = purchaseMappingInterface.selectCart(user_num);

		return clist;
	}
	
	public List<ProductDto> getProductData(String product_no){
		List<ProductDto> plist = purchaseMappingInterface.selectProduct(product_no);
		
		return plist;
	}
	
	public List<PurchaseDto> getPurchaseData(int user_num){
		List<PurchaseDto> purchaseList = purchaseMappingInterface.selectPurchase(user_num);
		
		return purchaseList;
	}
	
	public List<PurchaseBean> getPurchaseData2(@Param("purchase_no") String purchase_no){
		List<PurchaseBean> purchaseList2 = purchaseMappingInterface.selectPurchase2(purchase_no);
		
		return purchaseList2;
	}
	
	public List<PurchaseBean> selectPurchaseList(@Param("purchase_num") String purchase_num){
		List<PurchaseBean> purchaseLlist = purchaseMappingInterface.selectPurchaseList(purchase_num);
		
		return purchaseLlist;
	}

	public boolean insertPurchaseData(PurchaseBean bean) {
		boolean b = false;
		int re = purchaseMappingInterface.insertPurchsaeData(bean);
		if (re > 0)
			b = true;

		return b;
	}

	public boolean deleteCartProduct(int user_num) {

		boolean b = false;
		int re = purchaseMappingInterface.deleteCartProduct(user_num);

		if (re > 0)
			b = true;

		return b;
	}

	public boolean updateCartData(CartDto cartDto) {
		boolean b = false;
		int re = purchaseMappingInterface.updateCart(cartDto);
		if (re > 0)
			b = true;

		return b;
	}
	
	public boolean updateUserCash(@Param("updateCash") int updateCash, @Param("updatePoint") int updatePoint, @Param("user_num")int user_num) {
		boolean b = false;
		int re = purchaseMappingInterface.updateUserCash(updateCash, updatePoint, user_num);
		if (re > 0)
			b = true;

		return b;
	}
	
	// 주문 완료시 상품 재고변동(추후 model.product로 이동)
	public boolean updateProductStock(@Param("cart_cnt") int cart_cnt, @Param("product_num") int product_num) {
		boolean b = false;
		int re = purchaseMappingInterface.updateStock(cart_cnt, product_num);
		if (re > 0)
			b = true;

		return b;
	}
	
	public boolean insertPurchaseList(@Param("product_num") int product_num, @Param("purchase_num") int purchase_num, @Param("product_cnt") int product_cnt) {
		boolean b = false;
		int re = purchaseMappingInterface.insertPurchaseList(product_num, purchase_num, product_cnt);	
		if (re > 0)
			b = true;

		return b;
	}
	
	public String ordernoCreater(int user_num) {
		Random random = new Random();
		int randomNumber = random.nextInt(10000000);
		return String.format("%010d" + Integer.toString(user_num), randomNumber);
	}
	
	public int countCart() {
		int re = purchaseMappingInterface.cartCount();
		
		return re;
	}

}
