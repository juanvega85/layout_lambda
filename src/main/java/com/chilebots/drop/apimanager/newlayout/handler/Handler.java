package com.chilebots.drop.apimanager.newlayout.handler;

import static com.chilebots.drop.libraries.util.droputil.ValidatorUtil.isNull;
import static com.chilebots.drop.libraries.util.droputil.ValidatorUtil.isNullOrEmpty;

import org.springframework.beans.factory.annotation.Autowired;

import com.chilebots.drop.apimanager.newlayout.model.Request;
import com.chilebots.drop.apimanager.newlayout.model.Response;
import com.chilebots.drop.apimanager.newlayout.service.LayoutService;
import com.chilebots.drop.apimanager.newlayout.util.Constants;
import com.chilebots.drop.libraries.util.droputil.ResponseUtil;
import com.chilebots.drop.libraries.util.droputil.models.ValidateState;

import io.quarkus.funqy.Funq;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler de la lambda.
 * 
 * @author Matías Espinoza
 *
 */
@Slf4j
public class Handler {

	/**
	 * Nombre de la clase.
	 */
	private static final String CLASS = Constants.LAMBDA_NAME + "[Handler]";

	/**
	 * Servicio del layout.
	 */
	@Autowired
	private LayoutService layoutService;

	/**
	 * Handler de la lambda.
	 * 
	 * @param request Request de la lambda.
	 * @return Response de la lambda.
	 */
	@Funq
	public Response handler(Request request) {
		final String METHOD = CLASS + "[handler] ";
		Response response = new Response();

		ValidateState requestState = validateRequest(request);
		if (!requestState.isValid()) {
			log.error(METHOD + "error 400 - {}", requestState.getMessage());
			ResponseUtil.setBadRequest(response, requestState.getMessage());
		}

		log.info(METHOD + "inicio - module: {}", request.getModule());
		response = layoutService.generate(request);
		log.info(METHOD + "fin - module: {}", request.getModule());
		return response;
	}

	/**
	 * Valida el request.
	 * 
	 * @param request Request de la lambda.
	 * @return Si es valido.
	 */
	private ValidateState validateRequest(Request request) {
		ValidateState state = new ValidateState();
		final String REQUIRED = " es requerido";

		if (isNull(request))
			state.markInvalid("debe enviar un request valido");
		else if (isNullOrEmpty(request.getModule()))
			state.markInvalid("module" + REQUIRED);
		else if (isNullOrEmpty(request.getLayout()))
			state.markInvalid("layout" + REQUIRED);

		return state;
	}
}
