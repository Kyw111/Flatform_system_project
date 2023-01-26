package com.system.Flatform.api;


import com.system.Flatform.ticket.dto.TicketCreateDTO;
import com.system.Flatform.ticket.dto.TicketReplyCreateDTO;
import com.system.Flatform.ticket.dto.TicketReplyUpdateDTO;
import com.system.Flatform.ticket.dto.TicketUpdateDTO;
import com.system.Flatform.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.system.Flatform.utils.Constants.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketAPI {

    final static Logger logger = LogManager.getLogger(TicketAPI.class);
    private final TicketService ticketService;

    /**
     * 티켓 등록(저장) API
     * @param ticketCreateDTO
     * @return
     */
    @PostMapping
    public ResponseEntity createTicket(@RequestBody TicketCreateDTO ticketCreateDTO) {
        ticketService.createTicket(ticketCreateDTO);
        logger.log(Level.ALL, ">>>> log test");
        log.fatal(">>> test fatal");
        log.error(">>> test error");
        log.warn(">>> test warn");
        log.info(">>> test info");
        log.debug(">>> test debug");
        log.trace(">>> test trace");
        return ResponseEntity.ok().body(CREATE_SUCCESS);
    }

    /**
     * 티켓 수정 API
     * @param ticketUpdateDTO
     * @return
     */
    @PutMapping
    public ResponseEntity updateTicket(@RequestBody TicketUpdateDTO ticketUpdateDTO) {
        ticketService.updateTicket(ticketUpdateDTO);
        return ResponseEntity.ok().body(UPDATE_SUCCESS);
    }

    /**
     * 티켓 삭제 API
     * @param ticketIds
     * @return
     */
    @DeleteMapping("/{ticketIds}")
    public ResponseEntity deleteTickets(@PathVariable("ticketIds") List<Long> ticketIds) {
        ticketService.deleteTicket(ticketIds);
        return ResponseEntity.ok().body(DELETE_SUCCESS);
    }

    /**
     * 티켓 목록 조회 API
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity ticketList(Pageable pageable,
                                     @RequestParam(required = false, defaultValue = "") String ticketName,
                                     @RequestParam(required = false, defaultValue = "") String address) {
        return ResponseEntity.ok().body(ticketService.ticketList(pageable, ticketName, address));
    }

    /**
     * 티켓 상세 조회 API
     * @param ticketId
     * @return
     */
    @GetMapping("/{ticketId}")
    public ResponseEntity ticketDetail(@PathVariable("ticketId") Long ticketId) {
        return ResponseEntity.ok().body(ticketService.ticketDetail(ticketId));
    }

    /**
     * 티켓 답글 등록 API
     * @param ticketReplyCreateDTO
     * @return
     */
    @PostMapping("/reply")
    public ResponseEntity createTicketReply(@RequestBody TicketReplyCreateDTO ticketReplyCreateDTO) {
        ticketService.createTicketReply(ticketReplyCreateDTO);
        return ResponseEntity.ok().body(CREATE_REPLY_SUCCESS);
    }

    /**
     * 티켓 답글 수정 API
     * @param ticketReplyUpdateDTO
     * @return
     */
    @PutMapping("/reply")
    public ResponseEntity updateTicketReply(@RequestBody TicketReplyUpdateDTO ticketReplyUpdateDTO) {
        ticketService.updateTicketReply(ticketReplyUpdateDTO);
        return ResponseEntity.ok().body(UPDATE_SUCCESS);
    }

    /**
     * 티켓 답글 삭제 API
     * @param ticketReplyIds
     * @return
     */
    @DeleteMapping("/reply/{ticketReplyIds}")
    public ResponseEntity deleteTicketReply(@PathVariable("ticketReplyIds") List<Long> ticketReplyIds) {
        ticketService.deleteTicketReply(ticketReplyIds);
        return ResponseEntity.ok().body(DELETE_SUCCESS);
    }
}
