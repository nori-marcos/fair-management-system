package com.unb.fair_management_system.visitor.list;

import com.unb.fair_management_system.commons.EmptyRequest;
import com.unb.fair_management_system.starter.mediator.Handler;
import com.unb.fair_management_system.visitor.Visitor;
import com.unb.fair_management_system.visitor.VisitorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ListVisitorsHandler implements Handler<EmptyRequest, List<VisitorResponse>> {

  private final VisitorRepository visitorRepository;

  @Override
  public ResponseEntity<List<VisitorResponse>> handle(final EmptyRequest request) {
    final List<Visitor> visitors = visitorRepository.findAll();
    final List<VisitorResponse> response =
        visitors.stream()
            .map(
                visitor ->
                    new VisitorResponse(
                        visitor.getId(),
                        visitor.getContactName(),
                        visitor.getContactEmail(),
                        visitor.getFair().getName()))
            .toList();

    return ResponseEntity.ok(response);
  }
}
