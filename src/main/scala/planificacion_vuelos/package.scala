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
  val vuelosB5 = List(Vuelo("AA", 728, "DFW", 6, 35, "BOS", 10, 58, 0),

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

  val vuelosC2= List(
    Vuelo("DL", 139, "MSY", 16, 25, "LAX", 11, 20, 2),
    Vuelo("DL", 252, "LAX", 13, 35, "PHL", 12, 45, 2),
    Vuelo("DL", 553, "PVD", 7, 50, "TPA", 12, 5, 1),
    Vuelo("DL", 742, "DEN", 17, 5, "DFW", 19, 50, 0),
    Vuelo("DL", 412, "ATL", 16, 48, "RDU", 18, 0, 0),
    Vuelo("DL", 989, "DCA", 7, 30, "ATL", 11, 5, 0),
    Vuelo("DL", 317, "DEN", 11, 0, "ATL", 16, 0, 0),
    Vuelo("DL", 841, "BOS", 15, 0, "DFW", 18, 8, 0),
    Vuelo("DL", 264, "LAX", 7, 0, "ATL", 14, 16, 0),
    Vuelo("DL", 618, "SFO", 7, 0, "DFW", 12, 22, 0),
    Vuelo("DL", 785, "ATL", 6, 25, "DFW", 13, 5, 0),
    Vuelo("DL", 635, "ORD", 8, 10, "ATL", 11, 1, 0),
    Vuelo("DL", 877, "ORD", 8, 30, "DFW", 10, 44, 0),
    Vuelo("DL", 620, "SEA", 6, 50, "LAX", 11, 1, 0),
    Vuelo("DL", 551, "ATL", 8, 19, "DFW", 11, 10, 2),
    Vuelo("DL", 194, "MIA", 7, 15, "ATL", 15, 8, 0),
    Vuelo("DL", 270, "ATL", 8, 41, "SFO", 10, 45, 0),
    Vuelo("DL", 286, "PHX", 6, 0, "DFW", 10, 8, 0),
    Vuelo("DL", 175, "BOS", 8, 0, "TPA", 12, 8, 1),
    Vuelo("DL", 225, "BOS", 6, 30, "ATL", 10, 7, 0),
    Vuelo("DL", 793, "MSY", 9, 30, "DFW", 10, 55, 0),
    Vuelo("DL", 996, "DFW", 6, 10, "BOS", 11, 46, 1),
    Vuelo("DL", 995, "DFW", 8, 14, "PHX", 14, 5, 0),
    Vuelo("DL", 369, "ATL", 8, 28, "PHX", 10, 25, 0),
    Vuelo("DL", 784, "DFW", 10, 20, "ATL", 14, 28, 1),
    Vuelo("DL", 192, "SEA", 16, 25, "LAX", 10, 43, 1),
    Vuelo("DL", 472, "ATL", 15, 26, "MIA", 17, 15, 0),
    Vuelo("DL", 217, "PHL", 8, 25, "DFW", 10, 43, 0),
    Vuelo("DL", 219, "TPA", 16, 15, "DFW", 17, 56, 0),
    Vuelo("DL", 445, "ATL", 8, 57, "DEN", 10, 5, 0),
    Vuelo("DL", 587, "MIA", 19, 30, "DFW", 11, 28, 0),
    Vuelo("DL", 671, "ATL", 8, 22, "DFW", 12, 7, 0),
    Vuelo("DL", 825, "DFW", 8, 11, "SFO", 15, 5, 0),
    Vuelo("DL", 331, "SFO", 8, 30, "ATL", 15, 48, 0),
    Vuelo("DL", 706, "SEA", 8, 30, "ATL", 15, 49, 0),
    Vuelo("DL", 888, "DFW", 8, 7, "DEN", 10, 0, 0),
    Vuelo("DL", 835, "ATL", 9, 26, "SEA", 11, 30, 0),
    Vuelo("DL", 817, "DTW", 16, 5, "ATL", 17, 54, 0),
    Vuelo("DL", 703, "MIA", 9, 0, "DFW", 10, 55, 0),
    Vuelo("DL", 400, "DFW", 9, 55, "DCA", 13, 40, 0),
    Vuelo("DL", 686, "ATL", 13, 19, "PHL", 15, 15, 0),
    Vuelo("DL", 574, "MIA", 21, 10, "ATL", 12, 51, 0),
    Vuelo("DL", 672, "DFW", 9, 49, "DTW", 13, 20, 0),
    Vuelo("DL", 929, "PVD", 8, 30, "ATL", 11, 4, 0),
    Vuelo("DL", 939, "MSP", 6, 0, "ATL", 12, 2, 0),
    Vuelo("DL", 661, "DEN", 19, 50, "DFW", 12, 34, 0),
    Vuelo("DL", 842, "HOU", 15, 5, "ATL", 18, 2, 0),
    Vuelo("DL", 427, "RDU", 8, 55, "DFW", 10, 51, 0),
    Vuelo("DL", 170, "SEA", 8, 30, "MSY", 17, 53, 1),
    Vuelo("DL", 375, "ORD", 9, 55, "ATL", 12, 42, 0),
    Vuelo("DL", 146, "LAX", 12, 35, "ATL", 19, 50, 0),
    Vuelo("DL", 433, "BOS", 8, 25, "MIA", 11, 36, 0),
    Vuelo("DL", 692, "ATL", 16, 46, "ORD", 17, 50, 0),
    Vuelo("DL", 915, "DCA", 14, 20, "ATL", 16, 10, 0),
    Vuelo("DL", 477, "MSY", 9, 0, "LAX", 10, 59, 0),
    Vuelo("DL", 477, "TPA", 7, 45, "MSY", 11, 2, 0),
    Vuelo("DL", 817, "DTW", 16, 5, "DFW", 11, 40, 3),
    Vuelo("DL", 542, "DFW", 6, 41, "ATL", 10, 51, 1),
    Vuelo("DL", 143, "ATL", 22, 35, "LAX", 12, 10, 0),
    Vuelo("DL", 370, "MIA", 11, 25, "ORD", 14, 55, 1),
    Vuelo("DL", 581, "TPA", 6, 0, "DFW", 13, 3, 0),
    Vuelo("DL", 860, "ATL", 16, 47, "PHL", 18, 40, 0),
    Vuelo("DL", 139, "ATL", 15, 14, "DFW", 17, 53, 1),
    Vuelo("DL", 357, "ATL", 8, 26, "DFW", 11, 8, 1),
    Vuelo("DL", 877, "ORD", 8, 30, "HOU", 12, 55, 1),
    Vuelo("DL", 463, "DCA", 16, 0, "ATL", 17, 46, 0),
    Vuelo("DL", 353, "DFW", 8, 9, "ATL", 12, 37, 1),
    Vuelo("DL", 527, "DCA", 8, 50, "DFW", 10, 52, 0),
    Vuelo("DL", 710, "ATL", 8, 19, "DCA", 15, 1, 0),
    Vuelo("DL", 394, "ATL", 20, 46, "DCA", 12, 25, 0),
    Vuelo("DL", 505, "ORD", 18, 59, "DFW", 11, 18, 0),
    Vuelo("DL", 859, "DFW", 8, 12, "LAX", 10, 19, 1),
    Vuelo("DL", 369, "ATL", 8, 28, "LAX", 11, 30, 1),
    Vuelo("DL", 941, "DFW", 11, 45, "ATL", 16, 5, 1),
    Vuelo("DL", 691, "ATL", 15, 25, "DFW", 18, 11, 2),
    Vuelo("DL", 389, "PHL", 19, 35, "ATL", 11, 37, 0),
    Vuelo("DL", 509, "MIA", 16, 10, "DEN", 19, 55, 1),
    Vuelo("DL", 703, "MIA", 9, 0, "SEA", 13, 40, 1),
    Vuelo("DL", 660, "ABQ", 13, 25, "DCA", 10, 45, 1),
    Vuelo("DL", 326, "ATL", 19, 5, "BOS", 11, 35, 0),
    Vuelo("DL", 142, "ATL", 13, 21, "ORD", 14, 20, 0),
    Vuelo("DL", 618, "SFO", 7, 0, "MSY", 14, 50, 1),
    Vuelo("DL", 574, "ATL", 23, 45, "ORD", 12, 30, 0),
    Vuelo("DL", 618, "SFO", 7, 0, "ATL", 17, 54, 2),
    Vuelo("DL", 335, "ATL", 18, 56, "ORD", 19, 50, 0),
    Vuelo("DL", 405, "ATL", 18, 41, "DFW", 19, 55, 0),
    Vuelo("DL", 945, "MSP", 7, 35, "ATL", 10, 48, 0),
    Vuelo("DL", 852, "SFO", 17, 55, "DFW", 13, 2, 0),
    Vuelo("DL", 367, "BOS", 14, 20, "MIA", 11, 20, 2),
    Vuelo("DL", 141, "TPA", 17, 55, "SFO", 12, 17, 1),
    Vuelo("DL", 887, "SEA", 17, 45, "LAX", 11, 9, 1),
    Vuelo("DL", 998, "HOU", 14, 55, "DFW", 15, 55, 0),
    Vuelo("DL", 394, "DFW", 15, 5, "DCA", 12, 25, 2),
    Vuelo("DL", 382, "ATL", 15, 21, "SFO", 17, 30, 0),
    Vuelo("DL", 793, "ATL", 8, 19, "MSY", 15, 0, 0),
    Vuelo("DL", 180, "SFO", 14, 30, "DFW", 19, 48, 0),
    Vuelo("DL", 309, "DFW", 15, 1, "LAX", 19, 10, 2),
    Vuelo("DL", 131, "DFW", 8, 6, "LAX", 12, 0, 0),
    Vuelo("DL", 883, "ATL", 18, 56, "DFW", 11, 25, 1),
    Vuelo("DL", 174, "SEA", 6, 30, "DFW", 12, 7, 0)
  )

  val vuelosC3= List(
    Vuelo("DL", 491, "ATL", 18, 59, "MIA", 10, 45, 0),
    Vuelo("DL", 766, "ATL", 17, 5, "DTW", 18, 55, 0),
    Vuelo("DL", 202, "ATL", 6, 36, "PHL", 12, 5, 0),
    Vuelo("DL", 558, "DFW", 20, 46, "MIA", 12, 15, 0),
    Vuelo("DL", 276, "DFW", 6, 24, "TPA", 13, 0, 0),
    Vuelo("DL", 546, "DFW", 9, 40, "MIA", 13, 10, 0),
    Vuelo("DL", 954, "ATL", 8, 33, "MSP", 10, 10, 0),
    Vuelo("DL", 655, "BOS", 8, 20, "ATL", 10, 59, 0),
    Vuelo("DL", 205, "BOS", 9, 45, "ATL", 12, 23, 0),
    Vuelo("DL", 376, "ATL", 17, 7, "BNA", 17, 10, 0),
    Vuelo("DL", 339, "ATL", 6, 32, "MIA", 11, 0, 0),
    Vuelo("DL", 853, "DFW", 18, 51, "HOU", 19, 55, 0),
    Vuelo("DL", 808, "LAX", 8, 0, "TPA", 15, 37, 0),
    Vuelo("DL", 701, "BOS", 15, 45, "DCA", 17, 16, 0),
    Vuelo("DL", 933, "SFO", 9, 0, "LAX", 10, 16, 0),
    Vuelo("DL", 535, "BOS", 8, 20, "DCA", 10, 0, 0),
    Vuelo("DL", 996, "LAX", 12, 30, "DFW", 11, 4, 0),
    Vuelo("DL", 170, "SEA", 8, 30, "LAX", 10, 52, 0),
    Vuelo("DL", 202, "ATL", 6, 36, "BOS", 10, 4, 1),
    Vuelo("DL", 514, "TPA", 19, 10, "BOS", 11, 54, 0),
    Vuelo("DL", 325, "ATL", 20, 57, "MIA", 12, 35, 0),
    Vuelo("DL", 314, "ATL", 6, 25, "DCA", 15, 5, 0),
    Vuelo("DL", 618, "MSY", 15, 35, "ATL", 17, 54, 0),
    Vuelo("DL", 464, "MIA", 9, 10, "ATL", 10, 58, 0),
    Vuelo("DL", 170, "SEA", 8, 30, "TPA", 10, 43, 2),
    Vuelo("DL", 527, "DCA", 8, 50, "ABQ", 12, 30, 1),
    Vuelo("DL", 994, "SEA", 8, 25, "DFW", 15, 40, 1),
    Vuelo("DL", 197, "ATL", 18, 46, "SEA", 11, 5, 0),
    Vuelo("DL", 167, "MIA", 6, 0, "ATL", 14, 1, 0),
    Vuelo("DL", 410, "ATL", 10, 3, "BOS", 12, 20, 0),
    Vuelo("DL", 596, "ATL", 6, 26, "ORD", 11, 5, 0),
    Vuelo("DL", 300, "MSY", 13, 35, "ATL", 15, 56, 0),
    Vuelo("DL", 402, "DEN", 7, 45, "ATL", 12, 26, 0),
    Vuelo("DL", 644, "LAX", 6, 0, "SFO", 10, 6, 0),
    Vuelo("DL", 850, "ATL", 6, 24, "BOS", 14, 5, 0),
    Vuelo("DL", 412, "TPA", 14, 20, "DTW", 11, 55, 3),
    Vuelo("DL", 657, "ATL", 19, 8, "BNA", 19, 10, 0),
    Vuelo("DL", 378, "ATL", 15, 19, "BOS", 18, 55, 1),
    Vuelo("DL", 307, "MSY", 13, 0, "SFO", 16, 50, 1),
    Vuelo("DL", 202, "PHL", 8, 55, "BOS", 10, 4, 0),
    Vuelo("DL", 209, "DCA", 15, 35, "DFW", 17, 51, 0),
    Vuelo("DL", 756, "DFW", 13, 0, "ATL", 17, 59, 2),
    Vuelo("DL", 444, "ATL", 8, 19, "DFW", 11, 9, 2),
    Vuelo("DL", 223, "PHL", 16, 0, "ATL", 18, 5, 0),
    Vuelo("DL", 442, "LAX", 16, 0, "SFO", 17, 12, 0),
    Vuelo("DL", 252, "DFW", 20, 45, "PHL", 12, 45, 0),
    Vuelo("DL", 495, "RDU", 9, 40, "ATL", 11, 4, 0),
    Vuelo("DL", 462, "DFW", 20, 24, "DCA", 12, 5, 0),
    Vuelo("DL", 412, "RDU", 18, 40, "DTW", 11, 55, 1),
    Vuelo("DL", 258, "MIA", 12, 25, "BOS", 15, 25, 0),
    Vuelo("DL", 783, "DFW", 13, 41, "ATL", 17, 48, 1),
    Vuelo("DL", 369, "PHX", 11, 5, "LAX", 11, 30, 0),
    Vuelo("DL", 981, "ORD", 19, 55, "ATL", 12, 36, 0),
    Vuelo("DL", 335, "DFW", 13, 6, "ATL", 17, 56, 2),
    Vuelo("DL", 397, "ATL", 18, 49, "DFW", 11, 36, 1),
    Vuelo("DL", 394, "DFW", 15, 5, "ATL", 19, 55, 1),
    Vuelo("DL", 618, "DFW", 13, 15, "MSY", 14, 50, 0),
    Vuelo("DL", 824, "DFW", 6, 17, "MSY", 13, 5, 0),
    Vuelo("DL", 557, "ATL", 15, 12, "DFW", 18, 11, 2),
    Vuelo("DL", 867, "BOS", 15, 25, "ATL", 18, 5, 0),
    Vuelo("DL", 477, "TPA", 7, 45, "LAX", 10, 59, 1),
    Vuelo("DL", 378, "DCA", 17, 35, "BOS", 18, 55, 0),
    Vuelo("DL", 360, "PHX", 14, 25, "BOS", 12, 40, 1),
    Vuelo("DL", 179, "ATL", 13, 24, "DFW", 14, 30, 0),
    Vuelo("DL", 521, "ORD", 15, 45, "DFW", 18, 0, 0),
    Vuelo("DL", 739, "MIA", 6, 30, "MSY", 15, 5, 1),
    Vuelo("DL", 944, "LAX", 9, 0, "SFO", 10, 18, 0),
    Vuelo("DL", 790, "DFW", 9, 40, "ATL", 12, 42, 0),
    Vuelo("DL", 314, "PHX", 12, 10, "ATL", 12, 9, 0),
    Vuelo("DL", 179, "ATL", 13, 24, "LAX", 16, 40, 1),
    Vuelo("DL", 758, "ATL", 13, 22, "DFW", 16, 14, 2),
    Vuelo("DL", 395, "ATL", 13, 39, "MIA", 15, 25, 0),
    Vuelo("DL", 611, "STL", 19, 59, "ATL", 12, 23, 0),
    Vuelo("DL", 663, "BOS", 20, 45, "DCA", 12, 17, 0),
    Vuelo("DL", 636, "DFW", 6, 15, "ATL", 11, 0, 2),
    Vuelo("DL", 166, "SEA", 12, 30, "DCA", 11, 45, 1),
    Vuelo("DL", 790, "DFW", 9, 40, "TPA", 15, 5, 1),
    Vuelo("DL", 422, "TPA", 7, 25, "ATL", 15, 6, 0),
    Vuelo("DL", 412, "ATL", 16, 48, "DTW", 11, 55, 2),
    Vuelo("DL", 277, "DCA", 14, 5, "DFW", 16, 15, 0),
    Vuelo("DL", 772, "LAX", 19, 0, "SFO", 10, 15, 0),
    Vuelo("DL", 765, "ATL", 20, 59, "TPA", 12, 27, 0),
    Vuelo("DL", 446, "ATL", 20, 51, "LAX", 12, 40, 0),
    Vuelo("DL", 307, "MSY", 13, 0, "DFW", 14, 27, 0),
    Vuelo("DL", 304, "SEA", 22, 25, "ATL", 14, 3, 0),
    Vuelo("DL", 500, "DFW", 6, 15, "ATL", 10, 55, 2),
    Vuelo("DL", 556, "DFW", 16, 53, "ATL", 11, 35, 2),
    Vuelo("DL", 170, "MSY", 18, 25, "TPA", 10, 43, 0),
    Vuelo("DL", 219, "DFW", 18, 51, "PHX", 10, 20, 0),
    Vuelo("DL", 539, "DCA", 9, 5, "ATL", 10, 55, 0),
    Vuelo("DL", 154, "DFW", 20, 8, "ATL", 13, 5, 0),
    Vuelo("DL", 719, "ATL", 15, 10, "DFW", 18, 6, 1),
    Vuelo("DL", 603, "BOS", 15, 25, "TPA", 18, 39, 0),
    Vuelo("DL", 987, "SFO", 7, 0, "LAX", 10, 7, 0),
    Vuelo("DL", 182, "LAX", 12, 5, "ATL", 10, 9, 0),
    Vuelo("DL", 170, "LAX", 12, 15, "MSY", 17, 53, 0),
    Vuelo("DL", 878, "ATL", 19, 1, "STL", 19, 40, 0),
    Vuelo("DL", 17, "ATL", 10, 6, "DFW", 11, 10, 0),
    Vuelo("DL", 569, "SFO", 17, 0, "LAX", 18, 10, 0),
    Vuelo("DL", 307, "ATL", 11, 44, "SFO", 16, 50, 2)
  )

  val vuelosC4= List(
    Vuelo("DL", 171, "LAX", 21, 59, "SEA", 12, 32, 0),
    Vuelo("DL", 592, "DFW", 17, 57, "ATL", 12, 39, 2),
    Vuelo("DL", 143, "ATL", 22, 35, "SFO", 15, 9, 1),
    Vuelo("DL", 409, "ATL", 8, 20, "DFW", 11, 8, 2),
    Vuelo("DL", 837, "ATL", 12, 3, "DFW", 14, 17, 1),
    Vuelo("DL", 269, "ATL", 12, 0, "LAX", 13, 30, 0),
    Vuelo("DL", 239, "ATL", 8, 33, "DFW", 11, 5, 1),
    Vuelo("DL", 561, "ORD", 5, 30, "MIA", 10, 45, 1),
    Vuelo("DL", 314, "PHX", 12, 10, "DCA", 15, 5, 1),
    Vuelo("DL", 828, "STL", 6, 30, "ATL", 15, 8, 0),
    Vuelo("DL", 768, "ABQ", 9, 45, "DFW", 12, 23, 0),
    Vuelo("DL", 991, "DCA", 19, 55, "ATL", 11, 37, 0),
    Vuelo("DL", 139, "ATL", 15, 14, "MSY", 15, 45, 0),
    Vuelo("DL", 156, "SFO", 22, 20, "ATL", 13, 9, 0),
    Vuelo("DL", 819, "DFW", 15, 0, "LAX", 17, 8, 1),
    Vuelo("DL", 307, "DFW", 15, 14, "SFO", 16, 50, 0),
    Vuelo("DL", 509, "MIA", 16, 10, "DFW", 18, 8, 0),
    Vuelo("DL", 891, "BOS", 9, 40, "LAX", 13, 55, 1),
    Vuelo("DL", 487, "BOS", 18, 50, "SFO", 13, 59, 1),
    Vuelo("DL", 808, "SFO", 6, 0, "TPA", 15, 37, 1),
    Vuelo("DL", 157, "ATL", 15, 29, "LAX", 16, 55, 0),
    Vuelo("DL", 967, "STL", 10, 10, "ATL", 12, 35, 0),
    Vuelo("DL", 877, "DFW", 11, 52, "HOU", 12, 55, 0),
    Vuelo("DL", 691, "ATL", 15, 25, "SFO", 10, 55, 3),
    Vuelo("DL", 926, "DFW", 20, 23, "RDU", 13, 55, 0),
    Vuelo("DL", 285, "DFW", 11, 51, "LAX", 13, 0, 0),
    Vuelo("DL", 252, "LAX", 13, 35, "PHX", 15, 53, 0),
    Vuelo("DL", 847, "ATL", 15, 23, "DFW", 17, 55, 1),
    Vuelo("DL", 335, "DFW", 13, 6, "ORD", 19, 50, 3),
    Vuelo("DL", 957, "BOS", 15, 35, "SFO", 10, 45, 1),
    Vuelo("DL", 219, "TPA", 16, 15, "PHX", 10, 20, 1),
    Vuelo("DL", 309, "DEN", 16, 25, "LAX", 19, 10, 1),
    Vuelo("DL", 464, "MIA", 9, 10, "DCA", 13, 25, 1),
    Vuelo("DL", 444, "ATL", 8, 19, "PHX", 13, 10, 3),
    Vuelo("DL", 478, "DFW", 20, 31, "BNA", 12, 15, 0),
    Vuelo("DL", 986, "MIA", 12, 45, "ATL", 14, 33, 0),
    Vuelo("DL", 298, "ATL", 20, 37, "ORD", 11, 40, 0),
    Vuelo("DL", 522, "DFW", 13, 2, "DCA", 16, 59, 0),
    Vuelo("DL", 16, "DFW", 6, 15, "ATL", 11, 8, 0),
    Vuelo("DL", 618, "DFW", 13, 15, "ATL", 17, 54, 1),
    Vuelo("DL", 412, "TPA", 14, 20, "RDU", 18, 0, 1),
    Vuelo("DL", 868, "TPA", 6, 10, "ATL", 13, 0, 0),
    Vuelo("DL", 808, "SFO", 6, 0, "LAX", 10, 4, 0),
    Vuelo("DL", 806, "ATL", 6, 30, "RDU", 14, 0, 0),
    Vuelo("DL", 642, "SFO", 18, 0, "LAX", 19, 11, 0),
    Vuelo("DL", 550, "LAX", 17, 0, "SFO", 18, 12, 0),
    Vuelo("DL", 892, "MIA", 6, 10, "TPA", 10, 0, 0),
    Vuelo("DL", 947, "BNA", 10, 40, "ATL", 12, 37, 0),
    Vuelo("DL", 487, "DFW", 22, 24, "SFO", 13, 59, 0),
    Vuelo("DL", 374, "SFO", 12, 20, "DFW", 12, 3, 0),
    Vuelo("DL", 274, "LAX", 14, 0, "SFO", 15, 15, 0),
    Vuelo("DL", 552, "TPA", 18, 10, "ATL", 19, 29, 0),
    Vuelo("DL", 279, "DFW", 22, 50, "LAX", 13, 58, 0),
    Vuelo("DL", 124, "DFW", 13, 0, "ORD", 15, 14, 0),
    Vuelo("DL", 453, "SFO", 16, 0, "LAX", 17, 11, 0),
    Vuelo("DL", 744, "LAX", 7, 0, "SFO", 11, 7, 0),
    Vuelo("DL", 834, "MIA", 14, 15, "ATL", 16, 10, 0),
    Vuelo("DL", 297, "RDU", 13, 5, "ATL", 14, 27, 0),
    Vuelo("DL", 545, "ATL", 11, 44, "DFW", 14, 30, 1),
    Vuelo("DL", 188, "LAX", 22, 55, "ATL", 14, 7, 0),
    Vuelo("DL", 790, "ATL", 13, 41, "TPA", 15, 5, 0),
    Vuelo("DL", 724, "ATL", 18, 58, "RDU", 10, 15, 0),
    Vuelo("DL", 946, "ATL", 11, 57, "DFW", 14, 17, 1),
    Vuelo("DL", 721, "DFW", 15, 11, "ORD", 17, 20, 0),
    Vuelo("DL", 539, "DCA", 9, 5, "TPA", 13, 20, 1),
    Vuelo("DL", 904, "DFW", 11, 43, "ATL", 15, 55, 1),
    Vuelo("DL", 408, "DFW", 8, 6, "HOU", 11, 0, 0),
    Vuelo("DL", 317, "ATL", 16, 57, "TPA", 18, 20, 0)

  )

  val vuelosC5= List(
    Vuelo("DL", 197, "TPA", 16, 10, "ATL", 17, 37, 0),
    Vuelo("DL", 208, "TPA", 11, 40, "BOS", 14, 24, 0),
    Vuelo("DL", 233, "DFW", 11, 40, "DEN", 12, 37, 0),
    Vuelo("DL", 817, "ATL", 18, 42, "DFW", 11, 40, 2),
    Vuelo("DL", 598, "ATL", 11, 47, "ORD", 12, 35, 0),
    Vuelo("DL", 347, "BOS", 6, 0, "SFO", 11, 10, 1),
    Vuelo("DL", 141, "LAX", 21, 0, "SFO", 12, 17, 0),
    Vuelo("DL", 178, "LAX", 10, 40, "ATL", 17, 48, 0),
    Vuelo("DL", 977, "ATL", 12, 9, "SFO", 14, 15, 0),
    Vuelo("DL", 575, "ORD", 13, 20, "ATL", 16, 7, 0),
    Vuelo("DL", 125, "ATL", 11, 47, "DFW", 14, 25, 1),
    Vuelo("DL", 970, "ATL", 19, 43, "DFW", 11, 39, 1),
    Vuelo("DL", 444, "DFW", 11, 53, "PHX", 13, 10, 0),
    Vuelo("DL", 748, "LAX", 10, 40, "DFW", 15, 28, 0),
    Vuelo("DL", 320, "MSP", 10, 55, "ATL", 14, 11, 0),
    Vuelo("DL", 940, "DTW", 12, 20, "DFW", 14, 3, 0),
    Vuelo("DL", 511, "BNA", 15, 50, "ATL", 17, 44, 0),
    Vuelo("DL", 299, "ATL", 11, 59, "DFW", 14, 15, 1),
    Vuelo("DL", 358, "DFW", 13, 7, "BOS", 17, 55, 0),
    Vuelo("DL", 726, "ATL", 10, 18, "PHL", 12, 15, 0),
    Vuelo("DL", 169, "BOS", 11, 5, "DFW", 13, 59, 0),
    Vuelo("DL", 803, "ATL", 8, 25, "DFW", 10, 55, 1),
    Vuelo("DL", 566, "HOU", 11, 40, "ATL", 14, 33, 0),
    Vuelo("DL", 964, "ATL", 10, 4, "DTW", 11, 50, 0),
    Vuelo("DL", 584, "DFW", 13, 16, "TPA", 16, 40, 0),
    Vuelo("DL", 401, "ORD", 6, 0, "ATL", 11, 8, 2),
    Vuelo("DL", 660, "DFW", 17, 0, "DCA", 10, 45, 0),
    Vuelo("DL", 262, "DFW", 13, 16, "DTW", 16, 55, 0),
    Vuelo("DL", 625, "ATL", 22, 31, "SEA", 13, 0, 1),
    Vuelo("DL", 30, "ATL", 12, 30, "JFK", 14, 38, 0),
    Vuelo("DL", 139, "DCA", 12, 40, "ATL", 14, 28, 0),
    Vuelo("DL", 755, "BOS", 11, 45, "ATL", 14, 26, 0),
    Vuelo("DL", 469, "ATL", 22, 22, "MIA", 13, 56, 0),
    Vuelo("DL", 583, "MSP", 9, 0, "TPA", 14, 35, 1),
    Vuelo("DL", 174, "DFW", 13, 7, "MIA", 16, 45, 0),
    Vuelo("DL", 684, "LAX", 15, 0, "SFO", 16, 11, 0),
    Vuelo("DL", 276, "TPA", 10, 0, "PHL", 13, 40, 1),
    Vuelo("DL", 582, "TPA", 21, 30, "ATL", 12, 47, 0),
    Vuelo("DL", 523, "DCA", 10, 45, "SEA", 14, 30, 1),
    Vuelo("DL", 919, "PHL", 5, 35, "ATL", 13, 0, 0),
    Vuelo("DL", 307, "ATL", 11, 44, "MSY", 12, 16, 0),
    Vuelo("DL", 163, "SFO", 13, 0, "LAX", 14, 15, 0),
    Vuelo("DL", 408, "DFW", 8, 6, "ATL", 12, 35, 1),
    Vuelo("DL", 991, "DCA", 19, 55, "MSY", 12, 50, 1),
    Vuelo("DL", 139, "MSY", 16, 25, "DFW", 17, 53, 0),
    Vuelo("DL", 694, "DFW", 23, 49, "MSY", 10, 5, 0),
    Vuelo("DL", 688, "DFW", 15, 0, "ATL", 18, 0, 0),
    Vuelo("DL", 703, "DFW", 11, 43, "SEA", 13, 40, 0),
    Vuelo("DL", 821, "DTW", 8, 45, "ATL", 10, 29, 0),
    Vuelo("DL", 991, "ATL", 22, 32, "MSY", 12, 50, 0),
    Vuelo("DL", 256, "ATL", 20, 59, "DTW", 12, 50, 0),
    Vuelo("DL", 472, "DFW", 11, 38, "MIA", 17, 15, 1),
    Vuelo("DL", 145, "ATL", 15, 10, "DFW", 16, 22, 0),
    Vuelo("DL", 581, "DFW", 8, 12, "ABQ", 10, 5, 0),
    Vuelo("DL", 387, "SFO", 15, 0, "LAX", 16, 11, 0),
    Vuelo("DL", 260, "DFW", 17, 58, "ATL", 12, 54, 2),
    Vuelo("DL", 390, "HOU", 6, 15, "DFW", 12, 4, 0),
    Vuelo("DL", 856, "LAX", 8, 0, "SFO", 11, 7, 0),
    Vuelo("DL", 645, "DTW", 16, 0, "DFW", 17, 50, 0),
    Vuelo("DL", 408, "HOU", 9, 40, "ATL", 12, 35, 0),
    Vuelo("DL", 996, "LAX", 12, 30, "BOS", 11, 46, 2),
    Vuelo("DL", 508, "DFW", 10, 41, "ATL", 14, 27, 1),
    Vuelo("DL", 474, "ATL", 23, 55, "TPA", 11, 0, 0),
    Vuelo("DL", 578, "TPA", 11, 15, "ATL", 12, 44, 0),
    Vuelo("DL", 539, "ATL", 11, 54, "TPA", 13, 20, 0),
    Vuelo("DL", 845, "ATL", 15, 27, "DFW", 17, 52, 1),
    Vuelo("DL", 436, "TPA", 9, 35, "ATL", 11, 2, 0),
    Vuelo("DL", 321, "ATL", 8, 19, "MIA", 10, 0, 0),
    Vuelo("DL", 356, "ATL", 15, 30, "BOS", 17, 52, 0),
    Vuelo("DL", 449, "ATL", 11, 44, "MIA", 13, 25, 0),
    Vuelo("DL", 558, "SEA", 12, 35, "MIA", 12, 15, 2),
    Vuelo("DL", 558, "SEA", 12, 35, "DFW", 19, 43, 1),
    Vuelo("DL", 588, "PHX", 12, 55, "DTW", 10, 30, 1),
    Vuelo("DL", 873, "ATL", 9, 7, "DFW", 11, 8, 1),
    Vuelo("DL", 333, "DFW", 22, 28, "ABQ", 13, 10, 0),
    Vuelo("DL", 470, "ATL", 18, 45, "PVD", 11, 0, 0),
    Vuelo("DL", 222, "PHX", 10, 30, "ATL", 16, 10, 0),
    Vuelo("DL", 464, "ATL", 11, 45, "DCA", 13, 25, 0),
    Vuelo("DL", 528, "DCA", 6, 50, "BOS", 10, 9, 0),
    Vuelo("DL", 1042, "ABQ", 6, 30, "DFW", 10, 1, 0),
    Vuelo("DL", 651, "MSY", 6, 0, "DFW", 13, 1, 0),
    Vuelo("DL", 820, "ATL", 10, 14, "ORD", 11, 5, 0),
    Vuelo("DL", 170, "LAX", 12, 15, "TPA", 10, 43, 1),
    Vuelo("DL", 946, "ATL", 11, 57, "HOU", 16, 3, 2),
    Vuelo("DL", 819, "ORD", 11, 55, "LAX", 17, 8, 2),
    Vuelo("FF", 37, "JFK", 20, 45, "MIA", 13, 40, 0),
    Vuelo("HP", 2, "PHX", 8, 0, "ORD", 12, 20, 0),
    Vuelo("HP", 44, "ATL", 8, 15, "LAX", 11, 25, 1),
    Vuelo("HP", 7, "JFK", 17, 20, "PHX", 10, 57, 0),
    Vuelo("HP", 38, "DEN", 18, 3, "LAX", 10, 30, 1),
    Vuelo("HP", 268, "PHX", 14, 33, "DCA", 11, 51, 1),
    Vuelo("HP", 862, "DFW", 17, 14, "PHX", 18, 46, 0),
    Vuelo("HP", 547, "PHX", 15, 37, "DFW", 19, 1, 0),
    Vuelo("HP", 594, "SFO", 11, 53, "PHX", 14, 44, 0),
    Vuelo("HP", 207, "PHX", 13, 7, "ABQ", 14, 15, 0),
    Vuelo("HP", 97, "DEN", 16, 50, "PHX", 18, 49, 0),
    Vuelo("HP", 836, "ABQ", 12, 30, "PHX", 13, 45, 0),
    Vuelo("HP", 636, "DFW", 19, 34, "PHX", 11, 3, 0),
    Vuelo("HP", 518, "SEA", 16, 0, "PHX", 19, 50, 0),
    Vuelo("HP", 864, "DCA", 16, 40, "PHX", 10, 59, 1)
  )




  type Itinerario = List[Vuelo]

  def toItin(vuelo: Vuelo) = List(vuelo)

  def vuelosOrigen(origen: String, vuelos: List[Vuelo]): List[Vuelo] = vuelos.filter(p => p.Org == origen)
  def vuelosOrigenPar(origen: String, vuelos: ParSeq[Vuelo]): ParSeq[Vuelo] = vuelos.filter(p => p.Org == origen).par

  def escalasPosibles(origen: String, vuelos: List[Vuelo]): List[Vuelo] = vuelos.filterNot(v => v.Org== origen || v.Dst== origen)
  def escalasPosiblesPar(origen: String, vuelos: ParSeq[Vuelo]): ParSeq[Vuelo] = vuelos.filterNot(v => v.Org== origen || v.Dst== origen).par


  def lleganDestino(destino: String, itinerarios: List[Itinerario]): List[Itinerario] = itinerarios.filter((itin:Itinerario) => itin.last.Dst == destino)
  def lleganDestinoPar(destino: String, itinerarios: ParSeq[Itinerario]): ParSeq[Itinerario] = itinerarios.filter((itin:Itinerario) => itin.last.Dst == destino)



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

        val vuelosConMismoOrigen = vuelosOrigen(a1, vuelo).map(vuelo => List(vuelo))

        val itinEscala = for {
          vueloOrigen <- vuelosConMismoOrigen.map(f => f.head)
          vueloEscala <- auxItinerario(vueloOrigen.Dst, escalasPosibles(vueloOrigen.Org, vuelo))
        } yield vueloOrigen :: vueloEscala
        vuelosConMismoOrigen ++ itinEscala
      }
    }

    lleganDestino(a2, auxItinerario(a1, vuelosC4))
  }

  def itinerariosPar(a1: String, a2: String): ParSeq[Itinerario] = {
    def auxItinerario(a1: String, vuelo: List[Vuelo]): ParSeq[Itinerario] = {
      if (vuelo.isEmpty) {
        List(List()).par

      } else {

        val vuelosConMismoOrigen = vuelosOrigenPar(a1, vuelo.par).map(vuelo => List(vuelo))

        val itinEscala = for {
          vueloOrigen <- vuelosConMismoOrigen.map(f => f.head)
          vueloEscala <- auxItinerario(vueloOrigen.Dst, escalasPosiblesPar(vueloOrigen.Org, vuelo.par).toList)
        } yield vueloOrigen :: vueloEscala
        vuelosConMismoOrigen ++ itinEscala
      }
    }
    lleganDestinoPar(a2, auxItinerario(a1, vuelosC4))
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

    if (gmt.head > 0) { //restar
      if ((hora - gmt.head) < 0) { //baja de 0 la hora
        if ((min - gmt.last) < 0) { //baja de 0 el minuto
          val h = 24 + (hora - gmt.head)
          val m = 60 + (min - gmt.last)
          List(h, m)
        } else { //se mantiene el minuto
          val h = 24 + (hora - gmt.head)
          val m = min - gmt.last
          List(h, m)
        }
      } else { //se mantiene la hora
        if ((min - gmt.last) < 0) { //baja de 0 el minuto
          val h = hora - gmt.head
          val m = 60 + (min - gmt.last)
          List(h, m)
        } else { //se mantiene el minuto
          val h = hora - gmt.head
          val m = min - gmt.last
          List(h, m)
        }
      }
    } else if (gmt.head < 0) { //sumar
      if ((hora + (-gmt.head)) > 24) { //sube de 24 la hora
        if ((min + (-gmt.last)) > 60) { //sube de 60 el minuto
          val h = (hora + (-gmt.head)) - 24
          val m = (min + (-gmt.last)) - 60
          List(h, m)
        } else { //se mantiene el minuto
          val h = (hora + (-gmt.head)) - 24
          val m = min + gmt.last
          List(h, m)
        }
      } else { //se mantiene la hora
        if ((min + (-gmt.last)) > 60) { //sube de 60 el minuto
          val h = hora + (-gmt.head)
          val m = (min + (-gmt.last)) - 60
          List(h, m)
        } else { //se mantiene el minuto
          val h = hora + (-gmt.head)
          val m = min + gmt.last
          List(h, m)
        }
      }
    } else { //queda igual
      val h = hora
      val m = min
      List(h, m)
    }

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

  def itenerariosMenorTiempoTotalPar(a1:String, a2:String): List [Itinerario] = {
    val itinerarios = itinerariosPar(a1:String, a2:String).map((itinerario:Itinerario) => itinerarioGMT(itinerario)).toList
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

  def itinierariosMenorEscalasPar(a1: String, a2: String): List[Itinerario] = {
    val itinerario = itinerariosPar(a1,a2).toList
    itinerario.sortBy(itinerario => sumEscalas(itinerario)).take(3)
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

  def itenerariosMenorTiempoPar(a1:String, a2:String): List [Itinerario] = {
    val itinerarios = itinerariosPar(a1:String, a2:String)
    val vuelos = itinerarios.map((itinerario:Itinerario) => itinerarioGMT(itinerario)).toList
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

  def itinerariosSalidaPar(a1:String, a2:String, h: Int, m: Int): List[Itinerario] = {
    val itinerarios = itinerariosPar(a1,a2)
    val vuelosATiempo = itinerarios.filter((p:Itinerario) => horatoInt(List(p.last.HL.toString, p.last.ML.toString))
      < horatoInt(List(h.toString, m.toString))).toList
    vuelosATiempo.sortBy((i:Itinerario) => horatoInt(List(i.head.HS.toString, i.head.MS.toString))).reverse.take(3)
  }

}
