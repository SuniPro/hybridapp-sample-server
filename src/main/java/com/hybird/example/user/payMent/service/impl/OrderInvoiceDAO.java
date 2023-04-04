package com.hybird.example.user.payMent.service.impl;

import com.hybird.example.cmmn.mapper.OracleMapper;
import com.hybird.example.user.payMent.vo.CancelTicketVO;
import com.hybird.example.user.payMent.vo.MemberBasketVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import com.hybird.example.user.payMent.vo.TicketInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@OracleMapper("OrderInvoiceDAO")
public interface OrderInvoiceDAO {
    int selectBasketCount(@Param("list") List<MemberBasketVO> list, @Param("priceGrace") String priceGrace) throws Exception;
    List<OrderTicketVO> listBasketGoods(@Param("list") List<MemberBasketVO> list, @Param("priceGrace") String priceGrace) throws Exception;
    String getOrderNoMakeSeq() throws Exception;
    String getOrderSequence() throws Exception;
    String getOrderDetailSequence() throws Exception;
    int insertShopOrder(Map<String, Object> map) throws Exception;
    int insertShopOrderDetail(Map<String, Object> map) throws Exception;
    int insertPGRequest(Map<String, Object> map) throws Exception;
    int insertCertification(Map<String, Object> map) throws Exception;
    int insertApprovalRequest(Map<String, Object> map) throws Exception;
    int insertNetCancel(Map<String, Object> map) throws Exception;
    int insertPGResult(Map<String, Object> map) throws Exception;
    int insertShopPayment(Map<String, Object> map) throws Exception;
    int insertShopPaymentDetail(Map<String, Object> map) throws Exception;
    int insertShopPaymentQrcode(Map<String, Object> map) throws Exception;
    int insertItrevActivity(Map<String, Object> map) throws Exception;
    int insertItqrTicketDtl(Map<String, Object> map) throws Exception;
    String getQRCode() throws Exception;
    List<TicketInformation> listUnitTicketGoods(@Param("ticketSeq")  String ticketSeq, @Param("priceGrade") String priceGrade) throws Exception;
    List<TicketInformation> listPackageTicketGoods(@Param("ticketSeq")  String ticketSeq, @Param("priceGrade") String priceGrade) throws Exception;
    CancelTicketVO getCancelTicket(@Param("orderSequence")  String orderSequence, @Param("orderDetailSequence") String orderDetailSequence) throws Exception;
    int selectTicketUsedCount(@Param("orderSequence")  String orderSequence, @Param("orderDetailSequence") String orderDetailSequence) throws Exception;
    List<String> listTicketQRCode(@Param("orderSequence")  String orderSequence, @Param("orderDetailSequence") String orderDetailSequence) throws Exception;
    String selectQRCode(@Param("orderSequence")  String orderSequence, @Param("orderDetailSequence") String orderDetailSequence) throws Exception;
    int insertClientCancelRequest(Map<String, Object> map) throws Exception;
    int insertPGCancelRequest(Map<String, Object> map) throws Exception;
    int insertCancelResult(Map<String, Object> map) throws Exception;
    String getCancelSequence() throws Exception;
    int updatePaymentForCancel(Map<String, Object> map) throws Exception;
    int updatePaymentDetailForCancel(Map<String, Object> map) throws Exception;
    int updateItrevActivityForCancel(Map<String, Object> map) throws Exception;
    /* ### 간편결제 관련 시작 */
    List<Map<String, Object>> listSimpleCard(@Param("userId") String userId) throws Exception;
    Map<String, Object> getBillCardSelect(@Param("userId") String userId, @Param("billSeq") String billSeq) throws Exception;
    int insertBillKeyRequest(Map<String, Object> map) throws Exception;
    int insertBillKeyResponse(Map<String, Object> map) throws Exception;
    int updateBillKeyResponse(Map<String, Object> map) throws Exception;
    int getBillCardCount(@Param("cardName")  String cardName, @Param("cardNo") String cardNo, @Param("userId") String userId) throws Exception;
    int insertBillingResult(Map<String, Object> map) throws Exception;
    int deleteShopBasket(OrderTicketVO ticketVO) throws Exception;
    List<OrderTicketVO> listRefundPaid() throws Exception;
}
