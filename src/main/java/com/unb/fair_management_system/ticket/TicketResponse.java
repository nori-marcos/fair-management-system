package com.unb.fair_management_system.ticket;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicketResponse {
  private UUID id;
  private String fairName;
  private String exhibitorName;
  private String visitorName;
  private LocalDateTime issueDate;

  public TicketResponse(
      final UUID id,
      final String fairName,
      final String visitorName,
      final String exhibitorName,
      final LocalDateTime issueDate) {
    this.id = id;
    this.fairName = fairName;
    this.visitorName = visitorName;
    this.exhibitorName = exhibitorName;
    this.issueDate = issueDate;
  }
}
