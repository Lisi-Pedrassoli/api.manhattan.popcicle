package com.manhattan.demo.Services.Seller;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Seller.SellerRequestDto;
import com.manhattan.demo.Dtos.Seller.SellerResponseDto;
import com.manhattan.demo.Dtos.Seller.SellerUpdateDto;
import com.manhattan.demo.Entities.Seller.SellerEntity;
import com.manhattan.demo.Entities.Seller.SellerMapper;
import com.manhattan.demo.Exceptions.Seller.InvalidDocumentException;
import com.manhattan.demo.Exceptions.Seller.SellerNotFoundException;
import com.manhattan.demo.Repositories.Seller.SellerRepository;
import com.manhattan.demo.Utils.DocumentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    @Autowired
    private SellerRepository repository;

    public List<SellerResponseDto> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return SellerMapper.toDtoList(this.repository.findAll(pageable));
    }

    public List<SellerResponseDto> getReport(Boolean status){
        return SellerMapper.toDtoList(this.repository.findAllByAtivo(status));
    }

    public List<SellerResponseDto> findActive(){
        return SellerMapper.toDtoList(this.repository.findAllByAtivo(true));
    }

    public SellerResponseDto findByIdMap(String id){
        return SellerMapper.toDto(this.repository.findById(id).orElseThrow(SellerNotFoundException::new));
    }

    public SellerEntity findById(String id){
        return this.repository.findById(id).orElseThrow(SellerNotFoundException::new);
    }

    public SellerResponseDto save(SellerRequestDto body){
        if(!DocumentValidator.isValidCPF(body.cpf())) throw new InvalidDocumentException();
        return SellerMapper.toDto(
                this.repository.save(new SellerEntity(body.telefone(), body.recebimento(), body.cpf(), body.nome()))
        );
    }

    public SellerResponseDto update(String id, SellerUpdateDto body){
        SellerEntity seller = this.findById(id);
        seller.setAtivo(body.ativo());
        seller.setNome(body.nome());
        seller.setTelefone(body.telefone());
        seller.setRecebimento(body.recebimento());
        return SellerMapper.toDto(this.repository.save(seller));
    }

    public void delete(String id){
        SellerEntity seller = this.findById(id);
        seller.setAtivo(false);
        this.repository.save(seller);
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }
}
