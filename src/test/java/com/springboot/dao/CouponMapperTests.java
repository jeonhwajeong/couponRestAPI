package com.springboot.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CouponMapperTests {  // 데이터 테스트
	@Autowired
    private CouponMapper couponMapper;

    @Test
    public void addCoupon_test1() throws Exception {
    	couponMapper.addCoupon(1);
    	String couponId = couponMapper.unusedCouponId();
    	assertTrue(couponId.length() > 0);
    }
    
    @Test
    public void sendCoupon_test1() throws Exception {
    	String couponId = couponMapper.unusedCouponId();
    	couponMapper.sendCoupon(couponId);
		int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
		assertEquals(isValidCoupon, 1);
    }
    
    @Test
    public void sendCoupon_test2() throws Exception {
    	String couponId = "";
    	couponMapper.sendCoupon(couponId);
		int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
    	assertEquals(isValidCoupon, 0);
    }
    
    @Test
    public void paidCouponList_test1() throws Exception {
    	String couponId = couponMapper.unusedCouponId();
    	couponMapper.sendCoupon(couponId);  // 쿠폰 지급
    	
    	List<HashMap<String, String>> paidCouponList = couponMapper.paidCouponList();  // 지급된 상태 있음 확인
    	assertTrue(paidCouponList.size() > 0);
    }
    
    @Test
    public void paidCouponList_test2() throws Exception {
    	String couponId = couponMapper.unusedCouponId();
    	couponMapper.sendCoupon(couponId);  // 쿠폰 지급
    	String couponId1 = couponMapper.unusedCouponId();
       	couponMapper.sendCoupon(couponId1);  // 쿠폰 지급
       	
    	List<HashMap<String, String>> paidCouponList = couponMapper.paidCouponList();  // 지급된 상태 있음 확인
    	assertEquals(paidCouponList.size(), 2);
    }
    
    @Test
    public void paidCouponList_test3() throws Exception {
    	// 쿠폰 지급하지 않음 
    	List<HashMap<String, String>> paidCouponList = couponMapper.paidCouponList();  // 지급 상태 있음 확인
    	assertEquals(paidCouponList.size(), 0);
    }
    
    @Test
    public void useCoupon_test1() throws Exception {
    	String couponId = couponMapper.unusedCouponId();
    	couponMapper.sendCoupon(couponId);  // 쿠폰 지급
		
		int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인

		if (isValidCoupon > 0) {
			couponMapper.useCoupon(couponId);
		}
		int result = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
		assertEquals(isValidCoupon, 1);
		assertEquals(result, 0);
    }    

    @Test
    public void useCoupon_test2() throws Exception {
		String couponId = "NOT";  // 사용자 배정되지 않은 쿠폰 번호
		
		int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인

		if (isValidCoupon > 0) {
			couponMapper.useCoupon(couponId);
		}
		int result = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
		assertEquals(isValidCoupon, 0);
		assertEquals(result, 0);
    }   
    
    @Test
    public void cancleCoupon_test1() throws Exception {
    	String couponId = couponMapper.unusedCouponId();
    	couponMapper.sendCoupon(couponId);  // 쿠폰 지급

    	int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인

		if (isValidCoupon > 0) {
			couponMapper.cancleCoupon(couponId);
		}
		int result = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
		assertEquals(isValidCoupon, 1);
		assertEquals(result, 0);
    }    

    @Test
    public void cancleCoupon_test2() throws Exception {
		String couponId = "NOT";  // 사용자에게 전달 되지 않은 쿠폰 번호
		
		int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인

		if (isValidCoupon > 0) {
			couponMapper.cancleCoupon(couponId);
		}
		
		int result = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
		assertEquals(isValidCoupon, 0);
		assertEquals(result, 0);
    }    
	
    @Test
    public void expireCoupon_test1() throws Exception {
    	String couponId = couponMapper.unusedCouponId();
    	couponMapper.sendCoupon(couponId);  // 쿠폰 지급

    	int isValidCoupon = couponMapper.validCoupon(couponId);  // 해당 쿠폰 ID에 대해 배정된 쿠폰인지 확인
    	List<HashMap<String, String>> expireCouponList = couponMapper.expireCoupon();  // 만료일 확인 

    	assertEquals(isValidCoupon, 1);
    	assertEquals(expireCouponList.size(), 0);
    }
    
    @Test
    public void checkExpireCoupon_test1() throws Exception {
    	List<HashMap<String, String>> expireCouponList = couponMapper.checkExpireCoupon();  // 만료일 확인 
    	assertEquals(expireCouponList.size(), 0);
    }
}
