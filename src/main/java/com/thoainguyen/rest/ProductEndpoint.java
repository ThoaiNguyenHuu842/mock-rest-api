package com.thoainguyen.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductEndpoint {
  private static Integer currentId = 7;
  private static List<Product> products = new ArrayList<>(List.of(
    new Product.ProductBuilder().name("Milk").category("Drink").companyId(1).id(1).build(),
    new Product.ProductBuilder().name("Tea").category("Drink").companyId(2).id(2).build(),
    new Product.ProductBuilder().name("Rice").category("Food").companyId(2).id(3).build(),
    new Product.ProductBuilder().name("Lemon").category("Food").companyId(1).id(4).build(),
    new Product.ProductBuilder().name("Coffee").category("Drink").companyId(2).id(5).build(),
    new Product.ProductBuilder().name("Milk tea").category("Drink").companyId(1).id(6).build(),
    new Product.ProductBuilder().name("Water").category("Drink").companyId(1).id(7).build()
  ));

  @GetMapping("/company/{companyId}/products")
  public ResponseEntity<List<Product>> getProduces(@PathVariable Integer companyId, @RequestParam(required = false) String category, @RequestParam(required = false) String name) {
    return ResponseEntity.ok().body(products.stream()
      .filter(c -> c.getCompanyId().equals(companyId)
        && (StringUtils.isEmpty(category) || c.getCategory().equals(category))
        && (StringUtils.isEmpty(name) || c.getName().toLowerCase().indexOf(name.toLowerCase()) >= 0)
      )
      .collect(Collectors.toList())
    );
  }

  @GetMapping("/company/{companyId}/categories")
  public ResponseEntity<Set<String>> getCategories(@PathVariable Integer companyId) {
    return ResponseEntity.ok().body(Set.of("Drink","Food","Electronic ","Book"));
  }

  @PostMapping("/company/{companyId}/products")
  public ResponseEntity createProduct(@RequestBody Product product, @PathVariable Integer companyId) {
    product.setId(++currentId);
    product.setCompanyId(companyId);
    products.add(product);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/company/{companyId}/products/{id}")
  public ResponseEntity updateProduct(@RequestBody Product request, @PathVariable Integer id) {
    Product product = products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    product.setCategory(request.getCategory());
    product.setName(request.getName());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/company/{companyId}/products/{id}")
  public ResponseEntity deleteProduct(@PathVariable Integer id) {
    products = products.stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
    return ResponseEntity.ok().build();
  }
}
