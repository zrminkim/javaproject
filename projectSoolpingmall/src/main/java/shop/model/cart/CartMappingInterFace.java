package shop.model.cart;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface CartMappingInterFace {
	@Select("select cart_no, user_num, product_name, product_img, product_img_url, product_num, product_price, cart_cnt, (cart_cnt * product_price) as total from cart inner join product on product_num = product_no where user_num = #{user_num}")
	List<CartDto> selectAll(int user_num);
	
	@Select("select user_cash from user where user_no = #{user_no}")
	int selectUserCash(int user_no);
	
	@Select("select * from cart where product_num = #{product_num} and user_num = #{user_num}")
	List<CartDto> selectCartByProductNo(@Param("product_num") int product_num, @Param("user_num") int user_num);
	
	@Delete("delete from cart where product_num = #{product_num} and user_num = #{user_num}")	// 상세 페이지  완성시 조건을 cart_no로 변경
	int DeleteProduct(@Param("product_num") int product_num, @Param("user_num")int user_num);
	
	@Insert("insert into cart (user_num, product_num, cart_cnt) values(#{user_num}, #{product_num}, #{cart_cnt})")
	int addCart(@Param("user_num")int user_num,@Param("product_num") int product_num,@Param("cart_cnt") int cart_cnt);
	
	@Update("update cart set cart_cnt = cart_cnt + #{cart_cnt} where product_num = #{product_num}")
	int UpdateCartCnt(@Param("cart_cnt") int cart_cnt, @Param("product_num") int product_num);
}
