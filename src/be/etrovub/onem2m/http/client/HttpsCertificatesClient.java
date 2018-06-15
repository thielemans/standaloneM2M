package be.etrovub.onem2m.http.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

public class HttpsCertificatesClient extends HttpClient{    
    
    public HttpsCertificatesClient(String keystorePath, String keystorePass, String keystoreKeyPass, String keystoreType) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException{
    	super();
        FileInputStream in = new FileInputStream(keystorePath);
        KeyStore keyStore = KeyStore.getInstance(keystoreType); // Typically "PKCS12" or "JKS"
        keyStore.load(in, keystorePass.toCharArray());
        
		SSLContext sslContext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, keystoreKeyPass.toCharArray()).useTLS()
				.build();
		SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
       			
		httpClientBuilder.setSSLSocketFactory(sslConnectionFactory);    
    }
    
    public HttpsCertificatesClient(String keystorePath, String keystorePass, String keystoreKeyPass) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException{
    	this(keystorePath, keystorePass, keystoreKeyPass, "PKCS12");
    }
    	
}
