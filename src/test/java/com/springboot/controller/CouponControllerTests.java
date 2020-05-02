package com.springboot.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.springboot.dao.CouponMapper;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CouponControllerTests {  // 호출 되는지 테스트
	@Autowired
    private MockMvc mvc;
	
    @MockBean
    private CouponMapper CouponMapper;

    @Test
    public void addCoupon_test1() throws Exception {
    	String uri = "/addCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("cnt", "5")).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg", "success! addCoupon : 5");
    	assertEquals(content, jsonObject.toString());
    }
    
    @Test
    public void addCoupon_test2() throws Exception {
    	String uri = "/addCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("cnt", "test")).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg", "failed! check cnt : test");
    	assertEquals(content, jsonObject.toString());
    }
    
    @Test
    public void addCoupon_test3() throws Exception {
    	String uri = "/addCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg", "failed! cnt in null");
    	assertEquals(content, jsonObject.toString());
    }
    
    @Test
    public void addCoupon_test4() throws Exception {
    	String uri = "/addCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("cnt", "")).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg", "failed! cnt in null");
    	assertEquals(content, jsonObject.toString());
    }
    
    @Test
    public void sendCoupon_test1() throws Exception {
    	String uri = "/sendCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg", "failed! check coupon");
    	assertEquals(content, jsonObject.toString());
    }
    
    @Test
    public void paidCouponList_test1() throws Exception {
    	String uri = "/paidCouponList";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject();
		List<HashMap<String, String>> paidCouponList = new ArrayList<HashMap<String,String>>();
		jsonObject.put("resultData",paidCouponList );
    	assertEquals(content, jsonObject.toString());
    }

    @Test
    public void useCoupon_test1() throws Exception {
    	String uri = "/useCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)
    	.param("couponId", "test")).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg","failed! check couponId" );
    	assertEquals(content, jsonObject.toString());
    }
    
    @Test
    public void cancleCoupon_test1() throws Exception {
    	String uri = "/cancleCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)
    	.param("couponId", "test")).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultMsg","failed! check couponId" );
    	assertEquals(content, jsonObject.toString());
    }    

    @Test
    public void expireCoupon_test1() throws Exception {
    	String uri = "/expireCoupon";

    	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    	.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    	int status = mvcResult.getResponse().getStatus();
    	assertEquals(200, status);
    	String content = mvcResult.getResponse().getContentAsString();
		JSONObject jsonObject = new JSONObject();
		List<HashMap<String, String>> expireCouponList = new ArrayList<HashMap<String,String>>();
		jsonObject.put("resultData",expireCouponList);
    	assertEquals(content, jsonObject.toString());
    }
}
