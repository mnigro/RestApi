
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.ArrayList;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import com.google.gson.Gson;

public class Server {
    static MockServerClient mockServer = startClientAndServer(9300);

    public static void main(String[] args){

        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/items/item/ml123/_source")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody("{\n" +
                                        "    \"id\": \"ml123\",\n" +
                                        "    \"title\": \"Pantalon Leviss\",\n" +
                                        "    \"category_id\": \"MLA2312\",\n" +
                                        "    \"price\": 123,\n" +
                                        "    \"currency_id\": \"ARS\",\n" +
                                        "    \"available_quantity\": 10,\n" +
                                        "    \"buying_mode\": \"buy_it_now\",\n" +
                                        "    \"listing_type_id\": \"bronze\",\n" +
                                        "    \"condition\": \"Nuevo\",\n" +
                                        "    \"description\": \"\",\n" +
                                        "    \"video_id\": \"\",\n" +
                                        "    \"warranty\": \"   \",\n" +
                                        "    \"pictures\": [\n" +
                                        "        {\n" +
                                        "            \"source\": \"https://http2.mlstatic.com/D_NQ_NP_947601-MLA20372322970_082015-F.webp\"\n" +
                                        "        },\n" +
                                        "        {\n" +
                                        "            \"source\": \"https://http2.mlstatic.com/D_NQ_NP_897111-MLA20498909789_112015-F.webp\"\n" +
                                        "        }\n" +
                                        "    ]\n" +
                                        "}")
                                .withDelay(new Delay(SECONDS, 1)));

        mockServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/items/item/_search?")
                        .withQueryStringParameter("size","1000")
                        .withQueryStringParameter("from","0")
                        .withQueryStringParameter("filter_path","hits.hits._source")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody("{\n" +
                                        "    \"hits\": {\n" +
                                        "        \"hits\": [\n" +
                                        "            {\n" +
                                        "                \"_source\": {\n" +
                                        "                    \"title\": \"Item de test 2 - No Ofertar\",\n" +
                                        "                    \"category_id\": \"MLA5428\",\n" +
                                        "                    \"price\": 6,\n" +
                                        "                    \"currency_id\": \"ARS\",\n" +
                                        "                    \"available_quantity\": 1,\n" +
                                        "                    \"buying_mode\": \"buy_it_now\",\n" +
                                        "                    \"listing_type_id\": \"bronze\",\n" +
                                        "                    \"condition\": \"new\",\n" +
                                        "                    \"description\": \"Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box\",\n" +
                                        "                    \"video_id\": \"YOUTUBE_ID_HERE\",\n" +
                                        "                    \"warranty\": \"12 months by Ray Ban\",\n" +
                                        "                    \"pictures\": [\n" +
                                        "                        {\n" +
                                        "                            \"source\": \"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg\"\n" +
                                        "                        },\n" +
                                        "                        {\n" +
                                        "                            \"source\": \"http://en.wikipedia.org/wiki/File:Teashades.gif\"\n" +
                                        "                        }\n" +
                                        "                    ]\n" +
                                        "                }\n" +
                                        "            },\n" +
                                        "            {\n" +
                                        "                \"_source\": {\n" +
                                        "                    \"id\": \"ml123\",\n" +
                                        "                    \"title\": \"Pantalon Leviss\",\n" +
                                        "                    \"category_id\": \"MLA2312\",\n" +
                                        "                    \"price\": 123,\n" +
                                        "                    \"currency_id\": \"ARS\",\n" +
                                        "                    \"available_quantity\": 10,\n" +
                                        "                    \"buying_mode\": \"buy_it_now\",\n" +
                                        "                    \"listing_type_id\": \"bronze\",\n" +
                                        "                    \"condition\": \"Nuevo\",\n" +
                                        "                    \"description\": \"\",\n" +
                                        "                    \"video_id\": \"\",\n" +
                                        "                    \"warranty\": \"   \",\n" +
                                        "                    \"pictures\": [\n" +
                                        "                        {\n" +
                                        "                            \"source\": \"https://http2.mlstatic.com/D_NQ_NP_947601-MLA20372322970_082015-F.webp\"\n" +
                                        "                        },\n" +
                                        "                        {\n" +
                                        "                            \"source\": \"https://http2.mlstatic.com/D_NQ_NP_897111-MLA20498909789_112015-F.webp\"\n" +
                                        "                        }\n" +
                                        "                    ]\n" +
                                        "                }\n" +
                                        "            }\n" +
                                        "        ]\n" +
                                        "    }\n" +
                                        "}")
                                .withDelay(new Delay(SECONDS, 1)));

        mockServer
                .when(request()
                        .withMethod("DELETE")
                        .withPath("/items/123")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withDelay(new Delay(SECONDS, 1)));

        mockServer
                .when(request()
                        .withMethod("POST")
                        .withPath("/items/123")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withDelay(new Delay(SECONDS, 1)));
        mockServer
                .when(request()
                        .withMethod("PUT")
                        .withPath("/items/123")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withReasonPhrase("")
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"))
                                .withDelay(new Delay(SECONDS, 1)));

    }
}
