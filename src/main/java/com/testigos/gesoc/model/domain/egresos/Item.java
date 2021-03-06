package com.testigos.gesoc.model.domain.egresos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.el.parser.BooleanNode;
import com.testigos.gesoc.model.domain.persistentes.EntidadPersistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends EntidadPersistente {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "egreso_id")
    private @Getter @Setter Egreso egreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presupuesto_id")
    private @Setter @Getter Presupuesto presupuesto;

    @Column
    private @Getter @Setter String producto;

    @Column
    private @Getter @Setter double valorUnitario;

    @Column
    private @Getter @Setter int cantidad;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private final @Getter List<Categoria> categorizacion = new ArrayList<>();

    public Item(String producto, double valorUnitario, int cantidad) {
        this.producto = producto;
        this.valorUnitario = valorUnitario;
        this.cantidad = cantidad;
    }

    public double valorTotal() {
        return cantidad * valorUnitario;
    }

    public Boolean categorizar(Categoria cat) {
        if (!tieneCriterio(cat)) {
            categorizacion.add(cat);
            return true;
        } else return false;
    }

    private boolean tieneCriterio(Categoria cat) {
        return categorizacion.stream().anyMatch(cate -> cat.perteneceACriterio(cate.getCriterio()));
    }

    public String criterios() {
        return categorizacion.stream().map(c -> c.getNombre()).reduce((s1,s2) -> s1.concat(", " + s2)).get().concat(".");
    }
}
