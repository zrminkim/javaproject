package shop.model.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shop.common.model.ProductDto;
import shop.common.model.PurchaseDto;
import shop.common.model.UserDto;
import shop.controller.admin.UserBean;
import shop.model.cart.CartDto;

@Mapper
public interface PurchaseMappingInterface {	
	@Select("select * from user where user_no = #{user_no}")
	List<UserDto> selectUserData(int user_no);

	@Select("select cart_no, user_num, product_num, product_img,  product_name, product_price, cart_cnt, (cart_cnt * product_price) as total from cart inner join product on product_no = product_num where user_num = #{user_num}")
	List<CartDto> selectCart(int user_num);	
	
	@Select("select * from product where product_no = #{product_no}")
	List<ProductDto> selectProduct(String product_no);
	
	@Select("select * from purchase where user_num = #{user_num} order by purchase_no desc limit 1")
	List<PurchaseDto> selectPurchase(int user_num);
	
	@Select("select product_no, purchase_num, product_cnt, product_name, productl_regdate, product_price, product_img from purchase_list inner join product on product_num = product_no where purchase_num = #{purchase_num}")
	List<PurchaseBean> selectPurchaseList(@Param("purchase_num") String purchase_num);
	
	@Select("select * from purchase where purchase_no = #{purchase_no}")
	List<PurchaseBean> selectPurchase2(@Param("purchase_no") String purchase_no);
	
	@Select("select count(*) from cart;")
	int cartCount();
	
	@Insert("insert into purchase (user_num, purchase_total, purchase_rec, purchase_zipcode, purchase_addr1, purchase_addr2, purchase_tel, purchase_email, purchase_req, purchase_upoint, purchase_orderno) values(#{user_num}, #{purchase_total}, #{purchase_rec}, #{purchase_zipcode}, #{purchase_addr1}, #{purchase_addr2}, #{purchase_tel}, #{purchase_email}, #{purchase_req}, #{purchase_upoint}, #{purchase_orderno})")
	@Options(useGeneratedKeys = true, keyProperty = "purchase_no") 
	int insertPurchsaeData(PurchaseBean bean);
	
	@Update("update cart set cart_cnt = #{cart_cnt} where cart_no = #{cart_no}")
	int updateCart(CartDto cartDto);
	
	@Delete("delete from cart where user_num = #{user_num}")
	int deleteCartProduct(int user_num);
	
	@Update("update user set user_cash = #{updateCash}, user_point=#{updatePoint} where user_no = #{user_num}")	// 임시 유저 번호
	int updateUserCash(@Param("updateCash") int updateCash, @Param("updatePoint") int updatePoint, @Param("user_num") int user_num);
	
	// 주문 완료시 상품 재고변동(추후 model.product로 이동)
	@Update("update product set product_stock = product_stock - #{cart_cnt} where product_no = #{product_num}")
	int updateStock(@Param("cart_cnt") int cart_cnt, @Param("product_num") int product_num);
	
	 @Insert("insert into purchase_list (product_num, purchase_num, product_cnt) values(#{product_num}, #{purchase_num}, #{product_cnt})") 
	 int insertPurchaseList(@Param("product_num") int product_num, @Param("purchase_num") int purchase_num, @Param("product_cnt") int product_cnt);
	
}
