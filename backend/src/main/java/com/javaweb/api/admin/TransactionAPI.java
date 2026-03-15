package com.javaweb.api.admin;

import com.javaweb.model.request.transaction.TransactionRequestDTO;
import com.javaweb.model.request.transaction.TransactionSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.transaction.TransactionDetailResponseDTO;
import com.javaweb.model.response.transaction.TransactionResponseDTO;
import com.javaweb.service.admin.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/transactions")
public class TransactionAPI {

  @Autowired
  private TransactionService transactionService;

  @GetMapping
  public ResponseDTO<?> getTransactions(TransactionSearchRequestDTO request) {
    List<TransactionResponseDTO> transactions = transactionService.findAll(request);
    int totalItems = transactionService.countTotalItems(request);

    Map<String, Object> result = new HashMap<>();
    result.put("transactions", transactions);
    result.put("totalItems", totalItems);

    ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
    response.setData(result);
    response.setMessage("success");
    response.setDetail("Lấy danh sách giao dịch thành công");
    return response;
  }

  @PostMapping("/create")
  public ResponseDTO<?> createTransaction(@RequestBody TransactionRequestDTO request) {
    transactionService.createTransaction(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Thêm giao dịch thành công");
    return response;
  }

  @PutMapping("/edit/{id}")
  public ResponseDTO<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO request) {
    transactionService.updateTransaction(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật giao dịch thành công");
    return response;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseDTO<?> deleteTransaction(@PathVariable Long id) {
    transactionService.deleteTransaction(id);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Xóa giao dịch thành công");
    return response;
  }

  @GetMapping("/{id}")
  public ResponseDTO<?> getTransactionDetail(@PathVariable Long id) {
    TransactionDetailResponseDTO item = transactionService.getTransactionDetail(id);

    ResponseDTO<TransactionDetailResponseDTO> response = new ResponseDTO<>();
    response.setData(item);
    response.setMessage("success");
    response.setDetail("Lấy chi tiết giao dịch thành công");
    return response;
  }

  @PutMapping("/{id}/status")
  public ResponseDTO<?> updateTransactionStatus(@PathVariable Long id, @RequestParam String status) {
    transactionService.updateTransactionStatus(id, status);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật trạng thái giao dịch thành công");
    return response;
  }
}