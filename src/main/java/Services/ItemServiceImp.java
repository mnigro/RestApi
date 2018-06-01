package Services;

import Modelo.Item;
import Services.Exceptions.ItemException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.elasticsearch.client.Requests.createIndexRequest;

public class ItemServiceImp implements ItemService {
    private TransportClient client = null;

    public ItemServiceImp() throws MalformedURLException {
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {

        }
    }

    public int addItem(String id, Item item) {
        Item nuevo = getItem(id);

        if (nuevo != null)
            return -1;

        item.setId(id);
        IndexResponse response = client.prepareIndex("items", "item", id)
                .setSource(new Gson().toJson(item), XContentType.JSON)
                .execute()
                .actionGet();

        return Integer.parseInt(item.getId());
    }

    public Collection<Item> getItems() {
        //pasar esto a cuando se inicia la aplicacion
        Collection<Item> listItem = new ArrayList<Item>();
        String[] indices = client.admin()
                .indices()
                .getIndex(new GetIndexRequest())
                .actionGet()
                .getIndices();


        if (indices.length == 0 || !Arrays.asList(indices).contains("items")) {
            client.admin().indices().create(createIndexRequest("items")).actionGet();
            return listItem;
        }

        //--------------------------------------------------------------------

        long totalHits = client.prepareSearch("items")
                .setTypes("item")
                .setSize(0)
                .setQuery(QueryBuilders.matchAllQuery()).get().getHits().getTotalHits();

       if (totalHits ==0)
           return listItem;

        URL url = null;
        try {
            url = new URL("http://localhost:9200/items/item/_search?size=1000&from=0&filter_path=hits.hits._source");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assert url != null;
        StringBuffer response = null;
        try {
            response = ElasticService(url);

            assert response != null;
            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class).get("hits").getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("hits").getAsJsonArray();


            for (JsonElement element : jsonArray) {
                String itemElement = element.getAsJsonObject().get("_source").toString();
                Item item = new Gson().fromJson(itemElement, Item.class);
                listItem.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return listItem;
        }

        return listItem;
    }

    public Item getItem(String id) {
        URL url = null;
        try {
            url = new URL("http://localhost:9200/items/item/" + id + "/_source");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {

            StringBuffer response = ElasticService(url);
            if (response== null)
                return  null;
            Item item = new Gson().fromJson(response.toString(), Item.class);

            return item;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private StringBuffer ElasticService(URL url) throws IOException {
        assert url != null;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status = con.getResponseCode();

        if (status == 404)
            return null;

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
        return response;

    }

    public Item editItem(String id, Item item) throws ItemException {
        deleteItem(id);
        addItem(id, item);
        return getItem(id);
    }

    public void deleteItem(String id) {

        DeleteResponse response = client.prepareDelete("items", "item", id)
                .execute()
                .actionGet();
    }
}
