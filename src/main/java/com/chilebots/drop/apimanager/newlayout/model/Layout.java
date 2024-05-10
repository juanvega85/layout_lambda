package com.chilebots.drop.apimanager.newlayout.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo del layout.
 * 
 * @author Matías Espinoza
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Layout {

	/**
	 * Listado de posiciones que son transferencias
	 */
	private List<String> transfer;

	/**
	 * Identificador del drop.
	 */
	private String dropId;

	/**
	 * Listado de karts.
	 */
	private List<KartLayout> karts;

}
