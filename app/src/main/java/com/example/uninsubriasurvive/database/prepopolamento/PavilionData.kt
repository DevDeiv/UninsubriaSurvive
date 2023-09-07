package com.example.uninsubriasurvive.database.prepopolamento

import com.example.uninsubriasurvive.database.entity.Pavilion

class PavilionData {

    companion object{
        val pavilions = listOf<Pavilion>(
            Pavilion(
                null,
                "Seppilli",
                "Via Rossi 9, Varese",
                listOf(
                    "Lunedi' : 7:45 - 19:00",
                    "Martedi' : 7:45 - 19:00",
                    "Mercoledi' : 7:45 - 19:00",
                    "Giovedi' : 7:45 - 19:00",
                    "Venerdi' : 7:45 - 19:00",
                    "Sabato' : CHIUSO",
                    "Domenica' : CHIUSO",
                )),
            Pavilion(
                null,
                "Morselli",
                "Via Rossi 9, Varese",
                listOf(
                    "Lunedi' : 7:45 - 19:00",
                    "Martedi' : 7:45 - 19:00",
                    "Mercoledi' : 7:45 - 19:00",
                    "Giovedi' : 7:45 - 19:00",
                    "Venerdi' : 7:45 - 19:00",
                    "Sabato' : CHIUSO",
                    "Domenica' : CHIUSO",
                )),
            Pavilion(
                null,
                "Monte Generoso",
                "Via Monte Generoso 71, Varese",
                listOf(
                    "Lunedi' : 7:45 - 21:00",
                    "Martedi' : 7:45 - 21:00",
                    "Mercoledi' : 7:45 - 21:00",
                    "Giovedi' : 7:45 - 21:00",
                    "Venerdi' : 7:45 - 21:00",
                    "Sabato' : CHIUSO",
                    "Domenica' : CHIUSO",
                )),
        )
    }
}