/*
package com.hackathon

import java.sql.DriverManager

fun probarConexionMySQL(): Boolean {
    val url = "jdbc:mysql://34.170.94.214:3306/dummy_db?useSSL=false"
    val user = "Obed"
    val password = "1234obed"

    return try {
        val conn = DriverManager.getConnection(url, user, password)
        println("✅ Conexión exitosa a la base de datos")
        conn.close()
        true
    } catch (e: Exception) {
        println("❌ Error al conectar: ${e.message}")
        false
    }
}
//1234
//12345
*/
