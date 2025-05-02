package com.arqsoft.medici.infrastructure.rest.puerto;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arqsoft.medici.domain.dto.NotificacionDTO;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/notificacion")
public interface INotificacionController {
    @PostMapping(path = "/usuario", 
    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "usuario", value = "Notificacion usuario")
	public void sendNotificacionUsuario(@RequestBody NotificacionDTO request);
    
    @PostMapping(path = "/vendedor", 
    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "vendedor", value = "Notificacion vendedor")
	public void sendNotificacionVendedor(@RequestBody NotificacionDTO request);
}
