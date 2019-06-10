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

/*
 * ===>Project with event Bus <===
mvn io.fabric8:vertx-maven-plugin:1.0.5:setup -DprojectGroupId=chaos.vertx.event.bus -DprojectArtifactId=cluster-micro1 -Dverticle=chaos.vertx.MainVertxCluster -Ddependencies=infinispan
 ==> Command to run cluster 
 mvn compile vertx:run -Dvertx.runArgs="-cluster -Djava.net.preferIPv4Stack=true"
 */
 