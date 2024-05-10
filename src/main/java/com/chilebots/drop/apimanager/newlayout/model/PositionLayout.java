package com.chilebots.drop.apimanager.newlayout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Posición del layout.
 * 
 * @author Matías Espinoza
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class PositionLayout {

	/**
	 * Lado de la posición.
	 */
	private String side;

	/**
	 * Distancia de la posición.
	 */
	private Double distance;

	/**
	 * Id del tamano de la pos.
	 */
	private Integer boxSizeId;
}
