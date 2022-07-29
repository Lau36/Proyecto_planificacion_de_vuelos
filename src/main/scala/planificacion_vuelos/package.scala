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

  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos de forma paralela.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */


  /*def obtenerOrigen(origen: Vuelo):String = {
      origen.Org
    }
    def obtenerDestino(destino: Vuelo):String = {
      destino.Dst
    }*/

  def itinerarios1(a1: String, a2: String): List[Vuelo] = {
    val vuelos = for {
      vuelos1 <- vuelosB5
      if (vuelos1.Org == a1 && vuelos1.Dst == a2)
    }
    yield vuelos1
    vuelos
  }

  def itinerariosPar(a1: String, a2: String): List[Vuelo] = {
    val vuelos = for {
      vuelos1 <- vuelosB5.par
      if (vuelos1.Org == a1 && vuelos1.Dst == a2)
    }
    yield vuelos1
    vuelos.toList
  }

  def itinerarioV(a1: String, a2: String): List[Itinerario] = {
    def vueloDirecto(a1: String, vuelo: List[Vuelo]): Itinerario = vuelo.filter(p => p.Org == a1)

    def auxItinerario(a1: String, vuelo: List[Vuelo]): List[Itinerario] = {
      if (vuelo.isEmpty) {
        List(List())
      } else {
        val itinDirecto = vueloDirecto(a1, vuelo).map(x => List(x))
        val itinEscala = for {
          vueloP <- itinDirecto.map(f => f.head)
          vueloD <- auxItinerario(vueloP.Dst, vuelosB5.filterNot(p => p.Org == a1 || p.Dst == a1))
        } yield vueloP :: vueloD
        itinDirecto ++ itinEscala
      }
    }

    auxItinerario(a1, vuelosB5).filter((itinerario: List[Vuelo]) => itinerario.last.Dst == a2)
  }


  /*def vuelosDirectos(a1:String, a2:String): List[Itinerario] = {
    val vuelos = for {
      vuelos1 <- vuelosB5
      if(vuelos1.Org==a1 && vuelos1.Dst==a2)
    }
    yield vuelos1
    vuelos.map(x => toItin(x))
  }
  //ESCALAS
  def genEscalas(a1: String, a2: String):List[Itinerario] = {
    val escalas = for {
      escs <- vuelosB5
      if (escs.Dst == a2 && escs.Org!=a1) {
        genEscalas(a1 == esc, a2)
      }
      else yield escs
    } yield escs
  }

  def itinerarios2(a1: String, a2: String): List[Itinerario]={
    vuelosDirectos(a1,a2) ++ genEscalas(a1,a2)
  }*/

  //Lo de santi----------------------------------------------------------------------------------------------------------------
  def obtenerGmt(aero: String) = {
    val gmt = for {
      aero1 <- aeropuertos
      if aero == aero1.Cod
    } yield aero1

    List(gmt(0).GMT / 100, gmt(0).GMT % 100)
  }


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

  def tiempoVuelo(vuelo: Vuelo):List[String] = {
    var hour = 0
    var min = 0

    if (vuelo.HL < vuelo.HS) {
      if (vuelo.ML < vuelo.MS) {
        hour = 24 - vuelo.HS + vuelo.HL - 1
        min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString + min.toString)
      } else if (vuelo.ML == vuelo.MS) {
        hour = 24 - vuelo.HS + vuelo.HL
        List(hour.toString + "00")
      } else {
        hour = 24 - vuelo.HS + vuelo.HL
        min = vuelo.ML - vuelo.MS
        List(hour.toString + min.toString)
      }
    } else {
      if (vuelo.ML < vuelo.MS) {
        hour = vuelo.HL - vuelo.HS - 1
        min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString + min.toString)
      } else if (vuelo.ML == vuelo.MS) {
        hour = vuelo.HL - vuelo.HS
        List(hour.toString + min.toString)
      } else {
        hour = vuelo.HL - vuelo.HS
        min = 60 + vuelo.ML - vuelo.MS
        List(hour.toString + min.toString)
      }
    }
  }

  def sacarTiempoVuelo(hs:Int, ms:Int, hl:Int, ml:Int): Int ={
    val vueloImaginario = Vuelo("ALN",0,"ORG",hs,ms,"DST",hl, ml,0)
    tiempoVuelo(vueloImaginario)(0).toInt
  }

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada, encuentra al menos tres itinerarios (si los hay)
   * que correspondan a los menores tiempos de viaje (contando tiempo de vuelo y tiempo de espera en tierra)
   * @param a1 origen
   * @param a2 destino
   * @return Lista de itinerarios
   */

  def itenerariosMenorTiempoTotal(a1:String, a2:String): List [Itinerario] = {
   val itinerarios = itinerarioV(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario))
   val vuelosOrdenadosTiempo = itinerarios.sortBy((i:Itinerario) => sacarTiempoVuelo(i.head.HS, i.head.MS, i.last.HS,i.last.MS))
   vuelosOrdenadosTiempo.take(3)
 }

  //-----------------------------------------------------------------------------------------------------------------------


  /**
   * Esta funcion nos ayudara a encontrar los itinerarios que cumplan una determinada ruta y a su vez
   * cuenten con el menor numero de escalas posibles.
   *
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */

  def sumEscalas(itinerario: Itinerario): Int = {
    itinerario.collect { case vuelo: Vuelo => vuelo.Esc }.sum + itinerario.length - 1
  }

  def itinierariosMenorEscalas(a1: String, a2: String): List[Itinerario] = {
    itinerarioV(a1, a2).sortBy(itinerario => sumEscalas(itinerario)).take(3)
  }


  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada,
   * encuentra al menos tres itinerarios (si los hay) que hagan el menor numero de cambios de avión,
   * sin tener en cuenta el tiempo total de viaje (podrıa ser mas rapido cambiar varias veces de avión,
   * que esperar la salida de un vuelo que lo lleve al destino sin cambiar de avión).
   *
   * @param a1 origen
   * @param a2 destino
   * @return
   */

  def sumarHoras(itinerario: Itinerario): Int ={
    itinerario.collect { case vuelo: Vuelo => tiempoVuelo(vuelo)(0).toInt}.sum
  }

  def itenerariosMenorTiempo(a1:String, a2:String): List [Itinerario] = {
    val vuelos = itinerarioV(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario))
    val vuelosOrdenadosTiempo = vuelos.sortBy((i:Itinerario) => sumarHoras(i))
    vuelosOrdenadosTiempo.take(3)
  }

 /**
  * Esta funcion dados un aeropuerto destino a2 y una hora de la cita h:m
  * determina el itinerario tal que la hora de salida de a1 sea la hora más tarde posible para salir del aeropuerto a1
  * y llegar a tiempo a la cita (suponga que la cita es en el mismo aeropuerto).
  * @param a1 origen
  * @param a2 destino
  * @return lista de vuelos
  */

 //  def itinerariosSalida(a1:String, a2:String, h, m)

}
