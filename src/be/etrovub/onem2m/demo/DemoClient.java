package be.etrovub.onem2m.demo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.Group;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.commons.resource.Subscription;

import be.etrovub.onem2m.http.client.HttpClient;
import be.etrovub.onem2m.util.Marshaller;
import be.etrovub.onem2m.util.MarshallingException;
import be.etrovub.onem2m.util.RequestSender;
import be.etrovub.onem2m.util.Response;
import be.etrovub.onem2m.util.RqpUtil;

/**
 * This class contains the client demo code. We will create several ACPIs, AEs, Containers, CINs and Subscriptions.
 * In this demo we loosely follow the oneM2M application developer guide (http://onem2m.org/application-developer-guide)
 * @author Steffen Thielemans
 */

public class DemoClient {
	private static Log LOGGER = LogFactory.getLog(DemoClient.class);
	private HttpClient httpClient;
	private RequestSender requestSender;
	
	private String acpi;
	
	public DemoClient(HttpClient httpClient) {
		this.httpClient = httpClient;
		this.requestSender = new RequestSender(httpClient);
		
		//We create the ACPI (This is a rather clumsy way...)
		//Create the privileges with all originators and access control operations (63)
		ArrayList<String> originators = new ArrayList<String>();
		originators.add(Demo.adminACP);
		originators.add("light_ae1");
		originators.add("light_ae2");
		originators.add("smartphone_ae1");
		SetOfAcrs privileges = new SetOfAcrs();		
		privileges.getAccessControlRule().add(createACPRules(originators, 63));
		
		//Create the SELF privileges with all originators and access control operations (63)
		ArrayList<String> selfOriginators = new ArrayList<String>();
		selfOriginators.add(Demo.adminACP);
		SetOfAcrs selfPrivileges = new SetOfAcrs();		
		selfPrivileges.getAccessControlRule().add(createACPRules(selfOriginators, 63));
		
		//Create actual ACPI
		createACP("CSE-acp1", privileges, selfPrivileges);
		
		
		createAE("light_ae1", "A01.com.company.lightApp01", true);
		createAE("light_ae2", "A01.com.company.lightApp02", true);
		createAE("gateway_ae", "A01.com.company.gatewayApp", false);
		createAE("smartphone_ae", "A01.com.company.lightControlApp", false);

		String container1URI = createContainer("light", "light_ae1", "light_ae1");
		String container2URI = createContainer("light", "light_ae2", "light_ae2");

		ArrayList<String> members = new ArrayList<String>();
		members.add(container1URI);
		members.add(container2URI);
		createGroup("containers_grp", ResourceType.CONTAINER, 10, members, Demo.adminACP);
		
		createSubscription("lightstate_sub", "in-name/light_ae1", "light_ae1", "light_ae1/light");
		createSubscription("lightstate_sub", "in-name/light_ae2", "light_ae2", "light_ae2/light");
		
		createContentInstance("text/plain:0", "OFF", "light_ae1", "light_ae1/light");
		createContentInstance("text/plain:0", "OFF", "light_ae2", "light_ae2/light");
		
		//no discovery for now
		
		
		retrieveContentInstance("smartphone_ae", "light_ae1/light/la");
		retrieveContentInstance("smartphone_ae", "light_ae2/light/la");
		//retrieveContentInstance("smartphone_ae", "containers_grp/fopt/la");  //not working as intended?
	
		//Smartphone turns on lights  (Will be received by corresponding subscriptions and action can be taken)
		createContentInstance("text/plain:0", "ON", "smartphone_ae", "light_ae1/light");
		createContentInstance("text/plain:0", "ON", "smartphone_ae", "light_ae2/light");
		
	}


private AccessControlRule createACPRules(List<String> originators, int operation) {
	AccessControlRule newRule = new AccessControlRule();
	newRule.setAccessControlOperations(BigInteger.valueOf(operation));
	newRule.getAccessControlOriginators().addAll(originators);
	return newRule;
}

