package com.chilebots.drop.apimanager.newlayout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request de la lambda.
 * 
 * @author Matías Espinoza
 *
 */
@Getter
@NoArgsConstructor
public class Request {

	/**
	 * Serie del módulo.
	 */
	private String module;

	/**
	 * Layout en formato json.
	 */
	private String layout;
	
	/**
	 * Indica si hay que crear un grupo nuevo.
	 */
	private boolean createGroup;
	
	/**
	 * Indica el nombre del grupo.s
	 */
	private String groupName;
	
	/**
	 * Id del grupo.
	 */
	private int groupId;

}
