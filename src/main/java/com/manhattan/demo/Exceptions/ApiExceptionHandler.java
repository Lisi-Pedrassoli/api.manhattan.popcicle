package com.manhattan.demo.Exceptions;

import com.manhattan.demo.Exceptions.Authorization.InconsistentPasswordException;
import com.manhattan.demo.Exceptions.Authorization.LoginFailedException;
import com.manhattan.demo.Exceptions.Config.ProblemDetails;
import com.manhattan.demo.Exceptions.Customer.CustomerNotFoundException;
import com.manhattan.demo.Exceptions.Product.NotEnoughStockException;
import com.manhattan.demo.Exceptions.Product.ProductAlreadyHasRecipeException;
import com.manhattan.demo.Exceptions.Product.ProductNotFoundException;
import com.manhattan.demo.Exceptions.ProductType.ProductTypeNotFoundException;
import com.manhattan.demo.Exceptions.Production.ProductionNotFoundException;
import com.manhattan.demo.Exceptions.Production.SameStatusException;
import com.manhattan.demo.Exceptions.RawMaterial.InsufficientStockException;
import com.manhattan.demo.Exceptions.RawMaterial.RawMaterialNotFoundException;
import com.manhattan.demo.Exceptions.Report.ReportException;
import com.manhattan.demo.Exceptions.Sale.CantCancelSaleException;
import com.manhattan.demo.Exceptions.Sale.InconsistentProductQuantityException;
import com.manhattan.demo.Exceptions.Sale.SaleNotFoundException;
import com.manhattan.demo.Exceptions.Sale.SaleProductNotFoundException;
import com.manhattan.demo.Exceptions.Seller.InvalidDocumentException;
import com.manhattan.demo.Exceptions.Seller.SellerNotFoundException;
import com.manhattan.demo.Exceptions.User.UserAlreadyExistsException;
import com.manhattan.demo.Exceptions.User.UserNotFoundException;
import com.manhattan.demo.Utils.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            DataIntegrityViolationException.class,
            MethodArgumentNotValidException.class,
            ConversionFailedException.class,
            MissingRequestHeaderException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ProblemDetails> handleException(Exception ex, HttpServletRequest request) {
        ProblemDetails problemDetails = ExceptionUtil.getProblemDetails(request, ex);
        return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ProblemDetails> handleException(LoginFailedException ex, HttpServletRequest request) {
        String title = "Não foi possível realizar a autenticação";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetails);
    }

    @ExceptionHandler(InconsistentPasswordException.class)
    public ResponseEntity<ProblemDetails> handleException(InconsistentPasswordException ex, HttpServletRequest request) {
        String title = "Não foi possível realizar o cadastro";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(problemDetails);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(UserNotFoundException ex, HttpServletRequest request) {
        String title = "Usuário não encontrado";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetails> handleException(UserAlreadyExistsException ex, HttpServletRequest request) {
        String title = "Não foi possível realizar o cadastro";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(problemDetails);
    }

    @ExceptionHandler(ProductTypeNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(ProductTypeNotFoundException ex, HttpServletRequest request) {
        String title = "Tipo de produto não encontrado";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(ProductNotFoundException ex, HttpServletRequest request) {
        String title = "Produto não encontrado";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(RawMaterialNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(RawMaterialNotFoundException ex, HttpServletRequest request) {
        String title = "Matéria-prima não encontrada";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(ProductionNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(ProductionNotFoundException ex, HttpServletRequest request) {
        String title = "Produção não encontrada";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ProblemDetails> handleException(InsufficientStockException ex, HttpServletRequest request) {
        String title = "Estoque insuficiente";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetails);
    }

    @ExceptionHandler(SameStatusException.class)
    public ResponseEntity<ProblemDetails> handleException(SameStatusException ex, HttpServletRequest request) {
        String title = "Status inválido";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetails);
    }

    @ExceptionHandler(ReportException.class)
    public ResponseEntity<ProblemDetails> handleException(ReportException ex, HttpServletRequest request) {
        String title = "Erro ao gerar relatório";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetails);
    }

    @ExceptionHandler(InvalidDocumentException.class)
    public ResponseEntity<ProblemDetails> handleException(InvalidDocumentException ex, HttpServletRequest request) {
        String title = "Doumento inválido";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetails);
    }

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(SellerNotFoundException ex, HttpServletRequest request) {
        String title = "Vendedor não encontrado";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(CustomerNotFoundException ex, HttpServletRequest request) {
        String title = "Cliente não encontrado";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(SaleNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(SaleNotFoundException ex, HttpServletRequest request) {
        String title = "Venda não encontrada";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(SaleProductNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleException(SaleProductNotFoundException ex, HttpServletRequest request) {
        String title = "Produto venda não encontrado";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetails);
    }

    @ExceptionHandler(ProductAlreadyHasRecipeException.class)
    public ResponseEntity<ProblemDetails> handleException(ProductAlreadyHasRecipeException ex, HttpServletRequest request) {
        String title = "Não foi possível criar a receita";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(problemDetails);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ProblemDetails> handleException(NotEnoughStockException ex, HttpServletRequest request) {
        String title = "Não foi possível realizar a venda";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetails);
    }

    @ExceptionHandler(CantCancelSaleException.class)
    public ResponseEntity<ProblemDetails> handleException(CantCancelSaleException ex, HttpServletRequest request) {
        String title = "Não foi possível atualizar a venda";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(problemDetails);
    }

    @ExceptionHandler(InconsistentProductQuantityException.class)
    public ResponseEntity<ProblemDetails> handleException(InconsistentProductQuantityException ex, HttpServletRequest request) {
        String title = "Não foi possível fechar a venda";
        String detail = ex.getMessage();
        ProblemDetails problemDetails = new ProblemDetails(title,
                HttpStatus.NOT_ACCEPTABLE.value(),
                HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(problemDetails);
    }
}
