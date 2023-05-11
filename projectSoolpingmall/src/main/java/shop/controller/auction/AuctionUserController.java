package shop.controller.auction;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.common.model.AuctionProgressDto;
import shop.controller.userhistory.PaginationUser;
import shop.model.auction.AuctionDao;

@Controller
public class AuctionUserController {
	@Autowired
	private  AuctionDao auctionDao;

    @GetMapping("auctionuser")
	public String selectAutionList(Model model) {
    	System.out.println("111111");
    	
    	ArrayList<AuctionUserDto> list = (ArrayList<AuctionUserDto>)auctionDao.selectAutionList();
    	
		model.addAttribute("lists", list);
    	System.out.println("22222");
        return "auction/auctionmain";
    }

    @GetMapping("/auctionInfo")
    public String auctionInfo(@RequestParam("auction_no")String auction_no, @RequestParam(value="page",defaultValue = "1")int page, Model model) {
    	System.out.println("dd " + auction_no);
    	AuctionUserDto dto = (AuctionUserDto)auctionDao.selectAutionOne(auction_no);
    	PaginationAuction paginationAuction = new PaginationAuction(auctionDao.getCountProgress(auction_no), page);
    	paginationAuction.setAuction_no(auction_no);
    	ArrayList<AuctionProgressDto> list2 = (ArrayList<AuctionProgressDto>)auctionDao.selectProgressList(paginationAuction);
    	System.out.println("list2" + list2.size());
    	model.addAttribute("auction", dto);
    	model.addAttribute("pro", list2);
    	model.addAttribute("page",page);
    	model.addAttribute("pageVo",paginationAuction);
    	return "auction/auctionInfo";
    }
    
    @PostMapping("/startBid")
    public String startBid(AuctionUserBean auctionBean) {
    	System.out.println("dd " + auctionBean);
    	
    	String user_no = "1";
    	auctionBean.setUserap_num(user_no);
    	boolean b = auctionDao.startBid(auctionBean);
    	
    	return "redirect:/auctionuser";
    }
}


