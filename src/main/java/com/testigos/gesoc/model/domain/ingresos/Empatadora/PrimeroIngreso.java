package com.testigos.gesoc.model.domain.ingresos.Empatadora;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.testigos.gesoc.model.domain.egresos.Egreso;
import com.testigos.gesoc.model.domain.ingresos.Ingreso;

public class PrimeroIngreso extends EstrategiaEmpatadora {

    public List<Egreso> empatar(List<Condicion> condiciones, List<Ingreso> ingresos, List<Egreso> egresos) {
        ingresos.sort(Comparator.comparing(Ingreso::getMonto));

        List<Egreso> egresosADevolver = new ArrayList<>();

        for (Egreso eg : egresos) {
            for (Ingreso ing : ingresos) {
                if (eg.valorTotal() <= ing.valorDisponible() && (condiciones == null ||  condiciones.stream().allMatch(cond -> cond.cumpleCondicion(ing, eg)))) {
                    eg.setIngresoAsociado(ing);
                    ing.addEgreso(eg);
                    egresosADevolver.add(eg);
                    break;
                }
            }
        }

        return egresosADevolver;
    }
}
