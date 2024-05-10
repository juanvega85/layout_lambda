package com.chilebots.drop.apimanager.newlayout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chilebots.drop.apimanager.newlayout.repository.ModuleDropRepository;
import com.chilebots.drop.apimanager.newlayout.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio del módulo.
 * 
 * @author Matías Espinoza
 *
 */
@Service
@Slf4j
public class ModuleDropService {

	/**
	 * Nombre de la clase.
	 */
	private static final String CLASS = Constants.LAMBDA_NAME + "[ModuleDropService]";

	/**
	 * Repositorio del módulo
	 */
	@Autowired
	private ModuleDropRepository moduleDropRepository;

	/**
	 * Obtiene el Id del módulo por su serie.
	 * 
	 * @param serie Serie del módulo.
	 * @return Id del módulo.
	 */
	public int getIdBySerie(String serie) {
		final String METHOD = CLASS + "[getIdBySerie] ";
		int moduleId = moduleDropRepository.findIdBySerie(serie);

		try {
			moduleId = moduleDropRepository.findIdBySerie(serie);
			log.info(METHOD + "modulo '{}' encontrado con Id: '{}'", serie, moduleId);
		} catch (Exception e) {
			log.error(METHOD + "modulo '{}' no fue encontrado", serie);
			moduleId = 0;
		}

		return moduleId;
	}

}
