package com.hackathon

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.*
import java.sql.DriverManager

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/usuario/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respondText("ID inválido", status = HttpStatusCode.BadRequest)
                return@get
            }

            val url = "jdbc:mysql://34.170.94.214:3306/dummy_db?useSSL=false"
            val user = "Obed"
            val password = "1234obed"

            try {
                val connection = DriverManager.getConnection(url, user, password)
                val statement = connection.prepareStatement("SELECT name FROM dummy_table WHERE id = ?")
                statement.setInt(1, id)

                val resultSet = statement.executeQuery()
                if (resultSet.next()) {
                    val nombre = resultSet.getString("name")
                    call.respondText("Nombre: $nombre")
                } else {
                    call.respondText("Usuario no encontrado", status = HttpStatusCode.NotFound)
                }

                resultSet.close()
                statement.close()
                connection.close()

            } catch (e: Exception) {
                call.respondText("Error: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }

        get("/{name}/{id}") {
            val nombre = call.parameters["name"]
            val id = call.parameters["id"]?.toIntOrNull()

            if (nombre == null || id == null) {
                call.respondText("Nombre o ID inválido", status = HttpStatusCode.BadRequest)
                return@get
            }

            val url = "jdbc:mysql://34.170.94.214:3306/dummy_db?useSSL=false"
            val user = "Obed"
            val password = "1234obed"

            try {
                val connection = DriverManager.getConnection(url, user, password)
                val statement = connection.prepareStatement("INSERT INTO dummy_table (id, name) VALUES (?, ?)")
                statement.setInt(1, id)
                statement.setString(2, nombre)

                val filasInsertadas = statement.executeUpdate()

                statement.close()
                connection.close()

                if (filasInsertadas > 0) {
                    call.respondText("✅ Usuario insertado: ID=$id, Nombre=$nombre")
                } else {
                    call.respondText("❌ No se pudo insertar el usuario", status = HttpStatusCode.InternalServerError)
                }

            } catch (e: Exception) {
                call.respondText("Error al insertar: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }

        get("/saludo") {
            call.respondText("¡Este es mi saludo!", ContentType.Text.Plain)
        }
    }
}
