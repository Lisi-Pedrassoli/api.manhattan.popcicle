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
import com.manhattan.demo.Services.Log.LogService;
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
    @Autowired
    private LogService logService;

    public SaleEntity save(SaleRequestDto body, String usuarioId){
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

        sale.setProdutoVenda(listaProdutos);
        sale.setTotal(listaProdutos.stream()
                .map(saleProductEntity -> saleProductEntity.getValor() * saleProductEntity.getQuantidadeSaida())
                .reduce(0.0f, Float::sum));

        SaleEntity savedSale = this.repository.save(sale);

        logService.registrar(usuarioId, "Criação de venda", "Venda ID: " + savedSale.getId());

        return savedSale;
    }

    public List<SaleEntity> getReport(Long start, Long end){
        return this.repository.findByDate(start, end);
    }

    public List<SaleEntity> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return this.repository.findAll(pageable).getContent();
    }

    public SaleEntity findById(String saleId){
        return this.repository.findById(saleId).orElseThrow(SaleNotFoundException::new);
    }

    public SaleProductEntity findSeleProductById(String saleId){
        return this.saleProductRepository.findById(saleId).orElseThrow(SaleNotFoundException::new);
    }

    public SaleEntity close(String saleId, CloseSaleDto body, String usuarioId){
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
        sale.setTotal(listaProdutos.stream()
                .map(saleProductEntity -> saleProductEntity.getValor() *
                        (saleProductEntity.getQuantidadeSaida() - saleProductEntity.getQuantidadeVolta()))
                .reduce(0.0f, Float::sum));
        sale.setStatus(SaleStatus.CLOSED);

        SaleEntity updated = this.repository.save(sale);

        logService.registrar(usuarioId, "Fechamento de venda", "Venda ID: " + sale.getId());


        return updated;
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

        logService.registrar("sistema", "Cancelamento de venda", "Venda ID: " + sale.getId());
    }
    public SaleEntity update(String saleId, SaleRequestDto body, String usuarioId) {
        SaleEntity sale = this.findById(saleId);

        if (!sale.getStatus().equals(SaleStatus.OPENED)) {
            throw new RuntimeException("Só é possível atualizar vendas com status ABERTO");
        }

        // Atualizar vendedor, se informado
        if (body.vendedorId().isPresent()) {
            sale.setVendedor(this.sellerService.findById(body.vendedorId().get()));
        }

        // Atualizar os produtos da venda
        // Primeiro restaurar o estoque dos produtos antigos
        sale.getProdutoVenda().forEach(saleProduct -> {
            ProductEntity product = this.productService.findById(saleProduct.getReferenciaProduto());
            product.setEstoque(product.getEstoque() + saleProduct.getQuantidadeSaida());
            this.productService.saveRaw(product);
        });

        // Remover produtos antigos da venda
        sale.getProdutoVenda().clear();
        this.saleProductRepository.deleteAllByVenda(sale);

        // Adicionar novos produtos da requisição, atualizando estoque
        List<SaleProductEntity> newSaleProducts = body.produtoVenda()
                .stream().map(dto -> {
                    ProductEntity product = this.productService.findById(dto.productId());

                    if (!product.isAtivo() || product.getEstoque() < dto.quantidadeSaida()) {
                        throw new NotEnoughStockException();
                    }

                    product.setEstoque(product.getEstoque() - dto.quantidadeSaida());
                    this.productService.saveRaw(product);

                    return new SaleProductEntity(sale,
                            product.getTipoProduto().getValor(),
                            product.getTipoProduto().getTipo(),
                            dto.quantidadeSaida(),
                            product.getNome(),
                            product.getId());
                }).toList();

        sale.getProdutoVenda().addAll(newSaleProducts);

        // Recalcular total
        sale.setTotal(newSaleProducts.stream()
                .map(p -> p.getValor() * p.getQuantidadeSaida())
                .reduce(0.0f, Float::sum));

        SaleEntity updatedSale = this.repository.save(sale);

        logService.registrar(usuarioId, "Atualização de venda", "Venda ID: " + updatedSale.getId());

        return updatedSale;
    }


    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }
}
