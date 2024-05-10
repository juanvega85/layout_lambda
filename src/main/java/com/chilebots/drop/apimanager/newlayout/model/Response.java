package com.chilebots.drop.apimanager.newlayout.model;

import com.chilebots.drop.libraries.util.droputil.models.ResponseLambda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response de la lambda.
 * 
 * @author Matías Espinoza
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Response extends ResponseLambda {
	
	/**
	 * Listado de inserts.
	 */
	private String inserts;

}
