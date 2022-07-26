package object planificacion_vuelos {
  import scala.collection.{Map, Seq, mutable}
  import scala.util.Random
  import annotation.tailrec
  import scala.collection.parallel.{ParSeq, ParMap}
  import scala.collection.parallel.CollectionConverters._

    case class Aeropuerto(Cod:String,X:Int,Y:Int,GMT:Int)
    case class Vuelo(Aln:String, Num:Int, Org:String, HS:Int, MS:Int, Dst:String, HL:Int, ML:Int, Esc:Int)

    val aeropuertos=List(
      Aeropuerto("ABQ", 195, 275, -8),
      Aeropuerto("ATL", 470, 280, -6),
      Aeropuerto("BNA", 430, 240, -7),
      Aeropuerto("BOS", 590, 100, -6),
      Aeropuerto("DCA", 540, 180, -6),
      Aeropuerto("DEN", 215, 205, -8),
      Aeropuerto("DFW", 310, 305, -7),
      Aeropuerto("DTW", 445, 140, -6),
      Aeropuerto("HOU", 330, 355, -7),
      Aeropuerto("JFK", 565, 130, -6),
      Aeropuerto("LAX", 55, 270, -9),
      Aeropuerto("MIA", 535, 390, -6),
      Aeropuerto("MSP", 340, 115, -7),
      Aeropuerto("MSY", 405, 345, -7),
      Aeropuerto("ORD", 410, 155, -7),
      Aeropuerto("PHL", 550, 155, -6),
      Aeropuerto("PHX", 120, 290, -8),
      Aeropuerto("PVD", 595, 122, -6),
      Aeropuerto("RDU", 530, 230, -6),
      Aeropuerto("SEA", 55, 45, -9),
      Aeropuerto("SFO", 10, 190, -9),
      Aeropuerto("STL", 380, 210, -7),
      Aeropuerto("TPA", 500, 360, -6)
    )
    val vuelosB5= List(
      Vuelo("AS", 239, "LAX", 16, 10, "SEA", 18, 40, 1),
      Vuelo("AS", 256, "LAX", 1, 10, "TPA", 18, 40, 2),
      Vuelo("AS", 365, "LAX", 16, 10, "SFO", 18, 40, 0),
      Vuelo("CO", 987, "SEA", 16, 10, "LAX", 18, 40, 0),
      Vuelo("CO", 543, "SFO", 16, 10, "LAX", 18, 40, 0),
      Vuelo("DL", 643, "TPA", 16, 10, "SFO", 18, 40, 1),
      Vuelo("DL", 642, "TPA", 16, 10, "PVD", 18, 40, 2),
      Vuelo("DL", 789, "PVD", 16, 10, "TPA", 18, 40, 2),
      Vuelo("AS", 234, "PVD", 16, 10, "SEA", 18, 40, 0)
    )


    //Encontrando rutas
    //vuelo(aln: aeoro, num: codVuelo, org: aeroOrig, hs: horaSale, ms:minutoSale, dst: aeroLlega, hl: horallega, ml:minutoLlega, ess: numEscalas )



    def obtenerOrigen(origen: Vuelo):String = {
      origen.Org
    }
    def obtenerDestino(destino: Vuelo):String = {
      destino.Dst
    }

  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos.
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */
  def vuelosDirectos(a1:String, a2:String): List[Vuelo] = {
      for {
        vuelos <- vuelosB5
        if(obtenerOrigen(vuelos)==a1 && obtenerDestino(vuelos)==a2)
      }
      yield vuelos

    }

  /*def itinerarios(a1: String, a2: String): List[Vuelo] = {
    for{
      vuelos <- vuelosB5
      if(obtenerOrigen(vuelos)== a1 && obtenerDestino(vuelos)!= a2)
      for{
      vuelos <- vuelosB5
      if(obtenerOrigen(vuelos)==a1)
    } yield
    } yield
  }*/

  //Espacio pa nat









  def obtenerDiferencia(v1: Vuelo, v2:Vuelo) = {
      val obtenerGMT1 = for {
        aero1 <- aeropuertos
        if v1.Org == aero1.Cod
      } aero1.GMT

      val obtenerGMT2 = for {
        aero2 <- aeropuertos
        if v2.Org == aero2.Cod
      } aero2.GMT

      //def

  }

  def menorTiempoVuelo(a1:String, a2:String): List[Vuelo]={
    val vuelos = itinerarios(a1,a2)
    vuelos.sortBy((x: Vuelo) => x.Esc).reverse.take(3)
  }

  def minimizarTiempoVueloPar(a1:String, a2:String): List[Vuelo]={
    val vuelos = itinerariosPar(a1,a2)
    vuelos.sortBy((x: Vuelo) => x.Esc).drop(3)
  }


  /**
   * Esta funcion dados dos aeropuertos,uno de salida y otro de llegada,
   * devuelve la lista de itinerarios posibles para viajar entre esos dos aeropuertos de forma paralela.
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */
  def itinerariosPar(a1:String, a2:String): List[Vuelo] = {
    val vuelos = for {
      vuelos <- vuelosB5.par
      if obtenerOrigen(vuelos)==a1 && obtenerDestino(vuelos)==a2
    }yield vuelos
    vuelos.toList

  }

  def hora(vuelo:Vuelo): Int = {
    vuelo.HS
  }

  def min(vuelo:Vuelo): Int = {
    vuelo.MS
  }

  //Lo de santi
  def obtenerGmt(aero: String) = {
    val gmt = for {
      aero1 <- aeropuertos
      if aero == aero1.Cod
    } yield aero1
    gmt(0).GMT
  }

  def pasarAgmt(hora: Int, min: Int, gmt: Int) = {
    var h = 0
    val m = min

    if (gmt > 0){
      if ((hora - gmt) < 0) {
        h = 24 + (hora-gmt)
      }else{
        h = hora-gmt
      }
    }else{
      if ((hora + (-gmt)) > 24) {
        h = ((hora + (-gmt)) - 24)
      }else{
        h = hora + (-gmt)
      }
    }

    List(h, m)
  }

  def tiempoVuelo(vuelo: Vuelo) = {
    var hour = 0
    var min = 0

    if (vuelo.HL < vuelo.HS) {
      if (vuelo.ML < vuelo.MS) {
        hour = 24-vuelo.HS + vuelo.HL-1
        min = 60 + vuelo.ML - vuelo.MS
      }else{
        hour = 24-vuelo.HS + vuelo.HL
        min = vuelo.ML - vuelo.MS
      }
    }else{
      if (vuelo.ML < vuelo.MS) {
        hour = vuelo.HL - vuelo.HS - 1
        min = 60 + vuelo.ML - vuelo.MS
      }else{
        hour = vuelo.HL - vuelo.HS
        min = 60 + vuelo.ML - vuelo.MS
      }
    }

    List(hour.toString+min.toString)

  }

  def vueloConGMT(v1: Vuelo): Vuelo = {
    val horaSalida = pasarAgmt(v1.HS, v1.MS, obtenerGmt(v1.Org))
    val horaLlegada = pasarAgmt(v1.HL, v1.ML, obtenerGmt(v1.Dst))

    Vuelo(v1.Aln, v1.Num, v1.Org, horaSalida(0), horaSalida(1), v1.Dst, horaLlegada(0), horaLlegada(1), v1.Esc)
  }

  def itenerariosMenorTiempoTotal(a1:String, a2:String)  = {
    val vuelos = itinerarios(a1:String, a2:String).map((vuelo:Vuelo) => vueloConGMT(vuelo))
    val vuelosOrdenadosTiempo = vuelos.sortBy((v:Vuelo) => tiempoVuelo(v)(0).toInt)

    //vuelosOrdenadosTiempo.take(3)
    vuelosOrdenadosTiempo
  }




    /**
   * Esta funcion nos ayudara a encontrar los itinerarios que cumplan una determinada ruta y a su vez
   * cuenten con el menor numero de escalas posibles.
   * @param a1 origen
   * @param a2 destino
   * @return lista de vuelos
   */

  def itinerariosMenorCambio(a1:String, a2:String): List[Vuelo] ={
    val vuelos = itinerarios(a1, a2)
    vuelos.sortBy((x: Vuelo) => x.Esc).take(3)
  }

  def itineraciosMenorCambioPar(a1:String, a2:String): List[Vuelo] = {
    val vuelos = itinerariosPar(a1, a2)
    vuelos.sortBy((x: Vuelo) => x.Esc).take(3)
  }

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada,
   * encuentra al menos tres itinerarios (si los hay) que hagan el menor numero de cambios de avión,
   * sin tener en cuenta el tiempo total de viaje (podria ser mas rapido cambiar varias veces de avión,
   * que esperar la salida de un vuelo que lo lleve al destino sin cambiar de avión).
   * @param a1 origen
   * @param a2 destino
   * @return vuelos
   */

  /*def itinerariosMenorTiempo(a1:String, a2:String): List[Vuelo] = {

  }*/

  /**
   * Esta funcion dados dos aeropuertos, uno de salida y otro de llegada,
   * encuentra al menos tres itinerarios (si los hay) que hagan el menor numero de cambios de avión,
   * sin tener en cuenta el tiempo total de viaje (podrıa ser mas rapido cambiar varias veces de avión,
   * que esperar la salida de un vuelo que lo lleve al destino sin cambiar de avión).
   * @param a1 origen
   * @param a2 destino
   * @return
   */

  //  def itinerariosDistancia(a1:String, a2:String)

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
