package com.chilebots.drop.apimanager.newlayout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chilebots.drop.apimanager.newlayout.model.entity.KartEntity;
import com.chilebots.drop.apimanager.newlayout.repository.KartRepository;

/**
 * Servicio de los karts.
 * 
 * @author Matías Espinoza
 *
 */
@Service
public class KartService {

	/**
	 * Repositorio de los karts.
	 */
	@Autowired
	private KartRepository kartRepository;

	/**
	 * Guarda todos los karts que son enviados.
	 * 
	 * @param karts Listado de karts.
	 * @return Karts persistidos.
	 */
	public List<KartEntity> saveAll(List<KartEntity> karts) {
		return kartRepository.saveAll(karts);
	}

}
