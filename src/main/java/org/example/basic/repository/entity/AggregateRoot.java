package org.example.basic.repository.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {
    // 도메인 이벤트들을 저장하는 이벤트 저장소
    private final List<Object> domainEvents = new ArrayList<>();
    // 도메인 이벤트 추가
    // protected로 선언되어 있어 AggregateRoot를 상속한 클래스만 사용 가능
    protected void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }
    // 등록된 이벤트 목록을 읽을 수 있도록 제공하는 읽기 전용 getter
    public List<Object> getDomainEvents() { return Collections.unmodifiableList(domainEvents); }
    // 이벤트 처리 후, 저장된 이벤트들을 모두 삭제하는 메서드
    public void clearDomainEvents() { this.domainEvents.clear(); }

}
