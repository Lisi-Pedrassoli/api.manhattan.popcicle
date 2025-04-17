package com.manhattan.demo.Services.Production;

import com.manhattan.demo.Dtos.General.CountResponseDto;
import com.manhattan.demo.Dtos.Production.ProductionDto;
import com.manhattan.demo.Entities.Product.ProductEntity;
import com.manhattan.demo.Entities.Production.ProductionEntity;
import com.manhattan.demo.Entities.ProductionRecipe.ProductionRecipeEntity;
import com.manhattan.demo.Entities.RawMaterial.RawMaterialEntity;
import com.manhattan.demo.Entities.Recipe.RecipeEntity;
import com.manhattan.demo.Exceptions.Production.ProductionNotFoundException;
import com.manhattan.demo.Exceptions.Production.SameStatusException;
import com.manhattan.demo.Exceptions.RawMaterial.InsufficientStockException;
import com.manhattan.demo.Exceptions.Report.ReportException;
import com.manhattan.demo.Repositories.Production.ProductionRepository;
import com.manhattan.demo.Services.Product.ProductService;
import com.manhattan.demo.Services.ProductionRecipe.ProductionRecipeService;
import com.manhattan.demo.Services.RawMaterial.RawMaterialService;
import com.manhattan.demo.Services.Recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductionService {
    @Autowired
    private ProductionRepository repository;
    @Autowired
    private ProductionRecipeService productionRecipeService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RawMaterialService rawMaterialService;
    @Autowired
    private ProductService productService;

    public ProductionEntity save(ProductionDto body){
        ProductionEntity production = this.repository.save(new ProductionEntity(body.dataAtual(), body.vencimento()));

        body.receita().forEach(current -> {
            RecipeEntity recipe = recipeService.findById(current.receitaId());
            this.productionRecipeService.save(
                    new ProductionRecipeEntity(
                            current.quantidade(),
                            production,
                            recipe
                    )
            );
            recipe.getReceitaMateriaPrima().forEach(currentRmp -> {
                RawMaterialEntity rawMaterialEntity = currentRmp.getMateriaPrima();
                float newQuantity = rawMaterialEntity.getQuantidadeEstoque() - currentRmp.getQuantidadeMP();

                if (newQuantity < 0) {
                    throw new InsufficientStockException(rawMaterialEntity.getNome());
                }

                rawMaterialEntity.setQuantidadeEstoque(newQuantity);
                rawMaterialService.saveRaw(rawMaterialEntity);
            });
            ProductEntity productEntity = recipe.getProduto();
            productEntity.setEstoque(productEntity.getEstoque() + current.quantidade());
        });
        return this.findById(production.getId());
    }

    public ProductionEntity findById(String id){
        return this.repository.findById(id).orElseThrow(ProductionNotFoundException::new);
    }

    public List<ProductionEntity> findAll(int page, int items){
        Pageable pageable = PageRequest.of(page, items);
        return this.repository.findAll(pageable).getContent();
    }

    public List<ProductionEntity> findReport(String start, String end){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate localDateStart = LocalDate.parse(start, formatter);
            LocalDate localDateEnd = LocalDate.parse(end, formatter);

            java.sql.Date startDate = java.sql.Date.valueOf(localDateStart);
            java.sql.Date endDate = java.sql.Date.valueOf(localDateEnd);

            return this.repository.findByDate(startDate, endDate);
        } catch (Exception e){
            throw new ReportException();
        }
    }

    public CountResponseDto count(){
        return new CountResponseDto(this.repository.count());
    }

    public ProductionEntity updateStatus(String id, boolean status){
        ProductionEntity production = this.findById(id);

        if(production.isAtivo() == status){
            throw new SameStatusException();
        }

        if(status){
            this.activateProduction(production);
        } else {
            this.inactivateProduction(production);
        }

        production.setAtivo(status);
        this.repository.save(production);
        return this.findById(production.getId());
    }

    public void activateProduction(ProductionEntity production){
        productionRecipeService.findByProduction(production).forEach(pre -> {
            RecipeEntity recipe = pre.getReceita();
            ProductEntity product = recipe.getProduto();

            recipe.getReceitaMateriaPrima().forEach(rmp -> {
                RawMaterialEntity rawMaterial = rmp.getMateriaPrima();

                float quantity = rmp.getQuantidadeMP() * pre.getQuantidadeProduzida();

                if(rawMaterial.getQuantidadeEstoque() < quantity){
                    throw new InsufficientStockException(rawMaterial.getNome());
                }

                float newQuantity = rmp.getQuantidadeMP() * pre.getQuantidadeProduzida();
                rawMaterial.setQuantidadeEstoque(rawMaterial.getQuantidadeEstoque() - newQuantity);
                rawMaterialService.saveRaw(rawMaterial);
            });
            product.setEstoque(product.getEstoque() + pre.getQuantidadeProduzida());
            productService.saveRaw(product);
        });
    }

    public void inactivateProduction(ProductionEntity production){
        productionRecipeService.findByProduction(production).forEach(pre -> {
            RecipeEntity recipe = pre.getReceita();
            ProductEntity product = recipe.getProduto();

            product.setEstoque(product.getEstoque() - pre.getQuantidadeProduzida());
            productService.saveRaw(product);

            recipe.getReceitaMateriaPrima().forEach(rmp -> {
                RawMaterialEntity rawMaterial = rmp.getMateriaPrima();
                float newQuantity = rmp.getQuantidadeMP() * pre.getQuantidadeProduzida();
                rawMaterial.setQuantidadeEstoque(rawMaterial.getQuantidadeEstoque() + newQuantity);
                rawMaterialService.saveRaw(rawMaterial);
            });
        });
    }
}
