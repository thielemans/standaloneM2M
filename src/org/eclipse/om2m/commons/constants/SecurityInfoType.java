/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.constants;

import java.math.BigInteger;

public class SecurityInfoType {

	private SecurityInfoType() {}
	
	public static final BigInteger DYNAMIC_AUTHORIZATION_REQUEST = BigInteger.valueOf(1);
	public static final BigInteger DYNAMIC_AUTHORIZATION_RESPONSE = BigInteger.valueOf(2);
	public static final BigInteger E2E_RAND_OBJECT_REQUEST = BigInteger.valueOf(3);
	public static final BigInteger E2E_RAND_OBJECT_RESPONSE = BigInteger.valueOf(4);
	public static final BigInteger ESPRIM_OBJECT = BigInteger.valueOf(5);
	public static final BigInteger ES_CERT_KE_MESSAGE = BigInteger.valueOf(6);
}
