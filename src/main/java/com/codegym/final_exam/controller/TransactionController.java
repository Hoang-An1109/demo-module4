package com.codegym.final_exam.controller;

import com.codegym.final_exam.dto.TransactionDTO;
import com.codegym.final_exam.model.Customer;
import com.codegym.final_exam.model.Transaction;
import com.codegym.final_exam.payload.request.TransactionRequest;
import com.codegym.final_exam.payload.response.TransactionResponse;
import com.codegym.final_exam.service.ICustomerService;
import com.codegym.final_exam.service.ITransactionService;
import com.codegym.final_exam.service.impl.CustomerService;
import com.codegym.final_exam.service.impl.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class TransactionController {

    private final ITransactionService transactionService;
    private final ICustomerService customerService;

    @ModelAttribute("customers")
    public List<Customer> getAllCustomers()  {
        return customerService.findAll();
    }

    @GetMapping
    public String getHomePage(Model model) {
        List<TransactionDTO> transactions = transactionService.findAll();
        model.addAttribute("transactions", transactions);
        return "/index";
    }

    @GetMapping("/create")
    public String getCreateTransactionPage(Model model) {
        TransactionRequest transactionRequest = new TransactionRequest();
        model.addAttribute("transaction", transactionRequest);
        return "/create";
    }

    @PostMapping("/create")
    public String createTransaction(@Valid @ModelAttribute("transaction") TransactionRequest transactionRequest,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/create";
        }

        transactionService.save(transactionRequest);
        redirectAttributes.addFlashAttribute("message", "Giao dịch đã thêm mới thành công!");
        return "redirect:/home";
    }

    @GetMapping("/detail/{id}")
    public String getTransactionDetail(Model model, @PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        model.addAttribute("transaction", transaction);

        return "/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return "redirect:/home";
    }

    @PostMapping("/search")
    public String searchTransaction(Model model,
                                    @RequestParam(required = false) String searchTypeService,
                                    @RequestParam(required = false) String searchNameCustomer) {
        searchTypeService = (searchTypeService != null && searchTypeService.isEmpty()) ? null : searchTypeService;
        searchNameCustomer = (searchNameCustomer != null && searchNameCustomer.isEmpty()) ? null : searchNameCustomer;

        if (searchTypeService == null && searchNameCustomer == null){
           model.addAttribute("message", "Không có kết quả nào phù hợp!");
        } else if(searchTypeService == null || searchNameCustomer == null) {
            List<TransactionResponse> transactions = transactionService.findAllByTypeServiceOrNameCustomer(searchTypeService, searchNameCustomer);
            model.addAttribute("transactions", transactions);
        } else {
            List<TransactionResponse> transactions = transactionService.findAllByTypeServiceAndNameCustomer(searchTypeService, searchNameCustomer);
            model.addAttribute("transactions", transactions);
        }
        return "/index";
    }

    @GetMapping("/update/{id}")
    public String getUpdateTransactionPage(Model model, @PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        model.addAttribute("transaction", transaction);
        return "/update";
    }

    @PostMapping("/update/{id}")
    public String updateTransaction(@Valid @ModelAttribute("transaction") TransactionRequest transactionRequest,
                                    BindingResult bindingResult,
                                    @PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/update";
        }
        transactionService.update(id, transactionRequest);
        redirectAttributes.addFlashAttribute("message", "Giao dịch đã cập nhật thành công.");
        return "redirect:/home";
    }
}