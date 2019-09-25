package io.toxa108.pokerito.testfrontend.client

import io.grpc.*

import java.util.concurrent.Executor;

class BearerToken constructor(private val token: String): CallCredentials {

    @Override
    override fun applyRequestMetadata(method: MethodDescriptor<*, *>?, attrs: Attributes?, appExecutor: Executor?, applier: CallCredentials.MetadataApplier?) {
        appExecutor?.execute {
            try {
                val headers = Metadata();
                headers.put(Const.AUTHORIZATION_METADATA_KEY, String.format("%s %s", Const.BEARER_TYPE, token));
                applier?.apply(headers);
            } catch (e: Throwable) {
                applier?.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        }
    }

    override fun thisUsesUnstableApi() {

    }
}