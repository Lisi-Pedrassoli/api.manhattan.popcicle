package com.manhattan.demo.Services.Sale;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Sale.CloseSaleDto;
import com.manhattan.demo.Dtos.Sale.SaleRequestDto;
import com.manhattan.demo.Entities.Product.ProductEntity;
import com.manhattan.demo.Entities.Sale.SaleEntity;
import com.manhattan.demo.Entities.SaleProduct.SaleProductEntity;
import com.manhattan.demo.Enums.Sale.SaleStatus;
import com.manhattan.demo.Exceptions.Product.NotEnoughStockException;
import com.manhattan.demo.Exceptions.Sale.CantCancelSaleException;
import com.manhattan.demo.Exceptions.Sale.InconsistentProductQuantityException;
import com.manhattan.demo.Exceptions.Sale.SaleNotFoundException;
import com.manhattan.demo.Exceptions.Sale.SaleProductNotFoundException;
import com.manhattan.demo.Repositories.Sale.SaleRepository;
import com.manhattan.demo.Repositories.SaleProduct.SaleProductRepository;
import com.manhattan.demo.Services.Product.ProductService;
import com.manhattan.demo.Services.Seller.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository repository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SaleProductRepository saleProductRepository;

    public SaleEntity save(SaleRequestDto body){
        SaleEntity sale = new SaleEntity();

        if(body.vendedorId().isPresent()){
            sale.setVendedor(this.sellerService.findById(body.vendedorId().get()));
        }

        List<SaleProductEntity> listaProdutos = body.produtoVenda()
                .stream().map(saleProductRequestDto -> {
                    ProductEntity product = this.productService.findById(saleProductRequestDto.productId());
                    if(!product.isAtivo() || product.getEstoque() < saleProductRequestDto.quantidadeSaida()){
                        throw new NotEnoughStockException();
                    }

                    product.setEstoque(product.getEstoque() - saleProductRequestDto.quantidadeSaida());
                    this.productService.saveRaw(product);

                    return new SaleProductEntity(sale, product.getTipoProduto().getValor(),
                            product.getTipoProduto().getTipo(), saleProductRequestDto.quantidadeSaida(),
                            product.getNome(), product.getId()
                    );
                }).toList();

        sale.setProdutoVenda(listaProdutos); // aqui calcula o valor da venda
        sale.setTotal(listaProdutos.stream()
                .map(saleProductEntity -> saleProductEntity.getValor() * saleProductEntity.getQuantidadeSaida())
                .reduce(0.0f, Float::sum));

        System.out.println("Teste branch commit intelij 3");

        return this.repository.save(sale);
    }

    public List<SaleEntity> getReport(Long start, Long end){
        return this.repository.findByDate(start, end);
    }

    public List<SaleEntity> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return this.repository.findAll(pageable).getContent().stream().toList();
    }

    public SaleEntity findById(String saleId){
        return this.repository.findById(saleId).orElseThrow(SaleNotFoundException::new);
    }

    public SaleProductEntity findSeleProductById(String saleId){
        return this.saleProductRepository.findById(saleId).orElseThrow(SaleNotFoundException::new);
    }

    public SaleEntity close(String saleId, CloseSaleDto body) {
        SaleEntity sale = this.findById(saleId);

        if (!sale.getStatus().equals(SaleStatus.OPENED)) {
            throw new CantCancelSaleException();
        }

        List<SaleProductEntity> listaProdutos = body.produtosVenda().stream().map(saleProductCloseDto -> {
            SaleProductEntity saleProductEntity = this.saleProductRepository.findById(
                    saleProductCloseDto.produtoVendaId()).orElseThrow(SaleProductNotFoundException::new);

            if(saleProductEntity.getQuantidadeSaida() < saleProductCloseDto.quantidadeVolta()){
                throw new InconsistentProductQuantityException();
            }

            saleProductEntity.setQuantidadeVolta(saleProductCloseDto.quantidadeVolta());

            return this.saleProductRepository.save(saleProductEntity);
        }).toList();

        sale.getProdutoVenda().clear();
        sale.getProdutoVenda().addAll(listaProdutos);
        sale.setStatus(SaleStatus.CLOSED);

        return this.repository.save(sale);
    }

    public void cancel(String saleId){
        SaleEntity sale = this.findById(saleId);

        if(!sale.getStatus().equals(SaleStatus.OPENED)){
            throw new CantCancelSaleException();
        }

        sale.getProdutoVenda().forEach(saleProductEntity -> {
            ProductEntity product = this.productService.findById(saleProductEntity.getReferenciaProduto());
            product.setEstoque(product.getEstoque() + saleProductEntity.getQuantidadeSaida());
        });
        sale.setStatus(SaleStatus.CANCELED);
        this.repository.save(sale);
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }
}
