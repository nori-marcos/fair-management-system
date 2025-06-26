package com.unb.fair_management_system.starter.mediator;

import org.springframework.http.ResponseEntity;

public interface Handler<Request, Response> {
  ResponseEntity<Response> handle(Request request);
}
