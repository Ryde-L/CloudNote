import com.cloudnote.common.utils.JsonUtil;
import com.cloudnote.note.dto.ElasticSearchDto;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ElasticSearchTest {

    @Test
    public void testES() throws IOException {
        long totalStart=System.currentTimeMillis();
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        MatchQueryBuilder match = QueryBuilders.matchQuery("user_id", "2");
        QueryStringQueryBuilder field = QueryBuilders.queryStringQuery("内").field("tag").field("content");
        boolQuery.must(match).must(field);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQuery);

        SearchRequest searchRequest = new SearchRequest("cloud_note"); //索引
        searchRequest.source(sourceBuilder);
        long searchStart=System.currentTimeMillis();
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询用时："+(System.currentTimeMillis()-searchStart));

        SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            System.out.println(hit.getSourceAsString());
            System.out.println(hit.getId());
            Map map = JsonUtil.jsonToPojo(hit.getSourceAsString(), Map.class);
            System.out.println(map.get("user_id"));

        }

        System.out.println(Arrays.toString(searchHits));
        System.out.println("总用时："+(System.currentTimeMillis()-totalStart));
    }

    @Test
    public void testQueryId() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        GetRequest getRequest = new GetRequest("cloud_note", "1");
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        if (response.isExists()){
            System.out.println(response.getSourceAsString()); ;
        }
    }

    @Test
    public void testUrl() throws IOException {
        URL url=new URL("http://127.0.0.1:9200/cloud_note/_search?q="+ URLEncoder.encode("内","UTF-8"));
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        StringBuffer sb=new StringBuffer();
        String readLine=new String();
        BufferedReader responseReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
        while((readLine=responseReader.readLine())!=null){
            sb.append(readLine);
        }
        responseReader.close();
        ElasticSearchDto elasticSearchDto = JsonUtil.jsonToPojo(sb.toString(), ElasticSearchDto.class);
        System.out.println(sb.toString());
    }

    @Test
    public void testUpdate() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("cloud_note","1");
        Map m=new HashMap();
        m.put("content","new 内容");
        updateRequest.doc(m);
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
            client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
