import planificacion_vuelos.itinerario

package object planificacion_vuelos {

  import scala.collection.{Map, Seq, mutable}
  import scala.util.Random
  import annotation.tailrec
  import scala.collection.parallel.{ParSeq, ParMap}
  import scala.collection.parallel.CollectionConverters._

  case class Aeropuerto(Cod: String, X: Int, Y: Int, GMT: Int)

  case class Vuelo(Aln: String, Num: Int, Org: String, HS: Int, MS: Int, Dst: String, HL: Int, ML: Int, Esc: Int)

  val aeropuertos = List(
    Aeropuerto("ABQ", 195, 275, -800),
    Aeropuerto("ATL", 470, 280, -600),
    Aeropuerto("BNA", 430, 240, -700),
    Aeropuerto("BOS", 590, 100, -600),
    Aeropuerto("DCA", 540, 180, -600),
    Aeropuerto("DEN", 215, 205, -800),
    Aeropuerto("DFW", 310, 305, -700),
    Aeropuerto("DTW", 445, 140, -600),
    Aeropuerto("HOU", 330, 355, -700),
    Aeropuerto("JFK", 565, 130, -600),
    Aeropuerto("LAX", 55, 270, -900),
    Aeropuerto("MIA", 535, 390, -600),
    Aeropuerto("MSP", 340, 115, -700),
    Aeropuerto("MSY", 405, 345, -700),
    Aeropuerto("ORD", 410, 155, -700),
    Aeropuerto("PHL", 550, 155, -600),
    Aeropuerto("PHX", 120, 290, -800),
    Aeropuerto("PVD", 595, 122, -600),
    Aeropuerto("RDU", 530, 230, -600),
    Aeropuerto("SEA", 55, 45, -900),
    Aeropuerto("SFO", 10, 190, -900),
    Aeropuerto("STL", 380, 210, -700),
    Aeropuerto("TPA", 500, 360, -600)
  )
  val vuelosB5 = List(
    Vuelo("DL", 722, "ATL", 16, 50, "DFW", 19, 44, 0),
    Vuelo("DL", 722, "ATL", 2, 50, "DFW", 1, 44, 1),
    Vuelo("DL", 722, "ATL", 1, 50, "PHX", 13, 44, 0),
    Vuelo("DL", 722, "PHX", 18, 50, "DFW", 14, 44, 0),
  )


  type Itinerario = List[Vuelo]

  def toItin(vuelo: Vuelo) = List(vuelo)

  def vuelosOrigen(origen: String, vuelos: List[Vuelo]): List[Vuelo] = vuelos.filter(p => p.Org == origen)

  def escalasPosibles(origen: String, vuelos: List[Vuelo]): List[Vuelo] = vuelos.filterNot(v => v.Org== origen || v.Dst== origen)

  def lleganDestino(destino: String, itinerarios: List[Itinerario]): List[Itinerario] = itinerarios.filter((itin:Itinerario) => itin.last.Dst == destino)


  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos de forma paralela contando los vuelos directos y escalas.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de itinerarios
   */

  def itinerario(a1: String, a2: String): List[Itinerario] = {

    def auxItinerario(a1: String, vuelo: List[Vuelo]): List[Itinerario] = {
      if (vuelo.isEmpty) {
        List(List())

      } else {

        val vuelosDirectos = vuelosOrigen(a1, vuelo).map(vuelo => List(vuelo))

        val itinEscala = for {
          vueloOrigen <- vuelosDirectos.map(f => f.head)
          vueloEscala <- auxItinerario(vueloOrigen.Dst, escalasPosibles(vueloOrigen.Org, vuelo))
        } yield vueloOrigen :: vueloEscala
        vuelosDirectos ++ itinEscala
      }
    }

    lleganDestino(a2, auxItinerario(a1, vuelosB5))
  }

  def itinerariosPar(a1: String, a2: String): List[Vuelo] = {
    val vuelos = for {
      vuelos1 <- vuelosB5.par
      if (vuelos1.Org == a1 && vuelos1.Dst == a2)
    }
    yield vuelos1
    vuelos.toList
  }


  /**
   * Funcion que nos ayuda a obtener el gmt de un aeropuerto, devolviendo su hora y minuto en una lista
   * @param aero
   * @return List(hora, minuto)
   */
  def obtenerGmt(aero: String):List[Int] = {
    val gmt = for {
      aero1 <- aeropuertos
      if aero == aero1.Cod
    } yield aero1

    List(gmt(0).GMT / 100, gmt(0).GMT % 100)
  }


  /**
   * Funcion que recibe una hora, un minuto y una lisa de enteros que son la hora y el minuto GMT de un aeropuerto
   * y devuelve la nueva hora y minuto en una lista, posterior a acomodar su gmt
   * @param hora
   * @param min
   * @param gmt
   * @return List(hora, minuto)
   */
  def pasarAgmt(hora: Int, min: Int, gmt: List[Int]): List[Int] = {
    var h = 0
    var m = 0

    if (gmt.head > 0) { //restar
      if ((hora - gmt.head) < 0) { //baja de 0 la hora
        if ((min - gmt.last) < 0) { //baja de 0 el minuto
          h = 24 + (hora - gmt.head)
          m = 60 + (min - gmt.last)
        } else { //se mantiene el minuto
          h = 24 + (hora - gmt.head)
          m = min - gmt.last
        }
      } else { //se mantiene la hora
        if ((min - gmt.last) < 0) { //baja de 0 el minuto
          h = hora - gmt.head
          m = 60 + (min - gmt.last)
        } else { //se mantiene el minuto
          h = hora - gmt.head
          m = min - gmt.last
        }
      }
    } else if (gmt.head < 0) { //sumar
      if ((hora + (-gmt.head)) > 24) { //sube de 24 la hora
        if ((min + (-gmt.last)) > 60) { //sube de 60 el minuto
          h = (hora + (-gmt.head)) - 24
          m = (min + (-gmt.last)) - 60
        } else { //se mantiene el minuto
          h = (hora + (-gmt.head)) - 24
          m = min + gmt.last
        }
      } else { //se mantiene la hora
        if ((min + (-gmt.last)) > 60) { //sube de 60 el minuto
          h = hora + (-gmt.head)
          m = (min + (-gmt.last)) - 60
        } else { //se mantiene el minuto
          h = hora + (-gmt.head)
          m = min + gmt.last
        }
      }
    } else { //queda igual
      h = hora
      m = min
    }
    List(h, m)
  }

  /**
   * Funcion que recibe un itinerario y evuelve cada vuelo de este con su hora en horario gmt, para así
   * saber realmente a que hora salio un vuelo de manera general
   * @param itinerario
   * @return Itinerario
   */
  def itinerarioGMT(itinerario: Itinerario): Itinerario = {

    def vueloConGMT(v1: Vuelo): Vuelo = {
      val horaSalida = pasarAgmt(v1.HS, v1.MS, obtenerGmt(v1.Org))
      val horaLlegada = pasarAgmt(v1.HL, v1.ML, obtenerGmt(v1.Dst))
      Vuelo(v1.Aln, v1.Num, v1.Org, horaSalida(0), horaSalida(1), v1.Dst, horaLlegada(0), horaLlegada(1), v1.Esc)
    }

    for {
      vuelos <- itinerario
    } yield vueloConGMT(vuelos)

  }

  /**
   * funcion que recibe un vuelo y usando su hora de salida y de llegada calcula cuanto tardo en realizar el viaje,
   * devolviendo su hora en formato 24 horas. once horas seria 1100,  una hora sería 100, un minuto sería 1
   * @param vuelo
   * @return List[hora]
   */
  def tiempoVuelo(vuelo: Vuelo):List[String] = {
    var hour = 0
    var min = 0

    if (vuelo.HL < vuelo.HS) {
      if (vuelo.ML < vuelo.MS) {
        hour = 24 - vuelo.HS + vuelo.HL - 1
        min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      } else if (vuelo.ML == vuelo.MS) {
        hour = 24 - vuelo.HS + vuelo.HL
        List(hour.toString, min.toString)
      } else {
        hour = 24 - vuelo.HS + vuelo.HL
        min = vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      }
    } else {
      if (vuelo.ML < vuelo.MS) {
        hour = vuelo.HL - vuelo.HS - 1
        min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      } else if (vuelo.ML == vuelo.MS) {
        hour = vuelo.HL - vuelo.HS
        List(hour.toString + min.toString)
      } else {
        hour = vuelo.HL - vuelo.HS
        min = vuelo.ML - vuelo.MS
        List(hour.toString, min.toString)
      }
    }
  }

  /**
   * Convierte una lista de String que viene con una hora y minuto List(hora, minuto) en un numero entero horaminuto
   * @param hora
   * @return int
   */
  def horatoInt(hora: List[String]): Int = {

    if (hora.last.toInt < 10){
      List(hora.head+"0"+hora.last).head.toInt
    }else{
      List(hora.head+hora.last).head.toInt
    }

  }

  /**
   * Recibe la hora y minuto de salida de primer vuelo, y la hora y minuto de llegada dle ultimo y calcula cuanto
   * tomo realizar el viaje en total
   * @param hs
   * @param ms
   * @param hl
   * @param ml
   * @return Int
   */
  def sacarTiempoViaje(hs:Int, ms:Int, hl:Int, ml:Int): Int ={
    val vueloImaginario = Vuelo("ALN",0,"ORG",hs,ms,"DST",hl, ml,0)
    horatoInt(tiempoVuelo(vueloImaginario))
  }

  /**
   * Recibe un itinerario con sus horas en gmt y las convierte a su hora local
   * @param itinerario
   * @return Itinerario
   */
  def gmtALocal(itinerario: Itinerario): Itinerario ={
    def vueloConGMT(v1: Vuelo): Vuelo = {
      val horaSalida = pasarAgmt(v1.HS, v1.MS, List(-obtenerGmt(v1.Org).head, -obtenerGmt(v1.Org).last))
      val horaLlegada = pasarAgmt(v1.HL, v1.ML, List(-obtenerGmt(v1.Dst).head, -obtenerGmt(v1.Dst).last))
      Vuelo(v1.Aln, v1.Num, v1.Org, horaSalida(0), horaSalida(1), v1.Dst, horaLlegada(0), horaLlegada(1), v1.Esc)
    }
    for {
      vuelos <- itinerario
    } yield vueloConGMT(vuelos)
  }

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada, encuentra al menos tres itinerarios (si los hay)
   * que correspondan a los menores tiempos de viaje (contando tiempo de vuelo y tiempo de espera en tierra)
   * @param a1 origen
   * @param a2 destino
   * @return Lista de itinerarios
   */

  def itenerariosMenorTiempoTotal(a1:String, a2:String): List [Itinerario] = {
    val itinerarios = itinerario(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario))
    val vuelosOrdenadosTiempo = itinerarios.sortBy((i:Itinerario) => sacarTiempoViaje(i.head.HS, i.head.MS, i.last.HS,i.last.MS))
    vuelosOrdenadosTiempo.take(3).map(itinerario => gmtALocal(itinerario))
  }


  /**
   * Esta funcion suma las escalas de los itinerarios de vuelo contando la escala que hacen cuando se bajan del avion
   *
   * @param itinerario
   * @return int
   */

  def sumEscalas(itinerario: Itinerario): Int = {
    itinerario.collect { case vuelo: Vuelo => vuelo.Esc }.sum + itinerario.length - 1
  }

  /**
   * Esta funcion nos ayudara a encontrar los itinerarios que cumplan una determinada ruta y a su vez
   * cuenten con el menor numero de escalas posibles.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de itinerarios
   */
  def itinierariosMenorEscalas(a1: String, a2: String): List[Itinerario] = {
    itinerario(a1, a2).sortBy(itinerario => sumEscalas(itinerario)).take(3)
  }


  /**
   * Esta funcion realiza el tiempo de vuelo de cada itinerario sin contar el tiempo en tierra.
   *
   * @param itinerario
   * @return int
   */

  def sumarHoras(itinerario: Itinerario): Int ={
    itinerario.collect { case vuelo: Vuelo => tiempoVuelo(vuelo)(0).toInt}.sum
  }

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada, encuentra tre itinerarios (si los hay)
   * que hagan el menor numero de cambios de avion sin contar el tiempo total del vuelo.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de itinerarios
   */
  def itenerariosMenorTiempo(a1:String, a2:String): List [Itinerario] = {
    val vuelos = itinerario(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario))
    val vuelosOrdenadosTiempo = vuelos.sortBy((i:Itinerario) => sumarHoras(i))
    vuelosOrdenadosTiempo.take(3)
  }

 /**
  * Esta funcion dados un aeropuerto destino a2 y una hora de la cita h:m
  * determina el itinerario tal que la hora de salida de a1 sea la hora más tarde posible para salir del aeropuerto a1
  * y llegar a tiempo a la cita (suponga que la cita es en el mismo aeropuerto).
  * @param a1 origen
  * @param a2 destino
  * @return lista de itinerarios
  *
  */

 def itinerariosSalida(a1:String, a2:String, h: Int, m: Int): List[Itinerario] = {
   val vuelosATiempo = itinerario(a1, a2).filter((p:Itinerario) => horatoInt(List(p.last.HL.toString, p.last.ML.toString))
     < horatoInt(List(h.toString, m.toString)))
   vuelosATiempo.sortBy((i:Itinerario) => horatoInt(List(i.head.HS.toString, i.head.MS.toString))).reverse.take(3)
 }

}
