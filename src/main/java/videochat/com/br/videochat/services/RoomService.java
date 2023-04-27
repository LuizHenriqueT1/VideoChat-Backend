package videochat.com.br.videochat.services;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.video.v1.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoomService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;


    public String createRoom(String nameRoom) {
        Twilio.init(accountSid, authToken);
        Room room = Room.creator()
                .setType(Room.RoomType.PEER_TO_PEER)
                .setMaxParticipantDuration(60)
                .setUniqueName(nameRoom)
                .create();
        return room.getSid();
    }

    public String closeRoom(String sid) {
        Twilio.init(accountSid, authToken);
        Room room = Room.updater(
            sid, Room.RoomStatus.COMPLETED)
                .update();
        return room.getUniqueName();
    }

    public Set<String> getCalls(String nameRoom) {
        Twilio.init(accountSid, authToken);
        ResourceSet<Room> rooms = Room.reader()
                .setStatus(Room.RoomStatus.COMPLETED)
                .setUniqueName(nameRoom)
                .limit(20)
                .read();

        Set<String> list = new HashSet<>();
        for (Room room : rooms) {
            list.add(room.getSid());
        }
        return list;
    }
}
