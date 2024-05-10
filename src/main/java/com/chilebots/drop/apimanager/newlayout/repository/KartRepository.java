package com.chilebots.drop.apimanager.newlayout.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chilebots.drop.apimanager.newlayout.model.entity.KartEntity;

/**
 * Repositorio de los karts.
 * 
 * @author Matías Espinoza
 *
 */
@Repository
@Transactional
public interface KartRepository extends JpaRepository<KartEntity, Integer> {

}
