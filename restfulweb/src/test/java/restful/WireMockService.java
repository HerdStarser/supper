package restful;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;



public class WireMockService {


    public static void main(String[] args) throws Exception{
        String host="172.17.70.136";
        Integer port=8060;
        WireMock.configureFor(host,port);
        WireMock.removeAllMappings();

        produceWireMock("/for/data","a.txt");
    }

    private static void produceWireMock(String mappingUrl, String fileName) throws Exception{
        ClassPathResource resource=new ClassPathResource("test/response/"+fileName);
        String context = FileUtils.readFileToString(resource.getFile(), "UTF-8");

        WireMock.stubFor(WireMock.post(WireMock.urlMatching(mappingUrl))
                .willReturn(WireMock.aResponse().withBody(context).withStatus(200)));




    }
}