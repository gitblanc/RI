Nombre: Eduardo Blanco Bielsa
UO: 285176
Casos: Gestión de mecánicos ampliado, Gestión de nóminas, Gestión de grupos profesionales

---Anotaciones---
- En mi caso, he comprobado que no hubiese ningún warning, pero sale como si hubiese uno en la
  carpeta del proyecto pues se usa una versión mucho más antigua de Java que la que tengo instalada
  en mi ordenador personal, concretamente el proyecto usa la versión de Java SE 13 mientras que mi 
  equipo usa la versión 17.

- Como comenté a Lourdes, en los test de extensión de acceptance hay casos en los que da fallo "a veces"
  pues los test básicos y los de las ampliaciones mezclan funcionalidad, en concreto el método de redondeo
  (Round.twoCents() y Math.floor()). Por lo que prioricé que funcionasen los tests básicos. Es un error 
  de vuestro test, concretamente: createExpectedInvoice(List<WorkOrderDto workorders)
