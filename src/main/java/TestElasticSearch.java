import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class TestElasticSearch {
    public static void main(String[] args) {
        try {
//            Client client = new PreBuiltTransportClient(
//                    Settings.builder().put("client.transport.sniff", true)
//                            .put("cluster.name","elasticsearch").build())
//                    .addTransportAddresses(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

            String jsonObject = "{\"age\":14,\"dateOfBirth\":1471466076564,"
                    +"\"fullName\":\"John Doe\"}";
            IndexResponse response = client.prepareIndex("people", "Doe")
                    .setSource(jsonObject, XContentType.JSON).get();

            String id = response.getId();
            String index = response.getIndex();
            String type = response.getType();
            long version = response.getVersion();
            System.out.println(id);
            System.out.println(index);
            System.out.println(type);
            System.out.println(version);
            System.out.println(response.getResult());

//            SearchResponse responseGet = client.prepareSearch().execute().actionGet();
//            List<SearchHit> searchHits = Arrays.asList(responseGet.getHits().getHits());
//
//            searchHits.forEach(
//                    hit -> System.out.println(hit.getSourceAsString()));


            SearchResponse searchResponse = client.prepareSearch()
                    .setTypes()
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(15))
                    .execute()
                    .actionGet();
            List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());

            searchHits.forEach(
                    hit -> System.out.println(hit.getSourceAsString()));

        }catch (Exception ex){
            ex.printStackTrace();
        }



//        assertEquals(DocWriteResponse.Result.CREATED, response.getResult());
//        assertEquals(0, version);
//        assertEquals("people", index);
//        assertEquals("Doe", type);
    }
}
