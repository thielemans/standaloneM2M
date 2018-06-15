//NOT IN USE, REQUIRES ADDITIONAL WORK
/*
package be.etrovub.onem2m.http.server;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class RestHttpsServer extends RestHttpServer{

	// the keystore (with one key) we'll use to make the connection with the broker
	private final static String KEYSTORE_LOCATION = "C:/eclipse/jettycerts/server.p12";
	private final static String KEYSTORE_PASS = "password";
 
	// the truststore we use for our server. This keystore should contain all the keys that are allowed to make a connection to the server
	private final static String TRUSTSTORE_LOCATION = "C:/eclipse/jettycerts/trustedclientcerts.p12";
	private final static String TRUSTSTORE_PASS = "password";
	
	public RestHttpsServer() {
		this(DEFAULTPORT);
	}
	
	public RestHttpsServer(int port) {
		super(port);
		
		SslContextFactory sslContextFactory = new SslContextFactory();
	    sslContextFactory.setKeyStorePath(KEYSTORE_LOCATION);
		sslContextFactory.setKeyStorePassword(KEYSTORE_PASS);
		sslContextFactory.setKeyManagerPassword(KEYSTORE_PASS);
				
		sslContextFactory.setTrustStorePath(TRUSTSTORE_LOCATION);
		sslContextFactory.setTrustStorePassword(TRUSTSTORE_PASS);
		//sslContextFactory.setWantClientAuth(true);
		sslContextFactory.setNeedClientAuth(true);

	    ServerConnector serverConnector = new ServerConnector(server,sslContextFactory);
        serverConnector.setPort(8443);
		server.addConnector(serverConnector);
        
	}	
	
}*/
