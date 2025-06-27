package com.unb.fair_management_system.fair.list;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.fair.Fair;
import com.unb.fair_management_system.fair.FairRepository;
import com.unb.fair_management_system.starter.mediator.Handler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ListFairsHandler implements Handler<EmptyRequest, List<Fair>> {

  private final FairRepository fairRepository;

  @Override
  public ResponseEntity<List<Fair>> handle(final EmptyRequest request) {
    return ResponseEntity.ok(fairRepository.findAll());
  }
}
