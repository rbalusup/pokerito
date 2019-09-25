package io.toxa108.pokerito.userservice.interceptor

import io.grpc.*
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.toxa108.pokerito.userservice.Const

class AuthorizationServerInterceptor: ServerInterceptor {

    private val parser = Jwts.parser().setSigningKey(Const.JWT_SIGNING_KEY);

    override fun <ReqT, RespT> interceptCall(
            call: ServerCall<ReqT, RespT>,
            headers: Metadata,
            next: ServerCallHandler<ReqT, RespT>): ServerCall.Listener<ReqT> {

        val value = headers.get(Const.AUTHORIZATION_METADATA_KEY);

        val status: Status;
        status = if (value == null) {
            Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!value.startsWith(Const.BEARER_TYPE)) {
            Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {
            try {
                val token = value.substring(Const.BEARER_TYPE.length).trim();
                val claims: Jws<Claims> = parser.parseClaimsJws(token);
                val ctx = Context.current().withValue(Const.CLIENT_ID_CONTEXT_KEY, claims.body.subject);
                return Contexts.interceptCall(ctx, call, headers, next);
            } catch (e: Exception) {
                Status.UNAUTHENTICATED.withDescription(e.message).withCause(e);
            }
        }

        call.close(status, headers);
        return object : ServerCall.Listener<ReqT>() {
        }
    }
}