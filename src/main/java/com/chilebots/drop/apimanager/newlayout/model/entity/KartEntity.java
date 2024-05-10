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
 * Entidad del kart.
 * 
 * @author Matías Espinoza
 *
 */
@Entity
@Table(name = "kart")
@Getter
@Setter
@NoArgsConstructor
public class KartEntity implements Serializable {

	/**
	 * Id del kart
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_kart", nullable = false)
	private Integer id;

	/**
	 * Nombre del kart.
	 */
	@Column(name = "name_kart", nullable = false)
	private String name;

	/**
	 * Tipo de kart
	 */
	@Column(name = "type_kart", nullable = false)
	private String type;

	/**
	 * Largo del kart.
	 */
	@Column(name = "length_kart", nullable = false)
	private Double length;

	/**
	 * Fecha de creación del kart.
	 */
	@Column(name = "create_at_kart", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	/**
	 * Fecha de actualización del kart.
	 */
	@Column(name = "update_at_kart", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;
}