	private void createACP(String name, SetOfAcrs privileges, SetOfAcrs selfPrivileges) {		
		AccessControlPolicy newACP = new AccessControlPolicy();
		newACP.setName(name);
		newACP.setPrivileges(privileges);
		newACP.setSelfPrivileges(selfPrivileges);

		RequestPrimitive request = RqpUtil.createRqpWithContent(Demo.adminACP, Demo.basePath, ResourceType.ACCESS_CONTROL_POLICY, newACP);		
		Response response = requestSender.createOrGet(request);
		if(response.getResource() != null) {
			acpi = response.getResource().getResourceID();
		} else {
			//LOGGER.debug("ERROR with creating/getting ACP. Response code:" + response.getStatus());
		}
	}	
	
	private void createAE(String name, String api, Boolean reachable) {
		AE newAE = new AE();
		newAE.setName(name);
		newAE.setAppID(api);
		newAE.getAccessControlPolicyIDs().add(acpi);

		if(reachable) {
			newAE.setRequestReachability(true);
			newAE.getPointOfAccess().add(Demo.notificationServerPath);
		} else {
			newAE.setRequestReachability(false);
		}
		
		RequestPrimitive request = RqpUtil.createRqpWithContent(Demo.adminACP, Demo.basePath, ResourceType.AE, newAE);
		ResponsePrimitive response = httpClient.sendRequest(request);
		//LOGGER.info(response.toString());
	}
	
	private String createContainer(String name, String originator, String pathOffset) {
		Container newContainer = new Container();
		newContainer.setName(name);
		RequestPrimitive request = RqpUtil.createRqpWithContent(originator, Demo.basePath + pathOffset, ResourceType.CONTAINER, newContainer);
		ResponsePrimitive response = httpClient.sendRequest(request);
		//LOGGER.info(response.toString());
		return response.getLocation();
	}


	private void createContentInstance(String contentInfo, String content, String originator, String pathOffset) {
		ContentInstance cin = new ContentInstance();
		cin.setContentInfo(contentInfo);
		cin.setContent(content);
		RequestPrimitive request = RqpUtil.createRqpWithContent(originator, Demo.basePath + pathOffset, ResourceType.CONTENT_INSTANCE, cin);
		ResponsePrimitive response = httpClient.sendRequest(request);
		//LOGGER.info(response.toString());
	}	

	
	private void createGroup(String name, int memberType, int maxNrOfMembers, List<String> members, String originator) {
		Group group = new Group();
		group.setName(name);
		group.getMemberIDs().addAll(members);
		group.setMaxNrOfMembers(BigInteger.valueOf(maxNrOfMembers));
		group.setMemberType(BigInteger.valueOf(memberType));
		RequestPrimitive request = RqpUtil.createRqpWithContent(originator, Demo.basePath, ResourceType.GROUP, group);
		ResponsePrimitive response = httpClient.sendRequest(request);
		//LOGGER.info(response.toString());		
	}
	
	
	private void createSubscription(String name, String uri, String originator, String pathOffset) {
		Subscription sub = new Subscription();
		sub.setName(name);
		sub.setNotificationContentType(BigInteger.valueOf(1));
		sub.getNotificationURI().add(uri);
		RequestPrimitive request = RqpUtil.createRqpWithContent(originator, Demo.basePath + pathOffset, ResourceType.SUBSCRIPTION, sub);
		ResponsePrimitive response = httpClient.sendRequest(request);
		//LOGGER.info(response.toString());		
	}
	
	
	private void retrieveContentInstance(String originator, String pathOffset) {
			RequestPrimitive request = RqpUtil.retrieveRqp(originator, Demo.basePath + pathOffset);
			ResponsePrimitive response = httpClient.sendRequest(request);
			try {
				LOGGER.info(Marshaller.unmarshalResponse(response).toString());
			} catch (MarshallingException e) {
				e.printStackTrace();
			}		
		}
	
	
}
