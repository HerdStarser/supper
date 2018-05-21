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
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional
public class RestfulTest {

    @Resource
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //测试获取信息
    @Test
    public void  testhello() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/user")
                .param("name","joy")
                .param("age","18")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }
    @Test
    public void getUser() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/user/156")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("张三"));

    }
    @Test
    public void whenCreateUserSuccess() throws Exception{
        String user="{\"names\":\"1232\",\"age\":50}";
        String s = mvc.perform(MockMvcRequestBuilders.post("/user").content(user).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().toString();
        System.out.println(s);

    }

    @Test
    public void whenUpdateUserSuccess() throws Exception{
        String context="{\"name\":\"12756\",\"age\":0}";
        mvc.perform(MockMvcRequestBuilders.put("/user/2").content(context).contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void whenDeleteUserSuccess() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/user/3").contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(MockMvcResultMatchers.status().isOk());


    }


    @Test
    public void whenGetUser() throws Exception {
        String content = mvc.perform(MockMvcRequestBuilders.get("/user/2").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }

    @Test
    public void whenGetUsers() throws Exception {
        String context="{\"name\":\"12756\"}";
        String content = mvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(context))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }

}
