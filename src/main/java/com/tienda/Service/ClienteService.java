package com.tienda.Service;

import com.tienda.model.Cliente;
import com.tienda.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        validar(cliente);
        // Siempre registra uno nuevo: un "id" en el JSON no debe pisar otro cliente
        cliente.setId(null);
        if (cliente.getDni() != null && repository.existsByDni(cliente.getDni())) {
            throw new ResponseStatusException(CONFLICT, "Ya existe un cliente con DNI " + cliente.getDni());
        }
        return repository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente datos) {
        validar(datos);
        Cliente c = repository.findById(id)
                .orElseThrow(() -> noExiste(id));
        if (datos.getDni() != null && repository.existsByDniAndIdNot(datos.getDni(), id)) {
            throw new ResponseStatusException(CONFLICT, "Ya existe otro cliente con DNI " + datos.getDni());
        }

        c.setNombre(datos.getNombre());
        c.setApellido(datos.getApellido());
        c.setDni(datos.getDni());
        c.setTelefono(datos.getTelefono());
        c.setCorreo(datos.getCorreo());

        return repository.save(c);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw noExiste(id);
        }
        repository.deleteById(id);
    }

    private void validar(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "El nombre del cliente es obligatorio");
        }
        if (cliente.getDni() != null && !cliente.getDni().matches("\\d{8}")) {
            throw new ResponseStatusException(BAD_REQUEST, "El DNI debe tener 8 dígitos");
        }
    }

    private ResponseStatusException noExiste(Long id) {
        return new ResponseStatusException(NOT_FOUND, "Cliente con id " + id + " no existe");
    }
}
