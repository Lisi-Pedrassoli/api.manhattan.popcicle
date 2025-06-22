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
import com.manhattan.demo.Services.Log.LogService;
import com.manhattan.demo.Services.ProductType.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private LogService logService;

    public ProductResponseDto save(ProductRequestDto body, String usuarioId) {
        ProductTypeEntity productType = this.productTypeService.findById(body.tipoProdutoId());
        ProductEntity produto = new ProductEntity(body.nome(), body.estoque(), productType);
        produto.setAtivo(body.ativo());
        ProductEntity produtoSalvo = this.productRepository.save(produto);

        logService.registrar(usuarioId, "Cadastro de produto", "Produto: " + body.nome());

        return ProductMapper.toDto(produtoSalvo);
    }

    public ProductEntity saveRaw(ProductEntity product){
        return this.productRepository.save(product);
    }

    public List<ProductResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items, Sort.by(Sort.Direction.DESC, "nome"));//ordenando por ordem alfabetica
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

    public void delete(String id, String usuarioId) {
        ProductEntity product = this.findById(id);
        product.setAtivo(false);
        this.productRepository.save(product);

        logService.registrar(
                usuarioId,
                "Desativação de produto",
                "Produto '" + product.getNome() + "' (ID: " + product.getId() + ") foi desativado."
        );
    }

    public ProductResponseDto update(ProductUpdateDto body, String id, String usuarioId){
        ProductEntity product = this.findById(id);
        ProductTypeEntity productType = this.productTypeService.findById(body.tipoProdutoId());
        product.setAtivo(body.ativo());
        product.setNome(body.nome());
        product.setEstoque(body.estoque());
        product.setTipoProduto(productType);
        ProductEntity produtoAtualizado = this.productRepository.save(product);

        logService.registrar(usuarioId, "Atualização de produto", "Produto ID: " + id);

        return ProductMapper.toDto(produtoAtualizado);
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.productRepository.count());
    }

    public List<ProductResponseDto> findReport(int start, int end){
        return this.productRepository.findByQuantity(start, end).stream().map(ProductMapper::toDto).toList();
    }
}
