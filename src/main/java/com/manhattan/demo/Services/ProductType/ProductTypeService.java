package com.manhattan.demo.Services.ProductType;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeRequestDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeResponseDto;
import com.manhattan.demo.Dtos.ProductType.ProductTypeUpdateDto;
import com.manhattan.demo.Entities.ProductType.ProductTypeEntity;
import com.manhattan.demo.Entities.ProductType.ProductTypeMapper;
import com.manhattan.demo.Exceptions.ProductType.ProductTypeNotFoundException;
import com.manhattan.demo.Repositories.ProductType.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductTypeResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return ProductTypeMapper.toDtoList(this.productTypeRepository.findAll(pageable));
    }

    public ProductTypeResponseDto findByIdMap(String id){
        return ProductTypeMapper.toDto(this.productTypeRepository.findById(id).orElseThrow(ProductTypeNotFoundException::new));
    }

    public ProductTypeEntity findById(String id){
        return this.productTypeRepository.findById(id).orElseThrow(ProductTypeNotFoundException::new);
    }

    public ProductTypeResponseDto save(ProductTypeRequestDto body){
        return ProductTypeMapper.toDto(
                this.productTypeRepository.save(new ProductTypeEntity(body.tipo(), body.valor())));
    }

    public ProductTypeResponseDto update(String id, ProductTypeUpdateDto body){
        ProductTypeEntity productTypeEntity = this.findById(id);
        productTypeEntity.setTipo(body.tipo());
        productTypeEntity.setValor(body.valor());
        productTypeEntity.setAtivo(body.ativo());
        return ProductTypeMapper.toDto(this.productTypeRepository.save(productTypeEntity));
    }

    public void delete(String id){
        ProductTypeEntity productTypeEntity = this.findById(id);
        productTypeEntity.setAtivo(false);
        this.productTypeRepository.save(productTypeEntity);
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.productTypeRepository.count());
    }
}
