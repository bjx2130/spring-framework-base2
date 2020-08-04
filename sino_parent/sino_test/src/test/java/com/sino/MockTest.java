package com.sino;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sino.vo.Product;


@SpringBootTest
@AutoConfigureMockMvc
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class MockTest {
	private static final Logger log = LogManager.getLogger();
	private static final ObjectMapper mapper = new ObjectMapper(); 
	
	MockMvc mockMvc;
	
    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    
    
    @Test
    void getPdList()  throws Throwable{
    	
    	MvcResult r = this.mockMvc.perform(get("/getPdList/13289287271")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
        
        System.out.println("getPdList请求结果:"+r.getResponse().getContentAsString());
    	
    	
    }
    
    
    @Test
    void eligible()  throws Throwable{
    	///eligible/{phone}:{pdCode}
    	MvcResult r = this.mockMvc.perform(get("/eligible/13289287272:915001")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
        
        System.out.println("eligible请求结果:"+r.getResponse().getContentAsString());
    	
    	
    }
    
    
    
    @Test
    public void addOrder() throws Throwable{
    	Product blorder = new Product();
    	
    	MvcResult r = this.mockMvc.perform(post("/addOrder")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(mapper.writeValueAsString(blorder))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        
        System.out.println("addOrder请求结果:"+r.getResponse().getContentAsString());
    }    
}
