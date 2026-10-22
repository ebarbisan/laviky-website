package br.com.laviky.repository;

import br.com.laviky.model.EmpresaContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaContatoRepository extends JpaRepository<EmpresaContato, Long> {
}
