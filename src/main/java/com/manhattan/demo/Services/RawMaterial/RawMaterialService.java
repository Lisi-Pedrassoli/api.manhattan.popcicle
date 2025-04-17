package com.manhattan.demo.Services.RawMaterial;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.RawMaterial.RawMaterialRequestDto;
import com.manhattan.demo.Dtos.RawMaterial.RawMaterialUpdateDto;
import com.manhattan.demo.Dtos.RawMaterial.RawMaterialResponseDto;
import com.manhattan.demo.Entities.RawMaterial.RawMaterialEntity;
import com.manhattan.demo.Entities.RawMaterial.RawMaterialMapper;
import com.manhattan.demo.Exceptions.RawMaterial.RawMaterialNotFoundException;
import com.manhattan.demo.Repositories.RawMaterial.RawMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {
    @Autowired
    private RawMaterialRepository repository;

    public RawMaterialResponseDto save(RawMaterialRequestDto body){
        return RawMaterialMapper.toDto(
                this.repository.save(new RawMaterialEntity(body.quantidadeEstoque(), body.nome(), body.unidadeMedida()))
        );
    }

    public RawMaterialEntity saveRaw(RawMaterialEntity body){
        return this.repository.save(body);
    }

    public List<RawMaterialResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return RawMaterialMapper.toDtoList(this.repository.findAll(pageable));
    }

    public RawMaterialResponseDto findByIdMap(String id){
        return RawMaterialMapper.toDto(this.repository.findById(id).orElseThrow(RawMaterialNotFoundException::new));
    }

    public RawMaterialEntity findById(String id){
        return this.repository.findById(id).orElseThrow(RawMaterialNotFoundException::new);
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }

    public RawMaterialResponseDto update(RawMaterialUpdateDto body, String id){
        RawMaterialEntity rawMaterial = this.findById(id);
        rawMaterial.setAtivo(body.ativo());
        rawMaterial.setNome(body.nome());
        rawMaterial.setUnidadeMedida(body.unidadeMedida());
        rawMaterial.setQuantidadeEstoque(body.quantidadeEstoque());
        return RawMaterialMapper.toDto(this.repository.save(rawMaterial));
    }

    public void delete(String id){
        RawMaterialEntity rawMaterial = this.findById(id);
        rawMaterial.setAtivo(false);
        this.repository.save(rawMaterial);
    }

    public List<RawMaterialResponseDto> findReport(int start, int end){
        return this.repository.findByQuantity(start, end).stream().map(RawMaterialMapper::toDto).toList();
    }
}
