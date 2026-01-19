package site.codecrew.world.master.adapter.messaging;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import site.codecrew.kafka.KafkaUtil;
import site.codecrew.jpa.event.inbox.InboxEventProcessor;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberEventConsumer {

    private final InboxEventProcessor inboxEventProcessor;

    @KafkaListener(
        topics = "${app.kafka.topics.member-events}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        log.info(">>> [Batch Start] 배치 개수: {}", records.size());

        for (ConsumerRecord<String, String> record : records) {
            String eventId = KafkaUtil.getHeader(record, "eventId");
            String eventType = KafkaUtil.getHeader(record, "eventType");

            try {
                inboxEventProcessor.process(eventId, eventType, record.value());
                log.debug("처리 완료 - eventId: {}, eventType: {}", eventId, eventType);
            } catch (Exception e) {
                log.error("처리 실패 - eventId: {}, eventType: {}", eventId, eventType, e);
                // TODO: DLQ 처리
            }
        }

        ack.acknowledge();
        log.info(">>> [Batch End] 커밋 완료");
    }
}

//결론부터 말하면 **"네, 하나의 도메인(Aggregate Root) 당 하나의 토픽을 쓰는 것이 국룰(표준)"**입니다.예를 들어 Member 도메인이라면 member.created, member.updated 처럼 쪼개는 게 아니라, outbox.member라는 하나의 토픽에 다 때려 넣습니다.그 이유는 "순서 보장(Ordering)" 때문입니다. 이것이 카프카를 쓰는 가장 큰 이유이자 제약 사항입니다.1. 왜 하나로 합쳐야 하나요? (순서 보장)카프카는 "같은 토픽, 같은 파티션" 안에서만 순서를 보장합니다.만약 토픽을 이벤트 종류별로 쪼개버리면 아래와 같은 대참사가 발생합니다.상황:회원 가입 (MemberCreated -> 토픽 A)닉네임 변경 (MemberUpdated -> 토픽 B)문제: 네트워크 지연 등으로 컨슈머가 **토픽 B(변경)**를 **토픽 A(가입)**보다 먼저 읽어버릴 수 있습니다.결과: "없는 회원의 닉네임을 바꾸라고?" -> 에러 발생 (데이터 정합성 깨짐)2. 그럼 이벤트 구분은 어떻게 해요?그래서 아까 우리가 **헤더(Header)나 바디(Payload)**에 eventType을 넣은 것입니다.Topic: outbox.member (물리적 분류)Key: member_id (순서 보장용)Value:JSON{
//    "eventType": "MemberUpdatedEvent",  <-- 이걸 보고 로직 분기 (Switch 문)
//    "payload": { ... }
//}
//3. 요약 (Best Practice)구분전략예시TopicAggregate 단위로 1개outbox.member, outbox.orderPartitionAggregate ID 기준 해시member_id=100은 무조건 0번 파티션으로RoutingConsumer 내부에서 분기if (type == "Created") ... else if (type == "Updated") ...결론: 지금 하신 것처럼 aggregate_type("member") 기준으로 하나의 토픽으로 몰아넣는 것이 가장 정확한 설계입니다.