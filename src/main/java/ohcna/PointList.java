package ohcna;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PointList_table")
public class PointList {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        //private Long roomId;
        private Integer point;
        private String roomName;
        private String roomFloor;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }



        public Integer getPoint() {
            return point;
        }

        public void setPoint(Integer point) {
            this.point = point;
        }
        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }
        public String getRoomFloor() {
            return roomFloor;
        }

        public void setRoomFloor(String roomFloor) {
            this.roomFloor = roomFloor;
        }

}
