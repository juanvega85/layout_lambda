package com.chilebots.drop.apimanager.newlayout.model;

import java.util.HashMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo del kart del layout.
 * 
 * @author Matías Espinoza
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class KartLayout {

	/**
	 * Nombre del kart.
	 */
	private String name;

	/**
	 * Distancia del kart.
	 */
	private Double length;

	/**
	 * Tipo del kart.
	 */
	private String type;

	/**
	 * Listado de posiciones
	 */
	private HashMap<String, PositionLayout> positions;
}
