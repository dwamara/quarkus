package io.quarkus.it.kotser

import io.quarkus.it.kotser.model.Person
import io.quarkus.runtime.annotations.RegisterForReflection
import java.lang.reflect.Method
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import kotlin.reflect.jvm.javaMethod

@Path("/")
@RegisterForReflection
class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun hello(): Person {
        return Person("Jim Halpert")
    }

    @Path("suspend")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun suspendHello(): Person {
        return Person("Jim Halpert")
    }

    @Path("suspendList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun suspendHelloList() = listOf(Person("Jim Halpert"))

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun marry(person: Person): Person {
        return Person(person.name.substringBefore(" ") + " Halpert")
    }

    @GET
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    fun create(): Response? {
        val javaMethod: Method = this::reflect.javaMethod!!
        return Response
            .ok()
            .entity(javaMethod.invoke(this))
            .build()
    }

    fun reflect() = "hello, world"
}

