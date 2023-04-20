package videochat.com.br.videochat.controllers;

import com.twilio.base.ResourceSet;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videochat.com.br.videochat.services.RoomService;
import com.twilio.rest.video.v1.Room;

import java.util.Set;

@RestController
@RequestMapping(value = "/v1/Rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping(value = "/create-room/{nameRoom}")
    public String createRoom(@PathVariable String nameRoom) {
        return roomService.createRoom(nameRoom);
    }
    @PostMapping("close-room")
    public ResponseEntity<String> closeRoom(@RequestBody String sid) {
        roomService.closeRoom(sid);
        String uniqueName = roomService.closeRoom(sid);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Custom-Header", "Custom-Value")
                .body("Sala fechada com sucesso." + uniqueName);
    }

    @GetMapping(value = "/{nameRoom}")
    public ResponseEntity<Set<String>> getRoomsWithName(@PathVariable String nameRoom) {
        Set<String> list = roomService.getCalls(nameRoom);
        return ResponseEntity.ok().body(list);
    }
}
