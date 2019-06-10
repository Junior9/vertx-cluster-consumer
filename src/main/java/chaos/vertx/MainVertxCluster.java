package chaos.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class MainVertxCluster extends AbstractVerticle {

    @Override
    public void start() {
    	vertx.eventBus().<String> consumer ("AdrMsg",message->{
    		JsonObject json  = new JsonObject().put("served-by", this.toString());
    		if(message.body().isEmpty()) {
    			message.reply(json.put("message", "hello" ));
    		}else {
    			message.reply(json.put("message","hello "+ message.body() ));
    		}
    	});
    }
}