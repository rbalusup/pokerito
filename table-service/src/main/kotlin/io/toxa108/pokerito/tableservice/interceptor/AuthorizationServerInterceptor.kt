package io.toxa108.pokerito.tableservice.interceptor

import io.grpc.*
import io.jsonwebtoken.Jwts
import io.toxa108.pokerito.tableservice.Const

class AuthorizationServerInterceptor: ServerInterceptor {

    private val parser = Jwts.parser().setSigningKey(Const.JWT_SIGNING_KEY)

    private val accessWithoutToken = emptyList<String>()

    companion object {
        val USER_IDENTITY: Context.Key<String> = Context.key("userId")
    }

    override fun <ReqT, RespT> interceptCall(
            call: ServerCall<ReqT, RespT>,
            headers: Metadata,
            next: ServerCallHandler<ReqT, RespT>): ServerCall.Listener<ReqT> {

        if (accessWithoutToken.contains(call.methodDescriptor.fullMethodName)) {
            return Contexts.interceptCall(Context.current(), call, headers, next)
        }

        val value = headers.get(Const.AUTHORIZATION_METADATA_KEY)

        val status: Status
        status = if (value == null) {
            Status.UNAUTHENTICATED.withDescription("Authorization token is missing")
        } else if (!value.startsWith(Const.BEARER_TYPE)) {
            Status.UNAUTHENTICATED.withDescription("Unknown authorization type")
        } else {
            try {
                val token = value.substring(Const.BEARER_TYPE.length).trim()
                val jwt = parser.parse(token)
                var ctx = Context.current()
                ctx = ctx.withValue(USER_IDENTITY, jwt.body.toString())
                return Contexts.interceptCall(ctx, call, headers, next)
            } catch (e: Exception) {
                Status.UNAUTHENTICATED.withDescription(e.message).withCause(e)
            }
        }

        call.close(status, headers)
        return object : ServerCall.Listener<ReqT>() {
        }
    }
}