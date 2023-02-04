package com.system.Flatform.ticket.service.impl;

import com.system.Flatform.ticket.domain.Ticket;
import com.system.Flatform.ticket.domain.TicketReply;
import com.system.Flatform.ticket.dto.*;
import com.system.Flatform.ticket.repository.TicketReplyRepository;
import com.system.Flatform.ticket.repository.TicketRepository;
import com.system.Flatform.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.system.Flatform.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketReplyRepository ticketReplyRepository;


    /**
     * 티켓 등록(저장)
     * @param goodsCreateDTO
     */
    @Transactional
    @Override
    public void createGoods(GoodsCreateDTO goodsCreateDTO) {
        ticketRepository.save(goodsCreateDTO.toEntity());
    }

    /**
     * 티켓 수정
     * @param goodsUpdateDTO
     */
    @Transactional
    @Override
    public void updateGoods(GoodsUpdateDTO goodsUpdateDTO) {
        Ticket ticket = ticketRepository.findById(goodsUpdateDTO.getTicketId()).orElseThrow(()
                -> new IllegalArgumentException(NO_GOODS_INFO_MSG));
        ticket.ticketUpdate(goodsUpdateDTO);
    }

    /**
     * 티켓 삭제
     * @param ticketIds
     */
    @Transactional
    @Override
    public void deleteGoods(List<Long> ticketIds) {
        ticketRepository.deleteTickets(ticketIds);
    }

    /**
     * 티켓 목록 조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<TicketListDTO> goodsList(Pageable pageable, String ticketName, String address) {
        return ticketRepository.ticketList(pageable, ticketName, address);
    }

    /**
     * 티켓 상세 조회
     * @param ticketId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public GoodsDetailDTO goodsDetail(Long ticketId) {
        return ticketRepository.ticketDetail(ticketId);
    }

    /**
     * 티켓 답글 등록(저장)
     * @param goodsReplyCreateDTO
     */
    @Transactional
    @Override
    public void createGoodsReply(GoodsReplyCreateDTO goodsReplyCreateDTO) {
        Ticket ticket = ticketRepository.findById(goodsReplyCreateDTO.getTicketId()).orElseThrow(()
                -> new IllegalArgumentException(NO_GOODS_INFO_MSG));
        TicketReply savedTicketReply = ticketReplyRepository.save(goodsReplyCreateDTO.toEntity(ticket));
        savedTicketReply.setReplyParentId((ticket.getTicketReplyList().size() == 0) ?
                savedTicketReply.getTicketReplyId() :
                ticket.getTicketReplyList().get(ticket.getTicketReplyList().size()-1).getTicketReplyId());
    }

    /**
     * 티켓 답글 수정
     * @param goodsReplyUpdateDTO
     */
    @Transactional
    @Override
    public void updateGoodsReply(GoodsReplyUpdateDTO goodsReplyUpdateDTO) {
        TicketReply ticketReply = ticketReplyRepository.findById(goodsReplyUpdateDTO.getTicketReplyId()).orElseThrow(()
                -> new IllegalArgumentException(NO_GOODS_REPLY_MSG));
        ticketReply.updateReplyContent(goodsReplyUpdateDTO);
    }

    /**
     * 티켓 답글 삭제
     * @param ticketReplyIds
     */
    @Transactional
    @Override
    public void deleteGoodsReply(List<Long> ticketReplyIds) {
        ticketReplyRepository.deleteTicketReply(ticketReplyIds);
    }

}
