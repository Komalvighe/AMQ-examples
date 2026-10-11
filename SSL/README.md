# AMQ Broker 7.14 — Jakarta JMS 3.0 TLS examples

Includes Java examples for one-way TLS and two-way TLS (mutual TLS), plus illustrative broker acceptor snippets.

## Dependency
In `pom.xml`, replace `YOUR_SUPPORTED_VERSION` with the Artemis Jakarta JMS client version supported for your AMQ Broker 7.14 environment. This project uses `jakarta.jms` imports.

## One-way TLS
- Broker presents its certificate.
- Java client validates the broker using `client-truststore.p12`.
- No client keystore is required.
- Broker acceptor requires `sslEnabled=true`, `keyStorePath`, and `keyStorePassword`.

## Two-way TLS (mTLS)
- Broker and client both present certificates.
- Client uses a truststore for the broker and a keystore containing its own certificate/private key.
- Broker acceptor additionally uses `needClientAuth=true` and a truststore that trusts the client certificate/CA.

## Configure and run
1. Edit broker hostname, queue, paths, and passwords in the Java files.
2. Configure the broker acceptor using the applicable snippet in `broker-config/`.
3. Ensure the `orders` queue exists or auto-creation is enabled.
4. Replace the placeholder dependency version in `pom.xml`.
5. Run:
   `mvn compile exec:java -Dexec.mainClass=com.example.amq.OneWayTlsJmsClient`
   or
   `mvn compile exec:java -Dexec.mainClass=com.example.amq.TwoWayTlsJmsClient`

TLS does not replace broker authentication/authorization. Examples use username/password in addition to TLS; configure credentials and roles for your environment. For certificate-based identity authentication, additional broker security/login-module configuration is required.

Troubleshooting:
- `PKIX path building failed`: client truststore does not trust broker cert/CA.
- `bad_certificate` or client certificate required: client keystore is missing/invalid or broker does not trust it.
- Hostname mismatch: broker certificate SAN must match the hostname used by the client.
- `javax.jms` vs `jakarta.jms` errors: ensure the API namespace and client artifact match.

Official guide:
https://docs.redhat.com/en/documentation/red_hat_amq_broker/7.14/html-single/configuring_amq_broker/index#assembly-br-securing-brokers_configuring
