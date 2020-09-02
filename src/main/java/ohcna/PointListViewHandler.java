package ohcna;

import ohcna.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PointListViewHandler {


    @Autowired
    private PointListRepository pointListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRoomCreated_then_CREATE_1 (@Payload RoomCreated roomCreated) {
        try {
            if (roomCreated.isMe()) {
                // view 객체 생성
                PointList pointList = new PointList();
                // view 객체에 이벤트의 Value 를 set 함
                pointList.setId(roomCreated.getId());
                pointList.setRoomName(roomCreated.getName());
                pointList.setRoomFloor(roomCreated.getFloor());
                pointList.setPoint(0);
                // view 레파지 토리에 save
                pointListRepository.save(pointList);
                System.out.println("##### whenRoomCreated_then_CREATE_1 >> "+pointList.getId()+" <<< SUCCESS");
            }else{
                System.out.println("##### whenRoomCreated_then_CREATE_1 >> "+roomCreated.getId()+" <<< IS NOT ME >>>" +roomCreated.eventType.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

/*
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointCreated_then_UPDATE_1(@Payload PointCreated pointCreated) {
        try {
            if (pointCreated.isMe()) {
                System.out.println("##### whenPointCreated_then_UPDATE_1 >> "+pointCreated.getId()+" <<<  Start");
                // view 객체 조회
                Optional<PointList> pointListOptional = pointListRepository.findById(pointCreated.getId());
                if( pointListOptional.isPresent()) {
                    PointList pointList = pointListOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    pointList.setPoint(pointCreated.getPoint());
                       // view 레파지 토리에 save
                    pointListRepository.save(pointList);
                    System.out.println("##### whenPointCreated_then_UPDATE_1 >> "+pointList.getId()+" <<<   point: >>>"+pointList.getPoint() +"<<<<<");
                }else{
                    System.out.println("##### whenPointCreated_then_UPDATE_1 >> "+pointCreated.getId()+" <<<  NOT EXISTS");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointSaved_then_UPDATE_1(@Payload PointSaved pointSaved) {
        try {
            if (pointSaved.isMe()) {
                System.out.println("##### whenPointSaved_then_UPDATE_1 >> "+pointSaved.getId()+" <<<  Start");
                // view 객체 조회
                Optional<PointList> pointListOptional = pointListRepository.findById(pointSaved.getId());
                if( pointListOptional.isPresent()) {
                    PointList pointList = pointListOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    pointList.setPoint(pointSaved.getPoint());
                    // view 레파지 토리에 save
                    pointListRepository.save(pointList);
                    System.out.println("##### whenPointSaved_then_UPDATE_1 >> "+pointList.getId()+" <<<   point: >>>"+pointList.getPoint() +"<<<<<");
                }else{
                    System.out.println("##### whenPointSaved_then_UPDATE_1 >> "+pointSaved.getId()+" <<<  not exist");
                }
            }else{
                System.out.println("##### whenPointSaved_then_UPDATE_1 >> "+pointSaved.getId()+" <<<  IS NOT ME  >>>" +pointSaved.eventType.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointDeleted_then_DELETE_1(@Payload PointDeleted pointDeleted) {
        try {
            if (pointDeleted.isMe()) {
                // view 레파지 토리에 삭제 쿼리
                pointListRepository.deleteById(pointDeleted.getId());
                System.out.println("##### whenPointDeleted_then_DELETE_1 >> "+pointDeleted.getId()+" <<< SUCCESS");
            }else{
                System.out.println("##### whenPointDeleted_then_DELETE_1 >> "+pointDeleted.getId()+" <<<  IS NOT ME  >>>"+pointDeleted.eventType.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}