/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.constants.ShortName;

@XmlRootElement(name=ShortName.DAC)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class DynamicAuthorizationConsultation extends RegularResource {

	@XmlElement(name = ShortName.DYNAMIC_AUTHORIZATION_ENABLED, namespace="")
	private Boolean dynamicAuthorizationEnabled;
	
	@XmlList
	@XmlElement(name = ShortName.DYNAMIC_AUTHORIZATION_PoA, namespace="")
	private List<String> dynamicAuthorisationPoA;
	
	@XmlElement(name = ShortName.DYNAMIC_AUTHORIZATION_LIFETIME, namespace="")
	private String dynamicAuthorizationLifetime;

	public Boolean getDynamicAuthorizationEnabled() {
		return dynamicAuthorizationEnabled;
	}

	public void setDynamicAuthorizationEnabled(Boolean dynamicAuthorizationEnabled) {
		this.dynamicAuthorizationEnabled = dynamicAuthorizationEnabled;
	}

	public List<String> getDynamicAuthorisationPoA() {
		if (dynamicAuthorisationPoA == null) {
			dynamicAuthorisationPoA = new ArrayList<>();
		}
		return dynamicAuthorisationPoA;
	}

	public void setDynamicAuthorisationPoA(List<String> dynamicAuthorisationPoA) {
		this.dynamicAuthorisationPoA = dynamicAuthorisationPoA;
	}

	public String getDynamicAuthorizationLifetime() {
		return dynamicAuthorizationLifetime;
	}

	public void setDynamicAuthorizationLifetime(String dynamicAuthorizationLifetime) {
		this.dynamicAuthorizationLifetime = dynamicAuthorizationLifetime;
	}
	
	
	
}
