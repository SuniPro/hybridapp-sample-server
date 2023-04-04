package com.hybird.example.user.payMent.service;

import com.hybird.example.user.payMent.vo.CancelTicketVO;
import com.hybird.example.user.payMent.vo.MemberBasketVO;
import com.hybird.example.user.payMent.vo.OrderTicketVO;
import com.hybird.example.user.payMent.vo.TicketInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderInvoiceService {
    int selectBasketCount(final List<MemberBasketVO> list, final String priceGrace) throws Exception;
    List<OrderTicketVO> listBasketGoods(List<MemberBasketVO> list, String priceGrace) throws Exception;
    String getOrderNoMakeSeq() throws Exception;
    String getOrderSequence() throws Exception;
    String getOrderDetailSequence() throws Exception;
    int insertShopOrder(Map<String, Object> map) throws Exception;
    int insertShopOrderDetail(Map<String, Object> map) throws Exception;
    int insertPGRequest(Map<String, Object> map) throws Exception;
    void saveShopOrder(HttpServletRequest request, Map<String, Object> map, Model model, List<OrderTicketVO> orderList) throws Exception;
    String saveShopPayment(HttpServletRequest request, Model model) throws Exception;
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
    List<TicketInformation> listUnitTicketGoods(String ticketSeq, String priceGrade) throws Exception;
    List<TicketInformation> listPackageTicketGoods(String ticketSeq, String priceGrade) throws Exception;
    String cancelPayment(HttpServletRequest request, Model model) throws Exception;
    CancelTicketVO getCancelTicket(String orderSequence, String orderDetailSequence) throws Exception;
    int selectTicketUsedCount(String orderSequence, String orderDetailSequence) throws Exception;
    List<String> listTicketQRCode(String orderSequence, String orderDetailSequence) throws Exception;
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
    Map<String, Object> getBillCardSelect(String userId, String billSeq) throws Exception;
    void billSignUp(HttpServletRequest request, Map<String, Object> map, Model model) throws Exception;
    int insertBillKeyRequest(Map<String, Object> map) throws Exception;
    int insertBillKeyResponse(Map<String, Object> map) throws Exception;
    int updateBillKeyResponse(Map<String, Object> map) throws Exception;
    String billSignUpResponse(HttpServletRequest request, Model model) throws Exception;
    int getBillCardCount(String cardName, String cardNo, String userId) throws Exception;
    String saveShopBillPayment(HttpServletRequest request, Model model) throws Exception;
    int insertBillingResult(Map<String, Object> map) throws Exception;
    int deleteShopBasket(OrderTicketVO ticketVO) throws Exception;

    List<OrderTicketVO> listRefundPaid() throws Exception;
    void doRefundPaid() throws Exception;
    String refundCancelPayment(String orderSequence, String orderDetailSequence) throws Exception;

    int insertPaid(Map<String, Object> map) throws Exception;
    int updatePaid(Map<String, Object> map) throws Exception;
    int selectMaxCancelSequence(@Param("ipPurNo") String ipPurNo) throws Exception;
    int insertPaidVoid(Map<String, Object> map) throws Exception;

    void runExpireDate () throws Exception;
}
