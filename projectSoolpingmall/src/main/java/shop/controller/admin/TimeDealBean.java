package shop.controller.admin;

import lombok.Data;

@Data
public class TimeDealBean {
	private int product_no,timedeal_discnt, timedeal_no;
	private String timedeal_begin,timedeal_end;
}
