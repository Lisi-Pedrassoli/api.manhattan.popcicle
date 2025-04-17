package com.manhattan.demo.Services.Product;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Product.ProductRequestDto;
import com.manhattan.demo.Dtos.Product.ProductResponseDto;
import com.manhattan.demo.Dtos.Product.ProductUpdateDto;
import com.manhattan.demo.Entities.Product.ProductEntity;
import com.manhattan.demo.Entities.Product.ProductMapper;
import com.manhattan.demo.Entities.ProductType.ProductTypeEntity;
import com.manhattan.demo.Exceptions.Product.ProductNotFoundException;
import com.manhattan.demo.Repositories.Product.ProductRepository;
import com.manhattan.demo.Services.ProductType.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeService productTypeService;

    public ProductResponseDto save(ProductRequestDto body){
        ProductTypeEntity productType = this.productTypeService.findById(body.tipoProdutoId());
        return ProductMapper.toDto(
                this.productRepository.save(new ProductEntity(body.nome(), body.estoque(), productType))
        );
    }

    public ProductEntity saveRaw(ProductEntity product){
        return this.productRepository.save(product);
    }

    public List<ProductResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return ProductMapper.toDtoList(this.productRepository.findAll(pageable));
    }

    public List<ProductResponseDto> findAllWithoutRecipe(){
        return ProductMapper.toDtoList(this.productRepository.findAllByRecipeIsNull());
    }

    public ProductResponseDto findByIdMap(String id){
        return ProductMapper.toDto(this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new));
    }

    public ProductEntity findById(String id){
        return this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public void delete(String id){
        ProductEntity product = this.findById(id);
        product.setAtivo(false);
        this.productRepository.save(product);
    }

    public ProductResponseDto update(ProductUpdateDto body, String id){
        ProductEntity product = this.findById(id);
        ProductTypeEntity productType = this.productTypeService.findById(body.tipoProdutoId());
        product.setAtivo(body.ativo());
        product.setNome(body.nome());
        product.setEstoque(body.estoque());
        product.setTipoProduto(productType);
        return ProductMapper.toDto(this.productRepository.save(product));
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.productRepository.count());
    }

    public List<ProductResponseDto> findReport(int start, int end){
        return this.productRepository.findByQuantity(start, end).stream().map(ProductMapper::toDto).toList();
    }
}
