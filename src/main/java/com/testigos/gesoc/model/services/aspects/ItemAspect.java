package com.testigos.gesoc.model.services.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ItemAspect {

    // @Autowired
    // private RegistroRepository repo;
    //
    // @AfterReturning("execution(*
    // com.testigos.gesoc.model.services.ItemService.persist(..))")
    // public void registerPersist(JoinPoint joinPoint) {
    // Item item = (Item) joinPoint.getArgs()[0];
    // repo.save(new Registro(TipoRegistro.ALTA, Item.class.getSimpleName(), "Se
    // agrego un item referido al egreso: " + item.get() + ", referida a "+
    // egreso.getDescripcion()));
    // }
    //
    // @AfterReturning("execution(*
    // com.testigos.gesoc.model.services.ItemService.update(..))")
    // public void registerUpdate(JoinPoint joinPoint) {
    // Item item = (Item) joinPoint.getArgs()[0];
    // repo.save(new Registro(TipoRegistro.MODIFICACION, Item.class.getSimpleName(),
    // "Se modifico el egreso con id: " + egreso.getId()));
    // }
}
