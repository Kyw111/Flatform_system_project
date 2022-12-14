package com.system.Flatform.ticket.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.system.Flatform.ticket.dto.TicketDetailDTO;
import com.system.Flatform.ticket.dto.TicketListDTO;
import com.system.Flatform.ticket.dto.TicketReplyDetailDTO;
import com.system.Flatform.ticket.repository.TicketRepositoryCustom;
import com.system.Flatform.utils.enums.DelYn;
import com.system.Flatform.utils.enums.UseYn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.system.Flatform.ticket.domain.QTicket.ticket;
import static com.system.Flatform.ticket.domain.QTicketReply.ticketReply;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    /**
     * 티켓 삭제
     * @param ticketIds
     */
    @Transactional
    @Override
    public void deleteTickets(List<Long> ticketIds) {
        queryFactory.update(ticket)
                .set(ticket.useYn, UseYn.N)
                .set(ticket.delYn, DelYn.Y)
                .where(ticket.ticketId.in(ticketIds)).execute();

        queryFactory.update(ticketReply) // 티켓 답글과 함께 삭제
                .set(ticketReply.useYn, UseYn.N)
                .set(ticketReply.delYn, DelYn.Y)
                .where(ticketReply.ticket.ticketId.in(ticketIds)).execute();
    }

    /**
     * 티켓 목록 조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Page<TicketListDTO> ticketList(Pageable pageable, String ticketName, String address) {

        List<TicketListDTO> ticketDTOS = queryFactory.select(Projections.constructor(TicketListDTO.class,
                        ticket.ticketId,
                        ticket.ticketName,
                        ticket.address,
                        ticket.period
                ))
                .from(ticket)
                .where(ticket.useYn.eq(UseYn.Y),
                        ticket.delYn.eq(DelYn.N),
                        ticketNameContains(ticketName),
                        addressContains(address))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(ticketDTOS, pageable, ticketDTOS.size());
    }

    /**
     * 티켓 상세 조회
     * @param ticketId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public TicketDetailDTO ticketDetail(Long ticketId) {

        TicketDetailDTO ticketDetailDTO = queryFactory.select(Projections.constructor(TicketDetailDTO.class,
                        ticket.ticketId,
                        ticket.ticketName,
                        ticket.period,
                        ticket.address,
                        ticket.runningTime,
                        ticket.genre,
                        ticket.ticketPrice,
                        ticket.ageToWatch,
                        ticket.ticketInformation
                ))
                .from(ticket)
                .where(ticket.useYn.eq(UseYn.Y),
                        ticket.delYn.eq(DelYn.N),
                        ticket.ticketId.eq(ticketId))
                .fetchOne();

        List<TicketReplyDetailDTO> replyDTOList = queryFactory.select(Projections.constructor(TicketReplyDetailDTO.class,
                        ticketReply.ticketReplyId,
                        ticketReply.content,
                        ticketReply.replyDepth,
                        ticketReply.replyParentId
                ))
                .from(ticketReply)
                .leftJoin(ticket).on(ticketReply.ticket.ticketId.eq(ticket.ticketId))
                .where(ticketReply.useYn.eq(UseYn.Y),
                        ticketReply.delYn.eq(DelYn.N),
                        ticketReply.ticket.ticketId.eq(ticketId))
                .fetch();

        ticketDetailDTO.setTicketReplyList(replyDTOList);

        return ticketDetailDTO;
    }


    /** 이하 QueryDSL 검색 조건 관련 메소드 */

    BooleanExpression ticketNameContains(String ticketName) {
        if (ObjectUtils.isEmpty(ticketName)) return null;
        return ticket.ticketName.contains(ticketName);
    }

    BooleanExpression addressContains(String address) {
        if (ObjectUtils.isEmpty(address)) return null;
        return ticket.address.contains(address);
    }

}
