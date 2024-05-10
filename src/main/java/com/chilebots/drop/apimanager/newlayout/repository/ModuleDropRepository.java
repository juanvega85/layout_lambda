package com.chilebots.drop.apimanager.newlayout.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chilebots.drop.apimanager.newlayout.model.entity.ModuleDropEntity;

/**
 * Repositorio del módulo de drop.
 * 
 * @author Matías Espinoza
 *
 */
@Repository
@Transactional
public interface ModuleDropRepository extends JpaRepository<ModuleDropEntity, Integer> {

	/**
	 * Busca el id de un módulo de drop por su serie.
	 * 
	 * @param serie Serie del módulo de drop.
	 * @return Id del módulo de drop.
	 */
	@Query("SELECT m.id FROM ModuleDropEntity m WHERE m.serie = ?1")
	public int findIdBySerie(String serie);
}
