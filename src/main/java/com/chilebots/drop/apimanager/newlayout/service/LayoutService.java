package com.chilebots.drop.apimanager.newlayout.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chilebots.drop.apimanager.newlayout.model.KartLayout;
import com.chilebots.drop.apimanager.newlayout.model.Layout;
import com.chilebots.drop.apimanager.newlayout.model.Request;
import com.chilebots.drop.apimanager.newlayout.model.Response;
import com.chilebots.drop.apimanager.newlayout.model.entity.KartEntity;
import com.chilebots.drop.apimanager.newlayout.model.entity.PositionEntity;
import com.chilebots.drop.apimanager.newlayout.util.Constants;
import com.chilebots.drop.libraries.util.droputil.ResponseUtil;
import com.chilebots.drop.libraries.util.droputil.ValidatorUtil;
import com.chilebots.drop.libraries.util.droputil.constants.DropConst;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio del layout
 * 
 * @author Matías Espinoza
 *
 */
@Service
@Slf4j
public class LayoutService {

	/**
	 * Nombre de la clase.
	 */
	private static final String CLASS = Constants.LAMBDA_NAME + "[LayoutService]";

	/**
	 * Servicio del módulo.
	 */
	@Autowired
	private ModuleDropService moduleDropService;

	/**
	 * Servicio de los karts.
	 */
	@Autowired
	private KartService kartService;

	/**
	 * Servicio de las posiciones.
	 */
	@Autowired
	private PositionService positionService;

	/**
	 * Genera el layout.
	 * 
	 * @param request Request de la lambda.
	 * @return Response de la lambda.
	 */
	public Response generate(Request request) {
		final String METHOD = CLASS + "[generate] ";
		Response response = new Response();
		Layout layout = jsonLayoutToObject(request.getLayout());

		// validando si es posible parsear el layout (si el json es valido)
		if (layout == null) {
			log.error(METHOD + "error 500 - no fue posible parsear el layout");
			ResponseUtil.setServerInternalError(response, "no fue posible generar el layout");
			return response;
		}

		// validando que la serie del layout coincida con la rel request
		if (!layout.getDropId().equalsIgnoreCase(request.getModule())) {
			log.error(METHOD + "error 409 - modulo del layout({}) no coincide con el modulo del request({})",
					layout.getDropId(), request.getModule());
			ResponseUtil.setConflict(response, "modulo del layout no coincide con el modulo del request");
			return response;
		}

		// validando que el modulo exista
		int moduleId = moduleDropService.getIdBySerie(request.getModule());
		if (moduleId == DropConst.ZERO) {
			log.error(METHOD + "error 404 - modulo no encontrado");
			ResponseUtil.setNotFound(response, "modulo '" + request.getModule() + "' no encontrado");
			return response;
		}
		List<KartEntity> karts = getKarts(layout.getKarts());
		karts = kartService.saveAll(karts);

		List<PositionEntity> positions = getPositions(layout, request);
		positions = positionService.saveAll(positions);

		String inserts = buildRelationsLayout(karts, positions, layout);

		response.setInserts(inserts);
		ResponseUtil.setOk(response);
		return response;
	}

	/**
	 * Devuelve el listado de inserts de las relaciones.
	 * 
	 * @param karts     Listado de karts.
	 * @param positions Listado de posiciones.
	 * @param layout    Layout.
	 * @return Listado de inserts.
	 */
	private String buildRelationsLayout(List<KartEntity> karts, List<PositionEntity> positions, Layout layout) {
		StringBuilder inserts = new StringBuilder();

		layout.getKarts().forEach(k -> {
			KartEntity kartEntity = karts.stream().filter(kart -> kart.getName().equalsIgnoreCase(k.getName()))
					.findFirst().orElse(null);

			if (!ValidatorUtil.isNull(kartEntity)) {
				k.getPositions().forEach((key, value) -> {
					PositionEntity position = positions.stream().filter(p -> p.getName().equalsIgnoreCase(key))
							.findFirst().orElse(null);

					if (!ValidatorUtil.isNull(position)) {
						String query = "INSERT INTO kart_position (kart_id,position_id,side_kart_position,distance_kart_position) VALUES ({kart},{pos},'{side}',{distance});";
						query = query.replace("{kart}", kartEntity.getId().toString());
						query = query.replace("{pos}", position.getId().toString());
						query = query.replace("{side}", value.getSide());
						query = query.replace("{distance}", value.getDistance().toString());
						inserts.append(query);
					}
				});
			}
		});
		return inserts.toString();
	}

	/**
	 * Listado de posiciones.
	 * 
	 * @param layout Layout a cargar.
	 * @request request Request de la lambda.
	 * @return Listado de posiciones.
	 */
	private List<PositionEntity> getPositions(Layout layout, Request request) {
		Set<String> positionsSet = new HashSet<String>();

		layout.getKarts()
				.forEach(kart -> kart.getPositions().forEach((k, v) -> positionsSet.add(k + "-" + v.getBoxSizeId())));

		List<String> positions = new ArrayList<String>(positionsSet);

		int groupId = request.isCreateGroup() ? /* TODO crear grupo */ 2 : request.getGroupId();

		return positions.stream().map(p -> {
			String[] split = p.split("-");
			String name = split[0];
			String boxSizeId = split[1];

			if (ValidatorUtil.isNullOrEmpty(boxSizeId)) {
				log.error("error 409 - no viene definido el tamano de la caja");
				boxSizeId = "2"; // se setea el tamano mediano
			}

			PositionEntity position = new PositionEntity();
			position.setModuleDropId(Integer.parseInt(request.getModule()));
			position.setName(name);
			position.setPositionGroupId(groupId);
			position.setBoxSizeId(Integer.parseInt(boxSizeId));
			position.setType(getTypePosition(p, layout.getTransfer()));
			position.setCreateAt(new Date());
			return position;
		}).collect(Collectors.toList());
	}

	/**
	 * Devuelve el listado de karts.
	 * 
	 * @param kartsLayout Layout con los karts.
	 * @return Listado de karts.
	 */
	private List<KartEntity> getKarts(List<KartLayout> kartsLayout) {
		return kartsLayout.stream().map(k -> {
			KartEntity entity = new KartEntity();
			entity.setName(k.getName());
			entity.setType(k.getType());
			entity.setLength(k.getLength());
			entity.setCreateAt(new Date());
			return entity;
		}).collect(Collectors.toList());
	}

	/**
	 * Obtiene el tipo de la posición para el layout.
	 * 
	 * @param position  Posición.
	 * @param transfers Listado de posiciones que son transferencias.
	 * @return Devuele el tipo de una posición.
	 */
	private String getTypePosition(String position, List<String> transfers) {
		final String TOTEM_D = "TD";
		final String TOTEM_I = "TI";

		if (position.contains(TOTEM_D) || position.contains(TOTEM_I))
			return "lift";
		else if (transfers.contains(position))
			return "transfer";
		else
			return "kart";
	}

	/**
	 * transforma un json string a un objeto para el layout
	 * 
	 * @param layout Layout del módulo.
	 * @return Json en formato objeto.
	 */
	private Layout jsonLayoutToObject(String layout) {
		final String METHOD = CLASS + "[jsonLayoutToObject] ";
		Layout json = null;

		try {
			Gson gson = new Gson();
			json = gson.fromJson(layout, Layout.class);
			log.info(METHOD + "json parseado con exito");
			return json;
		} catch (Exception ex) {
			log.error(METHOD + "error al generar el layout", ex);
			return json;
		}
	}

}
