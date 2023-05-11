package shop.common.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TimedealDto {
	private int product_no,timedeal_discnt, timedeal_no;
	private String timedeal_begin,timedeal_end,timedeal_state;
	private LocalDate timedeal_regdate, timedeal_moddate;
	
}
