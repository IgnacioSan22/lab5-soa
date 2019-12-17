package soa.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SearchController {

  private final ProducerTemplate producerTemplate;

  @Autowired
  public SearchController(ProducerTemplate producerTemplate) {
    this.producerTemplate = producerTemplate;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }


  @RequestMapping("/search")
  @ResponseBody
  public Object search(@RequestParam("q") String q, 
                       @RequestParam("max") Optional<Integer> max) {
    HashMap<String, Object> headers = new HashMap<>();
    headers.put("CamelTwitterKeywords", q);
    headers.put("CamelTwitterCount", max.orElse(10));
    return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
  }
}