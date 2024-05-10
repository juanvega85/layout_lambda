package com.chilebots.drop.apimanager.newlayout.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chilebots.drop.apimanager.newlayout.model.entity.PositionEntity;
import com.chilebots.drop.apimanager.newlayout.repository.PositionRepository;

/**
 * Servicio de las posiciones.
 * 
 * @author Matías Espinoza
 *
 */
@Service
public class PositionService {

	/**
	 * Repositorio de las posiciones.
	 */
	@Autowired
	private PositionRepository positionRepository;

	/**
	 * Persiste un listado de posiciones.
	 * 
	 * @param positions Listado de posiciones.
	 * @return Posiciones persistidas.
	 */
	public List<PositionEntity> saveAll(List<PositionEntity> positions) {
		return positionRepository.saveAll(positions);
	}

}
