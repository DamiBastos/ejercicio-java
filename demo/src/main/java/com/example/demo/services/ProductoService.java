package com.example.demo.services;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.models.Producto;
import com.example.demo.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    @Transactional
    public ProductoDTO crearProducto(ProductoDTO productoDTO){

        Producto producto = new Producto(
                productoDTO.getId(),
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getCantidad()
        );


        Producto productoGuardado = productoRepository.save(producto);
        return productoGuardado.toDTO();
    }
    @Transactional
    public ResponseEntity<ProductoDTO> actualizarProducto(Long id, ProductoDTO productoDTO) {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if(!productoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();

            // Actualiza el producto
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setCantidad(productoDTO.getCantidad());
            productoRepository.save(producto);

            // Devuelvo el DTO del producto actualizado con código de estado 200
            return ResponseEntity.ok(new ProductoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getCantidad()
            ));
        } else {
            // Si no se encuentra el producto devuelve 404 (NOT FOUND)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<ProductoDTO> buscarPorId(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();

            // Devuelve el DTO del producto con código de estado 200
            return ResponseEntity.ok(new ProductoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getCantidad()
            ));
        } else {
            // Si no se encuentra el producto, devuelvo un código de estado 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public List<ProductoDTO> buscarPorNombre(String nombre) {
        List<Producto> productos = productoRepository.findByNombreCustomQuery(nombre);

        return productos.stream()
                .map(Producto::toDTO)
                .collect(Collectors.toList());
    }
    public ResponseEntity<String> eliminar(Long id) {
        try {
            Optional<Producto> productoOptional = productoRepository.findById(id);

            if(productoOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún producto. No se pudo eliminar el producto.");
            }

            productoRepository.deleteById(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún producto con ID " + id + ". No se realizó ninguna eliminación.");
        } catch (Exception e) {
            // Manejar otras excepciones si es necesario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al intentar eliminar el producto con ID " + id);
        }
    }

    public List<ProductoDTO> listarProductos(){
        List<Producto> productosOrdenados = productoRepository.findAll(Sort.by(Sort.Direction.ASC, "precio"));

        return productosOrdenados.stream()
                .map(Producto::toDTO)
                .collect(Collectors.toList());
    }


}
