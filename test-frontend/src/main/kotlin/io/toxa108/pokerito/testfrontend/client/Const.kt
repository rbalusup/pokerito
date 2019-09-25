package io.toxa108.pokerito.testfrontend.client

import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.Metadata.ASCII_STRING_MARSHALLER

// todo move to consul or k8s secret
class Const private constructor() {

    companion object {
        const val JWT_SIGNING_KEY = "L8hHXsaQOUjk5rg7XPGv4eL36anlCrkMz8CJ0i/8E/0="
        const val BEARER_TYPE = "Bearer"

        val AUTHORIZATION_METADATA_KEY: Metadata.Key<String> = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER)
        val CLIENT_ID_CONTEXT_KEY: Context.Key<String> = Context.key("clientId")
    }
}