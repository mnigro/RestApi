# RestApi
Ejercicio 2
La api esta configurada para trabajar en el puerto 8080
Los datos se almacenan en Elasticsearch es un servidor de búsqueda basado en Lucene. 
Provee un motor de búsqueda de texto completo, distribuido y con capacidad de multi-tenencia con documentos JSON.

Recurso	          Descripción	                Ejemplo
/items	          Permite listado de gestión.	GET      Lista de todos los items almacenados

/items/{Item_id}	Permite listado de gestión.	GET      Lista el item requerido si existe
                                              PUT      Modifica un item.
                                              POST     Agrega un item.
                                              DELETE   Elimina el item
                                              
Json para agregar o modificar un item.
"Content-Type: application/json" -d
'{
  "id":"ML252A"
  "title":"Item de test - No Ofertar",
  "category_id":"MLA5529",
  "price":10,
  "currency_id":"ARS",
  "available_quantity":1,
  "buying_mode":"buy_it_now",
  "listing_type_id":"bronze",
  "condition":"new",
  "description": "Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box",
  "video_id": "YOUTUBE_ID_HERE",
  "warranty": "12 months by Ray Ban",
  "pictures":[
    {"source":"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"},
    {"source":"http://en.wikipedia.org/wiki/File:Teashades.gif"}
  ]
}'


