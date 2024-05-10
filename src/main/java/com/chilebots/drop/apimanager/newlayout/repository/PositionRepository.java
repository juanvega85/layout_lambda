package com.chilebots.drop.apimanager.newlayout.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chilebots.drop.apimanager.newlayout.model.entity.PositionEntity;

/**
 * repositior de las posiciones.
 * 
 * @author Matías Espinoza
 *
 */
@Repository
@Transactional
public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {

}
