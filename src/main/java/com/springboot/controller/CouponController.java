package com.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dao.CouponMapper;

@SpringBootApplication
@RestController
public class CouponController {
	@Autowired
	private CouponMapper mapper;
	
	@RequestMapping(value="/addCoupon",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addCoupon(HttpServletRequest request) throws Exception {
		String resultMsg = "";
		String str = request.getParameter("cnt");
		
		if ("".equals(str) || str == null) {
			resultMsg = "failed! cnt in null";	
		} else {
			try {
				int cnt = Integer.parseInt(str);
				mapper.addCoupon(cnt);
				resultMsg = "success! addCoupon : " + cnt;
		    } catch (NumberFormatException e) {
		    	resultMsg = "failed! check cnt : " + str;	
		    }
		}
			
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("resultMsg", resultMsg);
		return jsonObject;
	}
	
	@RequestMapping(value="/sendCoupon",method=RequestMethod.PUT)
	public @ResponseBody Map<String,Object> sendCoupon(HttpServletRequest request) throws Exception {
		String resultMsg = "";
		String couponId = mapper.unusedCouponId();
		
		if ("".equals(couponId) || couponId == null) {
			resultMsg = "failed! check coupon";
		} else {
			mapper.sendCoupon(couponId);
			resultMsg = "success! sendCoupon : " + couponId;
		}
			
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("resultMsg", resultMsg);
		return jsonObject;
	}
	
	@RequestMapping(value="/paidCouponList",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> paidCuponList(HttpServletRequest request) throws Exception {
		List<HashMap<String, String>> paidCouponList = mapper.paidCouponList();
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("resultData", paidCouponList);
		return jsonObject;
	}
	
	@RequestMapping(value="/useCoupon",method=RequestMethod.PUT)
	public @ResponseBody Map<String,Object> useCoupon(HttpServletRequest request) throws Exception {
		String resultMsg = "";
		String couponId = request.getParameter("couponId");
		
		int isValidCoupon = mapper.validCoupon(couponId);
		if (isValidCoupon > 0) {
			mapper.useCoupon(couponId);
			resultMsg = "success! useCoupon";
		} else {
			resultMsg = "failed! check couponId";
		} 
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("resultMsg", resultMsg);
		return jsonObject;
	}	
	
	
	@RequestMapping(value="/cancleCoupon",method=RequestMethod.PUT)
	public @ResponseBody Map<String,Object> cancleCoupon(HttpServletRequest request) throws Exception {
		String resultMsg = "";
		String couponId = request.getParameter("couponId");
		
		int isValidCoupon = mapper.validCoupon(couponId);
		if (isValidCoupon> 0) {
			mapper.cancleCoupon(couponId);
			resultMsg = "success! cancleCoupon";
		} else {
			resultMsg = "failed! check couponId";
		} 
		
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("resultMsg", resultMsg);
		return jsonObject;
	}	
	
	@RequestMapping(value="/expireCoupon",method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> expireCoupon(HttpServletRequest request) throws Exception {
		List<HashMap<String, String>> expireCoupon = mapper.expireCoupon();
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("resultData", expireCoupon);
		return jsonObject;
	}
	
	@Scheduled(cron="0 10 16 * * ?")  // 매일 16:10에 만료일 3일 남은 쿠폰에 대해 사용자에게 메시지를 보냄
	public void checkExpireCoupon() throws Exception {
		List<HashMap<String, String>> checkExpireCoupon = mapper.checkExpireCoupon();
		for (int i = 0; i < checkExpireCoupon.size(); i++) {
			String couponId = checkExpireCoupon.get(i).get("couponId");
			System.out.println("만료일까지 3일 남았습니다!! couponId : " + couponId);
		}
	}
}//class end
