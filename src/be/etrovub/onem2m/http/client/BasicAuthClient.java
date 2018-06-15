package be.etrovub.onem2m.http.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class BasicAuthClient extends HttpClient{
	
	public BasicAuthClient(String username, String password){
		
		super();
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
		provider.setCredentials(AuthScope.ANY, credentials);

		httpClientBuilder.setDefaultCredentialsProvider(provider);

	}
	
}
