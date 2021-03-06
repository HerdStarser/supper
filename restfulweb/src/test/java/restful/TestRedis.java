package restful;


import com.restful.ApplicationStart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStart.class})
public class TestRedis {

    private MockMvc mvc;

    @Resource
    private WebApplicationContext wac;

    @Before
    public void setMockMvc(){
        mvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testRedisShardUtil_1() throws Exception{
        String json = "123456";
        String content = mvc.perform(MockMvcRequestBuilders.get("/testRedisShardUtil_1").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }

    @Test
    public void testredisUtil_1() throws Exception{
        String json = "896";
        String content = mvc.perform(MockMvcRequestBuilders.get("/testredisUtil_1").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }

    @Test
    public void testCachable() throws Exception {
        String json="13";
        String content = mvc.perform(MockMvcRequestBuilders.get("/testCachable").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }

}
