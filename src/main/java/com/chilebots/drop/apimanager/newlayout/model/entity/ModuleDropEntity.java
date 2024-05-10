package com.chilebots.drop.apimanager.newlayout.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad del módulo de drop.
 * 
 * @author Matías Espinoza
 *
 */
@Entity
@Table(name = "module_drop")
@Getter
@Setter
@NoArgsConstructor
public class ModuleDropEntity implements Serializable {

	/**
	 * Identificador único del módulo.
	 */
	@Id
	@Column(name = "id_module_drop", nullable = false)
	private Integer id;

	/**
	 * Serie del módulo.
	 */
	@Column(name = "serie_module_drop", unique = true, nullable = false)
	private String serie;

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
}
