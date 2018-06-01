package Api;

import Modelo.Item;
import Response.StandardResponse;
import Response.StatusResponse;
import Services.ItemService;
import Services.ItemServiceImp;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static spark.Spark.*;

public class ItemApiRest {
    public static void main(String[] args) throws MalformedURLException {
        port(8080);
        final ItemService itemService = new ItemServiceImp();
        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();

        get("/inicio", (request, response) -> {
            return new ModelAndView(new HashMap<>(), "VIewItem");
        }, engine);

        get("/items", (request, response) -> {
            response.type("application/json");
            Collection<Item> items = itemService.getItems();
            if (items.isEmpty())
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, "Sin items disponibles"));
            else
                return  new Gson().toJsonTree(items);
        });

        get("/items/:id", (request, response) -> {
            response.type("application/json");

           return  new Gson().toJsonTree(itemService.getItem(request.params(":id")));
        });

        post("/items/:id", (request, response) -> {
            response.type("application/json");
            String test = request.body();
            Item item;

            try {
                item = new Gson().fromJson(request.body(), Item.class);
            }
            catch (Exception e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"Formato de json invalido"));
            }

            if (item.getId()== "")
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El id no puede ser vacio"));

            if (item.getTitle() == "")
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El nombre no puede ser vacio"));

            if (item.getPrice() == 0)
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"Se debe establecer un precio"));

            if (item.getAvailable_quantity() == 0)
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"Se debe establecer una cantidad"));

            int rdo= itemService.addItem(request.params(":id"), item);
            if (rdo != -1)
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            else
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El id del item ya existe"));
        });

        put("/items/:id", (request, response) -> {
            response.type("application/json");
            Item toEdit;

            try {
                toEdit = new Gson().fromJson(request.body(), Item.class);
            }
            catch (Exception e){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"Formato de json invalido"));
            }

            if (toEdit.getId()== "")
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El id no puede ser vacio"));

            if (toEdit.getTitle() == "")
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El nombre no puede ser vacio"));

            if (toEdit.getAvailable_quantity() == 0)
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"Se debe establecer una cantidad"));

            if (toEdit.getPrice() == 0)
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"Se debe establecer un precio"));

            Item editedItem = itemService.editItem(request.params(":id"), toEdit);
            if (editedItem != null)
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, new Gson()
                                .toJsonTree(editedItem)));
            else
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.ERROR, "Item no encontrado"));
        });

        delete("/items/:id", (request, response) -> {
            response.type("application/json");
            itemService.deleteItem(request.params(":id"));
            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, "Item borrado"));
        });
    }
}
