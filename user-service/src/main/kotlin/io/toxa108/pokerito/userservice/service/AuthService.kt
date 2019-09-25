package io.toxa108.pokerito.userservice.service

import io.grpc.Context
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.toxa108.pokerito.userservice.Const
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class AuthService {
    private val tokens = HashMap<UUID,String>()
    private val parser = Jwts.parser().setSigningKey(Const.JWT_SIGNING_KEY)

    fun generateToken(id: UUID): String {
        val token = Jwts.builder()
                .setPayload(id.toString())
                .signWith(SignatureAlgorithm.HS256, Const.JWT_SIGNING_KEY)
                .compact()

        tokens[id] = token
        return token
    }

    fun getToken(id: UUID) = tokens[id]

    fun extractId(token: String) {
        val token = token.substring(Const.BEARER_TYPE.length).trim();
        val jwt = parser.parse(token);
        val ctx = Context.current().withValue(Const.CLIENT_ID_CONTEXT_KEY, jwt.body as String);
    }
}