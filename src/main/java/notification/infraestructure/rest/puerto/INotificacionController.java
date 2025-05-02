package notification.infraestructure.rest.puerto;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.ApiOperation;

import domain.dto.NotificacionDTO;

@RequestMapping("/notificacion")
public interface INotificacionController {
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(nickname = "notificacion", value = "Notificacion")
	public void sendNotificacion(@RequestBody NotificacionDTO request);
}
