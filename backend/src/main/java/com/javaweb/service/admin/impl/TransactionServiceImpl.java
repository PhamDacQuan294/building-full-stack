package com.javaweb.service.admin.impl;

import com.javaweb.converter.TransactionDTOConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.TransactionStatus;
import com.javaweb.model.request.transaction.TransactionRequestDTO;
import com.javaweb.model.request.transaction.TransactionSearchRequestDTO;
import com.javaweb.model.response.transaction.TransactionDetailResponseDTO;
import com.javaweb.model.response.transaction.TransactionResponseDTO;
import com.javaweb.repository.admin.CustomerRepository;
import com.javaweb.repository.admin.TransactionRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TransactionDTOConverter transactionDTOConverter;

  @Override
  public List<TransactionResponseDTO> findAll(TransactionSearchRequestDTO request) {
    List<TransactionEntity> entities = transactionRepository.findAll(request);
    List<TransactionResponseDTO> result = new ArrayList<>();

    for (TransactionEntity entity : entities) {
      result.add(transactionDTOConverter.toTransactionResponseDTO(entity));
    }

    return result;
  }

  @Override
  public int countTotalItems(TransactionSearchRequestDTO request) {
    return transactionRepository.countTotalItems(request);
  }

  @Override
  public void createTransaction(TransactionRequestDTO request) {
    CustomerEntity customer = customerRepository.findById(request.getCustomerId())
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    UserEntity staff = userRepository.findById(request.getStaffId())
      .orElseThrow(() -> new RuntimeException("Không tìm thấy staff"));

    TransactionEntity entity = new TransactionEntity();
    entity.setCustomer(customer);
    entity.setStaff(staff);
    entity.setTransactionType(request.getTransactionType());
    entity.setTransactionStatus(request.getTransactionStatus());
    entity.setContent(request.getContent());

    try {
      if (request.getTransactionDate() != null && !request.getTransactionDate().isBlank()) {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getTransactionDate());
        entity.setTransactionDate(date);
      }
    } catch (Exception e) {
      throw new RuntimeException("Sai định dạng ngày giao dịch");
    }

    transactionRepository.save(entity);
  }

  @Override
  public void updateTransaction(Long id, TransactionRequestDTO request) {
    TransactionEntity entity = transactionRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

    CustomerEntity customer = customerRepository.findById(request.getCustomerId())
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    UserEntity staff = userRepository.findById(request.getStaffId())
      .orElseThrow(() -> new RuntimeException("Không tìm thấy staff"));

    entity.setCustomer(customer);
    entity.setStaff(staff);
    entity.setTransactionType(request.getTransactionType());
    entity.setTransactionStatus(request.getTransactionStatus());
    entity.setContent(request.getContent());

    try {
      if (request.getTransactionDate() != null && !request.getTransactionDate().isBlank()) {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getTransactionDate());
        entity.setTransactionDate(date);
      }
    } catch (Exception e) {
      throw new RuntimeException("Sai định dạng ngày giao dịch");
    }

    transactionRepository.save(entity);
  }

  @Override
  public void deleteTransaction(Long id) {
    TransactionEntity entity = transactionRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

    transactionRepository.delete(entity);
  }

  @Override
  public TransactionDetailResponseDTO getTransactionDetail(Long id) {
    TransactionEntity entity = transactionRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

    return transactionDTOConverter.toTransactionDetailResponseDTO(entity);
  }

  @Override
  public void updateTransactionStatus(Long id, String status) {
    TransactionEntity entity = transactionRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

    entity.setTransactionStatus(TransactionStatus.valueOf(status));
    transactionRepository.save(entity);
  }
}