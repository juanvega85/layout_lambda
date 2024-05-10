package com.chilebots.drop.apimanager.newlayout.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de la posición.
 * 
 * @author Matías Espinoza
 *
 */
@Entity
@Table(name = "position")
@Getter
@Setter
@NoArgsConstructor
public class PositionEntity implements Serializable {

	/**
	 * Id de la posición
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_position", nullable = false)
	private Integer id;

	/**
	 * Nombre de la posición
	 */
	@Column(name = "name_position", nullable = false)
	private String name;

	/**
	 * Tipo de la posición.
	 */
	@Column(name = "type_position", nullable = false)
	private String type;

	/**
	 * Id del tamano de la caja.
	 */
	@Column(name = "box_size_id", nullable = false)
	private Integer boxSizeId;

	/**
	 * Id del módulo.
	 */
	@Column(name = "module_drop_id", nullable = false)
	private Integer moduleDropId;

	/**
	 * Id del grupo de posiciones.
	 */
	@Column(name = "position_group_id", nullable = false)
	private Integer positionGroupId;

	/**
	 * Fecha de creación de la posición.
	 */
	@Column(name = "create_at_position", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	/**
	 * Fecha de actualización de la posición.
	 */
	@Column(name = "update_at_position", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
}
