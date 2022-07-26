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
      Vuelo("AS", 239, "LAX", 16, 10, "SEA", 18, 40, 0),
      Vuelo("AS", 394, "SEA", 6, 50, "ORD", 12, 28, 0),
      Vuelo("AS", 610, "SEA", 13, 40, "PHX", 17, 25, 0),
      Vuelo("AS", 612, "SEA", 10, 15, "PHX", 13, 55, 0),
      Vuelo("AS", 96, "SEA", 17, 0, "LAX", 19, 30, 0),
      Vuelo("AS", 91, "LAX", 7, 0, "SEA", 13, 5, 0),
      Vuelo("AS", 223, "LAX", 12, 0, "SEA", 14, 40, 0),
      Vuelo("AS", 22, "SEA", 7, 0, "LAX", 14, 0, 0),
      Vuelo("AS", 210, "SEA", 8, 0, "LAX", 10, 35, 0),
      Vuelo("CO", 530, "DEN", 16, 9, "MSY", 13, 51, 3),
      Vuelo("CO", 1708, "ABQ", 10, 26, "DEN", 11, 39, 0),
      Vuelo("CO", 280, "PHX", 10, 50, "DFW", 16, 7, 1),
      Vuelo("CO", 261, "BOS", 16, 45, "DEN", 10, 46, 1),
      Vuelo("CO", 891, "DCA", 14, 30, "SEA", 10, 20, 1),
      Vuelo("CO", 642, "ORD", 10, 10, "DFW", 14, 51, 1),
      Vuelo("CO", 255, "DEN", 20, 28, "SEA", 12, 14, 0),
      Vuelo("CO", 171, "DEN", 18, 30, "SEA", 10, 29, 0),
      Vuelo("CO", 629, "DCA", 17, 40, "SFO", 12, 12, 1),
      Vuelo("CO", 196, "SFO", 6, 30, "DEN", 15, 2, 0),
      Vuelo("CO", 780, "DEN", 16, 1, "PHL", 11, 40, 0),
      Vuelo("CO", 467, "PHL", 17, 40, "PHX", 12, 20, 1),
      Vuelo("CO", 578, "DEN", 16, 1, "BOS", 12, 57, 1),
      Vuelo("CO", 786, "DEN", 13, 4, "BOS", 19, 1, 0),
      Vuelo("CO", 249, "DEN", 18, 5, "PHX", 10, 10, 0),
      Vuelo("CO", 479, "DEN", 12, 0, "LAX", 13, 29, 0),
      Vuelo("CO", 390, "ORD", 9, 15, "BOS", 14, 11, 1),
      Vuelo("CO", 445, "DEN", 12, 5, "SFO", 13, 57, 0),
      Vuelo("CO", 514, "MIA", 12, 25, "ORD", 16, 26, 1),
      Vuelo("CO", 233, "DFW", 18, 45, "DEN", 19, 34, 0),
      Vuelo("CO", 562, "PHX", 8, 0, "DEN", 14, 4, 0),
      Vuelo("CO", 1184, "ABQ", 17, 25, "DEN", 18, 39, 0),
      Vuelo("CO", 840, "DEN", 10, 25, "DFW", 13, 20, 0),
      Vuelo("CO", 758, "SEA", 6, 35, "DCA", 17, 22, 1),
      Vuelo("CO", 598, "LAX", 15, 30, "ORD", 12, 46, 1),
      Vuelo("CO", 742, "DEN", 16, 14, "ATL", 11, 9, 0),
      Vuelo("CO", 174, "LAX", 8, 50, "ORD", 17, 22, 1),
      Vuelo("CO", 395, "DTW", 7, 0, "DEN", 11, 0, 0),
      Vuelo("CO", 818, "SFO", 8, 55, "DEN", 12, 25, 0),
      Vuelo("CO", 131, "DCA", 6, 45, "LAX", 13, 44, 2),
      Vuelo("CO", 846, "PHX", 14, 35, "ATL", 11, 46, 1),
        Vuelo("DL", 579, "ORD", 6, 20, "ATL", 10, 3, 0),
        Vuelo("DL", 842, "ATL", 19, 1, "PHL", 10, 58, 0),
        Vuelo("DL", 700, "ATL", 19, 2, "MSP", 10, 40, 0),
        Vuelo("DL", 955, "ATL", 23, 45, "DFW", 12, 45, 0),
        Vuelo("DL", 544, "DEN", 9, 40, "DFW", 12, 24, 0),
        Vuelo("DL", 509, "DFW", 18, 56, "DEN", 19, 55, 0),
        Vuelo("DL", 784, "DFW", 10, 20, "BNA", 12, 0, 0),
        Vuelo("DL", 247, "BOS", 17, 10, "MIA", 10, 22, 0),
        Vuelo("DL", 177, "ORD", 18, 50, "ATL", 11, 39, 0),
        Vuelo("DL", 679, "PVD", 17, 5, "ATL", 19, 36, 0),
        Vuelo("DL", 152, "SFO", 5, 30, "ATL", 14, 25, 1),
        Vuelo("DL", 675, "ATL", 19, 9, "DEN", 10, 30, 0),
        Vuelo("DL", 149, "TPA", 9, 20, "DFW", 10, 52, 0),
        Vuelo("DL", 350, "ATL", 9, 2, "BOS", 11, 25, 0),
        Vuelo("DL", 946, "DFW", 15, 1, "HOU", 16, 3, 0),
        Vuelo("DL", 775, "ATL", 8, 38, "DFW", 11, 8, 1),
        Vuelo("DL", 197, "TPA", 16, 10, "SEA", 11, 5, 1),
        Vuelo("DL", 818, "DTW", 19, 50, "ATL", 11, 37, 0),
        Vuelo("DL", 296, "PHX", 14, 25, "ATL", 19, 53, 0),
        Vuelo("DL", 178, "ATL", 18, 58, "DCA", 10, 35, 0),
        Vuelo("DL", 252, "PHX", 16, 35, "DFW", 19, 48, 0),
        Vuelo("DL", 148, "MIA", 18, 0, "ATL", 19, 54, 0),
        Vuelo("DL", 324, "TPA", 17, 45, "MSP", 11, 32, 1),
        Vuelo("DL", 257, "ATL", 11, 59, "DEN", 13, 5, 0),
        Vuelo("DL", 588, "PHX", 12, 55, "DFW", 16, 15, 0),
        Vuelo("DL", 874, "DFW", 8, 20, "DCA", 12, 5, 0),
        Vuelo("DL", 908, "DFW", 18, 41, "ATL", 13, 1, 1),
        Vuelo("DL", 990, "TPA", 12, 55, "ATL", 14, 22, 0),
        Vuelo("DL", 599, "BOS", 20, 30, "ATL", 13, 5, 0),
        Vuelo("DL", 480, "MSY", 17, 35, "ATL", 19, 55, 0),
        Vuelo("DL", 593, "ATL", 19, 3, "DFW", 11, 17, 1),
        Vuelo("DL", 863, "BOS", 12, 15, "TPA", 15, 18, 0),
        Vuelo("DL", 415, "DCA", 17, 45, "DFW", 19, 53, 0),
        Vuelo("DL", 979, "SFO", 19, 0, "LAX", 10, 12, 0),
        Vuelo("DL", 310, "DFW", 8, 20, "ORD", 10, 40, 0),
        Vuelo("DL", 389, "PHL", 19, 35, "TPA", 13, 45, 1),
        Vuelo("DL", 963, "SFO", 21, 0, "LAX", 12, 6, 0),
        Vuelo("DL", 252, "LAX", 13, 35, "DFW", 19, 48, 1),
        Vuelo("DL", 691, "DFW", 19, 4, "SFO", 10, 55, 0),
        Vuelo("DL", 412, "TPA", 14, 20, "ATL", 15, 42, 0),
        Vuelo("DL", 201, "DTW", 5, 50, "ATL", 13, 7, 0),
        Vuelo("DL", 179, "DFW", 15, 40, "LAX", 16, 40, 0),
        Vuelo("DL", 187, "ATL", 19, 7, "LAX", 10, 55, 0),
        Vuelo("DL", 629, "HOU", 8, 0, "DFW", 10, 3, 0),
        Vuelo("DL", 690, "HOU", 16, 50, "ORD", 11, 14, 1),
        Vuelo("DL", 134, "SFO", 15, 30, "ATL", 12, 47, 0),
        Vuelo("DL", 143, "LAX", 12, 50, "SFO", 15, 9, 0),
        Vuelo("DL", 887, "DFW", 15, 14, "SEA", 17, 10, 0),
        Vuelo("DL", 280, "ATL", 16, 49, "DCA", 18, 30, 0),
        Vuelo("DL", 275, "SFO", 14, 0, "LAX", 15, 12, 0),
        Vuelo("DL", 367, "BOS", 14, 20, "PHL", 15, 37, 0),
        Vuelo("DL", 868, "ATL", 8, 26, "DTW", 10, 10, 0),
        Vuelo("DL", 441, "DFW", 18, 42, "ABQ", 19, 40, 0),
        Vuelo("DL", 842, "HOU", 15, 5, "PHL", 10, 58, 1),
        Vuelo("DL", 359, "RDU", 20, 25, "ATL", 11, 39, 0),
        Vuelo("DL", 647, "DTW", 16, 50, "PHX", 10, 35, 1),
        Vuelo("DL", 696, "ATL", 10, 16, "STL", 10, 50, 0),
        Vuelo("DL", 577, "ATL", 20, 56, "MSY", 11, 30, 0),
        Vuelo("DL", 364, "DFW", 17, 24, "TPA", 10, 40, 0),
        Vuelo("DL", 130, "LAX", 15, 15, "ATL", 12, 9, 0),
        Vuelo("DL", 701, "DCA", 17, 59, "ATL", 19, 48, 0),
        Vuelo("DL", 714, "ATL", 23, 47, "DTW", 12, 5, 0),
        Vuelo("DL", 885, "BNA", 5, 35, "ATL", 13, 2, 0),
        Vuelo("DL", 704, "ORD", 16, 50, "ATL", 19, 44, 0),
        Vuelo("DL", 378, "ATL", 15, 19, "DCA", 16, 55, 0),
        Vuelo("DL", 527, "DFW", 11, 47, "ABQ", 12, 30, 0),
        Vuelo("DL", 545, "DFW", 15, 21, "ABQ", 16, 5, 0),
        Vuelo("DL", 139, "ATL", 15, 14, "LAX", 11, 20, 3),
        Vuelo("DL", 951, "DTW", 17, 45, "ATL", 19, 33, 0),
        Vuelo("DL", 720, "LAX", 13, 45, "DFW", 19, 34, 1),
        Vuelo("DL", 198, "SEA", 13, 45, "DFW", 19, 27, 0),
        Vuelo("DL", 701, "BOS", 15, 45, "ATL", 19, 48, 1),
        Vuelo("DL", 487, "BOS", 18, 50, "DFW", 11, 38, 0),
        Vuelo("DL", 588, "DFW", 17, 1, "DTW", 10, 30, 0),
        Vuelo("DL", 252, "PHX", 16, 35, "PHL", 12, 45, 1),
        Vuelo("DL", 662, "MSY", 10, 5, "ATL", 12, 21, 0),
        Vuelo("DL", 317, "DEN", 11, 0, "TPA", 18, 20, 1),
        Vuelo("DL", 323, "ATL", 11, 44, "DFW", 14, 30, 2),
        Vuelo("DL", 195, "ATL", 16, 49, "DFW", 18, 5, 0),
        Vuelo("DL", 367, "PHL", 16, 10, "MIA", 11, 20, 1),
        Vuelo("DL", 901, "ATL", 18, 57, "PHX", 11, 0, 0),
        Vuelo("DL", 110, "SFO", 8, 0, "JFK", 16, 13, 0),
        Vuelo("DL", 638, "LAX", 18, 0, "SFO", 19, 15, 0),
        Vuelo("DL", 161, "DFW", 19, 10, "LAX", 10, 20, 0),
        Vuelo("DL", 139, "DFW", 18, 52, "LAX", 11, 20, 1),
        Vuelo("DL", 492, "MIA", 16, 30, "BOS", 19, 28, 0),
        Vuelo("DL", 173, "LAX", 16, 55, "SEA", 19, 23, 0),
        Vuelo("DL", 35, "JFK", 17, 35, "LAX", 10, 43, 0),
        Vuelo("DL", 832, "DFW", 18, 45, "ATL", 11, 42, 0),
        Vuelo("DL", 34, "LAX", 8, 5, "JFK", 15, 59, 0),
        Vuelo("DL", 111, "JFK", 17, 40, "SFO", 10, 52, 0),
        Vuelo("DL", 178, "LAX", 10, 40, "DCA", 10, 35, 1),
        Vuelo("DL", 307, "ATL", 11, 44, "DFW", 14, 27, 1),
        Vuelo("DL", 868, "TPA", 6, 10, "DTW", 10, 10, 1),
        Vuelo("DL", 805, "SFO", 20, 0, "LAX", 11, 15, 0),
        Vuelo("DL", 141, "TPA", 17, 55, "LAX", 19, 53, 0),
        Vuelo("DL", 174, "SEA", 6, 30, "MIA", 16, 45, 1),
        Vuelo("DL", 784, "BNA", 12, 30, "ATL", 14, 28, 0),
        Vuelo("DL", 830, "SEA", 15, 25, "ATL", 12, 48, 0),
        Vuelo("DL", 722, "DFW", 16, 50, "ATL", 19, 44, 0)
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
  def itinerarios(a1:String, a2:String): List[Vuelo] = {
      for {
        vuelos <- vuelosB5
        if(obtenerOrigen(vuelos)==a1 && obtenerDestino(vuelos)==a2)
      }yield vuelos

    }

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

  def minimizarTiempoPar(a1:String, a2:String): List[Vuelo]={
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
        h = hora + gmt
      }
    }

    List(h, m)
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
