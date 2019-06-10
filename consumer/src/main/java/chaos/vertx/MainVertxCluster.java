package chaos.vertx;


import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.eventbus.Message;
import rx.Single;

public class MainVertxCluster extends AbstractVerticle {

    @Override
    public void start() {
    	//Add an Http server
    	vertx.createHttpServer().requestHandler(req->{
    		//Use the eventBus to send a message to 'AdrMsg' anddress and extract the body of replay.
    		Single<JsonObject> obs1 = vertx.eventBus().<JsonObject>rxSend("AdrMsg", "Firmansys").map(Message::body);
    		Single<JsonObject> obs2 = vertx.eventBus().<JsonObject>rxSend("AdrMsg", "Indonesia").map(Message::body);
    		
    		Single.zip(obs1, obs2,(Firmansyah,Indonesia)->
    					new JsonObject()
    					.put("Firmansys",Firmansyah.getString("message") + " from " + Firmansyah.getString("served-by"))
    					.put("Indonesia",Indonesia.getString("message") + " from " + Indonesia.getString("served-by")))
    					.subscribe(
    						x->req.response().end(x.encode()),
    						t->req.response().setStatusCode(500).end(t.getMessage())
    					);
    		
    	}).listen(8080);
    }

}
