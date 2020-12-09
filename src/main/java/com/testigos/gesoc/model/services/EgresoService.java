package com.testigos.gesoc.model.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.testigos.gesoc.model.domain.egresos.Egreso;
import com.testigos.gesoc.model.domain.egresos.EgresoConPresupuestos;
import com.testigos.gesoc.model.domain.entidades.Entidad;
import com.testigos.gesoc.persistence.DAOEgreso;
import com.testigos.gesoc.persistence.DAOEgresoCP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EgresoService {

    @Autowired
    private DAOEgreso repoEgresos;

    @Autowired
    private DAOEgresoCP repoEgresosCP;

    // PERSIST
    public void persist(List<Egreso> egresos) {
        egresos.stream().forEach(e -> repoEgresos.persist(e));
    }

    public void persist(Egreso egreso) {
        repoEgresos.persist(egreso);
    }

    public void persist(EgresoConPresupuestos eg) {
        repoEgresosCP.persist(eg);
    }

    // FIND
    public Egreso findEgreso(int egreso) {
        return repoEgresos.find(egreso);
    }

    public Egreso findEgresoCP(int egreso) {
        return repoEgresosCP.find(egreso);
    }

    public Egreso findEgresoGeneral(int egreso) {
        Egreso eg = findEgreso(egreso);
        if (eg == null)
            eg = findEgresoCP(egreso);
        return eg;
    }

    public EgresoConPresupuestos findConPresupuestos(int id) {
        return repoEgresosCP.findConPresupuestos(id);
    }

    // FIND ALL SIN NADA

    public List<Egreso> findEgresos(Entidad entidad) {
        return repoEgresos.findAll().stream().filter(e -> e.getComprador().getId() == entidad.getId())
                .collect(Collectors.toList());
    }
    // public List<EgresoConPresupuestos> findEgresosCP(Entidad entidad) {
    // return repoEgresosCP.findAll().stream().filter(e -> e.getComprador().getId()
    // == entidad.getId())
    // .collect(Collectors.toList());
    // }
    //
    // public List<Egreso> findAll(Entidad entidad) {
    // List<Egreso> egresos = findEgresos(entidad);
    // egresos.addAll(findEgresosCP(entidad));
    // return egresos;
    // }

    // FIND ALL CON PROVEEDOR
    public List<Egreso> findEgresosConProveedor(Entidad entidad) {
        return repoEgresos.findAllConProveedor().stream().filter(e -> e.getComprador().getId() == entidad.getId())
                .collect(Collectors.toList());
    }

    // public List<EgresoConPresupuestos> findEgresosCPConProveedor(Entidad entidad)
    // {
    // return repoEgresosCP.findAllConProveedor().stream().filter(e ->
    // e.getComprador().getId() == entidad.getId())
    // .collect(Collectors.toList());
    // }

    // public List<Egreso> findAllConProveedor(Entidad entidad) {
    // List<Egreso> egresos = findEgresosConProveedor(entidad);
    // egresos.addAll(findEgresosCPConProveedor(entidad));
    // return egresos;
    // }

    // public List<Egreso> findAll(Entidad entidad) {
    // return repo.findAll().stream().filter(e -> e.getComprador().getId() ==
    // entidad.getId())
    // .collect(Collectors.toList());
    // }

    // FIND EGRESOS NO JUSTIFICADOS

    public List<Egreso> getEgresosNoJustificados(Entidad entidad) {
        return repoEgresos.findAllConIngreso().stream()
                .filter(eg -> eg.getComprador().getId() == entidad.getId() && eg.getIngresoAsociado() == null)
                .collect(Collectors.toList());
    }
    //
    // public List<Egreso> getEgresosCPNoJustificados(Entidad entidad) {
    // return repoEgresos.findAllConIngreso().stream().filter(eg ->
    // eg.getComprador().getId() == entidad.getId() && eg.getIngresoAsociado() ==
    // null)
    // .collect(Collectors.toList());
    // }
    //
    // public List<Egreso> getAllNoJustificados(Entidad entidad) {
    // List<Egreso> egresos = getEgresosNoJustificados(entidad);
    // egresos.addAll(getEgresosCPNoJustificados(entidad));
    // return egresos;
    // }

    // UPDATES

    public void update(List<Egreso> egresos) {
        for (Egreso e : egresos)
            updateEgreso(e);
    }

    public void updateEgreso(Egreso e) {
        if (e instanceof EgresoConPresupuestos)
            repoEgresosCP.update(e);
        else
            repoEgresos.update(e);
    }

    public void updatePresupuesto(EgresoConPresupuestos egresoActual) {
        repoEgresosCP.updatePresupuesto(egresoActual);
    }

    // FIND CON PROVEEDOR E ITEMS

    public List<Egreso> findAllConProveedoreItems(Entidad entidad) {
        return repoEgresos.findAllConProveedoreItems().stream().filter(e -> e.getComprador().getId() == entidad.getId())
                .collect(Collectors.toList());
    }

    public List<Egreso> findEgresosSinDocumentoComercial(Entidad entidad) {
        return repoEgresos.findAllConDocumentoComercial().stream()
                .filter(e -> e.getComprador().getId() == entidad.getId() && e.getDocumento() == null)
                .collect(Collectors.toList());
    }

    // public List<EgresoConPresupuestos> findEgresosCPSinDocumentoComercial(Entidad
    // entidad) {
    // return repoEgresosCP.findAllConDocumentoComercial().stream()
    // .filter(e -> e.getComprador().getId() == entidad.getId() && e.getDocumento()
    // == null)
    // .collect(Collectors.toList());
    // }
    //
    // public List<Egreso> findAllSinDocumentoComercial(Entidad entidad) {
    // List<Egreso> egresos = findEgresosSinDocumentoComercial(entidad);
    // egresos.addAll(findEgresosCPSinDocumentoComercial(entidad));
    // return egresos;
    // }

    public List<EgresoConPresupuestos> getEgresosInvalidosConUsuario() {
        return repoEgresosCP.findAllConUsuario().stream().filter(i -> !i.esValido()).collect(Collectors.toList());
    }

    // METODOS HOME
    // MONTO ACTUAL
    public Double montoActual(Entidad entidad) {
        return repoEgresos.findAllConItems().stream().filter(e -> e.getComprador().getId() == entidad.getId())
                .mapToDouble(Egreso::valorTotal).sum();
    }

    // public Double montoActualEgresosCP(Entidad entidad) {
    // return repoEgresosCP.findAllConItems().stream().filter(e ->
    // e.getComprador().getId() == entidad.getId())
    // .mapToDouble(Egreso::valorTotal).sum();
    // }
    //
    // public Double montoActual(Entidad entidad) {
    // return montoActualEgresos(entidad) + montoActualEgresosCP(entidad);
    // }

    // MONTO MES
    public Double montoMes(Entidad entidad) {
        return repoEgresos.findAllConItems().stream()
                .filter(e -> e.getFechaOperacion().getMonthValue() == LocalDate.now().getMonthValue()
                        && e.getComprador().getId() == entidad.getId())
                .mapToDouble(Egreso::valorTotal).sum();
    }
    // public Double montoMesEgresosCP(Entidad entidad) {
    // return repoEgresosCP.findAllConItems().stream()
    // .filter(e -> e.getFechaOperacion().getMonthValue() ==
    // LocalDate.now().getMonthValue()
    // && e.getComprador().getId() == entidad.getId())
    // .mapToDouble(Egreso::valorTotal).sum();
    // }
    // public Double montoMes(Entidad entidad) {
    // return montoMesEgresos(entidad) + montoMesEgresosCP(entidad);
    // }

    // MONTO ANIO

    public Double montoAnio(Entidad entidad) {
        return repoEgresos.findAllConItems().stream()
                .filter(e -> e.getFechaOperacion().getYear() == LocalDate.now().getYear()
                        && e.getComprador().getId() == entidad.getId())
                .mapToDouble(Egreso::valorTotal).sum();
    }

    // public Double montoAnioEgresosCP(Entidad entidad) {
    // return repoEgresosCP.findAllConItems().stream().filter(e ->
    // e.getFechaOperacion().getYear() == LocalDate.now().getYear()
    // && e.getComprador().getId() ==
    // entidad.getId()).mapToDouble(Egreso::valorTotal).sum();
    // }
    //
    // public Double montoAnio(Entidad entidad) {
    // return montoAnioEgresos(entidad) + montoAnioEgresosCP(entidad);
    // }

    // ULTIMO EGRESO

    public Egreso findUltimoEgreso(Entidad entidad) {
        return repoEgresos.findAllConProveedoreItems().stream().reduce((first, second) -> second)
                .filter(e -> e.getComprador().getId() == entidad.getId()).orElse(null);
    }
    //
    // public Egreso findUltimoEgresoCP(Entidad entidad) {
    // return repoEgresosCP.findAllConProveedoreItems().stream().reduce((first,
    // second) -> second)
    // .filter(e -> e.getComprador().getId() == entidad.getId()).orElse(null);
    // }
    //
    // public Egreso findUltimo(Entidad entidad) {
    //
    // Egreso ultimo_egreso = findUltimoEgreso(entidad);
    // if (ultimos_egresos == null) {
    // ultimos_egresos.sort(Comparator.comparing(Egreso::getFechaOperacion));
    // ultimo_egreso = ultimos_egresos.get(ultimos_egresos.size()-1);
    // }
    //
    // return ultimo_egreso;
    // }
}
